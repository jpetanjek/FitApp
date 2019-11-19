package com.example.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.core.entities.Korisnik;

@Dao
public interface DAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long[] unosKorisnika(Korisnik... korisnici);

    @Update public void azuriranjeKorisnika(Korisnik... korisnik);

    @Delete public void brisanjeKorisnika(Korisnik... korisnik);

    @Query("SELECT * FROM korisnik WHERE google_id=:googleId")
    public Korisnik dohvatiKorisnika(String googleId);
}
