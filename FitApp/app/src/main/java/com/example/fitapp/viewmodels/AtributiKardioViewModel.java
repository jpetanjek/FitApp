package com.example.fitapp.viewmodels;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.core.entities.AtributiKardioVjezbi;
import com.example.core.entities.KorisnikVjezba;
import com.example.core.entities.NamirniceObroka;
import com.example.database.VjezbaDAO;
import com.example.repository.AtributiKardioVjezbiDAL;
import com.example.repository.KorisnikVjezbaDAL;
import com.example.repository.NamirnicaDAL;

import java.util.List;

public class AtributiKardioViewModel extends AndroidViewModel {
    private Context context;

    public AtributiKardioViewModel(@NonNull Application application) {
        super(application);
        this.context = application;
    }


    public void create(AtributiKardioVjezbi atributiKardioVjezbi){
        AtributiKardioVjezbiDAL.Create(context,atributiKardioVjezbi);
    }

    public LiveData<AtributiKardioVjezbi> createEmpty(int korisnikVjezbaId){
        return AtributiKardioVjezbiDAL.CreateEmpty(context, korisnikVjezbaId);
    }

    public void delete(AtributiKardioVjezbi atributiKardioVjezbi){
        AtributiKardioVjezbiDAL.Delete(context,atributiKardioVjezbi);
    }

    public void update(AtributiKardioVjezbi atributiKardioVjezbi){
        AtributiKardioVjezbiDAL.Update(context,atributiKardioVjezbi);
    }

    public LiveData<AtributiKardioVjezbi> ReadById(String id){
        return AtributiKardioVjezbiDAL.ReadById(id,context);
    }

    public LiveData<AtributiKardioVjezbi> ReadByKorisnikVjezbaId(String id){
        return AtributiKardioVjezbiDAL.ReadByKorisnikVjezbaId(id,context);
    }

    //korisnik vjezba
    public KorisnikVjezba createEmptyKorisnikVjezba(int vjezbaId){
        return KorisnikVjezbaDAL.CreateEmpty(vjezbaId,KorisnikVjezbaDAL.CreateEmptySetovi(context).getId(),context);
    }

}