package com.example.repository;

import android.content.Context;

import com.example.core.entities.Korisnik;
import static com.example.database.MyDatabase.getInstance;
import com.example.database.MyDatabase;
import com.example.webservice.JsonApi;
import com.example.webservice.RetrofitInstance;

import RetroEntities.RetroKorisnik;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class KorisnikDAL {

    public static Korisnik Trenutni(Context context){
      MyDatabase myDatabase = MyDatabase.getInstance(context);

      Korisnik returnme = new Korisnik();
      returnme = myDatabase.getKorisnikDAO().dohvatiKorisnika();

        System.out.println(returnme);

      return returnme;
    };

    public static void Azuriraj(Context context,String atribut, String vrijednost){

        Retrofit retrofit = RetrofitInstance.getInstance();
        JsonApi jsonApi = retrofit.create(JsonApi.class);
        jsonApi.azurirajKorisnika(
                Trenutni(context).getId(),
                RequestBody.create(MediaType.parse("text/plain"), atribut),
                RequestBody.create(MediaType.parse("text/plain"), vrijednost)
                //31,
                //RequestBody.create(MediaType.parse("text/plain"), "prezime"),
                //RequestBody.create(MediaType.parse("text/plain"), "Bajk333")
        ).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });

        Korisnik korisnik = Trenutni(context);
        switch(atribut){
            case "datum_rodenja":
                korisnik.setDatumRodenja(vrijednost);
                break;
            case "visina":
                korisnik.setVisina(Float.parseFloat(vrijednost));
                break;
            case "masa":
                korisnik.setMasa(Float.parseFloat(vrijednost));
                break;
            case "cilj_mase":
                korisnik.setCilj_mase(Float.parseFloat(vrijednost));
                break;
            case "cilj_tjednog_mrsavljenja":
                korisnik.setCilj_tjednog_mrsavljenja(Float.parseFloat(vrijednost));
                break;
            case "spol":
                korisnik.setSpol(vrijednost);
                break;
        }

        MyDatabase myDatabase = MyDatabase.getInstance(context);
        myDatabase.getKorisnikDAO().azuriranjeKorisnika(korisnik);

    }
    public static void Kreiraj(Korisnik korisnik,Context context){
        Retrofit retrofit = RetrofitInstance.getInstance();
        JsonApi jsonApi = retrofit.create(JsonApi.class);
        jsonApi.unesiKorisnika(
                korisnik.getIme(),
                korisnik.getPrezime(),
                korisnik.getGoogle_id(),
                korisnik.getEmail(),
                korisnik.getVisina(),
                korisnik.getMasa(),
                korisnik.getCilj_mase(),
                korisnik.getCilj_tjednog_mrsavljenja(),
                korisnik.getSpol(),
                korisnik.getDatumRodenja()
        ).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });

        MyDatabase myDatabase = MyDatabase.getInstance(context);
        myDatabase.getKorisnikDAO().unosKorisnika(korisnik);
    }
    public static void IzbrisiKorisnikaLokalno(Context context){
        getInstance(context).getKorisnikDAO().brisanjeKorisnika();
    }
}
