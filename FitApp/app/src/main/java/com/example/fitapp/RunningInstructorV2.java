package com.example.fitapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import com.example.core.entities.AtributiKardioVjezbi;
import com.example.core.entities.Korisnik;
import com.example.database.VjezbaDAO;
import com.example.fitapp.viewmodels.AtributiKardioViewModel;
import com.example.repository.AtributiKardioVjezbiDAL;
import com.example.repository.KorisnikDAL;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//TODO
//Implementirati udaljenost
//Kada se prode zadana udaljenost - prekini vjezbu automatski

public class RunningInstructorV2 extends AppCompatActivity {
    private Chronometer chronometer;
    private long pauseOffset;

    //ViewModel
    private AtributiKardioViewModel kardioViewModel;
    private LiveData<AtributiKardioVjezbi> atributiKardioVjezbi;

    //Varijable stanja
    private boolean running;

    //UI varijable
    private Button start;
    private Button stop;
    private Button pause;
    private TextView kalorije;
    private TextView distance;
    private TextView pace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running_instructor_v2);

        //Inicijalizacija UI
        chronometer = findViewById(R.id.chronometer);
        chronometer.setFormat("%s");
        chronometer.setBase(SystemClock.elapsedRealtime());

        start = findViewById(R.id.start);
        stop = findViewById(R.id.stop);
        pause= findViewById(R.id.pause);

        kalorije = findViewById(R.id.kalorijeIspis);
        distance = findViewById(R.id.distance);
        pace = findViewById(R.id.pace);

        //dohvacanje iz bundle
        int idAtributKardio = getIntent().getExtras().getInt("idAtributiKardio");
        int idVjezbe= getIntent().getExtras().getInt("idVjezba");

        //Inicijalizacija ViewModel
        kardioViewModel = ViewModelProviders.of(this).get(AtributiKardioViewModel.class);
        atributiKardioVjezbi = kardioViewModel.ReadByIdLIVE(idAtributKardio);

        //Tick svaku sekundu
        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                //spremi preko viewmodela vrijeme
                AtributiKardioVjezbi update = AtributiKardioVjezbiDAL.ReadById(idAtributKardio,getApplicationContext());
                //sa korisnika izracun
                update.setKalorijaPotroseno(kardioViewModel.brojKalorija((int) (SystemClock.elapsedRealtime()-chronometer.getBase()),idVjezbe));
                update.setTrajanje((int) (SystemClock.elapsedRealtime()-chronometer.getBase()));


                kardioViewModel.update(update);
                //SA MIGAC!!!!!!!!!!!
                //update.setUdaljenostOtrcana();
            }
        });

        //ViewModel Updeta UI-sve osim vremena
        atributiKardioVjezbi.observe(this, new Observer<AtributiKardioVjezbi>() {
            @Override
            public void onChanged(AtributiKardioVjezbi atributiKardioVjezbi) {
                //update kalorije
                kalorije.setText(String.valueOf(atributiKardioVjezbi.getKalorijaPotroseno()));

                //update distance
                distance.setText(String.valueOf(atributiKardioVjezbi.getUdaljenostOtrcana()));

                //update pace - tempo km/h
                pace.setText(String.valueOf(atributiKardioVjezbi.getUdaljenostOtrcana()/((SystemClock.elapsedRealtime()-chronometer.getBase())/3600)));

                if(atributiKardioVjezbi.getUdaljenostPlanirana()*1000==atributiKardioVjezbi.getUdaljenostOtrcana()){
                    resetChronometer();
                }
            }
        });

        //Listeneri
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startChronometer();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetChronometer();
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseChronometer();
            }
        });

        startChronometer();
    }

    //Funkcije stanja
    public void startChronometer() {
        if (!running) {
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
            running = true;

        }
    }

    public void pauseChronometer() {
        if (running) {
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;
        }
    }

    public void resetChronometer() {
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;
        //prebaci se na novu aktivnost
    }
}
