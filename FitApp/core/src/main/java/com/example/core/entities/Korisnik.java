package com.example.core.entities;

import android.os.SystemClock;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Calendar;

import RetroEntities.RetroKorisnik;
import retrofit2.Response;

@Entity(tableName = "korisnik")
public class Korisnik {
    @PrimaryKey(autoGenerate = false)
    private int id;

    private String ime;
    private String prezime;
    private String email;
    private String spol;
    private float visina;
    private float masa;

    private String datumRodenja;
    @Nullable
    private String google_id;
    @Nullable
    private float cilj_mase;
    @Nullable
    private float cilj_tjednog_mrsavljenja;

    @Override
    public String toString(){
        return String.format(ime + " " + prezime + " " + email+ " " + spol+ " " + visina+ " " + masa+ " " + datumRodenja+ " " + google_id
                + " " +cilj_mase+ " " + cilj_tjednog_mrsavljenja);
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSpol(String spol) {
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

    public String getSpol() {
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

    public String getDatumRodenja() {
        return datumRodenja;
    }

    public void setDatumRodenja(String datumRodenja) {
        this.datumRodenja = datumRodenja;
    }

    public Korisnik parseKorisnik(Response<RetroKorisnik> response){
        Korisnik korisnik = new Korisnik();

        korisnik.id = response.body().getId();
        korisnik.ime = response.body().getIme();
        korisnik.prezime = response.body().getPrezime();
        korisnik.email = response.body().getEmail();
        korisnik.spol = response.body().getSpol();
        korisnik.visina = response.body().getVisina();
        korisnik.masa = response.body().getMasa();
        korisnik.cilj_mase = response.body().getCiljMase();
        korisnik.cilj_tjednog_mrsavljenja = response.body().getCiljTjednogMrsavljenja();
        korisnik.datumRodenja = response.body().getDatumRodenja();
        korisnik.google_id= response.body().getGoogle_id();

        return korisnik;
    }

    public float potroseneKalorije(int vrijeme, float mets){
        float sati= (float) (vrijeme/1000.0/3600.0);
        System.out.println("potroseneKalorije");
        System.out.println(sati);
        System.out.println(mets);
        System.out.println(getMasa());
        System.out.println(mets*getMasa()*sati);
        return  (mets*getMasa()*sati);
    }


    public double pocetneKalorije(Korisnik korisnik){
        double returnme = 1600;
        int godinaRodenja = Integer.parseInt(korisnik.getDatumRodenja().substring(korisnik.getDatumRodenja().length()-4));
        if(korisnik.getSpol()=="m"){
            returnme=((10.0*korisnik.getMasa()+6.25*korisnik.getVisina()-5.0*(Calendar.getInstance().get(Calendar.YEAR) -godinaRodenja)+5)*1.2)+korisnik.getCilj_tjednog_mrsavljenja()*1000;
        }else{
            returnme=((10.0*korisnik.getMasa()+6.25*korisnik.getVisina()-5.0*(Calendar.getInstance().get(Calendar.YEAR) -godinaRodenja)-161)*1.2)+korisnik.getCilj_tjednog_mrsavljenja()*1000;
        }

        //-1, -0.5 , -0.25 , 0.25 , 0.5, 1
        if(returnme<=1200){
            returnme=1200;
        }
        return returnme;
    }

}
