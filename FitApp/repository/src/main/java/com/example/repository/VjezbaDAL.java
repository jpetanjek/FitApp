package com.example.repository;

import android.content.Context;
import android.util.Log;

import com.example.core.entities.Korisnik;
import com.example.core.entities.KorisnikVjezba;
import com.example.core.entities.Vjezba;
import com.example.database.MyDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VjezbaDAL {

    public static long UnesiKorisnikVjezbu(KorisnikVjezba korisnikVjezba, Context context){
        return MyDatabase.getInstance(context).getVjezbaDAO().unosKorisnikoveVjezbe(korisnikVjezba)[0];
    }

    public static void ObrisiKorisnikVjezbu(KorisnikVjezba korisnikVjezba, Context context){
        MyDatabase.getInstance(context).getVjezbaDAO().brisanjeKorisnikoveVjezbe( korisnikVjezba.getId() );
    }

    public static List<KorisnikVjezba> DohvatiSveVjezbeIzmeduDatuma(String datumOd, String datumDo, Context context){

       List<KorisnikVjezba> korisnikVjezbas = MyDatabase.getInstance(context).getVjezbaDAO().dohvatiSveKorisnikoveVjezbe();

       List<KorisnikVjezba> filtriranaLista = new ArrayList<>();

       for(KorisnikVjezba kv: korisnikVjezbas){

           if( stringToDate( kv.getPlaniraniDatum() ).getTime() > stringToDate( datumOd ).getTime() &&
               stringToDate( kv.getPlaniraniDatum()).getTime() < stringToDate( datumDo).getTime() )
               filtriranaLista.add(kv);
       }
       return filtriranaLista;
    }

    public static List<KorisnikVjezba> DohvatiSveVjezbeZaDatum(String datum, Context context){

        List<KorisnikVjezba> korisnikVjezbas = MyDatabase.getInstance(context).getVjezbaDAO().dohvatiSveKorisnikoveVjezbe();

        List<KorisnikVjezba> filtriranaLista = new ArrayList<>();
        for(KorisnikVjezba kv: korisnikVjezbas){
            if(kv.getPlaniraniDatum() != null && datum != null && kv.getPlaniraniDatum().compareTo(datum) == 0)
                filtriranaLista.add(kv);
        }

        return filtriranaLista;
    }

    public static Vjezba DohvatiVjezbu(int id, Context context){

        return MyDatabase.getInstance(context).getVjezbaDAO().dohvatiVjezbu(id);
    }

    public static Date stringToDate(String datum){
        Date date1 = null;
        try {
            date1 = new SimpleDateFormat("dd.MM.yyyy").parse(datum);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date1;
    }

    public static String dateToString(Date datum){
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return dateFormat.format(datum);
    }
}
