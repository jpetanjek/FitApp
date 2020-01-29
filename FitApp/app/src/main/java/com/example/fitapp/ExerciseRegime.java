package com.example.fitapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.core.entities.Korisnik;
import com.example.core.entities.KorisnikVjezba;
import com.example.core.entities.NamirniceObroka;
import com.example.core.entities.Setovi;
import com.example.core.entities.Vjezba;
import com.example.database.MyDatabase;
import com.example.fitapp.adapters.ExerciseRegimeAdapter;
import com.example.fitapp.adapters.NamirniceObrokaAdapter;
import com.example.fitapp.viewmodels.ExerciseRegimeViewModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ExerciseRegime extends AppCompatActivity {

    private ExerciseRegimeViewModel exerciseRegimeViewModel;
    private Date trenutniDatumPonedjeljka;

    RecyclerView recyclerViewMonday;
    ExerciseRegimeAdapter adapterMonday;
    RecyclerView recyclerViewTuesday;
    ExerciseRegimeAdapter adapterTuesday;
    RecyclerView recyclerViewWednesday;
    ExerciseRegimeAdapter adapterWednesday;
    RecyclerView recyclerViewThursday;
    ExerciseRegimeAdapter adapterThursday;
    RecyclerView recyclerViewFriday;
    ExerciseRegimeAdapter adapterFriday;
    RecyclerView recyclerViewSaturnday;
    ExerciseRegimeAdapter adapterSaturnday;
    RecyclerView recyclerViewSunday;
    ExerciseRegimeAdapter adapterSunday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_regime);

        exerciseRegimeViewModel = ViewModelProviders.of(this).get(ExerciseRegimeViewModel.class);
        trenutniDatumPonedjeljka = new Date(System.currentTimeMillis());

        do{
            trenutniDatumPonedjeljka = dodajDaneDatumu(trenutniDatumPonedjeljka, -1);
        }
        while(trenutniDatumPonedjeljka.toString().contains("Mon") == false ); // Postavljanje prvog dana u tjednu (ponedjeljak)

        recyclerViewMonday = findViewById(R.id.recycleviewMonday);
        adapterMonday = new ExerciseRegimeAdapter();
        postaviRecycleViewe(recyclerViewMonday, adapterMonday);

        recyclerViewTuesday = findViewById(R.id.recycleviewTuesday);
        adapterTuesday = new ExerciseRegimeAdapter();
        postaviRecycleViewe(recyclerViewTuesday, adapterTuesday);

        recyclerViewWednesday = findViewById(R.id.recycleviewWednesday);
        adapterWednesday = new ExerciseRegimeAdapter();
        postaviRecycleViewe(recyclerViewWednesday, adapterWednesday);

        recyclerViewThursday = findViewById(R.id.recycleviewThursday);
        adapterThursday = new ExerciseRegimeAdapter();
        postaviRecycleViewe(recyclerViewThursday, adapterThursday);

        recyclerViewFriday = findViewById(R.id.recycleviewFriday);
        adapterFriday = new ExerciseRegimeAdapter();
        postaviRecycleViewe(recyclerViewFriday, adapterFriday);

        recyclerViewSaturnday = findViewById(R.id.recycleviewSaturnday);
        adapterSaturnday = new ExerciseRegimeAdapter();
        postaviRecycleViewe(recyclerViewSaturnday, adapterSaturnday);

        recyclerViewSunday = findViewById(R.id.recycleviewSunday);
        adapterSunday = new ExerciseRegimeAdapter();
        postaviRecycleViewe(recyclerViewSunday, adapterSunday);

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
                // Nakon promjene datuma se postavljaju namirnice u obroku za taj dan
                popuniVjezbePonedjeljka();
                popuniVjezbeUtorka();
                popuniVjezbeSrijede();
                popuniVjezbeCetvrtka();
                popuniVjezbePetka();
                popuniVjezbeSubote();
                popuniVjezbeNedjelje();
            }
        });

        tvDate.setText( "Week of " + dateToString(trenutniDatumPonedjeljka) );

        btnDateDecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Skok na prethodni tjedan
                trenutniDatumPonedjeljka = dodajDaneDatumu(trenutniDatumPonedjeljka, -7);
                tvDate.setText( "Week of " + dateToString(trenutniDatumPonedjeljka) );
            }
        });

        btnDateIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Skok na idući tjedan
                trenutniDatumPonedjeljka = dodajDaneDatumu(trenutniDatumPonedjeljka, 7);
                tvDate.setText( "Week of " + dateToString(trenutniDatumPonedjeljka) );
            }
        });

        dodajTrigereNaGumbove();
    }

    private void dodajTrigereNaGumbove() {

        final Button btnAddToMonday = findViewById(R.id.btnAddToMonday);
        final Button btnAddToTuesday = findViewById(R.id.btnAddToTuesday);
        final Button btnAddToWednesday = findViewById(R.id.btnAddToWednesday);
        final Button btnAddToThursday = findViewById(R.id.btnAddToThursday);
        final Button btnAddToFriday = findViewById(R.id.btnAddToFriday);
        final Button btnAddToSaturnday = findViewById(R.id.btnAddToSaturnday);
        final Button btnAddToSunday = findViewById(R.id.btnAddToSunday);

        btnAddToMonday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prikaziOdabirVjezbe( trenutniDatumPonedjeljka, adapterMonday );
            }
        });

        btnAddToTuesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prikaziOdabirVjezbe( dodajDaneDatumu(trenutniDatumPonedjeljka, 1), adapterTuesday );
            }
        });

        btnAddToWednesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prikaziOdabirVjezbe( dodajDaneDatumu(trenutniDatumPonedjeljka, 2), adapterWednesday );
            }
        });

        btnAddToThursday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prikaziOdabirVjezbe( dodajDaneDatumu(trenutniDatumPonedjeljka, 3), adapterThursday );
            }
        });

        btnAddToSaturnday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prikaziOdabirVjezbe( dodajDaneDatumu(trenutniDatumPonedjeljka, 4), adapterFriday );
            }
        });

        btnAddToSunday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prikaziOdabirVjezbe( dodajDaneDatumu(trenutniDatumPonedjeljka, 5), adapterFriday );
            }
        });

        btnAddToFriday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prikaziOdabirVjezbe( dodajDaneDatumu(trenutniDatumPonedjeljka, 6), adapterFriday );
            }
        });


    }

    private void prikaziOdabirVjezbe(final Date datum, final ExerciseRegimeAdapter adapter ) {

        AlertDialog.Builder builderSingle = new AlertDialog.Builder(ExerciseRegime.this);
        builderSingle.setTitle("Select Exercise:-");


        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ExerciseRegime.this, android.R.layout.select_dialog_singlechoice);
        List<Vjezba> vjezbas = MyDatabase.getInstance(ExerciseRegime.this).getVjezbaDAO().dohvatiSveVjezbe();

        for(Vjezba vjezba: vjezbas){
            if(vjezba.getId() != 1){
                arrayAdapter.add( vjezba.getNaziv() );
            }
        }

        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                KorisnikVjezba korisnikVjezba = new KorisnikVjezba();
                korisnikVjezba.setPlaniraniDatum( dateToString(datum) );
                korisnikVjezba.setIdVjezba( which + 2 );

                exerciseRegimeViewModel.insert(korisnikVjezba);
                adapter.setKorisnikVjezbas( exerciseRegimeViewModel.getAllKorisnikVjezba( dateToString(datum) ) );
            }
        });
        builderSingle.show();
    }


    private void postaviRecycleViewe(RecyclerView recyclerView, ExerciseRegimeAdapter adapter){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(false);
        recyclerView.setAdapter(adapter);
        adapter.setContext(this);
    }

    public void popuniVjezbePonedjeljka(){

        List<KorisnikVjezba> korisnikVjezbas = exerciseRegimeViewModel.getAllKorisnikVjezba( dateToString(trenutniDatumPonedjeljka) );
        adapterMonday.setKorisnikVjezbas(korisnikVjezbas);

        // Swipe to delete
        makeItemErasable(recyclerViewMonday, adapterMonday);
    }

    public void popuniVjezbeUtorka(){

        List<KorisnikVjezba> korisnikVjezbas = exerciseRegimeViewModel.getAllKorisnikVjezba( dateToString( dodajDaneDatumu(trenutniDatumPonedjeljka, 1)) );
        adapterTuesday.setKorisnikVjezbas(korisnikVjezbas);

        // Swipe to delete
        makeItemErasable(recyclerViewTuesday, adapterTuesday);
    }

    public void popuniVjezbeSrijede(){

        List<KorisnikVjezba> korisnikVjezbas = exerciseRegimeViewModel.getAllKorisnikVjezba( dateToString( dodajDaneDatumu(trenutniDatumPonedjeljka, 2)) );
        adapterWednesday.setKorisnikVjezbas(korisnikVjezbas);

        // Swipe to delete
        makeItemErasable(recyclerViewWednesday, adapterWednesday);
    }

    public void popuniVjezbeCetvrtka(){

        List<KorisnikVjezba> korisnikVjezbas = exerciseRegimeViewModel.getAllKorisnikVjezba( dateToString( dodajDaneDatumu(trenutniDatumPonedjeljka, 3)) );
        adapterThursday.setKorisnikVjezbas(korisnikVjezbas);

        // Swipe to delete
        makeItemErasable(recyclerViewThursday, adapterThursday);
    }

    public void popuniVjezbePetka(){

        List<KorisnikVjezba> korisnikVjezbas = exerciseRegimeViewModel.getAllKorisnikVjezba( dateToString( dodajDaneDatumu(trenutniDatumPonedjeljka, 4)) );
        adapterFriday.setKorisnikVjezbas(korisnikVjezbas);

        // Swipe to delete
        makeItemErasable(recyclerViewFriday, adapterFriday);
    }

    public void popuniVjezbeSubote(){

        List<KorisnikVjezba> korisnikVjezbas = exerciseRegimeViewModel.getAllKorisnikVjezba( dateToString( dodajDaneDatumu(trenutniDatumPonedjeljka, 5)) );
        adapterSaturnday.setKorisnikVjezbas(korisnikVjezbas);

        // Swipe to delete
        makeItemErasable(recyclerViewSaturnday, adapterSaturnday);
    }

    public void popuniVjezbeNedjelje(){

        List<KorisnikVjezba> korisnikVjezbas = exerciseRegimeViewModel.getAllKorisnikVjezba( dateToString( dodajDaneDatumu(trenutniDatumPonedjeljka, 6)) );
        adapterSunday.setKorisnikVjezbas(korisnikVjezbas);

        // Swipe to delete
        makeItemErasable(recyclerViewSunday, adapterSunday);
    }

    public Date stringToDate(String datum){
        Date date1 = null;
        try {
            date1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(datum); //default Java format
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date1;
    }

    public String dateToString(Date datum){
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy.");
        return dateFormat.format(datum);
    }

    public Date dodajDaneDatumu(Date datum, int dani){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(datum);
        calendar.add(Calendar.DATE, dani);
        datum = calendar.getTime();
        return datum;
    }

    public void makeItemErasable(RecyclerView recyclerView, final ExerciseRegimeAdapter adapter){

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                KorisnikVjezba korisnikVjezba = adapter.getKorisnikVjezbaAt(viewHolder.getAdapterPosition());

                adapter.removeItemAt(viewHolder.getAdapterPosition());

                exerciseRegimeViewModel.delete(korisnikVjezba);

                Toast.makeText(ExerciseRegime.this, "Vježba obrisana.", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);
    }
}
