package com.example.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.core.entities.AtributiVjezbiSnage;
import com.example.core.entities.KorisnikVjezba;
import com.example.core.entities.Setovi;
import com.example.core.entities.TipVjezbe;
import com.example.core.entities.Vjezba;

import java.util.List;

@Dao
public interface VjezbaDAO {

    //CRUD nad tablicom tip_vjezbe

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long[] unosTipaVjezbe(TipVjezbe... tipoviVjezbe);

    @Update
    public void azuriranjeTipaVjezbe(TipVjezbe... tipoviVjezbe);

    @Query("DELETE FROM tip_vjezbe WHERE id = :idTipaVjezbe")
    public void brisanjeTipaVjezbe(int idTipaVjezbe);

    @Query("SELECT * FROM tip_vjezbe")
    public List<TipVjezbe> dohvatiSveTipoveVjezbi();

    @Query("SELECT * FROM tip_vjezbe WHERE id = :idTipaVjezbe")
    public TipVjezbe dohvatiTipVjezbi(int idTipaVjezbe);

    // CRUD nad tablicom vjezba

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long[] unosVjezbe(Vjezba... vjezbe);

    @Update
    public void azuriranjeVjezbe(Vjezba... vjezbe);

    @Query("DELETE FROM vjezba WHERE id = :idVjezbe")
    public void brisanjeVjezbe(int idVjezbe);

    @Query("SELECT * FROM vjezba")
    public List<Vjezba> dohvatiSveVjezbe();

    @Query("SELECT count(*) FROM vjezba")
    public int dohvatiBrojVjezbi();

    @Query("SELECT * FROM vjezba WHERE id = :idVjezbe")
    public Vjezba dohvatiVjezbu(int idVjezbe);

    // CRUD nad tablicom Korisnik Vjezba

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long[] unosKorisnikoveVjezbe(KorisnikVjezba... korisnikoveVjezbe);

    @Update
    public void azuriranjeKorisnikoveVjezbe(KorisnikVjezba... korisnikoveVjezbe);

    @Query("DELETE FROM korisnik_vjezba WHERE id = :idKorisnikoveVjezbe")
    public void brisanjeKorisnikoveVjezbe(int idKorisnikoveVjezbe);

    @Query("SELECT * FROM korisnik_vjezba")
    public List<KorisnikVjezba> dohvatiSveKorisnikoveVjezbe();

    @Query("SELECT * FROM korisnik_vjezba WHERE id = :idKorisnikoveVjezbe")
    public KorisnikVjezba dohvatiKorisnikovuVjezbu(int idKorisnikoveVjezbe);

    @Query("SELECT * FROM korisnik_vjezba WHERE idSet = :idSet")
    public List<KorisnikVjezba> dohvatiVjezbeSeta(int idSet);



    // CRUD Setovi

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long[] unosSeta(Setovi... setovi);

    //PROVJERITI
    @Query("SELECT * FROM setovi WHERE id = :idSet")
    public Setovi dohvatiSet(int idSet);

    //AtributiVjezbiSnage
    @Query("SELECT * FROM atributi_vjezbi_snage WHERE korisnikVjezbaId = :vjezbaId")
    public AtributiVjezbiSnage dohvatiAtributeVjezbeSnagePoVjezbi(int vjezbaId);


    // CRUD atributi_vjezbe_snage

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long[] unosAtributaVjezbeSnage(AtributiVjezbiSnage... atributiVjezbiSnage);

}
