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
import com.example.core.entities.Vjezba;
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
        ProvjeriPostojanostVjezbi();
    }

    private void ProvjeriPostojanostVjezbi() {
        if(MyDatabase.getInstance(this).getVjezbaDAO().dohvatiBrojVjezbi()==0){
            Vjezba newVjezba = new Vjezba();
            newVjezba.setId(1);
            newVjezba.setMet(new Float(0));
            newVjezba.setNaziv("Plan exercises description");
            newVjezba.setTipVjezbe(1);
            newVjezba.setUpute("");
            newVjezba.setRepetition_lenght(5);
            MyDatabase.getInstance(this).getVjezbaDAO().unosVjezbe(newVjezba);

            Vjezba newVjezba1 = new Vjezba();
            newVjezba1.setId(2);
            newVjezba1.setMet(new Float(13.5));
            newVjezba1.setNaziv("Running");
            newVjezba1.setTipVjezbe(1);
            newVjezba1.setRepetition_lenght(5);
            newVjezba1.setUpute("Good runners condition their whole bodies. The arms drive the legs.");
            newVjezba1.setRepetition_lenght(5);
            MyDatabase.getInstance(this).getVjezbaDAO().unosVjezbe(newVjezba1);

            /*
            Vjezba newVjezba2 = new Vjezba();
            newVjezba2.setId(3);
            newVjezba2.setMet(new Float(4.8));
            newVjezba2.setNaziv("Walking");
            newVjezba2.setTipVjezbe(1);
            newVjezba2.setRepetition_lenght(5);
            newVjezba2.setUpute("Start slowly, do a few warm-up exercises and stretches first. Don't walk immediately after a big meal. Start with a 20 minute walk then increase gradually.");
            newVjezba2.setRepetition_lenght(5);
            MyDatabase.getInstance(this).getVjezbaDAO().unosVjezbe(newVjezba2);

            Vjezba newVjezba3= new Vjezba();
            newVjezba3.setId(4);
            newVjezba3.setMet(new Float(6));
            newVjezba3.setNaziv("Rowing");
            newVjezba3.setTipVjezbe(1);
            newVjezba3.setRepetition_lenght(5);
            newVjezba3.setUpute("Drive With Your Legs.");
            newVjezba3.setRepetition_lenght(5);
            MyDatabase.getInstance(this).getVjezbaDAO().unosVjezbe(newVjezba3);
            */
            Vjezba newVjezba4= new Vjezba();
            newVjezba4.setId(5);
            newVjezba4.setMet(new Float(6));
            newVjezba4.setNaziv("Deadlift");
            newVjezba4.setTipVjezbe(1);
            newVjezba4.setRepetition_lenght(5);
            newVjezba4.setUpute("Bend your knees until your shins touch the bar. Lift your chest up and straighten your lower back. Take a big breath, hold it, and stand up with the weight.");
            newVjezba4.setRepetition_lenght(5);
            MyDatabase.getInstance(this).getVjezbaDAO().unosVjezbe(newVjezba4);

            Vjezba newVjezba5= new Vjezba();
            newVjezba5.setId(6);
            newVjezba5.setMet(new Float(6));
            newVjezba5.setNaziv("Shoulder press");
            newVjezba5.setTipVjezbe(1);
            newVjezba5.setRepetition_lenght(5);
            newVjezba5.setUpute("Once the bar clears your head, push your shoulders back, move your head slightly forward and lock-out the bar directly over the top of your head.");
            newVjezba5.setRepetition_lenght(5);
            MyDatabase.getInstance(this).getVjezbaDAO().unosVjezbe(newVjezba5);

            Vjezba newVjezba6= new Vjezba();
            newVjezba6.setId(7);
            newVjezba6.setMet(new Float(6));
            newVjezba6.setNaziv("Bench press");
            newVjezba6.setTipVjezbe(1);
            newVjezba6.setRepetition_lenght(5);
            newVjezba6.setUpute("Keep your chest up throughout the movement. Elbows should be tucked and end up at approximately 45 degrees from your side.");
            newVjezba6.setRepetition_lenght(5);
            MyDatabase.getInstance(this).getVjezbaDAO().unosVjezbe(newVjezba6);

            Vjezba newVjezba7= new Vjezba();
            newVjezba7.setId(8);
            newVjezba7.setMet(new Float(5));
            newVjezba7.setNaziv("Squating");
            newVjezba7.setTipVjezbe(1);
            newVjezba7.setRepetition_lenght(5);
            newVjezba7.setUpute("Squat down by pushing your knees to the side while moving hips back. Break parallel by Squatting down until your hips are lower than your knees. Stand with your hips and knees locked at the top.");
            newVjezba7.setRepetition_lenght(5);
            MyDatabase.getInstance(this).getVjezbaDAO().unosVjezbe(newVjezba7);

            /*
            Vjezba newVjezba8= new Vjezba();
            newVjezba8.setId(9);
            newVjezba8.setMet(new Float(7.5));
            newVjezba8.setNaziv("Biking");
            newVjezba8.setTipVjezbe(1);
            newVjezba8.setRepetition_lenght(5);
            newVjezba8.setUpute("Don't pedal in high gear for extended periods of time. Keep your head up.");
            newVjezba8.setRepetition_lenght(5);
            MyDatabase.getInstance(this).getVjezbaDAO().unosVjezbe(newVjezba8);
            System.out.println("Kreirane vjezbe");
             */
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

