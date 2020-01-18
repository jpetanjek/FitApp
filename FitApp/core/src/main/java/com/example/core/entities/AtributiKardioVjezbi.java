package com.example.core.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "atributi_kardio_vjezbi")
public class AtributiKardioVjezbi {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ForeignKey(entity = KorisnikVjezba.class,parentColumns = "id", childColumns = "korisnikVjezbaId")
    @ColumnInfo(index = true)
    private int korisnikVjezbaId;

    public float getUdaljenostPlanirana() {
        return udaljenostPlanirana;
    }

    public void setUdaljenostPlanirana(float udaljenostPlanirana) {
        this.udaljenostPlanirana = udaljenostPlanirana;
    }

    private float udaljenostPlanirana;
    private float udaljenostOtrcana;
    private int trajanje;
    private int kalorijaPotroseno;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getKorisnikVjezbaId() {
        return korisnikVjezbaId;
    }

    public void setKorisnikVjezbaId(int korisnikVjezbaId) {
        this.korisnikVjezbaId = korisnikVjezbaId;
    }

    public float getUdaljenostOtrcana() {
        return udaljenostOtrcana;
    }

    public void setUdaljenostOtrcana(float udaljenostOtrcana) {
        this.udaljenostOtrcana = udaljenostOtrcana;
    }

    public float getudaljenostPlanirana() {
        return udaljenostPlanirana;
    }

    public void setudaljenostPlanirana(float brojMetara) {
        this.udaljenostPlanirana = brojMetara;
    }

    public int getTrajanje() {
        return trajanje;
    }

    public void setTrajanje(int trajanje) {
        this.trajanje = trajanje;
    }

    public int getKalorijaPotroseno() {
        return kalorijaPotroseno;
    }

    public void setKalorijaPotroseno(int kalorijaPotroseno) {
        this.kalorijaPotroseno = kalorijaPotroseno;
    }
}
