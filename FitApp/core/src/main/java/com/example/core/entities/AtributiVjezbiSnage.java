package com.example.core.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "atributi_vjezbi_snage")
public class AtributiVjezbiSnage {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ForeignKey(entity = KorisnikVjezba.class,parentColumns = "id", childColumns = "korisnikVjezbaId")
    @ColumnInfo(index = true)
    private int korisnikVjezbaId;

    private int brojPonavljanja;
    private float masaPonavljanja;
    private int trajanjeUSekundama;
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

    public int getBrojPonavljanja() {
        return brojPonavljanja;
    }

    public void setBrojPonavljanja(int brojPonavljanja) {
        this.brojPonavljanja = brojPonavljanja;
    }

    public float getMasaPonavljanja() {
        return masaPonavljanja;
    }

    public void setMasaPonavljanja(float masaPonavljanja) {
        this.masaPonavljanja = masaPonavljanja;
    }

    public int getTrajanjeUSekundama() {
        return trajanjeUSekundama;
    }

    public void setTrajanjeUSekundama(int trajanjeUSekundama) {
        this.trajanjeUSekundama = trajanjeUSekundama;
    }

    public int getKalorijaPotroseno() {
        return kalorijaPotroseno;
    }

    public void setKalorijaPotroseno(int kalorijaPotroseno) {
        this.kalorijaPotroseno = kalorijaPotroseno;
    }
}
