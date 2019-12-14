package com.example.fitapp.viewmodels;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.core.entities.Namirnica;
import com.example.core.entities.NamirniceObroka;
import com.example.database.MyDatabase;
import com.example.fitapp.AddSelectedFood;
import com.example.repository.NamirnicaDAL;

import RetroEntities.RetroNamirnica;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddSelectedFoodViewModel extends AndroidViewModel {
    public LiveData<Namirnica> namirnicaLiveData;
    public LiveData<NamirniceObroka> namirniceObrokaLiveData;
    public Namirnica dohvaceNamirnica;
    public void Inicijaliziraj(Integer idNamirnice) {
        if(ProvjeriPostojiLiLokalnaNamirnica(idNamirnice)){
            namirnicaLiveData = NamirnicaDAL.LIVEDohvatiLokalno(idNamirnice,getApplication().getApplicationContext());
            namirniceObrokaLiveData = NamirnicaDAL.LiveKreirajPraznuLokalnuNamirniceObroka(getApplication().getApplicationContext());
        }else{
            NamirnicaDAL.DohvatiWeb(idNamirnice, new Callback<RetroNamirnica>() {
                @Override
                public void onResponse(Call<RetroNamirnica> call, Response<RetroNamirnica> response) {
                    if(response.isSuccessful()){
                        dohvaceNamirnica = Namirnica.parseStaticNamirnica(response.body());
                    }
                }

                @Override
                public void onFailure(Call<RetroNamirnica> call, Throwable t) {

                }
            });
            MyDatabase.getInstance(getApplication().getApplicationContext()).getNamirnicaDAO().unosNamirnica(dohvaceNamirnica);
            namirnicaLiveData.getValue().setId(dohvaceNamirnica.getId());
            namirnicaLiveData.getValue().setIsbn(dohvaceNamirnica.getIsbn());
            namirnicaLiveData.getValue().setTezina(dohvaceNamirnica.getTezina());
            namirnicaLiveData.getValue().setBrojKalorija(dohvaceNamirnica.getBrojKalorija());
            namirnicaLiveData.getValue().setNaziv(dohvaceNamirnica.getNaziv());
            namirniceObrokaLiveData = NamirnicaDAL.LiveKreirajPraznuLokalnuNamirniceObroka(getApplication().getApplicationContext());
        }
    }

    private boolean ProvjeriPostojiLiLokalnaNamirnica(Integer idNamirnice){
        if(MyDatabase.getInstance(getApplication().getApplicationContext()).getNamirnicaDAO().dohvatiNamirnicu(idNamirnice) != null){
            return true;
        }else{
            return false;
        }
    }
    public AddSelectedFoodViewModel(@NonNull final Application application) {
        super(application);
    }



    public void update(Namirnica namirnica, Context context){
        NamirnicaDAL.AsyncUpdateNamirnica(namirnica,context);
    }

    public void updateObrok(NamirniceObroka obrok,Context context){
        NamirnicaDAL.AzurirajKorisnikovObrok(context,obrok);
    }


}
