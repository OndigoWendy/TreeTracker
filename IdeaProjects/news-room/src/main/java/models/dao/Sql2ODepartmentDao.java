package models.dao;

import models.DepartmentNews;
import models.Department;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;

public class Sql2ODepartmentDao implements DepartmentDao { //don't forget to shake hands with your interface!
    private final Sql2o sql2o;
    public Sql2ODepartmentDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(Department department) {

        String sql = "INSERT INTO departments + " (name, address, zipcode, phone, website, email) VALUES (:name, :address, :zipcode, :phone, :website, :email)";
        try (Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql, true)
                    .bind(department)
                    .executeUpdate()
                    .getKey();
            department.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<Department> getAll() {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM departments")
                    .executeAndFetch(Department.class);
        }
    }

    @Override
    public Department findById(int id) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM departments WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Department.class);
        }
    }

    @Override
    public void update(int id, String newName, String newAddress, String newZipcode, String newPhone, String newWebsite, String newEmail) {
        String sql = "UPDATE departments SET (name, address, zipcode, phone, website, email) = (:name, :address, :zipcode, :phone, :website, :email) WHERE id=:id"; //CHECK!!!
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("name", newName)
                    .addParameter("address", newAddress)
                    .addParameter("zipcode", newZipcode)
                    .addParameter("phone", newPhone)
                    .addParameter("website", newWebsite)
                    .addParameter("email", newEmail)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from departments WHERE id = :id"; //raw sql
        String deleteJoin = "DELETE from restaurants_foodtypes WHERE restaurantid = :restaurantId";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
            con.createQuery(deleteJoin)
                    .addParameter("restaurantId", id)
                    .executeUpdate();

        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void clearAll() {
        String sql = "DELETE from departments";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql).executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void addRestaurantToFoodtype(Department department, DepartmentNews departmentNews){
        String sql = "INSERT INTO departments_departmentnews (restaurantid, foodtypeid) VALUES (:restaurantId, :foodtypeId)";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("restaurantId", department.getId())
                    .addParameter("foodtypeId", departmentNews.getId())
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public List<DepartmentNews> getAllFoodtypesByRestaurant(int restaurantId){
        List<DepartmentNews> departmentNews = new ArrayList(); //empty list
        String joinQuery = "SELECT foodtypeid FROM departments_departmentnews WHERE restaurantid = :restaurantId";

        try (Connection con = sql2o.open()) {
            List<Integer> allFoodtypesIds = con.createQuery(joinQuery)
                    .addParameter("restaurantId", restaurantId)
                    .executeAndFetch(Integer.class);
            for (Integer foodId : allFoodtypesIds){
                String foodtypeQuery = "SELECT * FROM foodtypes WHERE id = :foodtypeId";
                departmentNews.add(
                        con.createQuery(foodtypeQuery)
                                .addParameter("foodtypeId", foodId)
                                .executeAndFetchFirst(DepartmentNews.class));
            }
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
        return departmentNews;
    }
}
