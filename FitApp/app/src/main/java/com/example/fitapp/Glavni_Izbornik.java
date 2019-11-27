package com.example.fitapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

public class Glavni_Izbornik extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glavni__izbornik);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.findViewById(R.id.toolbar).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                try{
                    Intent i = new Intent(Glavni_Izbornik.this, Profil.class);
                    startActivity(i);
                }
                catch (Exception e){
                    System.out.println(e.getMessage());
                }

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
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
