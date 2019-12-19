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
    private Integer repetition_lenght;
    private int ikona;

    public int getIkona() {
        return ikona;
    }

    public void setIkona(int ikona) {
        this.ikona = ikona;
    }

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

    public Integer getRepetition_lenght() { return repetition_lenght; }

    public void setRepetition_lenght(Integer repetition_lenght) { this.repetition_lenght = repetition_lenght; }

    public Integer izracunajPotroseneKalorije(Integer repetitions,Float tezina){
        //1 MET = 1 kcal/kg/hr
        Integer returnme;
        returnme=((repetition_lenght*repetitions)/3600)* Math.round(tezina);
        return  returnme;
    }
}
