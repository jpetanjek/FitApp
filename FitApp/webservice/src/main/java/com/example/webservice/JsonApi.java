package com.example.webservice;

import java.util.List;

import RetroEntities.RetroKorisnik;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface JsonApi {
    @GET("korisnik.php?query=getAll")
    Call<List<RetroKorisnik>> dohvatiSveKorisnike();

    @GET("korisnik.php?query=getById&user={googleId}")
    Call<RetroKorisnik> dohvatiKorisnika(@Path("googleId") String googleId);

    @POST("korisnik.php?query=update")
    Call<RetroKorisnik> azurirajKorisnika(@Field("identifikator") int idKorisnika, @Field("atribut") String atribut, @Field("vrijednost") String vrijednost);
}
