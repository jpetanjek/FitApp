package com.example.fitapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.fitapp.adapters.ExerciseConfAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ExerciseConfiguration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_configuration);

        String nazivVjezbe = getIntent().getExtras().getString("nazivVjezbe");

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



    }

}
