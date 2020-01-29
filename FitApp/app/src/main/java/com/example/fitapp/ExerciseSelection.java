package com.example.fitapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.core.entities.Vjezba;
import com.example.database.MyDatabase;
import com.example.fitapp.adapters.VjezbeAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import features.Text2Speech;

public class ExerciseSelection extends AppCompatActivity {

    private ArrayList<Vjezba> objektiVjezbi = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_selection);

        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        List<Vjezba> vjezbas = MyDatabase.getInstance(this).getVjezbaDAO().dohvatiSveVjezbe();
        ArrayList<String> lista = new ArrayList<String>();
        lista.add("Plan exercises");
        for (Vjezba temp : vjezbas){
            lista.add(temp.getNaziv());
            objektiVjezbi.add(temp);
        }

        setIconForExercise();

        View logoView = getToolbarLogoView(toolbar);
        logoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent i = new Intent(ExerciseSelection.this, Glavni_Izbornik.class);
                    startActivity(i);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });

        String mTitle[] = Arrays.copyOf(lista.toArray(),lista.toArray().length,String[].class);

        int images[] = {R.drawable.ic_history,R.drawable.ic_running, R.drawable.ic_person_walking, R.drawable.ic_man_in_canoe, R.drawable.ic_olympic_weightlifting_, R.drawable.ic_weightlifting, R.drawable.ic_upper_chest_training, R.drawable.ic_exercising_man,R.drawable.ic_bicycle_rider};

        final ListView listView = findViewById(R.id.exerciseListView);

            //VjezbeAdapter adapter = new VjezbeAdapter(this, mTitle, images);
            VjezbeAdapter adapter = new VjezbeAdapter(this,objektiVjezbi);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    switch (position){
                        case 0:
                            //Toast.makeText(ExerciseSelection.this, "Plan exercises description", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ExerciseSelection.this, ExerciseRegime.class);
                            startActivity(intent);
                            break;
                        case 1:
                            //Toast.makeText(ExerciseSelection.this, "Running description", Toast.LENGTH_SHORT).show();
                            break;
                        case 2:
                            //Toast.makeText(ExerciseSelection.this, "Walking description", Toast.LENGTH_SHORT).show();
                            break;
                        case 3:
                            //Toast.makeText(ExerciseSelection.this, "Rowing description", Toast.LENGTH_SHORT).show();
                            break;
                        case 4:
                            //Toast.makeText(ExerciseSelection.this, "Deadlift description", Toast.LENGTH_SHORT).show();
                            Intent intentDeadlift = new Intent(ExerciseSelection.this, ExerciseConfiguration.class);
                            intentDeadlift.putExtra("idVjezbe",objektiVjezbi.get(position).getId());
                            intentDeadlift.putExtra("naziv",objektiVjezbi.get(position).getNaziv());
                            startActivity(intentDeadlift);
                            break;
                        case 5:
                            //Toast.makeText(ExerciseSelection.this, "Shoulder press description", Toast.LENGTH_SHORT).show();
                            Intent intentShoulderPress = new Intent(ExerciseSelection.this, ExerciseConfiguration.class);
                            intentShoulderPress.putExtra("idVjezbe",objektiVjezbi.get(position).getId());
                            intentShoulderPress.putExtra("naziv",objektiVjezbi.get(position).getNaziv());
                            startActivity(intentShoulderPress);
                            break;
                        case 6:
                            //Toast.makeText(ExerciseSelection.this, "Bench press description", Toast.LENGTH_SHORT).show();
                            Intent intentBenchPress = new Intent(ExerciseSelection.this, ExerciseConfiguration.class);
                            intentBenchPress.putExtra("idVjezbe",objektiVjezbi.get(position).getId());
                            intentBenchPress.putExtra("naziv",objektiVjezbi.get(position).getNaziv());
                            startActivity(intentBenchPress);
                            break;
                        case 7:
                            //Toast.makeText(ExerciseSelection.this, "Squat description", Toast.LENGTH_SHORT).show();
                            Intent intentSquat = new Intent(ExerciseSelection.this, ExerciseConfiguration.class);
                            intentSquat.putExtra("idVjezbe",objektiVjezbi.get(position).getId());
                            intentSquat.putExtra("naziv",objektiVjezbi.get(position).getNaziv());
                            startActivity(intentSquat);
                            break;
                        case 8:
                            //Toast.makeText(ExerciseSelection.this, "Biking description", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            });

    }

    private void setIconForExercise(){
        for(Vjezba vjezba : objektiVjezbi ){
            Log.e("NazivVjezbe",vjezba.getNaziv());
            switch (vjezba.getNaziv()){
                case "Plan exercises description":
                    vjezba.setIkona(R.drawable.ic_history);
                    MyDatabase.getInstance(getApplicationContext()).getVjezbaDAO().azuriranjeVjezbe(vjezba);
                    break;
                case "Running":
                    vjezba.setIkona(R.drawable.ic_running);
                    MyDatabase.getInstance(getApplicationContext()).getVjezbaDAO().azuriranjeVjezbe(vjezba);
                    break;
                case "Walking":
                    vjezba.setIkona(R.drawable.ic_person_walking);
                    MyDatabase.getInstance(getApplicationContext()).getVjezbaDAO().azuriranjeVjezbe(vjezba);
                    break;
                case "Rowing":
                    vjezba.setIkona(R.drawable.ic_man_in_canoe);
                    MyDatabase.getInstance(getApplicationContext()).getVjezbaDAO().azuriranjeVjezbe(vjezba);
                    break;
                case "Deadlift":
                    vjezba.setIkona(R.drawable.ic_olympic_weightlifting_);
                    MyDatabase.getInstance(getApplicationContext()).getVjezbaDAO().azuriranjeVjezbe(vjezba);
                    break;
                case "Shoulder press":
                    vjezba.setIkona(R.drawable.ic_weightlifting);
                    MyDatabase.getInstance(getApplicationContext()).getVjezbaDAO().azuriranjeVjezbe(vjezba);
                    break;
                case "Bench press":
                    vjezba.setIkona(R.drawable.ic_upper_chest_training);
                    MyDatabase.getInstance(getApplicationContext()).getVjezbaDAO().azuriranjeVjezbe(vjezba);
                    break;
                case "Squating":
                    vjezba.setIkona(R.drawable.ic_exercising_man);
                    MyDatabase.getInstance(getApplicationContext()).getVjezbaDAO().azuriranjeVjezbe(vjezba);
                    break;
                case "Biking":
                    vjezba.setIkona(R.drawable.ic_bicycle_rider);
                    MyDatabase.getInstance(getApplicationContext()).getVjezbaDAO().azuriranjeVjezbe(vjezba);
                    break;
            }
        }
    }

    public static View getToolbarLogoView(Toolbar toolbar) {
        boolean hadContentDescription = android.text.TextUtils.isEmpty(toolbar.getLogoDescription());
        String contentDescription = String.valueOf(!hadContentDescription ? toolbar.getLogoDescription() : "logoContentDescription");
        toolbar.setLogoDescription(contentDescription);
        ArrayList<View> potentialViews = new ArrayList<View>();
        toolbar.findViewsWithText(potentialViews, contentDescription, View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION);
        View logoIcon = null;

        if (potentialViews.size() > 0) {
            logoIcon = potentialViews.get(0);
        }

        if (hadContentDescription)
            toolbar.setLogoDescription(null);

        return logoIcon;
    }

}