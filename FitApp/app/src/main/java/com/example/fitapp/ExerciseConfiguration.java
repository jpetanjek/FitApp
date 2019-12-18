package com.example.fitapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.fitapp.adapters.ExerciseConfAdapter;

public class ExerciseConfiguration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_configuration);

        String nazivVjezbe = getIntent().getExtras().getString("nazivVjezbe");

        Toolbar toolbar = findViewById(R.id.toolbar1);
        toolbar.setTitle(nazivVjezbe);

        ListView lvOdabirVjezbi = findViewById(R.id.lvOdabirVjezbi);


        ExerciseConfAdapter exerciseConfAdapter = new ExerciseConfAdapter(this);
        lvOdabirVjezbi.setAdapter(exerciseConfAdapter);

        View footerView = getLayoutInflater().inflate(R.layout.exercise_conf_list_footer, null);
        lvOdabirVjezbi.addFooterView(footerView);


    }
}
