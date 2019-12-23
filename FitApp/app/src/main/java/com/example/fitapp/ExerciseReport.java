package com.example.fitapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class ExerciseReport extends AppCompatActivity {
    private TextView mainPotroseneKalorija;
    private TextView potrosenoKalorija;
    private TextView trajanjeVjezbanja;
    private TextView ukupnoTrajanje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_report);

        mainPotroseneKalorija = findViewById(R.id.ivPotrosenoKalorija);
        potrosenoKalorija = findViewById(R.id.tvTotalCalories);
        trajanjeVjezbanja = findViewById(R.id.tvWorkoutDuration);

        LoadBundleData();

    }

    private void LoadBundleData(){
        String brKalorija = getIntent().getExtras().getString("brKalorija");
        int vrijemeVjezbanja = getIntent().getExtras().getInt("vrijemeVjezbi");

        mainPotroseneKalorija.setText(brKalorija);
        potrosenoKalorija.setText(brKalorija);
        trajanjeVjezbanja.setText(
                String.format(Locale.getDefault(),"%02d:%02d:%02d",
                        TimeUnit.MILLISECONDS.toHours(vrijemeVjezbanja*1000)%60,
                        TimeUnit.MILLISECONDS.toMinutes(vrijemeVjezbanja*1000)%60,
                        TimeUnit.MILLISECONDS.toSeconds(vrijemeVjezbanja*1000)%60)
        );
    }
}
