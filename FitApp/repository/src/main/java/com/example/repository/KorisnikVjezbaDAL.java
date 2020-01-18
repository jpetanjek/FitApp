package com.example.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.core.entities.AtributiKardioVjezbi;
import com.example.core.entities.KorisnikVjezba;
import com.example.database.MyDatabase;

public class KorisnikVjezbaDAL {
    public static KorisnikVjezba CreateEmpty(int vjezba_id, Context context){
        long id = MyDatabase.getInstance(context).getVjezbaDAO().createEmpty(vjezba_id);
        return MyDatabase.getInstance(context).getVjezbaDAO().dohvatiKorisnikovuVjezbu(id);
    }
}
