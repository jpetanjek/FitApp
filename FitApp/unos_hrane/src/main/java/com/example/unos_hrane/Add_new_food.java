package com.example.unos_hrane;

import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;


import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.example.core.entities.Namirnica;
import com.example.core.entities.NamirniceObroka;
import com.example.repository.KorisnikDAL;
import com.example.repository.NamirnicaDAL;

import managers.NamirnicaImporter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Add_new_food extends Fragment implements NamirnicaImporter {
    private OnFragmentInteractionListener mListener;
    private Callback<Integer> callback;
    private Add_new_food_ViewModel add_new_food_viewModel;
    private String obrok;
    private String datumNamirniceObroka;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_new_food, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        System.out.println("OnViewCreated");

        //final String obrok = getArguments().getString("Obrok");
        //final String datum = getArguments().getString("Datum");
        setBundle();




        add_new_food_viewModel = ViewModelProviders.of(this).get(Add_new_food_ViewModel.class);
        add_new_food_viewModel.namirnicaLiveData.observe(this, new Observer<Namirnica>() {
            @Override
            public void onChanged(Namirnica namirnica) {
                System.out.println("NAMIRNICA");
                System.out.println(namirnica.getId());
                System.out.println(namirnica.getBrojKalorija());
                System.out.println(namirnica.getIsbn());
                System.out.println(namirnica.getNaziv());
                System.out.println(namirnica.getTezina());

            }
        });

        add_new_food_viewModel.namirniceObrokaLiveData.observe(this, new Observer<NamirniceObroka>() {
            @Override
            public void onChanged(NamirniceObroka namirniceObroka) {
                System.out.println("NamirniceObroka");
                System.out.println(namirniceObroka.getDatum());
                System.out.println(namirniceObroka.getId());
                System.out.println(namirniceObroka.getIdKorisnik());
                System.out.println(namirniceObroka.getIdNamirnica());
                System.out.println(namirniceObroka.getMasa());
                System.out.println(namirniceObroka.getObrok());
            }
        });
        //update namirnica i ui
        Namirnica namirnica = new Namirnica();
        namirnica.setIsbn("123456789");
        namirnica.setTezina(100);
        namirnica.setBrojKalorija(120);
        namirnica.setNaziv("Hrenovke");
        add_new_food_viewModel.update(namirnica,getActivity());

        final NamirniceObroka namirniceObroka = new NamirniceObroka();
        namirniceObroka.setMasa(200);
        add_new_food_viewModel.updateObrok(namirniceObroka,getActivity());

        //pritisak gumba dodaj hranu
        final NamirniceObroka lokalnaNamirniceObroka = namirniceObroka;

        //spremi stvorenu hranu na webservis
            //dohvati njezin id i stvori namirnicu u lokalnoj bazi s tim id
            //spremi lokalnu hranu kao id obroka
        System.out.println("Kreiraj");
            NamirnicaDAL.Kreiraj(namirnica, getContext(), new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                    System.out.println("onReponse");

                    lokalnaNamirniceObroka.setIdKorisnik(KorisnikDAL.Trenutni(getContext()).getId());
                    //response.body je id novostvorene namirnice na webservisu
                    lokalnaNamirniceObroka.setIdNamirnica(response.body());
                    //iz bundle
                    lokalnaNamirniceObroka.setObrok(obrok);
                    lokalnaNamirniceObroka.setDatum(datumNamirniceObroka);
                    //lokalnaNamirniceObroka.setPlanirano();

                    //SPREMI NamirnicaUObrok
                    NamirnicaDAL.UnesiKorisnikovObrok(getContext(),lokalnaNamirniceObroka);

                    System.out.println("Dodana");


                    //zatvori fragment
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(mListener != null){
                        mListener.onFragmentInteraction(true);
                    }
                }

                @Override
                public void onFailure(Call<Integer> call, Throwable t) {

                }
            });




        /*
        final LifecycleOwner owner = this;
        add_new_food_viewModel.WebInicijalizacija(getActivity().getApplication(), new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                System.out.println("OnResponse");
                add_new_food_viewModel.namirnicaLiveData.observe(owner, new Observer<Namirnica>() {
                    @Override
                    public void onChanged(Namirnica namirnica) {
                        System.out.println("OnChanged");
                    }
                });
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });
*/


    }


    @Override
    public String getName() {
        return "Rucni unos nove hrane";
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
