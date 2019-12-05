package com.example.core.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.sql.Date;
import java.sql.Timestamp;

@Entity(tableName = "korisnik_vjezba")
public class KorisnikVjezba {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ForeignKey(entity = Korisnik.class,parentColumns = "id",childColumns = "idKorisnik")
    private int idKorisnik;

    @ForeignKey(entity = Vjezba.class,parentColumns = "id",childColumns = "idVjezba")
    private int idVjezba;


    //private Date datumPocetka;
    private boolean planirano;
    private String idGoogleKalendar;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdKorisnik() {
        return idKorisnik;
    }

    public void setIdKorisnik(int idKorisnik) {
        this.idKorisnik = idKorisnik;
    }

    public int getIdVjezba() {
        return idVjezba;
    }

    public void setIdVjezba(int idVjezba) {
        this.idVjezba = idVjezba;
    }

    /*
    public Date getDatumPocetka() {
        return datumPocetka;
    }

    public void setDatumPocetka(Date datumPocetka) {
        this.datumPocetka = datumPocetka;
    }
     */

    public boolean isPlanirano() {
        return planirano;
    }

    public void setPlanirano(boolean planirano) {
        this.planirano = planirano;
    }

    public String getIdGoogleKalendar() {
        return idGoogleKalendar;
    }

    public void setIdGoogleKalendar(String idGoogleKalendar) {
        this.idGoogleKalendar = idGoogleKalendar;
    }
}
