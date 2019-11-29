package com.example.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.core.entities.Korisnik;
import com.example.core.entities.KorisnikVjezba;
import com.example.core.entities.Namirnica;
import com.example.core.entities.NamirniceObroka;
import com.example.core.entities.TipVjezbe;
import com.example.core.entities.Vjezba;

@Database(version = MyDatabase.VERSION,entities = {Korisnik.class, Namirnica.class, Vjezba.class, TipVjezbe.class, KorisnikVjezba.class, NamirniceObroka.class},exportSchema = false)
public abstract class MyDatabase extends RoomDatabase {
    public static final String NAME = "FitAppBaza";
    public static final int VERSION = 1;
    private static MyDatabase INSTANCE = null;

    public synchronized static MyDatabase getInstance(final Context context){
        if(INSTANCE==null){
            INSTANCE = Room.databaseBuilder(
                    context.getApplicationContext(),
                    MyDatabase.class,
                    MyDatabase.NAME
            ).allowMainThreadQueries().build();
        }
        return INSTANCE;
    }

    public abstract KorisnikDAO getKorisnikDAO();

    public abstract NamirnicaDAO getNamirnicaDAO();
}
