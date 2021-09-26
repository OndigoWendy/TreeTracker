package models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class NewsDetails implements Comparable<NewsDetails> {
    private String content;
    private String writtenBy;
    private int rating;
    private int id;
    private int departmentId; //will be used to connect Restaurant to Review (one-to-many)
    private long createdat;
    private String formattedCreatedAt;


    public NewsDetails(String content, String writtenBy, int rating, int departmentId) {
        this.content = content;
        this.writtenBy = writtenBy;
        this.rating = rating;
        this.departmentId = departmentId;
        this.createdat = System.currentTimeMillis();
        setFormattedCreatedAt(); //we'll make me in a minute
    }
    @Override
    public int compareTo(NewsDetails newsDetailsObject) {
        if (this.createdat < newsDetailsObject.createdat)
        {
            return -1; //this object was made earlier than the second object.
        }
        else if (this.createdat > newsDetailsObject.createdat){ //this object was made later than the second object
            return 1;
        }
        else {
            return 0; //they were made at the same time, which is very unlikely, but mathematically not impossible.
        }
    }


    public String getContent() {
        return content;
    }

    public String getWrittenBy() {
        return writtenBy;
    }

    public int getRating() {
        return rating;
    }

    public int getId() {
        return id;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public long getCreatedat() {
        return createdat;
    }

    public void setCreatedat() {
        this.createdat = System.currentTimeMillis(); // It'll become clear soon why we need this explicit setter
    }

    public String getFormattedCreatedAt(){
        Date date = new Date(createdat);
        String datePatternToUse = "MM/dd/yyyy @ K:mm a"; //see https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html
        SimpleDateFormat sdf = new SimpleDateFormat(datePatternToUse);
        return sdf.format(date);
    }

    public void setFormattedCreatedAt(){
        Date date = new Date(this.createdat);
        String datePatternToUse = "MM/dd/yyyy @ K:mm a";
        SimpleDateFormat sdf = new SimpleDateFormat(datePatternToUse);
        this.formattedCreatedAt = sdf.format(date);
    }
    public void setContent(String content) {
        this.content = content;
    }

    public void setWrittenBy(String writtenBy) {
        this.writtenBy = writtenBy;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NewsDetails)) return false;
        NewsDetails newsDetails = (NewsDetails) o;
        return rating == newsDetails.rating &&
                id == newsDetails.id &&
                departmentId == newsDetails.departmentId &&
                Objects.equals(content, newsDetails.content) &&
                Objects.equals(writtenBy, newsDetails.writtenBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content, writtenBy, rating, id, departmentId);
    }
}
