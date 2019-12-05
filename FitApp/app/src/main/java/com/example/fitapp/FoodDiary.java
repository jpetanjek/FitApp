package com.example.fitapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.core.entities.Korisnik;
import com.example.core.entities.Namirnica;
import com.example.core.entities.NamirniceObroka;
import com.example.database.MyDatabase;
import com.example.fitapp.adapters.NamirniceObrokaAdapter;

import java.util.List;

public class FoodDiary extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_diary);

        popuniNamirniceObroka();
    }


    public void popuniNamirniceObroka(){
        MockUp();

        List<NamirniceObroka> namirniceObrokas = MyDatabase.getInstance(this).getNamirnicaDAO().dohvatiKorisnikovObrokPoVrsi("Breakfast");
        ListView lvBreakfast = findViewById(R.id.lvBreakfast);
        NamirniceObrokaAdapter namirniceObrokaAdapter = new NamirniceObrokaAdapter(this, R.layout.meallistrow, namirniceObrokas);
        lvBreakfast.setAdapter(namirniceObrokaAdapter);
        postaviVelicinuListViewa(lvBreakfast);

        namirniceObrokas = MyDatabase.getInstance(this).getNamirnicaDAO().dohvatiKorisnikovObrokPoVrsi("Lunch");
        ListView lvLunch = findViewById(R.id.lvLunch);
        namirniceObrokaAdapter = new NamirniceObrokaAdapter(this, R.layout.meallistrow, namirniceObrokas);
        lvLunch.setAdapter(namirniceObrokaAdapter);
        postaviVelicinuListViewa(lvLunch);

        namirniceObrokas = MyDatabase.getInstance(this).getNamirnicaDAO().dohvatiKorisnikovObrokPoVrsi("Dinner");
        ListView lvDinner = findViewById(R.id.lvDinner);
        namirniceObrokaAdapter = new NamirniceObrokaAdapter(this, R.layout.meallistrow, namirniceObrokas);
        lvDinner.setAdapter(namirniceObrokaAdapter);
        postaviVelicinuListViewa(lvDinner);

        namirniceObrokas = MyDatabase.getInstance(this).getNamirnicaDAO().dohvatiKorisnikovObrokPoVrsi("Snack");
        ListView lvSnack = findViewById(R.id.lvSnack);
        namirniceObrokaAdapter = new NamirniceObrokaAdapter(this, R.layout.meallistrow, namirniceObrokas);
        lvSnack.setAdapter(namirniceObrokaAdapter);
        postaviVelicinuListViewa(lvSnack);

    }

    public void postaviVelicinuListViewa(ListView listView){
        int brojElemenataListe = listView.getAdapter().getCount();

        final float scale = this.getResources().getDisplayMetrics().density;

        int pixels = (int) (brojElemenataListe * scale + 0.5f);
        pixels = pixels * 50; // Otprilike za 1 element je 50
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) listView.getLayoutParams();

        lp.height = pixels;
        listView.setLayoutParams(lp);
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
}
