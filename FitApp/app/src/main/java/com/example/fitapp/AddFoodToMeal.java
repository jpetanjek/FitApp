package com.example.fitapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.barkod.fragments.BarkodFragment;
import com.example.core.entities.Namirnica;
import com.example.fitapp.adapters.NamirniceAdapter;
import com.example.repository.NamirnicaDAL;
import com.example.unos_hrane.Add_new_food;

import java.util.ArrayList;
import java.util.List;

import RetroEntities.RetroNamirnica;
import adapter.CurrentActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddFoodToMeal extends AppCompatActivity {

    private ImageView slikaBarkoda;
    private EditText nazivNamirnice;
    private RecyclerView recyclerView;
    private String nazivObroka;
    private Bundle prosljedeniPodaci;

    private Button button;



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
                BarkodFragment barkodFragment = new BarkodFragment();
                barkodFragment.setArguments(prosljedeniPodaci);
                getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack("BarkodFragment")
                        .replace(R.id.fragmentModul, barkodFragment)
                        .commit();
            }
        });


        button = findViewById(R.id.btnUnosNoveNamirnice);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "test", Toast.LENGTH_LONG).show();

                Add_new_food anfFragment = new Add_new_food();
                anfFragment.setArguments(prosljedeniPodaci);
                getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack("AddNewFood")
                        .replace(R.id.fragmentModul, anfFragment)
                        .commit();
            }
        });
    }

        private void popuniSadrzajPretrage () {
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
                            if (response.isSuccessful()) {
                                List<Namirnica> dohvaceneNamirnice = new ArrayList<>();
                                for (RetroNamirnica namirnica : response.body()) {
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
