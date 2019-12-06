package com.example.fitapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.core.entities.Korisnik;
import com.example.core.entities.Namirnica;
import com.example.core.entities.NamirniceObroka;
import com.example.database.MyDatabase;
import com.example.fitapp.adapters.NamirniceObrokaAdapter;

import java.util.ArrayList;
import java.util.List;

public class FoodDiary extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_diary);

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

        popuniNamirniceObroka();
    }

    // Postavljanje recycleViewa i NamirniceObrokAdaptera na svaku listu posebno (doručak, ručak, večeru i snack)
    public void popuniNamirniceObroka(){
        //MockUp();

        // Doručak
        RecyclerView recyclerViewBreakfast = findViewById(R.id.recycleviewBreakfast);
        recyclerViewBreakfast.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewBreakfast.setHasFixedSize(true);

        final NamirniceObrokaAdapter adapterBreakfast = new NamirniceObrokaAdapter();
        recyclerViewBreakfast.setAdapter(adapterBreakfast);
        adapterBreakfast.setContext(this); // Koristiti DAL (obrisati)
        adapterBreakfast.setNamirnicas(MyDatabase.getInstance(this).getNamirnicaDAO().dohvatiNamirniceObrokaPoVrsi("Breakfast")); // // Koristiti DAL (obrisati)

        //Ručak
        RecyclerView recyclerViewLunch = findViewById(R.id.recycleviewLunch);
        recyclerViewLunch.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewLunch.setHasFixedSize(true);

        NamirniceObrokaAdapter adapterLunch = new NamirniceObrokaAdapter();
        recyclerViewLunch.setAdapter(adapterLunch);
        adapterLunch.setContext(this);// Koristiti DAL (obrisati)
        adapterLunch.setNamirnicas(MyDatabase.getInstance(this).getNamirnicaDAO().dohvatiNamirniceObrokaPoVrsi("Lunch")); // // Koristiti DAL (obrisati)

        //Večera
        RecyclerView recycleviewDinner = findViewById(R.id.recycleviewDinner);
        recycleviewDinner.setLayoutManager(new LinearLayoutManager(this));
        recycleviewDinner.setHasFixedSize(true);

        NamirniceObrokaAdapter adapterDinner = new NamirniceObrokaAdapter();
        recycleviewDinner.setAdapter(adapterDinner);
        adapterDinner.setContext(this);// Koristiti DAL (obrisati)
        adapterDinner.setNamirnicas(MyDatabase.getInstance(this).getNamirnicaDAO().dohvatiNamirniceObrokaPoVrsi("Dinner")); // Koristiti DAL (obrisati)

        //Snack
        RecyclerView recycleviewSnack = findViewById(R.id.recycleviewSnack);
        recycleviewSnack.setLayoutManager(new LinearLayoutManager(this));
        recycleviewSnack.setHasFixedSize(true);

        NamirniceObrokaAdapter adapterSnack = new NamirniceObrokaAdapter();
        recycleviewSnack.setAdapter(adapterSnack);
        adapterSnack.setContext(this); // Koristiti DAL (obrisati)
        adapterSnack.setNamirnicas(MyDatabase.getInstance(this).getNamirnicaDAO().dohvatiNamirniceObrokaPoVrsi("Snack")); // Koristiti DAL (obrisati)

        makeAdapterItemErasable(adapterBreakfast, recyclerViewBreakfast, "Breakfast");
        makeAdapterItemErasable(adapterLunch, recyclerViewLunch, "Lunch");
        makeAdapterItemErasable(adapterDinner, recycleviewDinner, "Dinner");
        makeAdapterItemErasable(adapterSnack, recycleviewSnack, "Snack");
    }

    public void makeAdapterItemErasable(final NamirniceObrokaAdapter adapter, RecyclerView recyclerView , final String obrok){
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                MyDatabase.getInstance(FoodDiary.this).getNamirnicaDAO().brisanjeKorisnikovogObroka(adapter.getNamirnicaObrokaAt(viewHolder.getAdapterPosition()));
                Toast.makeText(FoodDiary.this, "Namirnica obroka obrisana.", Toast.LENGTH_SHORT);
                adapter.setNamirnicas(MyDatabase.getInstance(FoodDiary.this).getNamirnicaDAO().dohvatiNamirniceObrokaPoVrsi(obrok));
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
        MyDatabase.getInstance(this).getNamirnicaDAO().unosKorisnikovogObroka(namirniceObroka);

        namirniceObroka = new NamirniceObroka();
        namirniceObroka.setIdKorisnik(korisnik.getId());
        namirniceObroka.setIdNamirnica(namirnice.get(1).getId());
        namirniceObroka.setObrok("Dinner");
        MyDatabase.getInstance(this).getNamirnicaDAO().unosKorisnikovogObroka(namirniceObroka);

        namirniceObroka = new NamirniceObroka();
        namirniceObroka.setIdKorisnik(korisnik.getId());
        namirniceObroka.setIdNamirnica(namirnice.get(2).getId());
        namirniceObroka.setObrok("Snack");
        MyDatabase.getInstance(this).getNamirnicaDAO().unosKorisnikovogObroka(namirniceObroka);

        namirniceObroka = new NamirniceObroka();
        namirniceObroka.setIdKorisnik(korisnik.getId());
        namirniceObroka.setIdNamirnica(namirnice.get(3).getId());
        namirniceObroka.setObrok("Lunch");
        MyDatabase.getInstance(this).getNamirnicaDAO().unosKorisnikovogObroka(namirniceObroka);

        namirniceObroka = new NamirniceObroka();
        namirniceObroka.setIdKorisnik(korisnik.getId());
        namirniceObroka.setIdNamirnica(namirnice.get(4).getId());
        namirniceObroka.setObrok("Dinner");
        MyDatabase.getInstance(this).getNamirnicaDAO().unosKorisnikovogObroka(namirniceObroka);
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
