package com.example.gimmedagame.persistence;

import com.example.gimmedagame.models.Poster;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class PosterPersistence {

    private CollectionReference ref;

    public PosterPersistence(){
        ref = FirebaseFirestore.getInstance().collection("posters");
    }

    public void insterPoster(Poster poster){
        ref.add(poster);
    }

    public void deletePoster(DocumentReference poster) {
        poster.delete();
    }
}
