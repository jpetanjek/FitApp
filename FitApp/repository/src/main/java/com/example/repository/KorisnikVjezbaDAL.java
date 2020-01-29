package com.example.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.core.entities.AtributiKardioVjezbi;
import com.example.core.entities.KorisnikVjezba;
import com.example.core.entities.Setovi;
import com.example.core.entities.Vjezba;
import com.example.database.MyDatabase;

public class KorisnikVjezbaDAL {
    public static KorisnikVjezba CreateEmpty(int vjezba_id, int idSet, Context context){
        long id = MyDatabase.getInstance(context).getVjezbaDAO().createEmpty(vjezba_id,idSet);
        return MyDatabase.getInstance(context).getVjezbaDAO().dohvatiKorisnikovuVjezbu(id);
    }

    public static Setovi  CreateEmptySetovi(Context context){
        long id = MyDatabase.getInstance(context).getVjezbaDAO().createEmptySet(MyDatabase.getInstance(context).getKorisnikDAO().dohvatiKorisnika().getId());
        return MyDatabase.getInstance(context).getVjezbaDAO().dohvatiSet(((int) id));
    }

    public static Vjezba readVjezba(int id, Context context){
        return MyDatabase.getInstance(context).getVjezbaDAO().dohvatiVjezbu(id);
    }

    public static void update(KorisnikVjezba update, Context context) {
        MyDatabase.getInstance(context).getVjezbaDAO().azuriranjeKorisnikoveVjezbe(update);
    }
}
