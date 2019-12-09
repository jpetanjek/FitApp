package com.example.fitapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.barkod.fragments.BarkodFragment;
import com.example.core.entities.Namirnica;
import com.example.fitapp.adapters.FoodDiaryAdapter;
import com.example.fitapp.adapters.NamirniceAdapter;
import com.example.fitapp.adapters.NamirniceObrokaAdapter;
import com.example.repository.NamirnicaDAL;

import java.util.ArrayList;
import java.util.List;

import RetroEntities.RetroNamirnica;
import adapter.CurrentActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddFoodToMeal extends AppCompatActivity {

    private ImageView slikaBarkoda;
    private EditText nazivNamirnice;
    private RecyclerView recyclerView;
    private String nazivObroka;
    private Bundle prosljedeniPodaci;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_to_meal);
        slikaBarkoda = findViewById(R.id.picBarcode);
        nazivNamirnice = findViewById(R.id.txtNazivNamirnice);
        prosljedeniPodaci = getIntent().getExtras();
        nazivObroka = prosljedeniPodaci.getString("Obrok");
        CurrentActivity.setActivity(AddFoodToMeal.this);
        slikaBarkoda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(AddFoodToMeal.this,BarkodSkenerActivity.class);
                i.putExtras(prosljedeniPodaci);
                startActivity(i);

            }
        });
        Button button = findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent i = new Intent(AddFoodToMeal.this,ModularActivity.class);
                //i.putExtras(prosljedeniPodaci);
                //startActivity(i);
            }
        });
    }

    private void popuniSadrzajPretrage(){
        recyclerView = findViewById(R.id.recyclerViewPretrazeneNamirnice);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final NamirniceAdapter namirniceAdapter = new NamirniceAdapter();
        recyclerView.setAdapter(namirniceAdapter);
        namirniceAdapter.setContext(this);
        nazivNamirnice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                NamirnicaDAL.DohvatiNamirniceSlicnogNaziva(nazivNamirnice.getText().toString(), new Callback<List<RetroNamirnica>>() {
                    @Override
                    public void onResponse(Call<List<RetroNamirnica>> call, Response<List<RetroNamirnica>> response) {
                        if(response.isSuccessful()){
                            List<Namirnica> dohvaceneNamirnice = new ArrayList<>();
                            for(RetroNamirnica namirnica:response.body()){
                                dohvaceneNamirnice.add(Namirnica.parseStaticNamirnica(namirnica));
                            }
                            namirniceAdapter.setNamirnice(dohvaceneNamirnice);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<RetroNamirnica>> call, Throwable t) {

                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        popuniSadrzajPretrage();
    }
}
