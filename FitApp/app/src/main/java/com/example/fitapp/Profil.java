package com.example.fitapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import org.w3c.dom.Text;

import java.util.concurrent.TimeoutException;

public class Profil extends AppCompatActivity {
    GoogleApiClient mGoogleApiClient;


    @Override
    protected void onStart() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleApiClient = new GoogleApiClient.Builder(this).addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();

        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_profil);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
            setSupportActionBar(toolbar);

            Button button = (Button) findViewById(R.id.button1);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        // ...
                        case R.id.button1:
                            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                                    new ResultCallback<Status>() {
                                        @Override
                                        public void onResult(@NonNull Status status) {
                                            Intent intent = new Intent(Profil.this, MainActivity.class);
                                            startActivity(intent);
                                        }
                                    }
                            );
                            break;
                        // ...
                    }

                }
            });
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();

        if(id==R.id.search){
            Toast.makeText(getApplicationContext(), "You can click search", Toast.LENGTH_SHORT).show();
        }
        else if(id==R.id.settings){
            Toast.makeText(getApplicationContext(), "You can click settings", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}
