package com.example.fitapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class ExerciseConfiguration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_configuration);

        String nazivVjezbe = getIntent().getExtras().getString("nazivVjezbe");

        Toolbar toolbar = findViewById(R.id.toolbar1);
        toolbar.setTitle(nazivVjezbe);

        ListView lvOdabirVjezbi = findViewById(R.id.lvOdabirVjezbi);
        TextView tvRedniBroj = findViewById(R.id.tvRedniBroj);
        TextView tvBrojPonavljanja = findViewById(R.id.tvBrojPonavljanja);
        TextView tvBrojKalorija = findViewById(R.id.tvBrojKalorija);

        View itemView = LayoutInflater.from(this).inflate(R.layout.exercise_conf_item, lvOdabirVjezbi, false);


    }
}
