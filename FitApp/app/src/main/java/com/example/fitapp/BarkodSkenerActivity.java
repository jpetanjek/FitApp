package com.example.fitapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.widget.Toast;

import com.example.core.entities.Namirnica;
import com.example.core.entities.NamirniceObroka;
import com.example.database.MyDatabase;
import com.example.fitapp.adapters.FoodDiaryAdapter;
import com.example.fitapp.fragments.BarcodeFragment;
import com.example.registracija.Repozitorij;
import com.example.repository.KorisnikDAL;
import com.example.repository.NamirnicaDAL;
import com.google.android.gms.vision.barcode.Barcode;

import java.util.List;

import RetroEntities.RetroNamirnica;
import info.androidhive.barcode.BarcodeReader;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BarkodSkenerActivity extends AppCompatActivity implements BarcodeReader.BarcodeReaderListener {

    private static final String TAG = BarcodeFragment.class.getSimpleName();
    private BarcodeReader barcodeReader;
    private Barcode skeniranObjekt;
    private Namirnica dohvacenaNamirnica;
    private String obrok;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barkod_skener);

        obrok = getIntent().getStringExtra("Obrok");
        // getting barcode instance
        barcodeReader = (BarcodeReader) getSupportFragmentManager().findFragmentById(R.id.barcode_fragment);


        /***
         * Providing beep sound. The sound file has to be placed in
         * `assets` folder
         */
        // barcodeReader.setBeepSoundFile("shutter.mp3");

        /**
         * Pausing / resuming barcode reader. This will be useful when you want to
         * do some foreground user interaction while leaving the barcode
         * reader in background
         * */
        // barcodeReader.pauseScanning();
        // barcodeReader.resumeScanning();
    }
    public void DohvatiNamirnicu(String isbn){
        NamirnicaDAL.DohvatiPoISBNWeb(isbn, new Callback<RetroNamirnica>() {
            @Override
            public void onResponse(Call<RetroNamirnica> call, Response<RetroNamirnica> response) {
                dohvacenaNamirnica = Namirnica.parseStaticNamirnica(response.body());
            }

            @Override
            public void onFailure(Call<RetroNamirnica> call, Throwable t) {
                dohvacenaNamirnica = null;
            }
        });
    }
    @Override
    public void onScanned(final Barcode barcode) {
        Log.e(TAG, "onScanned: " + barcode.displayValue);
        skeniranObjekt = barcode;
        barcodeReader.playBeep();

        DohvatiNamirnicu(barcode.displayValue);
        if(dohvacenaNamirnica!=null){
            FoodDiaryAdapter.setNamirnica(dohvacenaNamirnica);
            NamirniceObroka namirniceObroka = new NamirniceObroka();
            namirniceObroka.setIdKorisnik(KorisnikDAL.Trenutni(this).getId());
            namirniceObroka.setIdNamirnica(dohvacenaNamirnica.getId());
            namirniceObroka.setObrok(obrok);
            namirniceObroka.setDatum("08.12.2019");
            NamirnicaDAL.UnesiKorisnikovObrok(this,namirniceObroka);
            //MyDatabase.getInstance(this).getNamirnicaDAO().unosNamirniceObroka(namirniceObroka);

            /*runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    //Toast.makeText(getApplicationContext(),FoodDiaryAdapter.getNamirnica().getNaziv()+ " -"+obrok,Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(),KorisnikDAL.Trenutni(BarkodSkenerActivity.this).getIme()+" "+dohvacenaNamirnica.getNaziv()+ "-"+obrok,Toast.LENGTH_LONG).show();
                }
            });

             */
            finish();
        }

    }

    @Override
    public void onScannedMultiple(List<Barcode> barcodes) {
        Log.e(TAG, "onScannedMultiple: " + barcodes.size());

        String codes = "";
        for (Barcode barcode : barcodes) {
            codes += barcode.displayValue + ", ";
        }

        final String finalCodes = codes;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "Barcodes: " + finalCodes, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {

    }

    @Override
    public void onScanError(String errorMessage) {

    }

    @Override
    public void onCameraPermissionDenied() {
        Toast.makeText(getApplicationContext(), "Camera permission denied!", Toast.LENGTH_LONG).show();
        finish();
    }
}

