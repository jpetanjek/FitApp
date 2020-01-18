package com.example.fitapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import com.example.core.entities.AtributiKardioVjezbi;
import com.example.core.entities.KorisnikVjezba;
import com.example.database.VjezbaDAO;
import com.example.fitapp.viewmodels.AtributiKardioViewModel;

public class RunningConfiguration extends AppCompatActivity {
    private AtributiKardioViewModel kardioViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running_configuration);

        kardioViewModel = ViewModelProviders.of(this).get(kardioViewModel.getClass());

        //vjezbaId dobaviti iz bundle
        int vjezbaId=3;
        KorisnikVjezba korisnikVjezba = kardioViewModel.createEmptyKorisnikVjezba(vjezbaId);


        kardioViewModel.createEmpty(korisnikVjezba.getId()).observe(this, new Observer<AtributiKardioVjezbi>() {
            @Override
            public void onChanged(AtributiKardioVjezbi atributiKardioVjezbi) {
                System.out.println(atributiKardioVjezbi.getId());
                System.out.println(atributiKardioVjezbi.getKorisnikVjezbaId());
                System.out.println(atributiKardioVjezbi.getKalorijaPotroseno());
                System.out.println(atributiKardioVjezbi.getKorisnikVjezbaId());
                System.out.println(atributiKardioVjezbi.getTrajanje());
                System.out.println(atributiKardioVjezbi.getUdaljenostOtrcana());
                System.out.println(atributiKardioVjezbi.getudaljenostPlanirana());
            }
        });
    }
}
