package com.example.unos_hrane;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.core.entities.Namirnica;
import com.example.repository.NamirnicaDAL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Add_new_food_ViewModel extends AndroidViewModel {
    private LiveData<Namirnica> namirnicaLiveData;

    public Add_new_food_ViewModel(@NonNull final Application application) {
        super(application);
        Namirnica namirnica = new Namirnica();
        NamirnicaDAL.Kreiraj(namirnica, application, new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                //ovdje se id novostvorene namirnice na webservisu vraca u responsu
                //kako bi mogli njezinu lokalnu komponentu koristiti kao LIVE data
                NamirnicaDAL.LIVEDohvatiLokalno(response.body(),application);
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });
    }
}
