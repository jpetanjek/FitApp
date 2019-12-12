package com.example.barkod.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.net.DnsResolver;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.barkod.R;
import com.example.core.entities.Namirnica;
import com.example.core.entities.NamirniceObroka;
import com.example.repository.KorisnikDAL;
import com.example.repository.NamirnicaDAL;
import com.google.android.gms.vision.barcode.Barcode;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import RetroEntities.RetroNamirnica;
import adapter.CurrentActivity;
import adapter.CurrentFood;
import info.androidhive.barcode.BarcodeReader;
import managers.NamirnicaImporter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BarkodFragment extends Fragment implements BarcodeReader.BarcodeReaderListener, NamirnicaImporter {
    private static final String TAG = BarkodFragment.class.getSimpleName();
    private BarcodeReader barcodeReader;
    private Barcode skeniranObjekt;
    private Namirnica dohvacenaNamirnica;
    private Bundle dohvacenBundle;
    private String obrok;
    private String datumNamirniceObroka;
    private NamirniceObroka namirniceObroka;
    private OnFragmentInteractionListener mListener;


    public BarkodFragment() {
        System.out.println("Kreiran fragment!");
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //obrok = getActivity().getIntent().getStringExtra("Obrok");
        obrok = getArguments().getString("Obrok");
        datumNamirniceObroka = getArguments().getString("Datum");
        if (getArguments() != null) {

        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_barkod, container, false);

        barcodeReader = (BarcodeReader) getChildFragmentManager().findFragmentById(R.id.barcode_fragment);
        barcodeReader.setListener(this);
        System.out.println("Izrađen view!");
        return view;
    }

    @Override
    public void onScanned(final Barcode barcode) {
        skeniranObjekt = barcode;
        barcodeReader.playBeep();

        DohvatiNamirnicu(barcode.displayValue);
        unesiNovuNamirnicuObroka();
    }

    private void unesiNovuNamirnicuObroka() {
        if(dohvacenaNamirnica!=null){
            CurrentFood.setNamirnica(dohvacenaNamirnica);
            namirniceObroka = new NamirniceObroka();
            namirniceObroka.setIdKorisnik(KorisnikDAL.Trenutni(CurrentActivity.getActivity()).getId());
            namirniceObroka.setIdNamirnica(dohvacenaNamirnica.getId());
            namirniceObroka.setObrok(obrok);
            namirniceObroka.setDatum(datumNamirniceObroka);
            CurrentFood.setNamirnicaObroka(namirniceObroka);
            CurrentActivity.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(!CurrentActivity.getActivity().isFinishing()){
                        new AlertDialog.Builder(getContext())
                                .setTitle("Dodavanje namirnice u obrok!")
                                .setMessage("Želite li staviti "+dohvacenaNamirnica.getNaziv()+" u obrok "+obrok)
                                .setCancelable(true)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        NamirnicaDAL.UnesiKorisnikovObrok(CurrentActivity.getActivity(),CurrentFood.getNamirnicaObroka());
                                        getActivity().getSupportFragmentManager().popBackStack();
                                        if(mListener != null){
                                            mListener.onFragmentInteraction(true);
                                        }
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                }).show();
                    }
                }
            });
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
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), "Barcodes: " + finalCodes, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {

    }

    @Override
    public void onScanError(String errorMessage) {
        Log.e(TAG, "onScanError: " + errorMessage);
    }

    @Override
    public void onCameraPermissionDenied() {
        Toast.makeText(getActivity(), "Camera permission denied!", Toast.LENGTH_LONG).show();
    }
    public boolean provjeriPostojanje() {
        if(dohvacenaNamirnica!=null){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public String getName() {
        return "Barkod reader";
    }

    @Override
    public Fragment getFragment() {
        return this;
    }

    @Override
    public void setBundle() {
        obrok = getArguments().getString("Obrok");
        datumNamirniceObroka = getArguments().getString("Datum");
    }

    public interface OnFragmentInteractionListener{
        void onFragmentInteraction(boolean signalGotovo);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}

