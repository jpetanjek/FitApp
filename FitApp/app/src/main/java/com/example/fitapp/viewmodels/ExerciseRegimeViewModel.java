package com.example.fitapp.viewmodels;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.core.entities.Korisnik;
import com.example.core.entities.KorisnikVjezba;
import com.example.core.entities.NamirniceObroka;
import com.example.fitapp.ExerciseRegime;
import com.example.repository.NamirnicaDAL;
import com.example.repository.VjezbaDAL;

import java.util.List;

public class ExerciseRegimeViewModel extends AndroidViewModel {
    private List<KorisnikVjezba>  korisnikVjezbas;
    private Context context;

    public ExerciseRegimeViewModel(@NonNull Application application) {
        super(application);
        this.context = application;
    }

    public void insert(KorisnikVjezba korisnikVjezba){
        VjezbaDAL.UnesiKorisnikVjezbu(korisnikVjezba, context);
    }

    public void delete(KorisnikVjezba korisnikVjezba){
        VjezbaDAL.ObrisiKorisnikVjezbu(korisnikVjezba, context);
    }

    public List<KorisnikVjezba> getAllKorisnikVjezba( String datum ){
        korisnikVjezbas = VjezbaDAL.DohvatiSveVjezbeZaDatum(datum, context);

        return korisnikVjezbas;
    }

}
