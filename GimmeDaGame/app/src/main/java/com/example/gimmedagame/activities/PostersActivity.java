package com.example.gimmedagame.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.gimmedagame.R;
import com.example.gimmedagame.models.Poster;
import com.example.gimmedagame.utils.PosterRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class PostersActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference ref = db.collection("posters");
    private PosterRecyclerAdapter posterAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posters);

        // setup toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_posters);
        toolbar.setTitle("Posters");
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        // recycler
        setupRecyclerView();


        // floating point
        FloatingActionButton addPosterBtn = findViewById(R.id.floating_add_poster) ;
        addPosterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PostersActivity.this, NewPosterActivity.class));
            }
        });

    }

    private void setupRecyclerView(){
        Query query = ref;

        FirestoreRecyclerOptions<Poster> options = new FirestoreRecyclerOptions.Builder<Poster>()
                .setQuery(query, Poster.class)
                .build();

        posterAdapter = new PosterRecyclerAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.poster_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(posterAdapter);

        // swipe
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                boolean result = posterAdapter.deleteItem(viewHolder.getAdapterPosition());
                if(result)
                    Toast.makeText(PostersActivity.this, "deleted successfuly", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(PostersActivity.this, "not deleted - this in not your poster", Toast.LENGTH_LONG).show();
            }
        }).attachToRecyclerView(recyclerView);

        // on item click
        posterAdapter.setOnItemClickListener(new PosterRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Poster poster = documentSnapshot.toObject(Poster.class);
                String id = documentSnapshot.getId();
                startActivity(new Intent(PostersActivity.this, PosterOverviewActivity.class).putExtra("id", id));
            }
        });

    }

    @Override
    protected void onStart(){
        super.onStart();
        posterAdapter.startListening();
    }

    @Override
    protected void onStop(){
        super.onStop();
        posterAdapter.stopListening();
    }
}
