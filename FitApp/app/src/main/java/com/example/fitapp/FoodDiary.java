package com.example.fitapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.core.entities.Korisnik;
import com.example.core.entities.Namirnica;
import com.example.core.entities.NamirniceObroka;
import com.example.database.MyDatabase;
import com.example.fitapp.adapters.NamirniceObrokaAdapter;
import com.example.fitapp.viewmodels.NamirniceDoruckaViewModel;
import com.example.fitapp.viewmodels.NamirniceRuckaViewModel;
import com.example.fitapp.viewmodels.NamirniceSnackViewModel;
import com.example.fitapp.viewmodels.NamirniceVecereViewModel;

import java.util.List;

public class FoodDiary extends AppCompatActivity {

    private NamirniceDoruckaViewModel namirniceDoruckaViewModel;
    private NamirniceRuckaViewModel namirniceRuckaViewModel;
    private NamirniceVecereViewModel namirniceVecereViewModel;
    private NamirniceSnackViewModel namirniceSnackViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_diary);
        MockUp();
        popuniNamirniceObroka();

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

        namirniceDoruckaViewModel = ViewModelProviders.of(this).get(NamirniceDoruckaViewModel.class);
        namirniceDoruckaViewModel.getAllNamirniceObroka().observe(this, new Observer<List<NamirniceObroka>>() {
            @Override
            public void onChanged(List<NamirniceObroka> namirniceObrokas) {
                adapterBreakfast.setNamirnicas(namirniceObrokas);
            }
        });

        // Swipe to delete
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                namirniceDoruckaViewModel.delete(adapterBreakfast.getNamirnicaObrokaAt(viewHolder.getAdapterPosition()));
                Toast.makeText(FoodDiary.this, "Namirnica obroka obrisana.", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerViewBreakfast);
    }

    public void popuniNamirniceRucka(){
        //Ručak
        RecyclerView recyclerViewLunch = findViewById(R.id.recycleviewLunch);
        recyclerViewLunch.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewLunch.setHasFixedSize(true);

        final NamirniceObrokaAdapter adapterLunch = new NamirniceObrokaAdapter();
        recyclerViewLunch.setAdapter(adapterLunch);
        adapterLunch.setContext(this);// Koristiti DAL (obrisati)

        namirniceRuckaViewModel = ViewModelProviders.of(this).get(NamirniceRuckaViewModel.class);
        namirniceRuckaViewModel.getAllNamirniceObroka().observe(this, new Observer<List<NamirniceObroka>>() {
            @Override
            public void onChanged(List<NamirniceObroka> namirniceObrokas) {
                adapterLunch.setNamirnicas(namirniceObrokas);
            }
        });

        // Swipe to delete
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                namirniceRuckaViewModel.delete(adapterLunch.getNamirnicaObrokaAt(viewHolder.getAdapterPosition()));
                Toast.makeText(FoodDiary.this, "Namirnica obroka obrisana.", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerViewLunch);
    }

    public void popuniNamirniceVecere(){
        // Večera
        RecyclerView recycleviewDinner = findViewById(R.id.recycleviewDinner);
        recycleviewDinner.setLayoutManager(new LinearLayoutManager(this));
        recycleviewDinner.setHasFixedSize(true);

        final NamirniceObrokaAdapter adapterDinner = new NamirniceObrokaAdapter();
        recycleviewDinner.setAdapter(adapterDinner);
        adapterDinner.setContext(this);

        namirniceVecereViewModel = ViewModelProviders.of(this).get(NamirniceVecereViewModel.class);
        namirniceVecereViewModel.getAllNamirniceObroka().observe(this, new Observer<List<NamirniceObroka>>() {
            @Override
            public void onChanged(List<NamirniceObroka> namirniceObrokas) {
                adapterDinner.setNamirnicas(namirniceObrokas);
            }
        });

        // Swipe to delete
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                namirniceVecereViewModel.delete(adapterDinner.getNamirnicaObrokaAt(viewHolder.getAdapterPosition()));
                Toast.makeText(FoodDiary.this, "Namirnica obroka obrisana.", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recycleviewDinner);
    }

    public void popuniNamirniceSnacka(){
        //Snack
        RecyclerView recycleviewSnack = findViewById(R.id.recycleviewSnack);
        recycleviewSnack.setLayoutManager(new LinearLayoutManager(this));
        recycleviewSnack.setHasFixedSize(true);

        final NamirniceObrokaAdapter adapterSnack = new NamirniceObrokaAdapter();
        recycleviewSnack.setAdapter(adapterSnack);
        adapterSnack.setContext(this);

        namirniceSnackViewModel = ViewModelProviders.of(this).get(NamirniceSnackViewModel.class);
        namirniceSnackViewModel.getAllNamirniceObroka().observe(this, new Observer<List<NamirniceObroka>>() {
            @Override
            public void onChanged(List<NamirniceObroka> namirniceObrokas) {
                adapterSnack.setNamirnicas(namirniceObrokas);
            }
        });

        // Swipe to delete
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                namirniceSnackViewModel.delete(adapterSnack.getNamirnicaObrokaAt(viewHolder.getAdapterPosition()));
                Toast.makeText(FoodDiary.this, "Namirnica obroka obrisana.", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recycleviewSnack);
    }



    public void MockUp(){
        Korisnik korisnik = MyDatabase.getInstance(this).getKorisnikDAO().dohvatiKorisnika();
        List<Namirnica> namirnice = MyDatabase.getInstance(this).getNamirnicaDAO().dohvatiSveNamirnice();

        NamirniceObroka namirniceObroka = new NamirniceObroka();
        namirniceObroka.setIdKorisnik(korisnik.getId());
        namirniceObroka.setIdNamirnica(namirnice.get(0).getId());
        namirniceObroka.setObrok("Breakfast");
        MyDatabase.getInstance(this).getNamirnicaDAO().unosNamirniceObroka(namirniceObroka);

        namirniceObroka = new NamirniceObroka();
        namirniceObroka.setIdKorisnik(korisnik.getId());
        namirniceObroka.setIdNamirnica(namirnice.get(1).getId());
        namirniceObroka.setObrok("Dinner");
        MyDatabase.getInstance(this).getNamirnicaDAO().unosNamirniceObroka(namirniceObroka);

        namirniceObroka = new NamirniceObroka();
        namirniceObroka.setIdKorisnik(korisnik.getId());
        namirniceObroka.setIdNamirnica(namirnice.get(2).getId());
        namirniceObroka.setObrok("Snack");
        MyDatabase.getInstance(this).getNamirnicaDAO().unosNamirniceObroka(namirniceObroka);

        namirniceObroka = new NamirniceObroka();
        namirniceObroka.setIdKorisnik(korisnik.getId());
        namirniceObroka.setIdNamirnica(namirnice.get(3).getId());
        namirniceObroka.setObrok("Lunch");
        MyDatabase.getInstance(this).getNamirnicaDAO().unosNamirniceObroka(namirniceObroka);

        namirniceObroka = new NamirniceObroka();
        namirniceObroka.setIdKorisnik(korisnik.getId());
        namirniceObroka.setIdNamirnica(namirnice.get(4).getId());
        namirniceObroka.setObrok("Dinner");
        MyDatabase.getInstance(this).getNamirnicaDAO().unosNamirniceObroka(namirniceObroka);
    }
}
