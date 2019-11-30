package com.example.repository;

import android.content.Context;

import com.example.core.entities.Korisnik;
import com.example.database.KorisnikDAO;
import com.example.database.MyDatabase;
import com.example.webservice.JsonApi;
import com.example.webservice.RetrofitInstance;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
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
}
