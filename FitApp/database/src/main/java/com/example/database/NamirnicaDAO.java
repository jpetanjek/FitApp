package com.example.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.core.entities.Namirnica;
import com.example.core.entities.NamirniceObroka;
import com.example.core.entities.Obrok;

import java.util.List;

@Dao
public interface NamirnicaDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long[] unosNamirnica(Namirnica... namirnice);

    @Update
    public void azuriranjeNamirnica(Namirnica... namirnice);

    @Query("DELETE FROM namirnica WHERE id = :idNamirnice")
    public void brisanjeNamirnice(int idNamirnice);

    @Query("SELECT * FROM namirnica WHERE id = :idNamirnice")
    public Namirnica dohvatiNamirnicu(int idNamirnice);

    @Query("SELECT * FROM namirnica")
    public List<Namirnica> dohvatiSveNamirnice();

    //CRUD nad namirnice_u_obroku
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long[] unosKorisnikovogObroka(NamirniceObroka... namirniceObroka);

    @Update
    public void azuriranjeKorisnikovogObroka(NamirniceObroka... namirniceObroka);

    @Query("SELECT * FROM namirnice_u_obroku")
    public List<NamirniceObroka> dohvatiSveKorisnikoveObroke();

    @Query("DELETE FROM namirnice_u_obroku WHERE id=:idNamirniceObroka")
    public void brisanjeKorisnikovogObroka(int idNamirniceObroka);

    @Query("SELECT * FROM namirnice_u_obroku WHERE obrok=:vrstaObroka")
    public List<NamirniceObroka> dohvatiKorisnikovObrokPoVrsi(String vrstaObroka);
}
