package com.example.fitapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;

import com.example.core.entities.KorisnikVjezba;

import java.util.ArrayList;
import java.util.List;

public class ExerciseInstructor extends AppCompatActivity {
    private String nazivVjezbe;
    private List<Integer> listaKorisnikVjezbeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_instructor);

        nazivVjezbe = getIntent().getExtras().getString("nazivVjezbe");
        Toolbar toolbar = findViewById(R.id.toolbar1);
        //toolbar.setTitle(toolbar.getTitle() + nazivVjezbe);

        listaKorisnikVjezbeId = getIntent().getIntegerArrayListExtra("listaKorisnikVjezbeId");
        String naziv = toolbar.getTitle().toString();
        for(int item: listaKorisnikVjezbeId){
            naziv = naziv.concat( " id:");
            naziv = naziv.concat(Integer.toString(item));
            Log.v("UNESENO2", Integer.toString(item));

        }
        toolbar.setTitle(naziv);
    }
}
