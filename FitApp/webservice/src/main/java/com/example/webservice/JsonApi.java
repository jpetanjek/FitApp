package com.example.webservice;

import java.util.List;

import RetroEntities.RetroKorisnik;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JsonApi {
    @GET("korisnik.php?query=getAll")
    Call<List<RetroKorisnik>> dohvatiSveKorisnike();

    @GET("korisnik.php?query=getById")
    Call<RetroKorisnik> dohvatiKorisnika(@Query("user") String googleId);

    @Multipart
    @POST("korisnik.php?query=update")
    Call<String> azurirajKorisnika(@Part("identifikator") int idKorisnika,
                                 @Part("atribut") RequestBody atribut,
                                 @Part("vrijednost") RequestBody vrijednost);

    @Multipart
    @POST("korisnik.php?query=insert")
    Call<Void> unesiKorisnika (@Part("ime") String ime,
                               @Part("prezime") String prezime,
                               @Part("google_id") String googleId,
                               @Part("email") String email,
                               @Part("visina") Float visina,
                               @Part("razina_aktivnosti") Integer razina_aktivnosti,
                               @Part("cilj_mase") Integer ciljMase,
                               @Part("cilj_tjednog_mrsavljenja") Float ciljTjednogMrsavljenja,
                               @Part("spol") String spol,
                               @Part("datum_rodenja") String datumRodenja);

}
