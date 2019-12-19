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

    private float brojMetara;
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

    public float getBrojMetara() {
        return brojMetara;
    }

    public void setBrojMetara(float brojMetara) {
        this.brojMetara = brojMetara;
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
