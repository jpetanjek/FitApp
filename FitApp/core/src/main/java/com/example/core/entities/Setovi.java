package com.example.core.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "setovi")
public class Setovi {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ForeignKey(entity = Korisnik.class,parentColumns = "id",childColumns = "idKorisnik")
    private int idKorisnik;

    boolean planirano;
    String id_google_kalendar;
    int trajanjePauze;

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

    public boolean isPlanirano() {
        return planirano;
    }

    public void setPlanirano(boolean planirano) {
        this.planirano = planirano;
    }

    public String getId_google_kalendar() {
        return id_google_kalendar;
    }

    public void setId_google_kalendar(String id_google_kalendar) {
        this.id_google_kalendar = id_google_kalendar;
    }

    public int getTrajanjePauze() {
        return trajanjePauze;
    }

    public void setTrajanjePauze(int trajanjePauze) {
        this.trajanjePauze = trajanjePauze;
    }
}
