package com.example.unos_hrane;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.core.entities.Korisnik;
import com.example.core.entities.Namirnica;
import com.example.core.entities.NamirniceObroka;
import com.example.repository.NamirnicaDAL;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Add_new_food_ViewModel extends AndroidViewModel {
    public LiveData<Namirnica> namirnicaLiveData;
    public LiveData<NamirniceObroka> namirniceObrokaLiveData;

    public Add_new_food_ViewModel(@NonNull final Application application) {
        super(application);
        namirnicaLiveData = NamirnicaDAL.LIVEKreirajPraznuLokalno(getApplication().getApplicationContext());
        namirniceObrokaLiveData = NamirnicaDAL.LiveKreirajPraznuLokalnuNamirniceObroka(getApplication().getApplicationContext());
    }


    public void update(Namirnica namirnica,Context context){
        NamirnicaDAL.AsyncUpdateNamirnica(namirnica,context);
    }

    public void updateObrok(NamirniceObroka obrok,Context context){
        NamirnicaDAL.AzurirajKorisnikovObrok(context,obrok);
    }
    /*
    public void WebInicijalizacija (final Application application, final Callback<Integer> callback){
        Namirnica namirnica = new Namirnica();
        NamirnicaDAL.Kreiraj(namirnica, application, new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                //ovdje se id novostvorene namirnice na webservisu vraca u responsu
                //kako bi mogli njezinu lokalnu komponentu koristiti kao LIVE data
                namirnicaLiveData = NamirnicaDAL.LIVEDohvatiLokalno(response.body(),application);
                callback.onResponse(call,response);
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                callback.onFailure(call,t);
            }
        });
    }
    */
}
