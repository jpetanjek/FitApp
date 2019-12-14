package com.example.core.entities;

import androidx.annotation.InspectableProperty;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.sql.Date;

@Entity(tableName = "namirnice_u_obroku")
public class NamirniceObroka {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ForeignKey(entity = Namirnica.class,parentColumns = "id",childColumns = "idNamirnica")
    private int idNamirnica;

    private String obrok;

    @ForeignKey(entity = Korisnik.class,parentColumns = "id",childColumns = "idKorisnik")
    private int idKorisnik;

    private String datum;
    private boolean planirano;

    @Nullable
    private float masa;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdNamirnica() {
        return idNamirnica;
    }

    public void setIdNamirnica(int idNamirnica) {
        this.idNamirnica = idNamirnica;
    }

    public String getObrok() {
        return obrok;
    }

    public void setObrok(String obrok) {
        this.obrok = obrok;
    }

    public int getIdKorisnik() {
        return idKorisnik;
    }

    public void setIdKorisnik(int idKorisnik) {
        this.idKorisnik = idKorisnik;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }


    public boolean isPlanirano() {
        return planirano;
    }

    public void setPlanirano(boolean planirano) {
        this.planirano = planirano;
    }

    public float getMasa() {
        return masa;
    }

    public void setMasa(float masa) {
        this.masa = masa;
    }
}
