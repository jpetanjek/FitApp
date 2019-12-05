package com.example.core.entities;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import RetroEntities.RetroNamirnica;
import retrofit2.Response;

@Entity(tableName = "namirnica")
public class Namirnica {
    @PrimaryKey
    private int id;

    private String naziv;
    private int brojKalorija;
    private int tezina;

    @Nullable
    private String isbn;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public int getBrojKalorija() {
        return brojKalorija;
    }

    public void setBrojKalorija(int brojKalorija) {
        this.brojKalorija = brojKalorija;
    }

    public int getTezina() {
        return tezina;
    }

    public void setTezina(int tezina) {
        this.tezina = tezina;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }


    public Namirnica parseNamirnica(RetroNamirnica response){
        Namirnica novaNamirnica = new Namirnica();
        novaNamirnica.setId(response.getId());
        novaNamirnica.setNaziv(response.getNaziv());
        novaNamirnica.setTezina(response.getTezina());
        novaNamirnica.setBrojKalorija(response.getBrojKalorija());
        novaNamirnica.setIsbn(response.getIsbn());
        return novaNamirnica;
    }

}
