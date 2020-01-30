package com.example.fitapp.running;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.fitapp.R;
import com.google.android.material.snackbar.Snackbar;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;

import adapter.CurrentActivity;
import managers.RunningInterface;

public class GPSTrcanje implements RunningInterface {
    /* Formatting variables */
    private String decimalPattern = "#.###";
    private String kmh = "km/h";
    private String kms = "km";

    /* GPS related variables */
    private boolean granted = false;
    private final int GPS_PERMISSION = 42;
    private LocationManager lm;
    private LinkedList<Location> locations;
    private double totalDistance, averageSpeed, totalSpeed;
    private ArrayList<Long> timePerKm;
    private long elapsedTime, totalTime;
    private DecimalFormat decimal = new DecimalFormat(decimalPattern);

    public GPSTrcanje(boolean dozvoljenost, LocationManager locationManager) {
        granted = dozvoljenost;
        if(dozvoljenost){
            lm = locationManager;
        }
    }
    @Override
    public float getDistance() {
        return (float)totalDistance;
    }

    @Override
    public Fragment getFragment() {
        return null;
    }

    @Override
    public void update() {
        if(granted){
            startGPS();
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void reset() {
        stopGPS();
    }


    private void startGPS() {

        Log.e("GPS", "Pokrenut Start GPS");
        /* Request update every 400ms (Doherty Thresold) with 1 meter minimum distance difference */
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 400, 1, listener);

        /* Instantiate lists */
        locations = new LinkedList<>();
        timePerKm = new ArrayList<>();

        /* Reset variables */
        totalDistance = 0;
        averageSpeed = 0;
        elapsedTime = 0;
        totalSpeed = 0;
        totalTime = 0;

    }
    /* Stop gathering positions */
    private void stopGPS() { lm.removeUpdates(listener); }


    /* GPS listener setup */
    private LocationListener listener = new LocationListener() {

        @Override /* Location actually changed */
        public void onLocationChanged(Location location) {

            /* Get new speed & convert m/s to km/h */
            double currentSpeed = location.getSpeed() * 3.6;
            Log.e("GPS Brzina:",String.valueOf(currentSpeed));

            /* Compute & update current speed */

            /* Compute & update average speed */
            totalSpeed += currentSpeed;
            averageSpeed = totalSpeed / (locations.size() + 1);
            Log.e("GPS avg speed:",String.valueOf(averageSpeed));
            /* Not moving */
            if (currentSpeed == 0)
                return;

            if (!locations.isEmpty()) {

                /* Compute & update total distance */
                int before = (int) totalDistance;
                totalDistance += (location.distanceTo(locations.peekLast()) / 1000);
                Log.e("GPS Total Distance:",String.valueOf(totalDistance));
                /* New kilometer */
                int done = (int) totalDistance - before;
            }

            /* Save new position */
            locations.add(location);
        }

        @Override /* GPS sensor has been enabled */
        public void onProviderEnabled(String provider) {}

        @Override /* GPS sensor has been disabled */
        public void onProviderDisabled(String provider) {}

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    };
}
