package com.moringaschool.treecordbook.trees;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.moringaschool.treecordbook.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.findBooksButton)
    Button mFindRestaurantsButton;
    @BindView(R.id.id_city)
    TextView mAppNameTextView;
    @BindView(R.id.savedBooksButton) Button mSavedRestaurantsButton;
    @BindView(R.id.CatalogButton) Button mCatalogButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        mFindRestaurantsButton.setOnClickListener(this);
        mSavedRestaurantsButton.setOnClickListener(this);
        mCatalogButton.setOnClickListener(this);
    }
    @Override
    public void onClick(View v){
        if (v == mFindRestaurantsButton) {
            Intent intent = new Intent(HomeActivity.this, CitySearch.class);
            startActivity(intent);
        }
        if (v == mSavedRestaurantsButton) {
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(intent);
        }

        if (v == mCatalogButton) {
            Intent intent = new Intent(HomeActivity.this, CatalogActivity.class);
            startActivity(intent);
        }
    }

}
