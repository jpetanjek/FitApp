package com.example.fitapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.core.entities.Namirnica;
import com.example.core.entities.NamirniceObroka;
import com.example.database.MyDatabase;
import com.example.fitapp.viewmodels.AddSelectedFoodViewModel;
import com.example.registracija.Repozitorij;
import com.example.repository.KorisnikDAL;
import com.example.repository.NamirnicaDAL;
import com.example.unos_hrane.Add_new_food_ViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import RetroEntities.RetroNamirnica;
import features.KalendarDogadaj;
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
    private Button btnCreateFood,btnKalendar;
    private NamirniceObroka namirniceObroka;
    private int brojKalorija;
    private int tezinaNamirnice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_selected_food);

        New_food = findViewById(R.id.new_food);
        Number_of_servings = findViewById(R.id.number_of_servings);
        Serving_size = findViewById(R.id.serving_size);
        Calorie = findViewById(R.id.calorie);
        btnCreateFood = findViewById(R.id.btnUnos);

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
                Serving_size.setText(String.valueOf(namirnica.getTezina()));
                Calorie.setText(String.valueOf(namirnica.getBrojKalorija()));
                brojKalorija = namirnica.getBrojKalorija();
                tezinaNamirnice = namirnica.getTezina();
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


        final LinearLayout numberOfServings = findViewById(R.id.numberOfServings);
        numberOfServings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NumberOfServingsDialog cdd=new NumberOfServingsDialog(AddSelectedFood.this,Integer.valueOf(String.valueOf(Number_of_servings.getText())));
                cdd.setDialogResult(new NumberOfServingsDialog.OnMyDialogResult() {
                    @Override
                    public void finish(String result) {
                        Number_of_servings.setText(result);
                        int brojPonavljanja = Integer.parseInt(Number_of_servings.getText().toString());
                        int ukupnoKalorija = brojKalorija*brojPonavljanja;
                        String inkrementalniBrojKalorija = String.valueOf(ukupnoKalorija);
                        Calorie.setText(inkrementalniBrojKalorija);
                        namirniceObroka.setMasa(IzracunajMasuNamirniceObroka());
                    }
                });
                cdd.show();

            }
        });
        //update ui
        namirniceObroka = new NamirniceObroka();
        //mock unos


        //namirniceObroka.setMasa(200);
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

        System.out.println("Dodana");
        btnCreateFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NamirnicaDAL.UnesiKorisnikovObrok(v.getContext(),namirniceObroka);
                finish();
            }
        });
        Button btnKalendar = findViewById(R.id.btnKalendar);
        btnKalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String naslov = "Pojedi "+ MyDatabase.getInstance(v.getContext()).getNamirnicaDAO().dohvatiNamirnicu(namirniceObroka.getIdNamirnica()).getNaziv();
                String opis = "Potrebno je pojesti "+MyDatabase.getInstance(v.getContext()).getNamirnicaDAO().dohvatiNamirnicu(namirniceObroka.getIdNamirnica()).getNaziv()+" za obrok "+namirniceObroka.getObrok();
                Calendar calendar = Calendar.getInstance();
                long pocetnoVrijeme = calendar.getTimeInMillis();
                long zavrsnoVrijeme = pocetnoVrijeme + 120*60*1000;
                KalendarDogadaj.OtvoriDogadajKalendara(v.getContext(),pocetnoVrijeme,zavrsnoVrijeme,naslov,opis,KalendarDogadaj.KROZ_INTERVAL_POCETKA_I_KRAJA);
            }
        });
    }

    private float IzracunajMasuNamirniceObroka(){
        int brPosluzivanja = Integer.parseInt(Number_of_servings.getText().toString());
        float ukupnaMasa = brojKalorija * brPosluzivanja * tezinaNamirnice;
        return ukupnaMasa;
    }
}
