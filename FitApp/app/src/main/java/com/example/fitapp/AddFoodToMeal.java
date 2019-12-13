package com.example.fitapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.barkod.fragments.BarkodFragment;
import com.example.core.entities.Namirnica;
import com.example.fitapp.adapters.NamirniceAdapter;
import com.example.fitapp.managers.NamirnicaManager;
import com.example.repository.NamirnicaDAL;
import com.example.unos_hrane.Add_new_food;

import java.util.ArrayList;
import java.util.List;

import RetroEntities.RetroNamirnica;
import adapter.CurrentActivity;
import adapter.CurrentFood;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddFoodToMeal extends AppCompatActivity implements Add_new_food.OnFragmentInteractionListener, BarkodFragment.OnFragmentInteractionListener, BarkodFragment.onScannedObjectListener{

    private ImageView slikaBarkoda;
    private EditText nazivNamirnice;
    private RecyclerView recyclerView;
    private String nazivObroka;
    private String datumObroka;
    private Bundle prosljedeniPodaci;
    private NamirnicaManager namirnicaManager;
    private Button button;
    //private RelativeLayout layout;
    //private FrameLayout frameLayout;

    private NamirniceAdapter namirniceAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_to_meal);

        Toolbar toolbar = findViewById(R.id.toolbarAddFood);
        setSupportActionBar(toolbar);

        //layout = findViewById(R.id.activity_add_food_to_meal);
        //frameLayout = findViewById(R.id.fragmentModul);

        View logoView = getToolbarLogoView(toolbar);
        logoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Intent i = new Intent(AddFoodToMeal.this, FoodDiary.class);
                    startActivity(i);
                }
                catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }
        });

        slikaBarkoda = findViewById(R.id.picBarcode);
        nazivNamirnice = findViewById(R.id.txtNazivNamirnice);

        prosljedeniPodaci = getIntent().getExtras();
        nazivObroka = prosljedeniPodaci.getString("Obrok");
        datumObroka = prosljedeniPodaci.getString("Datum");
        CurrentActivity.setActivity(AddFoodToMeal.this);
        namirnicaManager = NamirnicaManager.getINSTANCE();
        namirnicaManager.setupManager(this);

        slikaBarkoda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BarkodFragment barkodFragment = new BarkodFragment();
                barkodFragment.setArguments(prosljedeniPodaci);
                namirnicaManager.startModule(barkodFragment);

                //layout.setVisibility(View.GONE);
                //frameLayout.setVisibility(View.VISIBLE);
                /*getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack("BarkodFragment")
                        .replace(R.id.fragmentModul, barkodFragment)
                        .commit();

                 */
            }
        });


        button = findViewById(R.id.btnUnosNoveNamirnice);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "test", Toast.LENGTH_LONG).show();

                /*
                Add_new_food anfFragment = new Add_new_food();
                anfFragment.setArguments(prosljedeniPodaci);
                getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack("AddNewFood")
                        .replace(R.id.fragmentModul, anfFragment)
                        .commit();

                 */
                Add_new_food anfFragment = new Add_new_food();
                anfFragment.setArguments(prosljedeniPodaci);
                namirnicaManager.startModule(anfFragment);
                //layout.setVisibility(View.GONE);
                //frameLayout.setVisibility(View.VISIBLE);

            }
        });
    }

        private void popuniSadrzajPretrage () {
            recyclerView = findViewById(R.id.recyclerViewPretrazeneNamirnice);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            namirniceAdapter = new NamirniceAdapter(datumObroka,nazivObroka);
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

    public static View getToolbarLogoView(Toolbar toolbar){
        boolean hadContentDescription = android.text.TextUtils.isEmpty(toolbar.getLogoDescription());
        String contentDescription = String.valueOf(!hadContentDescription ? toolbar.getLogoDescription() : "logoContentDescription");
        toolbar.setLogoDescription(contentDescription);
        ArrayList<View> potentialViews = new ArrayList<View>();
        toolbar.findViewsWithText(potentialViews,contentDescription, View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION);
        View logoIcon = null;

        if(potentialViews.size() > 0){
            logoIcon = potentialViews.get(0);
        }

        if(hadContentDescription)
            toolbar.setLogoDescription(null);

        return logoIcon;
    }

    @Override
    protected void onResume() {
        super.onResume();
        popuniSadrzajPretrage();
        }


    @Override
    public void onFragmentInteraction(boolean signalGotovo) {
        if(signalGotovo){
            onBackPressed();
            finish();
        }
    }

    @Override
    public void onScannedCompleteInteraction(Namirnica namirnica) {
        Intent i = new Intent(AddFoodToMeal.this,AddSelectedFood.class);
        Bundle bundle = new Bundle();
        bundle.putInt("idNamirnice", namirnica.getId());
        bundle.putString("Obrok",nazivObroka);
        bundle.putString("Datum",datumObroka);
        i.putExtras(bundle);
        startActivity(i);
    }
}
