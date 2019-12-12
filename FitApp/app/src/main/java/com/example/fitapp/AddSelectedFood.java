package com.example.fitapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.core.entities.Namirnica;
import com.example.core.entities.NamirniceObroka;
import com.example.fitapp.viewmodels.AddSelectedFoodViewModel;
import com.example.registracija.Repozitorij;
import com.example.repository.KorisnikDAL;
import com.example.repository.NamirnicaDAL;
import com.example.unos_hrane.Add_new_food_ViewModel;

import RetroEntities.RetroNamirnica;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddSelectedFood extends AppCompatActivity {
    private AddSelectedFoodViewModel addSelectedFoodViewModel;
    private String obrok,datum;
    private int idNamirnice;
    private Namirnica namirnica;

    private EditText New_food;
    private TextView Number_of_servings;
    private TextView Serving_size;
    private TextView Calorie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_selected_food);

        New_food = findViewById(R.id.new_food);
        Number_of_servings = findViewById(R.id.number_of_servings);
        Serving_size = findViewById(R.id.serving_size);
        Calorie = findViewById(R.id.calorie);

        obrok = getIntent().getExtras().getString("Obrok");
        datum = getIntent().getExtras().getString("Datum");
        idNamirnice = getIntent().getExtras().getInt("idNamirnice");


        if(obrok!=null && datum!=null && idNamirnice!=0){
            System.out.println("NAMIRNICA ADDSELECTED FOOD");
            System.out.println("Obrok:"+obrok);
            System.out.println("Datum:"+datum);
            System.out.println("IdNamirnice:"+idNamirnice);
        }

        addSelectedFoodViewModel = ViewModelProviders.of(this).get(AddSelectedFoodViewModel.class);
        addSelectedFoodViewModel.Inicijaliziraj(idNamirnice);
        addSelectedFoodViewModel.namirnicaLiveData.observe(this, new Observer<Namirnica>() {
            @Override
            public void onChanged(Namirnica namirnica) {
                New_food.setText(namirnica.getNaziv());
                Serving_size.setText(namirnica.getTezina());
                Calorie.setText(namirnica.getBrojKalorija());

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
                Number_of_servings.setText("1");

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
        //addSelectedFoodViewModel.updateObrok(namirniceObroka,this);


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

        Intent i = new Intent(AddSelectedFood.this,FoodDiary.class);
        startActivity(i);
        finish();
    }

}
