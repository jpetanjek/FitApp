package com.example.fitapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitapp.fragments.AccelerometerRunning;
import com.example.fitapp.managers.NamirnicaManager;
import com.example.fitapp.managers.RunningManager;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import adapter.CurrentActivity;

public class RunningInstructor extends AppCompatActivity {

    private RunningManager runningManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running_instructor);

        CurrentActivity.setActivity(this);

        runningManager = RunningManager.getInstance();
        runningManager.setupManager(this);

        AccelerometerRunning akcelerometarTrcanje = new AccelerometerRunning();
        runningManager.startModule(akcelerometarTrcanje);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}