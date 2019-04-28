package com.example.gimmedagame.utils;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gimmedagame.R;
import com.example.gimmedagame.models.Poster;
import com.example.gimmedagame.persistence.PosterPersistence;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.text.SimpleDateFormat;

public class PosterRecyclerAdapter extends FirestoreRecyclerAdapter<Poster, PosterRecyclerAdapter.PosterHolder> {
    private OnItemClickListener listener;


    public PosterRecyclerAdapter(@NonNull FirestoreRecyclerOptions<Poster> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull PosterHolder posterHolder, int i, @NonNull Poster poster) {
        posterHolder.textViewTitle.setText(poster.getTitle());
        posterHolder.textViewDescrition.setText(poster.getDescription());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        posterHolder.textViewDate.setText(sdf.format(poster.getPostDate()));

    }

    @NonNull
    @Override
    public PosterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.poster_item,
                parent, false);

        return new PosterHolder(v);
    }

    public boolean deleteItem(int position){
        DocumentReference ref = getSnapshots().getSnapshot(position).getReference();

        if(!(getSnapshots().getSnapshot(position).getData().get("userName").equals(FirebaseAuth.getInstance().getCurrentUser().getDisplayName())))
            return false;
        new PosterPersistence().deletePoster(ref);
        return true;
    }

    class PosterHolder extends RecyclerView.ViewHolder{

        TextView textViewTitle;
        TextView textViewDescrition;
        TextView textViewDate;

        public PosterHolder(View itemView){
            super(itemView);

            textViewDescrition = itemView.findViewById(R.id.text_view_description);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDate = itemView.findViewById(R.id.text_view_date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION && listener != null){
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

}
