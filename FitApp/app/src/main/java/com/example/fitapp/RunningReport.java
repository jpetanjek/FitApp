package com.example.fitapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextView;

import com.example.core.entities.AtributiKardioVjezbi;
import com.example.core.entities.KorisnikVjezba;
import com.example.fitapp.viewmodels.AtributiKardioViewModel;

import java.util.ArrayList;

public class RunningReport extends AppCompatActivity {

    //UI
    private Chronometer chronometer;
    private TextView kalorije;
    private TextView distance;
    private TextView pace;
    private TextView datumPocetak;
    private TextView datumKraj;
    private TextView pace2;
    private TextView distance2;

    //ViewModel
    private AtributiKardioViewModel kardioViewModel;
    private KorisnikVjezba korisnikVjezba;
    private LiveData<AtributiKardioVjezbi> atributiKardioVjezbi;

    //bundle
    int idAtributKardio;
    int idVjezba;
    int idKorisnikVjezba;
    long base;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running_report);


        Toolbar toolbar = findViewById(R.id.toolbarAddFood);
        setSupportActionBar(toolbar);

        View logoView = getToolbarLogoView(toolbar);
        logoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Intent i = new Intent(RunningReport.this, Glavni_Izbornik.class);
                    startActivity(i);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });


        kalorije = findViewById(R.id.kalorijeIspis);
        distance = findViewById(R.id.distance);
        pace = findViewById(R.id.pace);
        distance2 = findViewById(R.id.distance2);
        pace2 = findViewById(R.id.pace2);
        datumKraj = findViewById(R.id.datumKraj);
        datumPocetak = findViewById(R.id.datumPocetak);

        //dohvacanje iz bundle
        idAtributKardio = getIntent().getExtras().getInt("idAtributiKardio");
        idVjezba = getIntent().getExtras().getInt("idVjezba");
        idKorisnikVjezba = getIntent().getExtras().getInt("idKorisnikVjezba");
        base = getIntent().getExtras().getLong("base");

        //Inicijalizacija ViewModel
        kardioViewModel = ViewModelProviders.of(this).get(AtributiKardioViewModel.class);
        atributiKardioVjezbi = kardioViewModel.ReadByIdLIVE(idAtributKardio);

        korisnikVjezba = kardioViewModel.readById(idKorisnikVjezba);

        //ViewModel Updeta UI-sve osim vremena
        atributiKardioVjezbi.observe(this, new Observer<AtributiKardioVjezbi>() {
            @Override
            public void onChanged(AtributiKardioVjezbi atributiKardioVjezbi) {
                kalorije.setText(String.valueOf(atributiKardioVjezbi.getKalorijaPotroseno()));
                distance.setText(String.valueOf(atributiKardioVjezbi.getUdaljenostOtrcana()));
                pace.setText(String.valueOf((atributiKardioVjezbi.getUdaljenostOtrcana()/1000)/atributiKardioVjezbi.getTrajanje()));
                distance2.setText(String.valueOf(atributiKardioVjezbi.getUdaljenostOtrcana()));
                pace2.setText(String.valueOf((atributiKardioVjezbi.getUdaljenostOtrcana()/1000)/atributiKardioVjezbi.getTrajanje()));
                chronometer = findViewById(R.id.chronometer);
                chronometer.setFormat("%s");
                chronometer.setBase(base);
                datumKraj.setText(String.valueOf(korisnikVjezba.getDatumVrijemeKraja()));
                datumPocetak.setText(String.valueOf(korisnikVjezba.getDatumVrijemePocetka()));
            }
        });

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
}
