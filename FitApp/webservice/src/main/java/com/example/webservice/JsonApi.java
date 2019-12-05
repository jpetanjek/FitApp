package com.example.webservice;

import java.util.List;

import RetroEntities.RetroKorisnik;
import RetroEntities.RetroNamirnica;
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
    Call<Void> azurirajKorisnika(@Part("identifikator") int idKorisnika,
                                 @Part("atribut") RequestBody atribut,
                                 @Part("vrijednost") RequestBody vrijednost);

    @Multipart
    @POST("korisnik.php?query=insert")
    Call<Void> unesiKorisnika (@Part("ime") String ime,
                               @Part("prezime") String prezime,
                               @Part("google_id") String googleId,
                               @Part("email") String email,
                               @Part("visina") Float visina,
                               @Part("masa") Float masa,
                               @Part("cilj_mase") Float ciljMase,
                               @Part("cilj_tjednog_mrsavljenja") Float ciljTjednogMrsavljenja,
                               @Part("spol") String spol,
                               @Part("datum_rodenja") String datumRodenja);

    @Multipart
    @POST("namirnica.php?query=update")
    Call<Void> azurirajNamirnicu(
            @Part("identifikator") Integer IdNamirnica,
            @Part("atribut") RequestBody atribut,
            @Part("vrijednost") RequestBody vrijednost
    );
    @Multipart
    @POST("namirnica.php?query=delete")
    Call<Void> izbrisiNamirnicu(
            @Part("identifikator") Integer idNamirnice
    );

    @GET("namirnica.php?query=getAll")
    Call<List<RetroNamirnica>> dohvatiSveNamirnice();

    @GET("namirnica.php?query=getById")
    Call<RetroNamirnica> dohvatiNamirnicu(@Query("namirnica") Integer idNamirnice);

    @Multipart
    @POST("namirnica.php?query=insert")
    Call<Void> unesiNamirnicu(
            @Part("naziv") String naziv,
            @Part("broj_kalorija") Integer brojKalorija,
            @Part("tezina") Integer tezina,
            @Part("isbn") String isbn
    );

    @GET("namirnica.php?query=getByGoogleId")
    Call<RetroNamirnica> dohvatiNamirnicuPoISBN(@Query("namirnica") String isbn);

    @GET("namirnica.php?query=getByName")
    Call<List<RetroNamirnica>> dohvatiNamirnicePoImenu(@Query("naziv") String nazivNamirnice);
}
