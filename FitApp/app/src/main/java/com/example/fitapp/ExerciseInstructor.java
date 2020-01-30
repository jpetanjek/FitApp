package com.example.fitapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.core.entities.AtributiVjezbiSnage;
import com.example.core.entities.KorisnikVjezba;
import com.example.core.entities.Setovi;
import com.example.core.entities.Vjezba;
import com.example.database.MyDatabase;
import com.example.database.VjezbaDAO;
import com.example.repository.KorisnikDAL;
import com.example.repository.VjezbaDAL;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import features.Text2Speech;

import static com.example.database.MyDatabase.getInstance;

public class ExerciseInstructor extends AppCompatActivity {
    private String nazivVjezbe;
    private List<Integer> listaKorisnikVjezbeId;
    private Button btnStart;
    private Button btnText2Speech;
    private CountDownTimer countDownTimer=null;
    private TextView tvCountDown;
    private TextView ivCalories;
    private Text2Speech text2Speech;
    private ImageView ikonaVjezbe;

    private Button btnPause;
    private Button btnResume;
    private Button btnFinish;


    private List<KorisnikVjezba> listaKorniskVjezbi;
    private List<Vjezba> vjezba;
    private List<AtributiVjezbiSnage> atributiVjezbiSnage;
    private Integer brojTrenutneVjezbe;
    private Setovi setovi;

    private boolean zaustavljenoVrijeme;
    private boolean pauza;

    private long preostaloVrijeme;

    private Vjezba vjezbaIzvodenja;

    private Date pocetakSeta;
    private int vrijemeOstalihVjezbi;
    private int ukupnoTrajanjeVjezbanja;
    private float ukupanBrojKalorija;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_instructor);

        //povezivanje varijabli za viewomvima
        btnStart = findViewById(R.id.btnStartTimer);
        tvCountDown = findViewById(R.id.tvCountdownTimer);
        ivCalories = findViewById(R.id.ivCalories);
        text2Speech = Text2Speech.getInstance(this);
        btnText2Speech = findViewById(R.id.btnT2sUpute);
        ikonaVjezbe = findViewById(R.id.ivIkona);

        btnResume = findViewById(R.id.btnResume);
        btnPause = findViewById(R.id.btnPause);
        btnFinish = findViewById(R.id.btnFinish);


        nazivVjezbe = getIntent().getExtras().getString("nazivVjezbe");
        Toolbar toolbar = findViewById(R.id.toolbar1);
        //toolbar.setTitle(toolbar.getTitle() + nazivVjezbe);

        listaKorisnikVjezbeId = getIntent().getIntegerArrayListExtra("listaKorisnikVjezbeId");

        int idSeta = getIntent().getExtras().getInt("idSeta");

        //Dohvacanje vjezbi po id seta iz bundle
        MyDatabase myDatabase = getInstance(ExerciseInstructor.this);
        listaKorniskVjezbi = myDatabase.getVjezbaDAO().dohvatiVjezbeSeta(idSeta);
        setovi = myDatabase.getVjezbaDAO().dohvatiSet(idSeta);

        postaviSlikuVjezbe();
        ukupanBrojKalorija=0;
        pocetakSeta = Calendar.getInstance().getTime();
        ukupnoTrajanjeVjezbanja=0;

        vjezba = new ArrayList<Vjezba>();
        atributiVjezbiSnage = new ArrayList<AtributiVjezbiSnage>();
        for(int i=0;i < listaKorniskVjezbi.size(); i++){
            //Dohvacanje trajanje repa
            vjezba.add(myDatabase.getVjezbaDAO().dohvatiVjezbu(listaKorniskVjezbi.get(i).getIdVjezba()));
            atributiVjezbiSnage.add(myDatabase.getVjezbaDAO().dohvatiAtributeVjezbeSnagePoVjezbi(listaKorniskVjezbi.get(i).getId()));
        }
        brojTrenutneVjezbe = 0;
    }

    private void postaviSlikuVjezbe() {
        int identifikatorVjezbe = getIntent().getExtras().getInt("idVjezbe");
        vjezbaIzvodenja = MyDatabase.getInstance(this).getVjezbaDAO().dohvatiVjezbu(identifikatorVjezbe);
        ikonaVjezbe.setImageResource(vjezbaIzvodenja.getIkona());
    }


    @Override
    protected void onResume() {
        super.onResume();

        pauza = false;
        //stanje vjezbe 0=vjezba 1=pauza
        zaustavljenoVrijeme = false;

        btnPause.setVisibility(View.GONE);
        btnResume.setVisibility(View.GONE);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //trajanje rep*broj rep + 5 sekundi za pripremu pozicije
                startTimer(vjezba.get(brojTrenutneVjezbe).getRepetition_lenght()*atributiVjezbiSnage.get(brojTrenutneVjezbe).getBrojPonavljanja());
                btnStart.setVisibility(View.GONE);
                btnPause.setVisibility(View.VISIBLE);
            }
        });

        btnResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer((int) preostaloVrijeme/1000);
                zaustavljenoVrijeme=false;
                btnResume.setVisibility(View.GONE);
                btnPause.setVisibility(View.VISIBLE);
            }
        });

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zaustavljenoVrijeme=true;
                btnPause.setVisibility(View.GONE);
                btnResume.setVisibility(View.VISIBLE);
                pauseTimer();
            }
        });

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zavrsiVjezbu();
            }
        });

        //gumb pauza
        pauseTimer();

        btnText2Speech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text2Speech.govori(vjezbaIzvodenja.getUpute());
            }
        });


    }


    private void startTimer(final int trajanjeTimera){
        final int trajanjeTimeraSek = trajanjeTimera * 1000;

        countDownTimer = new CountDownTimer(trajanjeTimeraSek,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                preostaloVrijeme = millisUntilFinished;

                if(zaustavljenoVrijeme==false && pauza==false){
                    //broji kalorije
                    ukupanBrojKalorija += (vjezba.get(brojTrenutneVjezbe)
                            .izracunajPotroseneKalorije( 1, KorisnikDAL.Trenutni(getApplicationContext()).getMasa()));
                    ukupnoTrajanjeVjezbanja++;

                    ivCalories.setText( String.format ("%.2f", ukupanBrojKalorija) );
                }

                if(millisUntilFinished / 1000 == trajanjeTimeraSek-1 && zaustavljenoVrijeme==false && pauza==false){

                    //izgovori da se korisnik pripremi
                    text2Speech.govori("Get in position to start executing " + vjezba.get(brojTrenutneVjezbe).getNaziv());
                }
                if(millisUntilFinished / 1000 == trajanjeTimeraSek-5 &&zaustavljenoVrijeme==false && pauza==false){
                    //pocetak vjezbe
                    text2Speech.govori("Start executing " + vjezba.get(brojTrenutneVjezbe).getNaziv());
                }
                if(millisUntilFinished / 1000 == trajanjeTimeraSek-10 && trajanjeTimeraSek > 30 && zaustavljenoVrijeme==false && pauza==false){
                    //izgovori podatke o pripremi
                    text2Speech.govori(vjezba.get(brojTrenutneVjezbe).getUpute());
                }
                if(millisUntilFinished / 1000 == 10 && trajanjeTimeraSek > 40 && zaustavljenoVrijeme==false && pauza==false){
                    //izgovori 10 seconds untill finish
                    text2Speech.govori("10 seconds untill finished");
                }
                if(millisUntilFinished / 1000 == trajanjeTimeraSek/2 && trajanjeTimeraSek > 50 && zaustavljenoVrijeme==false && pauza==false){
                    text2Speech.govori("You are half way there");
                }

                //pauzirano
                if(millisUntilFinished /  1000 == trajanjeTimeraSek -1 && pauza==true && zaustavljenoVrijeme==false){
                    text2Speech.govori("Starting resting period of " + trajanjeTimera + "seconds, remember to breath");
                }
                if(millisUntilFinished /1000 == 10 && trajanjeTimeraSek > 20 && pauza==true && zaustavljenoVrijeme==false){
                    text2Speech.govori("10 seconds until resting period is over");
                }


                //tvCountDown.setText(sati+":"+minute+":"+String.valueOf(seconds));
                tvCountDown.setText(
                        String.format(Locale.getDefault(),"%02d:%02d:%02d",
                                TimeUnit.MILLISECONDS.toHours(millisUntilFinished)%60,
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)%60,
                                TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)%60)
                );
            }

            @Override
            public void onFinish() {
                //TODO
                //on timer finish
                tvCountDown.setText("00:00:00");
                //izgovori x seconds rest time i ponovo pokreni timer sa tim restom, pa prijedi na sljedecu vjezbu
                //ako je zadnja vjezba u setu
                if(brojTrenutneVjezbe==listaKorniskVjezbi.size()){
                    //prijedi na ekran izvjestaja
                    zavrsiVjezbu();
                }
                if(pauza==false && zaustavljenoVrijeme==false){
                    brojTrenutneVjezbe++;
                }
                //pokreni pauzu
                if(pauza==false){
                    vrijemeOstalihVjezbi+=trajanjeTimera;
                    startTimer(setovi.getTrajanjePauze());
                    pauza=true;
                }else{
                    startTimer(vjezba.get(brojTrenutneVjezbe).getRepetition_lenght()*atributiVjezbiSnage.get(brojTrenutneVjezbe).getBrojPonavljanja());
                    pauza=false;
                }
            }
        };
        countDownTimer.start();
    }

    private void pauseTimer(){
        if(countDownTimer!= null){
            countDownTimer.cancel();
            zaustavljenoVrijeme = true;
        }
    }

    private void stopTimer(){
        if(countDownTimer!= null){
            countDownTimer.cancel();
            zaustavljenoVrijeme = false;
        }
    }

    private void zavrsiVjezbu(){
        /*
        Bundle bundle = new Bundle();
        bundle.putString("brKalorija",ivCalories.getText().toString());
        bundle.putInt("vrijemeVjezbi",ukupnoTrajanjeVjezbanja);
        bundle.putInt("vrijemePauzi",0);
        bundle.putString("pocetakSeta", pocetakSeta.toString());
        Intent intent = new Intent(ExerciseInstructor.this, ExerciseReport.class);
        intent.putExtras(bundle);
        startActivity(intent);
         */

        ExerciseInstructor.this.finish();
    }

}


