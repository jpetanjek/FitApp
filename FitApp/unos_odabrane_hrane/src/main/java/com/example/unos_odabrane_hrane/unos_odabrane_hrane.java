package com.example.unos_odabrane_hrane;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.core.entities.Namirnica;
import com.example.core.entities.NamirniceObroka;
import com.example.repository.KorisnikDAL;
import com.example.repository.NamirnicaDAL;

import managers.NamirnicaImporter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class unos_odabrane_hrane extends Fragment implements NamirnicaImporter {

    private OnFragmentInteractionListener mListener;
    private Callback<Integer> callback;
    private unos_odabrane_hrane_viewModel add_new_food_viewModel;

    private String obrok;
    private String datumNamirniceObroka;


    //sucelje
    private Button uiButtonDodaj;
    private TextView uiNaziv;
    private TextView uiBrojPosluzivanja;
    private TextView uiTezina;
    private TextView uiKalorije;

    //lokalne varijable za interakciju s ViewModelom
    private Namirnica namirnica;
    private NamirniceObroka namirniceObroka;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_unos_odabrane_hrane, container, false);

        setBundle();

        //inicijalizacija sucelja
        uiButtonDodaj = (Button) view.findViewById(R.id.btnUnos);
        uiNaziv = (TextView) view.findViewById(R.id.naziv);
        uiBrojPosluzivanja = (TextView) view.findViewById(R.id.broj_posluzivanja);
        uiTezina = (TextView) view.findViewById(R.id.tezina);
        uiKalorije = (TextView) view.findViewById(R.id.kalorije);

        //inicijaliziraj Namirnicu
        namirnica = new Namirnica();
        add_new_food_viewModel = ViewModelProviders.of(this).get(unos_odabrane_hrane_viewModel.class);
        add_new_food_viewModel.namirnicaLiveData.observe(this, new Observer<Namirnica>() {
            @Override
            public void onChanged(Namirnica namirnica) {


                System.out.println("NAMIRNICA");
                System.out.println(namirnica.getId());
                System.out.println(namirnica.getBrojKalorija());
                System.out.println(namirnica.getIsbn());
                System.out.println(namirnica.getNaziv());
                System.out.println(namirnica.getTezina());

                //uiNaziv.setText(namirnica.getNaziv());
                //uiKalorije.setText(namirnica.getBrojKalorija());
                //uiTezina.setText(namirnica.getTezina());

            }
        });

        //inicijaliziraj Namirnicu_obroka
        namirniceObroka = new NamirniceObroka();
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

                if(namirniceObroka.getMasa()==0){
                    namirniceObroka.setMasa(1);
                }

                //uiBrojPosluzivanja.setText(Float.toString(namirniceObroka.getMasa()));
            }
        });

        return view;

    }

        //unos kolicine
        uiBrojPosluzivanja.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                namirniceObroka.setMasa(Float.parseFloat(uiBrojPosluzivanja.getText().toString()));
                add_new_food_viewModel.updateObrok(namirniceObroka,getContext());
            }
        });

        //pritisak gumba
        uiButtonDodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //spremi stvorenu hranu na webservis
                //dohvati njezin id i stvori namirnicu u lokalnoj bazi s tim id
                //spremi lokalnu hranu kao id obroka
                System.out.println("Kreiraj");
                NamirnicaDAL.Kreiraj(namirnica, getContext(), new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        System.out.println("onReponse");

                        namirniceObroka.setIdKorisnik(KorisnikDAL.Trenutni(getContext()).getId());
                        //response.body je id novostvorene namirnice na webservisu
                        namirniceObroka.setIdNamirnica(response.body());
                        //iz bundle
                        namirniceObroka.setObrok(obrok);
                        namirniceObroka.setDatum(datumNamirniceObroka);
                        //lokalnaNamirniceObroka.setPlanirano();

                        //SPREMI NamirnicaUObrok
                        NamirnicaDAL.UnesiKorisnikovObrok(getContext(),namirniceObroka);

                        System.out.println("Dodana");


                        //zatvori fragment

                        if(mListener != null){
                            mListener.onFragmentInteraction(true);
                        }
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {

                    }
                });
            }
        });

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