package com.example.fitapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

import java.util.List;

import features.Text2Speech;

import static com.example.database.MyDatabase.getInstance;

public class ExerciseInstructor extends AppCompatActivity {
    private String nazivVjezbe;
    private List<Integer> listaKorisnikVjezbeId;
    private Button btnStart;
    private Button btnFinish;
    private Button btnText2Speech;
    private CountDownTimer countDownTimer=null;
    private TextView tvCountDown;
    private Text2Speech text2Speech;
    private ImageView ikonaVjezbe;

    private List<KorisnikVjezba> listaKorniskVjezbi;
    private List<Vjezba> vjezba;
    private List<AtributiVjezbiSnage> atributiVjezbiSnage;
    private Integer brojTrenutneVjezbe;
    private Setovi setovi;
    private Vjezba vjezbaIzvodenja;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_instructor);

        //povezivanje varijabli za viewomvima
        btnStart = findViewById(R.id.btnStartTimer);
        btnFinish = findViewById(R.id.btnFinish);
        tvCountDown = findViewById(R.id.tvCountdownTimer);
        text2Speech = Text2Speech.getInstance(this);
        btnText2Speech = findViewById(R.id.btnT2sUpute);
        ikonaVjezbe = findViewById(R.id.ivIkona);

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

        for(int i=0;i < listaKorniskVjezbi.size(); i++){
            //Dohvacanje trajanje repa
            vjezba.add(myDatabase.getVjezbaDAO().dohvatiVjezbu(listaKorniskVjezbi.get(i).getIdVjezba()));
            atributiVjezbiSnage.add(myDatabase.getVjezbaDAO().dohvatiAtributeVjezbeSnagePoVjezbi(listaKorniskVjezbi.get(i).getId()));
        }
        brojTrenutneVjezbe = 0;


        String naziv = toolbar.getTitle().toString();
        naziv = naziv.concat(" id_seta: ");
        naziv = naziv.concat( Integer.toString(idSeta) );
        for(int item: listaKorisnikVjezbeId){
            naziv = naziv.concat( " id:");
            naziv = naziv.concat(Integer.toString(item));
        }
        toolbar.setTitle(naziv);
    }

    private void postaviSlikuVjezbe() {
        int identifikatorVjezbe = getIntent().getExtras().getInt("idVjezbe");
        vjezbaIzvodenja = MyDatabase.getInstance(this).getVjezbaDAO().dohvatiVjezbu(identifikatorVjezbe);
        ikonaVjezbe.setImageResource(vjezbaIzvodenja.getIkona());
    }


    @Override
    protected void onResume() {
        super.onResume();

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //trajanje rep*broj rep + 10 sekundi za pripremu pozicije
                startTimer(atributiVjezbiSnage.get(brojTrenutneVjezbe).getTrajanjeUSekundama()*atributiVjezbiSnage.get(brojTrenutneVjezbe).getBrojPonavljanja()+10);
            }
        });

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopTimer();
            }
        });

        btnText2Speech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text2Speech.govori("I love Stapic!");
            }
        });


    }

    private void startTimer(final int trajanjeTimera){
        //trajanjeTimera = trajanjeTimera * 1000;


        countDownTimer = new CountDownTimer(trajanjeTimera,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long seconds = millisUntilFinished / 1000;
                long minutes = seconds / 60;
                long hours = minutes/ 60;
                String sati = new String();
                String minute = new String();
                //String sekunde = new String();
                if(hours<=0){
                    sati = "00";
                }
                if(minutes<=0){
                    minute = "00";
                }

                if(millisUntilFinished / 1000 == trajanjeTimera-1){
                    //izgovori da se korisnik pripremi
                }
                if(millisUntilFinished / 1000 == trajanjeTimera-10){
                    //izgovori podatke o pripremi
                }
                if(millisUntilFinished / 1000 == 10 && trajanjeTimera > 20){
                    //izgovori 10 seconds untill finish
                }

                tvCountDown.setText(sati+":"+minute+":"+String.valueOf(seconds));
            }

            @Override
            public void onFinish() {
                //TODO
                //on timer finish
                tvCountDown.setText("00:00:00");
                //izgovori x seconds rest time i ponovo pokreni timer sa tim restom, pa prijedi na sljedecu vjezbu
                setovi.getTrajanjePauze();
                brojTrenutneVjezbe++;
            }
        };
        countDownTimer.start();
    }
    private void stopTimer(){
        if(countDownTimer!= null){
            countDownTimer.cancel();
        }

    }

}


