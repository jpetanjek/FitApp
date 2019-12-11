package com.example.fitapp.viewmodels;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.core.entities.Namirnica;
import com.example.core.entities.NamirniceObroka;
import com.example.repository.NamirnicaDAL;

import java.util.List;

public class NamirniceObrokaViewModel extends AndroidViewModel {
    private LiveData<List<NamirniceObroka>>  namirniceObrokas;
    private int ukupniBrojKalorija;
    private Context context;

    public NamirniceObrokaViewModel(@NonNull Application application) {
        super(application);
        this.context = application;
    }

    public void insert(NamirniceObroka namirnicaObroka){
        NamirnicaDAL.UnesiKorisnikovObrok(context, namirnicaObroka);
    }

    public void delete(NamirniceObroka namirnicaObroka){
        NamirnicaDAL.ObrisiKorisnikovObrok(context, namirnicaObroka);
    }

    public LiveData<List<NamirniceObroka>> getAllNamirniceObroka(String vrstaObroka, String datum){
        namirniceObrokas = NamirnicaDAL.DohvatiSveNamirniceObrokaZaDatum(vrstaObroka, datum, context);

        return namirniceObrokas;
    }

    public int getUkupniBrojKalorija(String datum){
        ukupniBrojKalorija = NamirnicaDAL.DohvatiUkupniBrojKalorija(datum,context);

        return ukupniBrojKalorija;
    }

}
