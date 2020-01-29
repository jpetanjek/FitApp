package com.example.fitapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import com.example.core.entities.AtributiKardioVjezbi;
import com.example.core.entities.Korisnik;
import com.example.core.entities.KorisnikVjezba;
import com.example.database.VjezbaDAO;
import com.example.fitapp.running.Akcelerometar;
import com.example.fitapp.running.GPSTrcanje;
import com.example.fitapp.viewmodels.AtributiKardioViewModel;
import com.example.repository.AtributiKardioVjezbiDAL;
import com.example.repository.KorisnikDAL;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import adapter.CurrentActivity;
import managers.RunningInterface;

//TODO
//Implementirati udaljenost
//Kada se prode zadana udaljenost - prekini vjezbu automatski

public class RunningInstructorV2 extends AppCompatActivity {
    private Chronometer chronometer;
    private long pauseOffset;
    private LocationManager lm;

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

    //bundle
    int idAtributKardio;
    int idVjezba;
    int idKorisnikVjezba;


    private RunningInterface modulTrcanja;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running_instructor_v2);

        CurrentActivity.setActivity(this);
        lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        if(lm.isProviderEnabled(LocationManager.GPS_PROVIDER)){

            int check = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
            if(check!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},42);
            }else{
                modulTrcanja = new GPSTrcanje(true,lm);
                modulTrcanja.update();
            }
        }else{
            modulTrcanja = new Akcelerometar();
        }

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
        idAtributKardio = getIntent().getExtras().getInt("idAtributiKardio");
        idVjezba = getIntent().getExtras().getInt("idVjezba");
        idKorisnikVjezba = getIntent().getExtras().getInt("idKorisnikVjezba");

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
                update.setKalorijaPotroseno(kardioViewModel.brojKalorija((int) (SystemClock.elapsedRealtime()-chronometer.getBase()),idVjezba));
                update.setTrajanje((int) (SystemClock.elapsedRealtime()-chronometer.getBase()));

                if(!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                    modulTrcanja.update();
                }
                Log.e("STEPS:",String.valueOf(modulTrcanja.getDistance()));
                update.setUdaljenostOtrcana(modulTrcanja.getDistance());

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

                if(atributiKardioVjezbi.getUdaljenostPlanirana()*1000<=atributiKardioVjezbi.getUdaljenostOtrcana()){
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
            pause.setVisibility(View.VISIBLE);
            start.setVisibility(View.INVISIBLE);
            stop.setVisibility(View.INVISIBLE);
        }
    }

    public void pauseChronometer() {
        if (running) {
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;
            pause.setVisibility(View.INVISIBLE);
            start.setVisibility(View.VISIBLE);
            stop.setVisibility(View.VISIBLE);
        }
    }

    public void resetChronometer() {
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;
        //prebaci se na novu aktivnost
        KorisnikVjezba korisnikVjezba = kardioViewModel.readById(idKorisnikVjezba);
        korisnikVjezba.setDatumVrijemePocetka(String.valueOf(Calendar.getInstance().getTime()));
        kardioViewModel.updateKorisnikVjezba(korisnikVjezba);

        Intent intent = new Intent(RunningInstructorV2.this, RunningInstructorV2.class);
        intent.putExtra("idAtributiKardio", idAtributKardio);
        intent.putExtra("idVjezba", idVjezba);
        intent.putExtra("idKorisnikVjezba", idKorisnikVjezba);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 42 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            modulTrcanja = new GPSTrcanje(true,lm);
            modulTrcanja.update();
        }
    }
}
