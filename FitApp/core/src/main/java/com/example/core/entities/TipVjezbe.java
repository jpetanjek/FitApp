package com.example.core.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tip_vjezbe")
public class TipVjezbe {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String naziv;

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
}
