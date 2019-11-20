package com.example.registracija;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class Registracija extends AppCompatActivity {

    ImageView imageView;
    TextView email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registracija);

        //imageView = findViewById(R.id.profile_image);
        imageView = findViewById(R.id.profile_image);
        email = findViewById(R.id.tvMail);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);

        if (acct != null) {
            String personEmail = acct.getEmail();
            Uri personPhoto = acct.getPhotoUrl();


            email.setText(personEmail);
            Glide.with(this).load(String.valueOf(personPhoto)).into(imageView);
    }
    }
}
