package com.example.core.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "vjezba")
public class Vjezba {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ForeignKey(entity = TipVjezbe.class,parentColumns = "id",childColumns = "tipVjezbe")
    @ColumnInfo(index = true)
    private int tipVjezbe;

    private String naziv;
    private String upute;
    private Float met;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTipVjezbe() {
        return tipVjezbe;
    }

    public void setTipVjezbe(int tipVjezbe) {
        this.tipVjezbe = tipVjezbe;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getUpute() {
        return upute;
    }

    public void setUpute(String upute) {
        this.upute = upute;
    }

    public Float getMet() {
        return met;
    }

    public void setMet(Float met) {
        this.met = met;
    }
}
