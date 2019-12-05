package com.example.repository;

import android.content.Context;

import com.example.core.entities.Korisnik;
import com.example.core.entities.Namirnica;
import com.example.database.MyDatabase;
import com.example.webservice.JsonApi;
import com.example.webservice.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import RetroEntities.RetroNamirnica;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.example.database.MyDatabase.getInstance;

public class NamirnicaDAL {

    public static Namirnica DohvatiWeb(Integer identifikator){
        final Namirnica[] returnme = {new Namirnica()};

        Retrofit retrofit = RetrofitInstance.getInstance();
        JsonApi jsonApi = retrofit.create(JsonApi.class);
        jsonApi.dohvatiNamirnicu(identifikator).enqueue(new Callback<RetroNamirnica>() {
            @Override
            public void onResponse(Call<RetroNamirnica> call, Response<RetroNamirnica> response) {

                returnme[0] = returnme[0].parseNamirnica(response.body());
            }

            @Override
            public void onFailure(Call<RetroNamirnica> call, Throwable t) {

            }
        });

        return returnme[0];
    }

    public static Namirnica DohvatiLokalno(Integer identifikator, Context context){
        return MyDatabase.getInstance(context).getNamirnicaDAO().dohvatiNamirnicu(identifikator);
    }

    public static Namirnica DohvatiPoISBNWeb(String isbn){
        final Namirnica[] returnme = {new Namirnica()};

        Retrofit retrofit = RetrofitInstance.getInstance();
        JsonApi jsonApi = retrofit.create(JsonApi.class);
        jsonApi.dohvatiNamirnicuPoISBN(isbn).enqueue(new Callback<RetroNamirnica>() {
            @Override
            public void onResponse(Call<RetroNamirnica> call, Response<RetroNamirnica> response) {
                returnme[0] = returnme[0].parseNamirnica(response.body());
            }

            @Override
            public void onFailure(Call<RetroNamirnica> call, Throwable t) {

            }
        });

        return returnme[0];
    }

    public static Namirnica DohvatiPoISBNLokalno(String isbn, Context context){
        return MyDatabase.getInstance(context).getNamirnicaDAO().dohvatiNamirnicuPoISBN(isbn);
    }

    public static void DohvatiSveWebULokalnu(final Context context){
        Retrofit retrofit = RetrofitInstance.getInstance();
        JsonApi jsonApi = retrofit.create(JsonApi.class);
        jsonApi.dohvatiSveNamirnice().enqueue(new Callback<List<RetroNamirnica>>() {
            @Override
            public void onResponse(Call<List<RetroNamirnica>> call, Response<List<RetroNamirnica>> response) {
                for(RetroNamirnica r: response.body()){
                    Namirnica namirnica = new Namirnica();
                    namirnica = namirnica.parseNamirnica(r);

                    MyDatabase.getInstance(context).getNamirnicaDAO().unosNamirnica(namirnica);
                }
            }

            @Override
            public void onFailure(Call<List<RetroNamirnica>> call, Throwable t) {

            }
        });
    }

    public static List<Namirnica> DohvatiSveWeb(final Context context){
        final List<Namirnica> returnme = new ArrayList<>();
        Retrofit retrofit = RetrofitInstance.getInstance();
        JsonApi jsonApi = retrofit.create(JsonApi.class);
        jsonApi.dohvatiSveNamirnice().enqueue(new Callback<List<RetroNamirnica>>() {
            @Override
            public void onResponse(Call<List<RetroNamirnica>> call, Response<List<RetroNamirnica>> response) {
                for(RetroNamirnica r: response.body()){
                    Namirnica namirnica = new Namirnica();
                    namirnica = namirnica.parseNamirnica(r);

                    returnme.add(namirnica);
                }
            }

            @Override
            public void onFailure(Call<List<RetroNamirnica>> call, Throwable t) {

            }
        });
        return returnme;
    }

    public static List<Namirnica> DohvatiSveLokalno(Context context){
        MyDatabase myDatabase = MyDatabase.getInstance(context);

        List<Namirnica> returnme = new ArrayList<>();
        returnme=myDatabase.getNamirnicaDAO().dohvatiSveNamirnice();

        return returnme;
    }

    public static void Azuriraj(Context context,Integer identifikator,String atribut, String vrijednost){

        Retrofit retrofit = RetrofitInstance.getInstance();
        JsonApi jsonApi = retrofit.create(JsonApi.class);
        jsonApi.azurirajNamirnicu(
                identifikator,
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

        Namirnica namirnica = DohvatiLokalno(identifikator,context);
        switch(atribut){
            case "id":
                namirnica.setId(Integer.parseInt(vrijednost));
                break;
            case "naziv":
                namirnica.setNaziv(vrijednost);
                break;
            case "brojKalorija":
                namirnica.setBrojKalorija(Integer.parseInt(vrijednost));
                break;
            case "tezina":
                namirnica.setTezina(Integer.parseInt(vrijednost));
                break;
            case "isbn":
                namirnica.setIsbn(vrijednost);
                break;
        }

        MyDatabase myDatabase = MyDatabase.getInstance(context);
        myDatabase.getNamirnicaDAO().azuriranjeNamirnica(namirnica);

    }

    public static void Kreiraj(Namirnica namirnica,Context context){
        Retrofit retrofit = RetrofitInstance.getInstance();
        JsonApi jsonApi = retrofit.create(JsonApi.class);
        jsonApi.unesiNamirnicu(namirnica.getNaziv(),namirnica.getBrojKalorija(),namirnica.getTezina(),namirnica.getIsbn()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });

        MyDatabase myDatabase = MyDatabase.getInstance(context);
        myDatabase.getNamirnicaDAO().unosNamirnica(namirnica);
    }

    public static void KreirajLokalno(Namirnica namirnica,Context context){
        MyDatabase myDatabase = MyDatabase.getInstance(context);
        myDatabase.getNamirnicaDAO().unosNamirnica(namirnica);
    }

    public static void IzbrisiLokalno(Context context, Integer identifikator){
        getInstance(context).getNamirnicaDAO().brisanjeNamirnice(identifikator);
    }
}