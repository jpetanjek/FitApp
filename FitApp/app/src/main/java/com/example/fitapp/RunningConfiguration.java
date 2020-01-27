package com.example.fitapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
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

        kardioViewModel = ViewModelProviders.of(this).get(AtributiKardioViewModel.class);


        //vjezbaId je 1 za trcanje
        int vjezbaId=1;
        KorisnikVjezba korisnikVjezba = kardioViewModel.createEmptyKorisnikVjezba(vjezbaId);

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
            }
        });
    }
}
