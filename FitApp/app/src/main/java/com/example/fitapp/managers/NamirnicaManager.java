package com.example.fitapp.managers;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.barkod.fragments.BarkodFragment;
import com.example.fitapp.R;

import managers.NamirnicaImporter;

public class NamirnicaManager {
    private static NamirnicaManager INSTANCE;
    private AppCompatActivity activity;

    private NamirnicaManager(){

    }

    public static NamirnicaManager getINSTANCE() {
        if(INSTANCE==null){
            INSTANCE = new NamirnicaManager();
        }
        return INSTANCE;
    }
    public void setupManager(AppCompatActivity activity){

        this.activity = activity;
    }
    public void startModule(NamirnicaImporter importer){
        /*activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentModul,importer.getFragment())
                .addToBackStack(importer.getName())
                .commit();


         */
        activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_add_food_to_meal,importer.getFragment())
                .addToBackStack(importer.getName())
                .commit();

    }

}
