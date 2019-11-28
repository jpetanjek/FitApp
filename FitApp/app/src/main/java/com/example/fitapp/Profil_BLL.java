package com.example.fitapp;

import com.example.webservice.JsonApi;
import com.example.webservice.RetrofitInstance;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class Profil_BLL {

    // Prima ID korisnika i atribut kojeg želimo ažurirati
    // Funkcija ažurira bazu podataka sa primljenom vrijednošću
     public static void AzurirajKorisnika(int idKorisnika, String atribut, String vrijednost){
        Retrofit retrofit = RetrofitInstance.getInstance();
        JsonApi jsonApi = retrofit.create(JsonApi.class);
        jsonApi.azurirajKorisnika(
                idKorisnika,
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
