package com.example.fitapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import features.Text2Speech;

public class ExerciseInstructor extends AppCompatActivity {
    private String nazivVjezbe;
    private List<Integer> listaKorisnikVjezbeId;
    private Button btnStart;
    private Button btnFinish;
    private Button btnText2Speech;
    private CountDownTimer countDownTimer=null;
    private TextView tvCountDown;
    private Text2Speech text2Speech;


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

        nazivVjezbe = getIntent().getExtras().getString("nazivVjezbe");
        Toolbar toolbar = findViewById(R.id.toolbar1);
        //toolbar.setTitle(toolbar.getTitle() + nazivVjezbe);

        listaKorisnikVjezbeId = getIntent().getIntegerArrayListExtra("listaKorisnikVjezbeId");
        String naziv = toolbar.getTitle().toString();
        for(int item: listaKorisnikVjezbeId){
            naziv = naziv.concat( " id:");
            naziv = naziv.concat(Integer.toString(item));
            Log.v("UNESENO2", Integer.toString(item));

        }
        toolbar.setTitle(naziv);
    }


    @Override
    protected void onResume() {
        super.onResume();

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer(10);
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

    private void startTimer(int trajanjeTimera){
        trajanjeTimera = trajanjeTimera * 1000;
        countDownTimer = new CountDownTimer(trajanjeTimera,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long seconds = millisUntilFinished / 1000;
                long minutes = seconds / 60;
                long hours = minutes/ 60;
                String sati = new String();
                String minute = new String();
                String sekunde = new String();
                if(hours<=0){
                    sati = "00";
                }
                if(minutes<=0){
                    minute = "00";
                }

                tvCountDown.setText(hours+":"+minute+":"+String.valueOf(seconds));
            }

            @Override
            public void onFinish() {
                //TODO
                //on timer finish
                tvCountDown.setText("00:00:00");
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


