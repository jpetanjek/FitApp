package com.example.fitapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import androidx.appcompat.widget.Toolbar;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;


import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.core.entities.Korisnik;
import com.example.core.entities.Namirnica;
import com.example.core.entities.NamirniceObroka;
import com.example.database.MyDatabase;
import com.example.fitapp.adapters.NamirniceObrokaAdapter;
import com.example.fitapp.viewmodels.NamirniceObrokaViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FoodDiary extends AppCompatActivity {

    private NamirniceObrokaViewModel namirniceDoruckaViewModel;
    private NamirniceObrokaViewModel namirniceRuckaViewModel;
    private NamirniceObrokaViewModel namirniceVecereViewModel;
    private NamirniceObrokaViewModel namirniceSnackViewModel;
    private Date trenutniDatum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_diary);

        trenutniDatum = new Date(System.currentTimeMillis());

        final TextView tvDate = findViewById(R.id.tvDate);
        Button btnDateDecrement = findViewById(R.id.btnDateDecrement);
        Button btnDateIncrement = findViewById(R.id.btnDateIncrement);

        tvDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                popuniNamirniceObroka(); // Nakon promjene datuma se postavljaju namirnice u obroku za taj dan
            }
        });

        btnDateDecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Smanji trenutniDatum za 1
                promijeniVrijednostDatumaZaBrojDana(-1);
                tvDate.setText(dohvatiStringDatuma()); // Postavlja se vrijednost tvDate, što triggera tvDate.afterTextChanged
            }
        });

        btnDateIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Povećaj trenutniDatum za 1
                promijeniVrijednostDatumaZaBrojDana(1); // Postavlja se vrijednost tvDate, što triggera tvDate.afterTextChanged
                tvDate.setText(dohvatiStringDatuma());
            }
        });


        tvDate.setText(dohvatiStringDatuma()); // Inicijalno postavljanje datuma (prilikom prvog učitavanja), triggera tv.Date.afterTextChanged
        MockUp();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        View logoView = getToolbarLogoView(toolbar);
        logoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Intent i = new Intent(FoodDiary.this, Glavni_Izbornik.class);
                    startActivity(i);
                }
                catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }
        });
    }

    private void promijeniVrijednostDatumaZaBrojDana(int brojDana){
        Calendar c = Calendar.getInstance();
        c.setTime(trenutniDatum);
        c.add(Calendar.DATE, brojDana);
        trenutniDatum = c.getTime();
    }

    private String dohvatiStringDatuma(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        return sdf.format(trenutniDatum);
    }

    // Postavljanje recycleViewa i NamirniceObrokAdaptera na svaku listu posebno (doručak, ručak, večeru i snack)
    public void popuniNamirniceObroka(){
        popuniNamirniceDorucka();
        popuniNamirniceRucka();
        popuniNamirniceVecere();
        popuniNamirniceSnacka();
    }

    public void popuniNamirniceDorucka(){
        // Doručak
        RecyclerView recyclerViewBreakfast = findViewById(R.id.recycleviewBreakfast);
        recyclerViewBreakfast.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewBreakfast.setHasFixedSize(true);

        final NamirniceObrokaAdapter adapterBreakfast = new NamirniceObrokaAdapter();
        recyclerViewBreakfast.setAdapter(adapterBreakfast);
        adapterBreakfast.setContext(this);

        namirniceDoruckaViewModel = ViewModelProviders.of(this).get(NamirniceObrokaViewModel.class);
        namirniceDoruckaViewModel.getAllNamirniceObroka("Breakfast", dohvatiStringDatuma()).observe(this, new Observer<List<NamirniceObroka>>() {
            @Override
            public void onChanged(List<NamirniceObroka> namirniceObrokas) {
                adapterBreakfast.setNamirnicas(namirniceObrokas);
            }
        });

        // Swipe to delete
        makeItemErasable(recyclerViewBreakfast, namirniceDoruckaViewModel, adapterBreakfast);
    }

    public void popuniNamirniceRucka(){
        //Ručak
        RecyclerView recyclerViewLunch = findViewById(R.id.recycleviewLunch);
        recyclerViewLunch.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewLunch.setHasFixedSize(true);

        final NamirniceObrokaAdapter adapterLunch = new NamirniceObrokaAdapter();
        recyclerViewLunch.setAdapter(adapterLunch);
        adapterLunch.setContext(this);// Koristiti DAL (obrisati)

        namirniceRuckaViewModel = ViewModelProviders.of(this).get(NamirniceObrokaViewModel.class);
        namirniceRuckaViewModel.getAllNamirniceObroka("Lunch", dohvatiStringDatuma()).observe(this, new Observer<List<NamirniceObroka>>() {
            @Override
            public void onChanged(List<NamirniceObroka> namirniceObrokas) {
                adapterLunch.setNamirnicas(namirniceObrokas);
            }
        });

        // Swipe to delete
        makeItemErasable(recyclerViewLunch, namirniceRuckaViewModel, adapterLunch);
    }

    public void popuniNamirniceVecere(){
        // Večera
        RecyclerView recycleviewDinner = findViewById(R.id.recycleviewDinner);
        recycleviewDinner.setLayoutManager(new LinearLayoutManager(this));
        recycleviewDinner.setHasFixedSize(true);

        final NamirniceObrokaAdapter adapterDinner = new NamirniceObrokaAdapter();
        recycleviewDinner.setAdapter(adapterDinner);
        adapterDinner.setContext(this);

        namirniceVecereViewModel = ViewModelProviders.of(this).get(NamirniceObrokaViewModel.class);
        namirniceVecereViewModel.getAllNamirniceObroka("Dinner", dohvatiStringDatuma()).observe(this, new Observer<List<NamirniceObroka>>() {
            @Override
            public void onChanged(List<NamirniceObroka> namirniceObrokas) {
                adapterDinner.setNamirnicas(namirniceObrokas);
            }
        });

        // Swipe to delete
        makeItemErasable(recycleviewDinner, namirniceVecereViewModel, adapterDinner);
    }

    public void popuniNamirniceSnacka(){
        //Snack
        RecyclerView recycleviewSnack = findViewById(R.id.recycleviewSnack);
        recycleviewSnack.setLayoutManager(new LinearLayoutManager(this));
        recycleviewSnack.setHasFixedSize(true);

        final NamirniceObrokaAdapter adapterSnack = new NamirniceObrokaAdapter();
        recycleviewSnack.setAdapter(adapterSnack);
        adapterSnack.setContext(this);

        namirniceSnackViewModel = ViewModelProviders.of(this).get(NamirniceObrokaViewModel.class);
        namirniceSnackViewModel.getAllNamirniceObroka("Snack", dohvatiStringDatuma()).observe(this, new Observer<List<NamirniceObroka>>() {
            @Override
            public void onChanged(List<NamirniceObroka> namirniceObrokas) {
                adapterSnack.setNamirnicas(namirniceObrokas);
            }
        });

        // Swipe to delete
        makeItemErasable(recycleviewSnack, namirniceSnackViewModel, adapterSnack);
    }

    public void makeItemErasable(RecyclerView recyclerView, final NamirniceObrokaViewModel viewModel, final NamirniceObrokaAdapter adapter){

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                viewModel.delete(adapter.getNamirnicaObrokaAt(viewHolder.getAdapterPosition()));
                Toast.makeText(FoodDiary.this, "Namirnica obroka obrisana.", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);
    }


    public void MockUp(){
        Korisnik korisnik = MyDatabase.getInstance(this).getKorisnikDAO().dohvatiKorisnika();
        List<Namirnica> namirnice = MyDatabase.getInstance(this).getNamirnicaDAO().dohvatiSveNamirnice();

        NamirniceObroka namirniceObroka = new NamirniceObroka();
        namirniceObroka.setIdKorisnik(korisnik.getId());
        namirniceObroka.setIdNamirnica(namirnice.get(0).getId());
        namirniceObroka.setObrok("Breakfast");
        namirniceObroka.setDatum("08.12.2019");
        MyDatabase.getInstance(this).getNamirnicaDAO().unosNamirniceObroka(namirniceObroka);

        namirniceObroka = new NamirniceObroka();
        namirniceObroka.setIdKorisnik(korisnik.getId());
        namirniceObroka.setIdNamirnica(namirnice.get(1).getId());
        namirniceObroka.setObrok("Dinner");
        namirniceObroka.setDatum("08.12.2019");
        MyDatabase.getInstance(this).getNamirnicaDAO().unosNamirniceObroka(namirniceObroka);

        namirniceObroka = new NamirniceObroka();
        namirniceObroka.setIdKorisnik(korisnik.getId());
        namirniceObroka.setIdNamirnica(namirnice.get(2).getId());
        namirniceObroka.setObrok("Snack");
        namirniceObroka.setDatum("08.12.2019");
        MyDatabase.getInstance(this).getNamirnicaDAO().unosNamirniceObroka(namirniceObroka);

        namirniceObroka = new NamirniceObroka();
        namirniceObroka.setIdKorisnik(korisnik.getId());
        namirniceObroka.setIdNamirnica(namirnice.get(3).getId());
        namirniceObroka.setObrok("Lunch");
        namirniceObroka.setDatum("08.12.2019");
        MyDatabase.getInstance(this).getNamirnicaDAO().unosNamirniceObroka(namirniceObroka);

        namirniceObroka = new NamirniceObroka();
        namirniceObroka.setIdKorisnik(korisnik.getId());
        namirniceObroka.setIdNamirnica(namirnice.get(4).getId());
        namirniceObroka.setObrok("Dinner");
        namirniceObroka.setDatum("08.12.2019");
        MyDatabase.getInstance(this).getNamirnicaDAO().unosNamirniceObroka(namirniceObroka);

        namirniceObroka = new NamirniceObroka();
        namirniceObroka.setIdKorisnik(korisnik.getId());
        namirniceObroka.setIdNamirnica(namirnice.get(4).getId());
        namirniceObroka.setObrok("Dinner");
        namirniceObroka.setDatum("10.12.2019");
        MyDatabase.getInstance(this).getNamirnicaDAO().unosNamirniceObroka(namirniceObroka);

        namirniceObroka = new NamirniceObroka();
        namirniceObroka.setIdKorisnik(korisnik.getId());
        namirniceObroka.setIdNamirnica(namirnice.get(4).getId());
        namirniceObroka.setObrok("Dinner");
        namirniceObroka.setDatum("09.12.2019");
        MyDatabase.getInstance(this).getNamirnicaDAO().unosNamirniceObroka(namirniceObroka);

        namirniceObroka = new NamirniceObroka();
        namirniceObroka.setIdKorisnik(korisnik.getId());
        namirniceObroka.setIdNamirnica(namirnice.get(3).getId());
        namirniceObroka.setObrok("Dinner");
        namirniceObroka.setDatum("11.12.2019");
        MyDatabase.getInstance(this).getNamirnicaDAO().unosNamirniceObroka(namirniceObroka);

        namirniceObroka = new NamirniceObroka();
        namirniceObroka.setIdKorisnik(korisnik.getId());
        namirniceObroka.setIdNamirnica(namirnice.get(2).getId());
        namirniceObroka.setObrok("Snack");
        namirniceObroka.setDatum("10.12.2019");
        MyDatabase.getInstance(this).getNamirnicaDAO().unosNamirniceObroka(namirniceObroka);
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
