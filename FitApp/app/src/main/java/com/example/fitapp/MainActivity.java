package com.example.fitapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;

import static com.example.database.MyDatabase.getInstance;

import com.example.core.entities.Korisnik;
import com.example.database.KorisnikDAO;
import com.example.database.MyDatabase;
import com.example.registracija.Registracija;
import com.example.webservice.JsonApi;
import com.example.webservice.RetrofitInstance;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.util.List;

import RetroEntities.RetroKorisnik;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    GoogleSignInClient mGoogleSignInClient;
    int RC_SIGN_IN = 0;

    GoogleSignInAccount account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        if(GoogleSignIn.getLastSignedInAccount(this)==null){
            //ako korisnik nije prijavljen pomocu googleSignIn
            //obavi prijavu i registriraj ga ako ne postoji u bazi
            signIn();

        }else{
            //ako je korisnik prijavljen pomocu googleSignIn
            //prebaci ga na glavni izbornik

            Intent intent = new Intent(MainActivity.this, Glavni_Izbornik.class);
            startActivity(intent);


            //ispod je samo ispis testnih podataka u logcat
            System.out.println("onCreate");

            Retrofit retrofit = RetrofitInstance.getInstance();

            JsonApi jsonApi = retrofit.create(JsonApi.class);

            Call<RetroKorisnik> poziv = jsonApi.dohvatiKorisnika(account.getId());


            //ovo je lokalna baza
            //MyDatabase myDatabase = getInstance(this);

            //za napraviti parse iz RetroKorisnik u DaoKorisnik

            //spremiti dohvacenog korisnika u lokalnu bazu
            //myDatabase.getDAO().unosKorisnika();

            //svi korisnici
            /*Call<List<RetroKorisnik>> poziv = jsonApi.dohvatiSveKorisnike();

            poziv.enqueue(new Callback<List<RetroKorisnik>>() {
                @Override
                public void onResponse(Call<List<RetroKorisnik>> call, Response<List<RetroKorisnik>> response) {
                    for(RetroKorisnik R : response.body()){
                        System.out.println(R.getIme());
                    }
                }

                @Override
                public void onFailure(Call<List<RetroKorisnik>> call, Throwable t) {
                    System.out.println("Test2");
                }
            });*/
        }
    }

    private void signIn() {
        //Ovo pokrece fragmente od GoogleSignIn integracije, kada se zavrsi pokrece se onActivityResult
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        account = GoogleSignIn.getLastSignedInAccount(this);
        if(account!=null) {
            System.out.println("onStart");
            System.out.println(account.getId());
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);


            System.out.println("onActivityResult");
            System.out.println(account.getId());
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI. Prvi put kada se instalira aplikacija
            // Ovdje provjeravamo da li je vec registriran (na web servisu  provjeri da li postoji neko sa istim google id)

            Retrofit retrofit = RetrofitInstance.getInstance();

            JsonApi jsonApi = retrofit.create(JsonApi.class);

            Call<RetroKorisnik> poziv = jsonApi.dohvatiKorisnika(account.getId());

            poziv.enqueue(new Callback<RetroKorisnik>() {
                @Override
                public void onResponse(Call<RetroKorisnik> call, Response<RetroKorisnik> response) {
                    if(response.body()!=null){
                        // ako je posalji ga na glavni izbornik


                        // dodaj korisnika u lokalnu bazu
                        Korisnik korisnik = new Korisnik();
                        korisnik.parseKorisnik(response);
                        long[] odgovor = getInstance(getApplicationContext()).getKorisnikDAO().unosKorisnika(korisnik);

                        Intent intent = new Intent(MainActivity.this, Glavni_Izbornik.class);
                        startActivity(intent);
                    }else{
                        // ako nije posalji ga na registraciju
                        // ali prije ga dodaj u lokalnu bazu
                        Intent intent = new Intent(MainActivity.this, Registracija.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<RetroKorisnik> call, Throwable t) {
                    //ako poziv ne uspije znaci da je web servis mrtav
                }
            });










        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Error", "signInResult:failed code=" + e.getStatusCode());
        }
    }

}

