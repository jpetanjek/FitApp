package com.example.fitapp.viewmodels;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.core.entities.AtributiKardioVjezbi;
import com.example.core.entities.Korisnik;
import com.example.core.entities.KorisnikVjezba;
import com.example.core.entities.NamirniceObroka;
import com.example.core.entities.Vjezba;
import com.example.database.MyDatabase;
import com.example.database.VjezbaDAO;
import com.example.repository.AtributiKardioVjezbiDAL;
import com.example.repository.KorisnikDAL;
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

    public AtributiKardioVjezbi ReadById(int id){
        return AtributiKardioVjezbiDAL.ReadById(id,context);
    }

    public LiveData<AtributiKardioVjezbi> ReadByIdLIVE(int id){
        return AtributiKardioVjezbiDAL.ReadByIdLIVE(id,context);
    }

    public LiveData<AtributiKardioVjezbi> ReadByKorisnikVjezbaId(String id){
        return AtributiKardioVjezbiDAL.ReadByKorisnikVjezbaId(id,context);
    }

    //korisnik vjezba
    public KorisnikVjezba createEmptyKorisnikVjezba(int vjezbaId){
        return KorisnikVjezbaDAL.CreateEmpty(vjezbaId,KorisnikVjezbaDAL.CreateEmptySetovi(context).getId(),context);
    }

    public void updateKorisnikVjezba(KorisnikVjezba update){
        KorisnikVjezbaDAL.update(update,context);
    }

    //vjezba
    public Vjezba readVjezba(int id){
        return KorisnikVjezbaDAL.readVjezba(id,context);
    }

    //korisnik
    public float brojKalorija(int vrijeme,int idVjezbe){
        return KorisnikDAL.Trenutni(context).potroseneKalorije(vrijeme,readVjezba(idVjezbe).getMet());
    }
}