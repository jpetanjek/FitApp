package com.example.fitapp.managers;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitapp.R;

import managers.RunningInterface;

public class RunningManager {
    private static RunningManager INSTANCE;
    private AppCompatActivity activity;

    private RunningManager(){ }

    public static RunningManager getInstance(){
        if(INSTANCE == null){
            INSTANCE = new RunningManager();
        }
        return INSTANCE;
    }
    public void setupManager(AppCompatActivity activity){
        this.activity = activity;
    }
    public void startModule(RunningInterface suceljeTrcanja){
        this.activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.Modularnost,suceljeTrcanja.getFragment())
                .commit();
    }
}
