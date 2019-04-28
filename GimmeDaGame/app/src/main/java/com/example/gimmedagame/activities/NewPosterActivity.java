package com.example.gimmedagame.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.gimmedagame.R;
import com.example.gimmedagame.models.Poster;
import com.example.gimmedagame.persistence.PosterPersistence;

public class NewPosterActivity extends AppCompatActivity {

    PosterPersistence persistence;

    private EditText editTextTitle;
    private EditText editTextDesc;
    private EditText editTextPhone;
    private EditText editTextEmail;
    private EditText editTextLocation;
    private EditText editTextPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_poster);

        editTextTitle = (EditText) findViewById(R.id.edit_text_title);
        editTextDesc = (EditText)findViewById(R.id.edit_text_description);
        editTextPhone = (EditText)findViewById(R.id.edit_text_phone);
        editTextEmail = (EditText)findViewById(R.id.edit_text_email);
        editTextLocation = (EditText)findViewById(R.id.edit_text_location);
        editTextPrice = (EditText)findViewById(R.id.edit_text_price);

        // setup toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_add_poster);
        toolbar.setTitle("Add new poster");
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        // persistence
        persistence = new PosterPersistence();

    }

    public void addNewPoster(View v){
        // add toast message if title is empty

        persistence.insterPoster(
                new Poster(
                     editTextTitle.getText().toString(),
                     editTextPhone.getText().toString(),
                     editTextEmail.getText().toString(),
                     editTextLocation.getText().toString(),
                     Double.parseDouble(editTextPrice.getText().toString()),
                     editTextDesc.getText().toString()
                )
        );

        finish();

    }
}
