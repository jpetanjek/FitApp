package com.example.fitapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.core.entities.AtributiKardioVjezbi;
import com.example.core.entities.KorisnikVjezba;
import com.example.database.VjezbaDAO;
import com.example.fitapp.viewmodels.AtributiKardioViewModel;
import com.example.repository.AtributiKardioVjezbiDAL;
import com.example.repository.NamirnicaDAL;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import adapter.CurrentActivity;

public class RunningConfiguration extends AppCompatActivity {
    private AtributiKardioViewModel kardioViewModel;
    private LiveData<AtributiKardioVjezbi> atributiKardioVjezbi;

    private EditText udaljenostPlan;
    private Button plus;
    private Button minus;
    private Button start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running_configuration);

        udaljenostPlan = findViewById(R.id.unosUdaljenosti);
        plus = findViewById(R.id.plus);
        minus = findViewById(R.id.minus);
        start = findViewById(R.id.startButton);

        //bundle za id vjezbe
        int idVjezba = getIntent().getExtras().getInt("idVjezbe");

        kardioViewModel = ViewModelProviders.of(this).get(AtributiKardioViewModel.class);


        //vjezbaId je 2 za trcanje
        //int vjezbaId=2;
        KorisnikVjezba korisnikVjezba = kardioViewModel.createEmptyKorisnikVjezba(idVjezba);

        atributiKardioVjezbi = kardioViewModel.createEmpty(korisnikVjezba.getId());


        atributiKardioVjezbi.observe(this, new Observer<AtributiKardioVjezbi>() {
            @Override
            public void onChanged(AtributiKardioVjezbi atributiKardioVjezbi) {
                System.out.println("Atributi Kardio Vjezbe");
                System.out.println(atributiKardioVjezbi.getId());
                System.out.println(atributiKardioVjezbi.getKorisnikVjezbaId());
                System.out.println(atributiKardioVjezbi.getKalorijaPotroseno());
                System.out.println(atributiKardioVjezbi.getTrajanje());
                System.out.println(atributiKardioVjezbi.getUdaljenostOtrcana());
                System.out.println(atributiKardioVjezbi.getUdaljenostPlanirana());

                udaljenostPlan.setText(String.valueOf(atributiKardioVjezbi.getUdaljenostPlanirana()));

            }
        });

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AtributiKardioVjezbi update = AtributiKardioVjezbiDAL.ReadById(atributiKardioVjezbi.getValue().getId(),getApplicationContext());
                update.setUdaljenostPlanirana(atributiKardioVjezbi.getValue().getUdaljenostPlanirana()+1);
                AtributiKardioVjezbiDAL.Update(getApplicationContext(),update);
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AtributiKardioVjezbi update = AtributiKardioVjezbiDAL.ReadById(atributiKardioVjezbi.getValue().getId(),getApplicationContext());
                update.setUdaljenostPlanirana(atributiKardioVjezbi.getValue().getUdaljenostPlanirana()-1);
                AtributiKardioVjezbiDAL.Update(getApplicationContext(),update);
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                korisnikVjezba.setDatumVrijemePocetka(String.valueOf(Calendar.getInstance().getTime()));

                System.out.println("SDF PRINT:");
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                String datum = sdf.format(new Date(System.currentTimeMillis()));
                System.out.println(datum);


                korisnikVjezba.setDatumPocetka(datum);
                kardioViewModel.updateKorisnikVjezba(korisnikVjezba);


                Intent intent = new Intent(RunningConfiguration.this, RunningInstructorV2.class);
                intent.putExtra("idAtributiKardio", atributiKardioVjezbi.getValue().getId());
                intent.putExtra("idVjezba", idVjezba);
                intent.putExtra("idKorisnikVjezba", korisnikVjezba.getId());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }
}
