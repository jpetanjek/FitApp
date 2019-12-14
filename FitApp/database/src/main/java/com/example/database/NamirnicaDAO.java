package com.example.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
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

    @Query("SELECT * FROM namirnica WHERE id = :idNamirnice")
    public LiveData<Namirnica> LIVEdohvatiNamirnicu(int idNamirnice);

    @Query("SELECT * FROM namirnica")
    public List<Namirnica> dohvatiSveNamirnice();

    @Query("SELECT * FROM namirnica n JOIN namirnice_u_obroku nuo ON n.id = nuo.idNamirnica WHERE nuo.obrok = :vrstaObroka ")
    public List<Namirnica> dohvatiNamirniceIzObroka(String vrstaObroka);

    @Query("SELECT * FROM namirnica WHERE naziv  LIKE '%' || :unos || '%'")
    public List<Namirnica> dohvatiNamirnicePoImenu(String unos);

    @Query("SELECT * FROM namirnica WHERE isbn = :unos")
    public Namirnica dohvatiNamirnicuPoISBN(String unos);

  
    //CRUD nad namirnice_u_obroku
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long[] unosNamirniceObroka(NamirniceObroka... namirniceObroka);

    @Update
    public void azuriranjeNamirniceObroka(NamirniceObroka... namirniceObroka);

    @Delete
    public void brisanjeNamirniceObroka(NamirniceObroka... namirniceObroka);

    @Query("SELECT * FROM namirnice_u_obroku")
    public List<NamirniceObroka> dohvatiSveNamirniceObroka();

    @Query("SELECT * FROM namirnice_u_obroku WHERE id = :unos")
    public LiveData<NamirniceObroka> dohvatiNamirniceObrokaPoId(String unos);

    @Query("DELETE FROM namirnice_u_obroku WHERE id=:idNamirniceObroka")
    public void brisanjeKorisnikovogObroka(int idNamirniceObroka);

    @Query("SELECT * FROM namirnice_u_obroku WHERE obrok=:vrstaObroka and datum=:datum")
    public List<NamirniceObroka> dohvatiNamirniceObrokaPoVrstiZaDatum(String vrstaObroka, String datum);

    @Query("SELECT SUM(n.brojKalorija) FROM namirnice_u_obroku nuo LEFT JOIN namirnica n on nuo.idNamirnica=n.id WHERE nuo.datum=:datum")
    public int dohvatiUkupanBrojKalorija(String datum);
}
