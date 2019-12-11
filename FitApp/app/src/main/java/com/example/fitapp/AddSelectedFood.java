package com.example.fitapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import com.example.core.entities.Namirnica;
import com.example.core.entities.NamirniceObroka;
import com.example.fitapp.viewmodels.AddSelectedFoodViewModel;
import com.example.repository.KorisnikDAL;
import com.example.repository.NamirnicaDAL;
import com.example.unos_hrane.Add_new_food_ViewModel;

public class AddSelectedFood extends AppCompatActivity {
    private AddSelectedFoodViewModel addSelectedFoodViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_selected_food);

        //final String obrok = getArguments().getString("Obrok");
        //final String datum = getArguments().getString("Datum");
        String obrok = "Dorucak";
        String datum = "11/12/2019";
        Integer idNamirnice= 1;

        addSelectedFoodViewModel.Inicijaliziraj(idNamirnice);
        addSelectedFoodViewModel = ViewModelProviders.of(this).get(AddSelectedFoodViewModel.class);
        addSelectedFoodViewModel.namirnicaLiveData.observe(this, new Observer<Namirnica>() {
            @Override
            public void onChanged(Namirnica namirnica) {
                System.out.println("NAMIRNICA");
                System.out.println(namirnica.getId());
                System.out.println(namirnica.getBrojKalorija());
                System.out.println(namirnica.getIsbn());
                System.out.println(namirnica.getNaziv());
                System.out.println(namirnica.getTezina());

            }
        });

        addSelectedFoodViewModel.namirniceObrokaLiveData.observe(this, new Observer<NamirniceObroka>() {
            @Override
            public void onChanged(NamirniceObroka namirniceObroka) {
                System.out.println("NamirniceObroka");
                System.out.println(namirniceObroka.getDatum());
                System.out.println(namirniceObroka.getId());
                System.out.println(namirniceObroka.getIdKorisnik());
                System.out.println(namirniceObroka.getIdNamirnica());
                System.out.println(namirniceObroka.getMasa());
                System.out.println(namirniceObroka.getObrok());
            }
        });

        //update ui
        final NamirniceObroka namirniceObroka = new NamirniceObroka();
        //mock unos
        namirniceObroka.setMasa(200);
        addSelectedFoodViewModel.updateObrok(namirniceObroka,this);


        //pritisak na gumb GOTOVO
        namirniceObroka.setIdKorisnik(KorisnikDAL.Trenutni(this).getId());
        //response.body je id novostvorene namirnice na webservisu
        namirniceObroka.setIdNamirnica(idNamirnice);
        //iz bundle
        namirniceObroka.setObrok(obrok);
        namirniceObroka.setDatum(datum);
        //lokalnaNamirniceObroka.setPlanirano();

        //SPREMI NamirnicaUObrok
        NamirnicaDAL.UnesiKorisnikovObrok(this,namirniceObroka);

        System.out.println("Dodana");

    }
}
