package com.example.fitapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;



public class Glavni_Izbornik extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glavni__izbornik);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        View logoView = getToolbarLogoView(toolbar);
        logoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Intent i = new Intent(Glavni_Izbornik.this, Profil.class);
                    startActivity(i);
                }
                catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }
        });


        Button btnFoodDiary = findViewById(R.id.btnFoodDiary);
        btnFoodDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Glavni_Izbornik.this, FoodDiary.class);
                startActivity(intent);

            }
        });
        Button btnBarkodSkener = findViewById(R.id.btnBarkodSkener);
        btnBarkodSkener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Glavni_Izbornik.this,BarkodSkenerActivity.class));
            }
        });

        Button btnExerciseSelection = findViewById(R.id.btnExcerciseSelection);
        btnExerciseSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Glavni_Izbornik.this,ExerciseSelection.class));
            }
        });

        /*
        Button button = findViewById(R.id.registracija);

/*
        Button button = (Button) findViewById(R.id.registracija);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.registracija:

                        Intent intent = new Intent(Glavni_Izbornik.this, Registracija.class);
                        startActivity(intent);
                        break;
                }

            }
        });

         */
    }

    public static View getToolbarLogoView(Toolbar toolbar){
        boolean hadContentDescription = android.text.TextUtils.isEmpty(toolbar.getLogoDescription());
        String contentDescription = String.valueOf(!hadContentDescription ? toolbar.getLogoDescription() : "logoContentDescription");
        toolbar.setLogoDescription(contentDescription);
        ArrayList<View> potentialViews = new ArrayList<View>();
        toolbar.findViewsWithText(potentialViews,contentDescription, View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION);
        View logoIcon = null;

        if(potentialViews.size() > 0){
            logoIcon = potentialViews.get(0);
        }

        if(hadContentDescription)
            toolbar.setLogoDescription(null);

        return logoIcon;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }


    /*
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
     */
}
