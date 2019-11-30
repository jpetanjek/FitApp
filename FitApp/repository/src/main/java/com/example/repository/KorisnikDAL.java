package com.example.repository;

import android.content.Context;

import com.example.core.entities.Korisnik;
import static com.example.database.MyDatabase.getInstance;
import com.example.database.MyDatabase;
import com.example.webservice.JsonApi;
import com.example.webservice.RetrofitInstance;

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
        ).enqueue(new Callback<String>()
                  {
                      @Override
                      public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                      }

                      @Override
                      public void onFailure(Call<String> call, Throwable t) {
                      }
                  }
        );
    }
    public static void Kreiraj(Korisnik korisnik){
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
    }
    public static void IzbrisiKorisnikaLokalno(Context context){
        getInstance(context).getKorisnikDAO().brisanjeKorisnika();
    }
}
