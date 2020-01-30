package com.example.fitapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class ExerciseReport extends AppCompatActivity {
    private TextView mainPotroseneKalorija;
    private TextView potrosenoKalorija;
    private TextView tvWorkoutDuration;
    private TextView tvTotalDuration;
    private TextView tvDatumVrijemePocetka;
    private TextView tvTrajanjeVjezbanja;
    private TextView ukupnoTrajanje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_report);

        mainPotroseneKalorija = findViewById(R.id.ivPotrosenoKalorija);
        potrosenoKalorija = findViewById(R.id.tvTotalCalories);
        tvWorkoutDuration = findViewById(R.id.tvWorkoutDuration);
        tvTotalDuration = findViewById(R.id.tvTotalDuration);
        tvDatumVrijemePocetka = findViewById(R.id.tvDatumVrijemePocetka);
        tvTrajanjeVjezbanja = findViewById(R.id.tvTrajanjeVjezbanja);

        LoadBundleData();

    }

    private void LoadBundleData(){
        String brKalorija = getIntent().getExtras().getString("brKalorija");
        int vrijemeVjezbanja = getIntent().getExtras().getInt("vrijemeVjezbi");
        String pocetakSeta = getIntent().getExtras().getString("pocetakSeta");

        mainPotroseneKalorija.setText(brKalorija);
        potrosenoKalorija.setText(brKalorija);
        tvWorkoutDuration.setText(formatSeconds(vrijemeVjezbanja));

        Date datum = stringToDate(pocetakSeta);
        tvDatumVrijemePocetka.setText( dateToString(datum) + " - " + dateToString(Calendar.getInstance().getTime()) );

        int razlikaSekundi = (int) Math.abs(  Calendar.getInstance().getTime().getTime()  -  datum.getTime() ) / 1000; // Ukupno trajanje Seta (razlika početka i kraja seta u sekundama)
        tvTotalDuration.setText( formatSeconds( razlikaSekundi ) );

        tvTrajanjeVjezbanja.setText( Integer.toString ( vrijemeVjezbanja/60 )  ); // Sekunde u minute
    }

    public String formatSeconds(int timeInSeconds) {
        int hours = timeInSeconds / 3600;
        int secondsLeft = timeInSeconds - hours * 3600;
        int minutes = secondsLeft / 60;
        int seconds = secondsLeft - minutes * 60;

        String formattedTime = "";
        if (hours < 10)
            formattedTime += "0";
        formattedTime += hours + ":";

        if (minutes < 10)
            formattedTime += "0";
        formattedTime += minutes + ":";

        if (seconds < 10)
            formattedTime += "0";
        formattedTime += seconds;

        return formattedTime;
    }

    public Date stringToDate(String datum){
        Date date1 = null;
        try {
            date1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(datum); //default Java format
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date1;
    }

    public String dateToString(Date datum){
        DateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM HH:mm:ss "); //Dan, datum mjesec sati minute
        return dateFormat.format(datum);
    }


}
