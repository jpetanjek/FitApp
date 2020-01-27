package com.example.fitapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.fitapp.viewmodels.NamirniceObrokaViewModel;
import com.example.repository.NamirnicaDAL;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Glavni_Izbornik extends AppCompatActivity {
    private Date trenutniDatum;
    NamirniceObrokaViewModel namirniceObrokaViewModel;

    private String dohvatiStringDatuma(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        return sdf.format(trenutniDatum);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glavni__izbornik);
        trenutniDatum = new Date(System.currentTimeMillis());



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
                //startActivity(new Intent(Glavni_Izbornik.this,BarkodSkenerActivity.class));
                startActivity(new Intent(Glavni_Izbornik.this,AddFoodToMeal.class));
            }
        });

        Button btnExerciseSelection = findViewById(R.id.btnExcerciseSelection);
        btnExerciseSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Glavni_Izbornik.this,ExerciseSelection.class));
            }
        });

        Button btnStepChart = findViewById(R.id.stepChart);
        btnStepChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Glavni_Izbornik.this,RunningInstructor.class));
            }
        });

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

    @Override
    protected void onStart() {
        super.onStart();
        namirniceObrokaViewModel = ViewModelProviders.of(this).get(NamirniceObrokaViewModel.class);
        int ukupniBrojKalorija = namirniceObrokaViewModel.getUkupniBrojKalorija(dohvatiStringDatuma());
        TextView brojKalorija = findViewById(R.id.actual_food);
        brojKalorija.setText(String.valueOf(ukupniBrojKalorija));
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
