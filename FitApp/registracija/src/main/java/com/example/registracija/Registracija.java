package com.example.registracija;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class Registracija extends AppCompatActivity {

    ImageView imageView;
    TextView email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registracija);

        //imageView = findViewById(R.id.profile_image);
        imageView = findViewById(R.id.profile_image);
        email = findViewById(R.id.tvMail);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);

        if (acct != null) {
            String personEmail = acct.getEmail();
            Uri personPhoto = acct.getPhotoUrl();


            email.setText(personEmail);
            Glide.with(this).load(String.valueOf(personPhoto)).into(imageView);
        }
    }

    //Po kliku gumba za registriranje, dodajemo novog korisnika sa
    /*
    Retrofit retrofit = RetrofitInstance.getInstance();
    JsonApi jApi = retrofit.create(JsonApi.class);
    RetroKorisnik noviKorisnik = new RetroKorisnik();
            noviKorisnik.setIme("Matej");
            noviKorisnik.setPrezime("Loncar");
            noviKorisnik.setEmail("mloncar@foi.hr");
            noviKorisnik.setVisina(Float.parseFloat("180.0"));
            noviKorisnik.setRazinaAktivnosti(1);
            noviKorisnik.setCiljMase(180);
            noviKorisnik.setCiljTjednogMrsavljenja(Float.parseFloat("0.5"));
            noviKorisnik.setSpol("M");
            noviKorisnik.setDatumRodenja("01/12/1998");
            Call<Void> poziv = jApi.unesiKorisnika(noviKorisnik.getIme(),noviKorisnik.getPrezime(),acct.getId(),noviKorisnik.getEmail(),
                    noviKorisnik.getVisina(),noviKorisnik.getRazinaAktivnosti(),noviKorisnik.getCiljMase(),
                    noviKorisnik.getCiljTjednogMrsavljenja(),noviKorisnik.getSpol(),noviKorisnik.getDatumRodenja());
            poziv.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if(response.isSuccessful()){
                        System.out.println("Zapisan");
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    System.out.println("Ne radi");
                }
            });

            //još ga nadodati u lokalnu bazu¸¸
     */
}
