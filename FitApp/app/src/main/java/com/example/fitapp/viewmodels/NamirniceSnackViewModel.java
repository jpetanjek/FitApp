package com.example.fitapp.viewmodels;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.core.entities.NamirniceObroka;
import com.example.repository.NamirnicaDAL;

import java.util.List;

public class NamirniceSnackViewModel extends AndroidViewModel {

    private LiveData<List<NamirniceObroka>> namirniceObrokas;
    private Context context;

    public NamirniceSnackViewModel(@NonNull Application application) {
        super(application);
        this.context = application;
        namirniceObrokas = NamirnicaDAL.DohvatiSveNamirniceObroka("Snack", application);
    }

    public void insert(NamirniceObroka namirniceObroka){
        NamirnicaDAL.UnesiKorisnikovObrok(context, namirniceObroka);
    }

    public void delete(NamirniceObroka namirniceObroka){
        NamirnicaDAL.ObrisiKorisnikovObrok(context, namirniceObroka);
    }

    public LiveData<List<NamirniceObroka>> getAllNamirniceObroka(){
        return namirniceObrokas;
    }
}

