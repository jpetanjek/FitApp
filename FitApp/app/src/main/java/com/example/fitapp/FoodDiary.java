package com.example.fitapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.Observer;

import androidx.appcompat.widget.Toolbar;

import androidx.lifecycle.ViewModelProviders;
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
import com.example.repository.NamirnicaDAL;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FoodDiary extends AppCompatActivity {

    private NamirniceObrokaViewModel namirniceObrokaViewModel;
    private Date trenutniDatum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_diary);

        namirniceObrokaViewModel = ViewModelProviders.of(this).get(NamirniceObrokaViewModel.class);

        final RecyclerView recyclerViewBreakfast = findViewById(R.id.recycleviewBreakfast);
        final NamirniceObrokaAdapter adapterBreakfast = new NamirniceObrokaAdapter();
        postaviRecycleViewe(recyclerViewBreakfast, adapterBreakfast);

        final RecyclerView recyclerViewLunch = findViewById(R.id.recycleviewLunch);
        final NamirniceObrokaAdapter adapterLunch = new NamirniceObrokaAdapter();
        postaviRecycleViewe(recyclerViewLunch, adapterLunch);

        final RecyclerView recycleviewDinner = findViewById(R.id.recycleviewDinner);
        final NamirniceObrokaAdapter adapterDinner = new NamirniceObrokaAdapter();
        postaviRecycleViewe(recycleviewDinner, adapterDinner);

        final RecyclerView recycleviewSnack = findViewById(R.id.recycleviewSnack);
        final NamirniceObrokaAdapter adapterSnack = new NamirniceObrokaAdapter();
        postaviRecycleViewe(recycleviewSnack, adapterSnack);

        trenutniDatum = new Date(System.currentTimeMillis());

        final TextView tvDate = findViewById(R.id.tvDate);
        Button btnDateDecrement = findViewById(R.id.btnDateDecrement);
        Button btnDateIncrement = findViewById(R.id.btnDateIncrement);
        dodajTrigereNaGumbove();
        IspisiNamirniceObroka();
        tvDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // Nakon promjene datuma se postavljaju namirnice u obroku za taj dan
                popuniNamirniceDorucka(recyclerViewBreakfast, adapterBreakfast);
                popuniNamirniceRucka(recyclerViewLunch, adapterLunch);
                popuniNamirniceVecere(recycleviewDinner, adapterDinner);
                popuniNamirniceSnacka(recycleviewSnack, adapterSnack);
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
        //MockUp();

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

    private void postaviRecycleViewe(RecyclerView recyclerView, NamirniceObrokaAdapter adapter){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(false);
        recyclerView.setAdapter(adapter);
        adapter.setContext(this);
    }

    private void IspisiNamirniceObroka(){
        List<NamirniceObroka> namirniceObrokas = new ArrayList<>();
        namirniceObrokas = MyDatabase.getInstance(this).getNamirnicaDAO().dohvatiSveNamirniceObroka();
        for(int i=0;i<namirniceObrokas.size();i++){
            System.out.println(namirniceObrokas.get(i).getIdNamirnica());
        }

    }
    private void dodajTrigereNaGumbove(){
        Button btnAddDorucak = findViewById(R.id.btnAddBreakfast);
        Button btnAddRucak = findViewById(R.id.btnAddLunch);
        Button btnAddVecera = findViewById(R.id.btnAddDinner);
        Button btnAddUzina = findViewById(R.id.btnAddSnack);

        //bundle ("Datum" => datum, "Obrok" => obrok)
        btnAddDorucak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                Bundle bundle = new Bundle();
                bundle.putString("Obrok","Breakfast");
                bundle.putString("Datum",dateFormat.format(trenutniDatum).toString());
                Intent i = new Intent(FoodDiary.this,AddFoodToMeal.class);
                i.putExtras(bundle);
                startActivity(i);
            }
        });

        btnAddRucak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                Bundle bundle = new Bundle();
                bundle.putString("Obrok","Lunch");
                bundle.putString("Datum",dateFormat.format(trenutniDatum).toString());
                Intent i = new Intent(FoodDiary.this,AddFoodToMeal.class);
                i.putExtras(bundle);
                startActivity(i);
            }
        });

        btnAddVecera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                Bundle bundle = new Bundle();
                bundle.putString("Obrok","Dinner");
                bundle.putString("Datum",dateFormat.format(trenutniDatum).toString());
                Intent i = new Intent(FoodDiary.this,AddFoodToMeal.class);
                i.putExtras(bundle);
                startActivity(i);
            }
        });
        btnAddUzina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                Bundle bundle = new Bundle();
                bundle.putString("Obrok","Snack");
                bundle.putString("Datum",dateFormat.format(trenutniDatum).toString());
                Intent i = new Intent(FoodDiary.this,AddFoodToMeal.class);
                i.putExtras(bundle);
                startActivity(i);
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

    public void popuniNamirniceDorucka(RecyclerView recyclerViewBreakfast, final NamirniceObrokaAdapter adapterBreakfast){
        // Doručak
        List<NamirniceObroka> namirniceObrokas = namirniceObrokaViewModel.getAllNamirniceObroka("Breakfast", dohvatiStringDatuma());
        adapterBreakfast.setNamirnicas(namirniceObrokas);

        // Swipe to delete
        makeItemErasable(recyclerViewBreakfast, adapterBreakfast);
    }

    public void popuniNamirniceRucka(RecyclerView recyclerViewLunch, final NamirniceObrokaAdapter adapterLunch ){
        //Ručak
        List<NamirniceObroka> namirniceObrokas = namirniceObrokaViewModel.getAllNamirniceObroka("Lunch", dohvatiStringDatuma());
        adapterLunch.setNamirnicas(namirniceObrokas);

        // Swipe to delete
        makeItemErasable(recyclerViewLunch, adapterLunch);
    }

    public void popuniNamirniceVecere(RecyclerView recycleviewDinner, final NamirniceObrokaAdapter adapterDinner){
        // Večera
        List<NamirniceObroka> namirniceObrokas = namirniceObrokaViewModel.getAllNamirniceObroka("Dinner", dohvatiStringDatuma());
        adapterDinner.setNamirnicas(namirniceObrokas);

        // Swipe to delete
        makeItemErasable(recycleviewDinner, adapterDinner);
    }

    public void popuniNamirniceSnacka(RecyclerView recycleviewSnack,final NamirniceObrokaAdapter adapterSnack ){
        //Snack
        List<NamirniceObroka> namirniceObrokas = namirniceObrokaViewModel.getAllNamirniceObroka("Snack", dohvatiStringDatuma());
        adapterSnack.setNamirnicas(namirniceObrokas);

        // Swipe to delete
        makeItemErasable(recycleviewSnack, adapterSnack);
    }

    public void makeItemErasable(RecyclerView recyclerView, final NamirniceObrokaAdapter adapter){

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                NamirniceObroka namirniceObroka = adapter.getNamirnicaObrokaAt(viewHolder.getAdapterPosition());

                adapter.removeItemAt(viewHolder.getAdapterPosition());

                namirniceObrokaViewModel.delete(namirniceObroka);

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
