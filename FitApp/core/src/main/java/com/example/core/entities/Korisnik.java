package com.example.core.entities;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import RetroEntities.RetroKorisnik;
import retrofit2.Response;

@Entity(tableName = "korisnik")
public class Korisnik {
    @PrimaryKey(autoGenerate = false)
    private int id;

    private String ime;
    private String prezime;
    private String email;
    private char spol;
    private float visina;
    private float masa;
    @Nullable
    private String google_id;
    @Nullable
    private float cilj_mase;
    @Nullable
    private float cilj_tjednog_mrsavljenja;

    public void setIme(String ime) {
        this.ime = ime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSpol(char spol) {
        this.spol = spol;
    }

    public void setVisina(float visina) {
        this.visina = visina;
    }

    public void setMasa(float masa) {
        this.masa = masa;
    }

    public void setGoogle_id(String google_id) {
        this.google_id = google_id;
    }

    public void setCilj_mase(float cilj_mase) {
        this.cilj_mase = cilj_mase;
    }

    public void setCilj_tjednog_mrsavljenja(float cilj_tjednog_mrsavljenja) {
        this.cilj_tjednog_mrsavljenja = cilj_tjednog_mrsavljenja;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public String getEmail() {
        return email;
    }

    public char getSpol() {
        return spol;
    }

    public float getVisina() {
        return visina;
    }

    public float getMasa() {
        return masa;
    }

    public String getGoogle_id() {
        return google_id;
    }

    public float getCilj_mase() {
        return cilj_mase;
    }

    public float getCilj_tjednog_mrsavljenja() {
        return cilj_tjednog_mrsavljenja;
    }

    public Korisnik parseKorisnik(Response<RetroKorisnik> response){
        Korisnik korisnik = new Korisnik();

        korisnik.id = response.body().getId();
        korisnik.ime = response.body().getIme();
        korisnik.prezime = response.body().getPrezime();
        korisnik.email = response.body().getEmail();
        korisnik.spol = response.body().getSpol().charAt(0);
        korisnik.visina = response.body().getVisina();
        // TODO: Dodati masu (i getMasa) u RetroKorisnik
        korisnik.masa = response.body().getMasa();
        korisnik.cilj_mase = response.body().getCiljMase();
        korisnik.cilj_tjednog_mrsavljenja = response.body().getCiljTjednogMrsavljenja();

        return korisnik;
    }
}
