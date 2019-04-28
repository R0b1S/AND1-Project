package com.example.gimmedagame.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.gimmedagame.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class PosterOverviewActivity extends AppCompatActivity {

    private TextView title;
    private TextView desc;
    private TextView date;
    private TextView user;
    private TextView phone;
    private TextView email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poster_overview);

        Bundle bundle = getIntent().getExtras();
        String id = bundle.getString("id");

        Log.d("test1", ""+id);

        title = findViewById(R.id.text_view_title);
        desc = findViewById(R.id.text_view_desc);
        date = findViewById(R.id.text_view_date);
        user = findViewById(R.id.text_view_username);
        phone = findViewById(R.id.text_view_phone);
        email = findViewById(R.id.text_view_email);

        // setup toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_poster_overview);
        toolbar.setTitle("Poster");
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        DocumentReference doc = FirebaseFirestore.getInstance().collection("posters").document(id);
        doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    Map<String, Object> doc = document.getData();


                    Log.d("test2", ""+doc.get("title").toString());
                    Log.d("test3", ""+doc.get("userName").toString());

                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                    title.setText(doc.get("title").toString());
                    desc.setText(doc.get("description").toString());
                    user.setText(doc.get("userName").toString());
                    date.setText(sdf.format(doc.get("postDate")));
                    phone.setText(doc.get("phone").toString());
                    email.setText(doc.get("email").toString());

                }
            }
        });

    }
}
