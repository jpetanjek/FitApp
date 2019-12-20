package com.example.fitapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.core.entities.AtributiVjezbiSnage;
import com.example.core.entities.Korisnik;
import com.example.core.entities.KorisnikVjezba;
import com.example.core.entities.Vjezba;
import com.example.database.MyDatabase;
import com.example.fitapp.adapters.ExerciseConfAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ExerciseConfiguration extends AppCompatActivity {
    private int idVjezbe;
    private String nazivVjezbe;
    private ImageView ikonaVjezbe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_configuration);

        nazivVjezbe = getIntent().getExtras().getString("naziv");
        idVjezbe = getIntent().getExtras().getInt("idVjezbe");
        int referencaIkone = MyDatabase.getInstance(getApplicationContext())
                .getVjezbaDAO()
                .dohvatiVjezbu(idVjezbe)
                .getIkona();
        ikonaVjezbe = findViewById(R.id.ivSlika);
        ikonaVjezbe.setImageResource(referencaIkone);

        Toolbar toolbar = findViewById(R.id.toolbar1);
        toolbar.setTitle(nazivVjezbe);

        // Popunjavanje listviewa
        final ListView lvOdabirVjezbi = findViewById(R.id.lvOdabirVjezbi);

        final ExerciseConfAdapter exerciseConfAdapter = new ExerciseConfAdapter(this, null);
        lvOdabirVjezbi.setAdapter(exerciseConfAdapter);

        View footerView = getLayoutInflater().inflate(R.layout.exercise_conf_list_footer, null);
        lvOdabirVjezbi.addFooterView(footerView);

        EditText tvRestTime = footerView.findViewById(R.id.tvRestTime);
        exerciseConfAdapter.kontrolaUnosa(tvRestTime);

        // Dodavanje itema u listu (klikom na +)
        TextView tvAddExercise = findViewById(R.id.tvAddExercise);
        tvAddExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Napraviti novi objekt i dodati ga na listu, ažurirati listu
                ArrayList<ExerciseConfAdapter.ExerciseConfItem> lista = exerciseConfAdapter.getLista();
                ExerciseConfAdapter.ExerciseConfItem newItem = exerciseConfAdapter.new ExerciseConfItem(lista.size()+1, 12, 10);
                lista.add(newItem);
                exerciseConfAdapter.setLista(lista);

                // Postavljanje na dno
                lvOdabirVjezbi.smoothScrollToPosition(lista.size());
            }
        });


        // Pokretanje Exercise instructora
        final Button start = findViewById(R.id.btnStart);
        start.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 ArrayList<Integer> listaKorisnikVjezbeId = new ArrayList<Integer>();

                 // Za svaki element liste, zapisati podatke o konfiguraciji u KorisnikVjezbe i AtributiVjezbeSnage
                 for (ExerciseConfAdapter.ExerciseConfItem item: exerciseConfAdapter.getLista()) {

                     Korisnik korisnik = MyDatabase.getInstance(ExerciseConfiguration.this).getKorisnikDAO().dohvatiKorisnika();
                     Vjezba vjezba = MyDatabase.getInstance(ExerciseConfiguration.this).getVjezbaDAO().dohvatiVjezbu(1);

                     KorisnikVjezba korisnikVjezba = new KorisnikVjezba();
                     korisnikVjezba.setIdKorisnik(korisnik.getId());
                     korisnikVjezba.setIdVjezba(vjezba.getId());
                     korisnikVjezba.setIdGoogleKalendar("nista");
                     korisnikVjezba.setPlanirano(false);

                     long[] id = MyDatabase.getInstance(ExerciseConfiguration.this).getVjezbaDAO().unosKorisnikoveVjezbe(korisnikVjezba);
                     listaKorisnikVjezbeId.add( (int) id[0] );



                     AtributiVjezbiSnage atributiVjezbiSnage = new AtributiVjezbiSnage();
                     atributiVjezbiSnage.setKorisnikVjezbaId(korisnikVjezba.getId());
                     atributiVjezbiSnage.setKalorijaPotroseno(0);
                     atributiVjezbiSnage.setBrojPonavljanja(item.getBrojPonavljanja());
                     atributiVjezbiSnage.setMasaPonavljanja(item.getMasa());
                     atributiVjezbiSnage.setTrajanjeUSekundama(15);

                 }

                 Intent intent = new Intent(ExerciseConfiguration.this, ExerciseInstructor.class);
                 intent.putExtra("nazivVjezbe", nazivVjezbe);
                 intent.putExtra("listaKorisnikVjezbeId", (ArrayList<Integer>) listaKorisnikVjezbeId);
                 startActivity(intent);

             }
         }
        );




    }

}
