package features;

import android.content.Context;
import android.content.Intent;
import android.provider.CalendarContract;

public class KalendarDogadaj {
   public static int KROZ_INTERVAL_POCETKA_I_KRAJA = 0;
   public static int CIJELI_DAN = 1;
    /**
     *
     * @param context Kontekst aktivnosti iz koje se poziva funkcija OtvoriDogadajKalendara
     * @param pocetnoVrijeme Početno vrijeme događaja u milisekundama
     * @param zavrsnoVrijeme Završno vrijeme događaja u milisekundama
     * @param naslov Naziv događaja
     * @param opis Opis događaja
     * @param cjelodnevnoTrajanje Varijable koje označavaju trajanje novokreiranog događaja, za prosljeđenu vrijednost KROZ_INTERVAL_POCETKA_I_KRAJA, događaj će trajati od vremena početka do kraja, dok kod prosljeđene vrijednosti CIJELI_DAN bit će anotiran.
     */
    public static void OtvoriDogadajKalendara(Context context, long pocetnoVrijeme, long zavrsnoVrijeme, String naslov, String opis, int cjelodnevnoTrajanje){

        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,pocetnoVrijeme);
        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,zavrsnoVrijeme);
        intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY,cjelodnevnoTrajanje);

        intent.putExtra(CalendarContract.Events.TITLE,naslov);
        intent.putExtra(CalendarContract.Events.DESCRIPTION,opis);
        //intent.putExtra(CalendarContract.Events.EVENT_LOCATION,"Lokacija");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}

/*
  Kako parsati podatke.
  Calendar calendar = Calendar.getInstance();
  long pocetnoVrijeme = calendar.getTimeInMillis();
  long zavrsnoVrijeme = calendar.getTimeInMillis()+60*60*1000;
  KalendarDogadaj.OtvoriDogadajKalendara(getApplicationContext(),pocetnoVrijeme,zavrsnoVrijeme,"Naslov","Opis",KalendarDogadaj.KROZ_INTERVAL_POCETKA_I_KRAJA);
*/