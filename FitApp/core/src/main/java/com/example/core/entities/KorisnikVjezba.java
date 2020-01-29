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

    @ForeignKey(entity = Setovi.class,parentColumns = "id",childColumns = "idSet")
    private int idSet;

    @ForeignKey(entity = Vjezba.class,parentColumns = "id",childColumns = "idVjezba")
    private int idVjezba;

    private String datumVrijemePocetka;

    private String planiraniDatum;

    public String getPlaniraniDatum() {
        return planiraniDatum;
    }

    public void setPlaniraniDatum(String planiraniDatum) {
        this.planiraniDatum = planiraniDatum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdSet() {
        return idSet;
    }

    public void setIdSet(int idSet) {
        this.idSet = idSet;
    }

    public int getIdVjezba() {
        return idVjezba;
    }

    public void setIdVjezba(int idVjezba) {
        this.idVjezba = idVjezba;
    }

    public String getDatumVrijemePocetka() {
        return datumVrijemePocetka;
    }

    public void setDatumVrijemePocetka(String datumVrijemePocetka) {
        this.datumVrijemePocetka = datumVrijemePocetka;
    }
}
