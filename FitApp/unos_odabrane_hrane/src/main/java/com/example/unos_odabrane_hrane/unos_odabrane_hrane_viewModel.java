package com.example.unos_odabrane_hrane;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.core.entities.Namirnica;
import com.example.core.entities.NamirniceObroka;
import com.example.repository.NamirnicaDAL;

public class unos_odabrane_hrane_viewModel extends AndroidViewModel {
    public LiveData<Namirnica> namirnicaLiveData;
    public LiveData<NamirniceObroka> namirniceObrokaLiveData;

    public unos_odabrane_hrane_viewModel(@NonNull final Application application) {
        super(application);
        namirnicaLiveData = NamirnicaDAL.LIVEKreirajPraznuLokalno(getApplication().getApplicationContext());
        namirniceObrokaLiveData = NamirnicaDAL.LiveKreirajPraznuLokalnuNamirniceObroka(getApplication().getApplicationContext());
    }


    public void update(Namirnica namirnica, Context context){
        NamirnicaDAL.AsyncUpdateNamirnica(namirnica,context);
    }

    public void updateObrok(NamirniceObroka obrok,Context context){
        NamirnicaDAL.AzurirajKorisnikovObrok(context,obrok);
    }

}
