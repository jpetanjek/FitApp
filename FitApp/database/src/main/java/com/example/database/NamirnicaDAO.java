package com.example.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.core.entities.Namirnica;

@Dao
public interface NamirnicaDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long[] unosNamirnica(Namirnica... namirnice);

    @Update
    public void azuriranjeNamirnica(Namirnica... namirnice);

    @Query("DELETE FROM namirnica WHERE id = :idNamirnice")
    public void brisanjeNamirnice(int idNamirnice);

    @Query("SELECT * FROM namirnica WHERE id=:idNamirnice")
    public Namirnica dohvatiNamirnicu(int idNamirnice);
}
