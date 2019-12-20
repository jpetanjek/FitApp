package com.example.fitapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.widget.Toast;

import static com.example.database.MyDatabase.getInstance;

import com.example.core.entities.Korisnik;
import com.example.core.entities.Namirnica;
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
import java.util.concurrent.TimeUnit;

import RetroEntities.RetroKorisnik;
import RetroEntities.RetroNamirnica;
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

        if(!isNetworkAvailable(this)) {
            Toast.makeText(this,"No Internet connection. Connect to an available network and try again.",Toast.LENGTH_LONG).show();
            finish(); //Calling this method to close this activity when internet is not available.
        }
        
        dohvatiSveNamirnice();

        /*
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        System.out.println("onCreate - MainActivity");
        if(GoogleSignIn.getLastSignedInAccount(this)==null){
            //ako korisnik nije prijavljen pomocu googleSignIn
            //obavi prijavu i registriraj ga ako ne postoji u bazi
            signIn();

        }else{
            //ako je korisnik prijavljen pomocu googleSignIn
            //prebaci ga na glavni izbornik
            account = GoogleSignIn.getLastSignedInAccount(this);

            Intent intent = new Intent(MainActivity.this, Glavni_Izbornik.class);
            startActivity(intent);




            //ispod je samo ispis testnih podataka u logcat




            Retrofit retrofit = RetrofitInstance.getInstance();

            JsonApi jsonApi = retrofit.create(JsonApi.class);

            Call<RetroKorisnik> poziv = jsonApi.dohvatiKorisnika(account.getId());
*/

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
            });
        }*/
    }

    private void signIn() {
        //Ovo pokrece fragmente od GoogleSignIn integracije, kada se zavrsi pokrece se onActivityResult
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
        System.out.println("signIn");
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        try {
            account = GoogleSignIn.getLastSignedInAccount(this);
        }catch (NullPointerException e){
            System.out.println(e.getMessage());
        }
        System.out.println("onStart - MainActivity");
        if(account!=null) {
            System.out.println("onStart - account!=null");
            //System.out.println(account.getId());
            WebServisProvjera();
        }else {
            signIn();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            account = GoogleSignIn.getLastSignedInAccount(this);
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
            //System.out.println("onActivityResult");
            //System.out.println(account.getId());
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            //Intent intent = new Intent(MainActivity.this, Registracija.class);
            //startActivity(intent);
            System.out.println("handleSignIn");
            //System.out.println(account.getId());

            // Signed in successfully, show authenticated UI. Prvi put kada se instalira aplikacija
            WebServisProvjera();

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Error", "signInResult:failed code=" + e.getStatusCode());
            System.out.println("Fail sign in");
            signIn();
        }
    }

    private void WebServisProvjera(){
        // Ovdje provjeravamo da li je vec registriran (na web servisu  provjeri da li postoji neko sa istim google id)

        System.out.println("WebServisProvjera");

        Retrofit retrofit = RetrofitInstance.getInstance();

        JsonApi jsonApi = retrofit.create(JsonApi.class);

        Call<RetroKorisnik> poziv = jsonApi.dohvatiKorisnika(account.getId());

        System.out.println(account.getId());

        poziv.enqueue(new Callback<RetroKorisnik>() {
            @Override
            public void onResponse(Call<RetroKorisnik> call, Response<RetroKorisnik> response) {
                System.out.println("Response");
                if(response.body().getGoogle_id()!=null){
                    // dodaj korisnika u lokalnu bazu

                    Korisnik korisnik = new Korisnik();
                    korisnik = korisnik.parseKorisnik(response);
                    long[] odgovor = getInstance(MainActivity.this).getKorisnikDAO().unosKorisnika(korisnik);

                    // ako je posalji ga na glavni izbornik
                    System.out.println("Registriran je");
                    Intent intent2 = new Intent(MainActivity.this, Glavni_Izbornik.class);
                    startActivity(intent2);
                }
            }

            @Override
            public void onFailure(Call<RetroKorisnik> call, Throwable t) {
                System.out.println("Fail");
                System.out.println("Nije registriran");
                Intent intent3 = new Intent(MainActivity.this, Registracija.class);
                startActivityForResult(intent3,0);
            }
        });
    }

    public void dohvatiSveNamirnice(){

        Retrofit retrofit = RetrofitInstance.getInstance();
        JsonApi jsonApi = retrofit.create(JsonApi.class);
        Call<List<RetroNamirnica>> poziv = jsonApi.dohvatiSveNamirnice();

        poziv.enqueue(new Callback<List<RetroNamirnica>>() {
            @Override
            public void onResponse(Call<List<RetroNamirnica>> call, Response<List<RetroNamirnica>> response) {
                for(RetroNamirnica r: response.body()){
                    Namirnica namirnica = new Namirnica();
                    namirnica = namirnica.parseNamirnica(r);

                    MyDatabase.getInstance(MainActivity.this).getNamirnicaDAO().unosNamirnica(namirnica);
                }
            }

            @Override
            public void onFailure(Call<List<RetroNamirnica>> call, Throwable t) {
            }
        });

    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(conMan.getActiveNetworkInfo() != null && conMan.getActiveNetworkInfo().isConnected())
            return true;
        else
            return false;
    }

}

