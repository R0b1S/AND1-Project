package com.example.gimmedagame.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gimmedagame.R;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int AUTH_REQ_CODE = 42;

    Button btn_sign_out;
    Button btn_sign_in;
    Button btn_continue;
    TextView text_welcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_sign_out = (Button) findViewById(R.id.btn_sign_out);
        btn_continue = (Button) findViewById(R.id.btn_continue);
        btn_sign_in = (Button) findViewById(R.id.btn_sign_in);
        text_welcome = (TextView) findViewById(R.id.text_welcome);

    }

    @Override
    protected void onStart(){
        super.onStart();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        // if there s logged user
        if(user == null){
            // set buttons visibility
            btn_sign_in.setVisibility(View.VISIBLE);
            btn_continue.setVisibility(View.INVISIBLE);
            btn_sign_out.setVisibility(View.INVISIBLE);
            //Toast.makeText(this, "NOONE LOGGED", Toast.LENGTH_LONG).show();
            text_welcome.setText("Welcome \n Please Sign in!");
        } else {
            // set buttons visibility
            btn_sign_in.setVisibility(View.INVISIBLE);
            btn_continue.setVisibility(View.VISIBLE);
            btn_sign_out.setVisibility(View.VISIBLE);
            //Toast.makeText(this, ""+FirebaseAuth.getInstance().getCurrentUser().getEmail(), Toast.LENGTH_LONG).show();
            text_welcome.setText("Welcome back \n" + user.getDisplayName());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == AUTH_REQ_CODE){
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if(resultCode == RESULT_OK){
                // get user
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Toast.makeText(this, ""+user.getEmail(), Toast.LENGTH_SHORT).show();

                // continue to lobby
                Intent intent = new Intent(this, PostersActivity.class);
                startActivity(intent);


                // set buttons visibility
                btn_sign_in.setVisibility(View.INVISIBLE);
                btn_continue.setVisibility(View.VISIBLE);
                btn_sign_out.setVisibility(View.VISIBLE);
            }
        }
    }

    private void showSignInOptions(){

        // list all login providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build()
        );

        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                AUTH_REQ_CODE
        );
    }

    public void signIn(View v){
        showSignInOptions();
    }

    public void signOut(View v){
        AuthUI.getInstance()
                .signOut(MainActivity.this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        btn_sign_in.setVisibility(View.VISIBLE);
                        btn_continue.setVisibility(View.INVISIBLE);
                        btn_sign_out.setVisibility(View.INVISIBLE);
                        showSignInOptions();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void continueToLobby(View v){
        Intent intent = new Intent(this, PostersActivity.class);
        startActivity(intent);
    }

}
