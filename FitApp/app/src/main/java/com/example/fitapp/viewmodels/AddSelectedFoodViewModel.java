package com.example.fitapp.viewmodels;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.core.entities.Namirnica;
import com.example.core.entities.NamirniceObroka;
import com.example.repository.NamirnicaDAL;

public class AddSelectedFoodViewModel extends AndroidViewModel {
    public LiveData<Namirnica> namirnicaLiveData;
    public LiveData<NamirniceObroka> namirniceObrokaLiveData;

    public void Inicijaliziraj(Integer idNamirnice) {
        namirnicaLiveData = NamirnicaDAL.LIVEDohvatiLokalno(idNamirnice,getApplication().getApplicationContext());
        namirniceObrokaLiveData = NamirnicaDAL.LiveKreirajPraznuLokalnuNamirniceObroka(getApplication().getApplicationContext());
    }

    public AddSelectedFoodViewModel(@NonNull final Application application, Integer idNamirnice) {
        super(application);
    }



    public void update(Namirnica namirnica, Context context){
        NamirnicaDAL.AsyncUpdateNamirnica(namirnica,context);
    }

    public void updateObrok(NamirniceObroka obrok,Context context){
        NamirnicaDAL.AzurirajKorisnikovObrok(context,obrok);
    }


}
