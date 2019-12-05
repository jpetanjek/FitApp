package com.example.fitapp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.core.entities.Namirnica;
import com.example.core.entities.NamirniceObroka;
import com.example.database.MyDatabase;
import com.example.fitapp.R;

import java.util.ArrayList;
import java.util.List;

public class NamirniceObrokaAdapter extends ArrayAdapter<NamirniceObroka> {

    private Context context;
    private int layoutResource;
    private List<NamirniceObroka> namirniceObrokas;


    public NamirniceObrokaAdapter(Context context, int layoutResource, List<NamirniceObroka> namirniceObrokas) {
        super(context, layoutResource, namirniceObrokas);
        this.context = context;
        this.layoutResource = layoutResource;
        this.namirniceObrokas = namirniceObrokas;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            view = layoutInflater.inflate(layoutResource, null);
        }

        NamirniceObroka namirniceObroka = getItem(position);

        if (namirniceObroka != null) {
            //TextView idNamirnicaObrok = (TextView) view.findViewById(R.id.idNamirnicaObrok);
            TextView nazivNamirnice = (TextView) view.findViewById(R.id.nazivNamirnice);
            TextView brojKalorija = (TextView) view.findViewById(R.id.brojKalorija);

                    Namirnica namirnica = MyDatabase.getInstance(context).getNamirnicaDAO().dohvatiNamirnicu(namirniceObroka.getIdNamirnica());

                    nazivNamirnice.setText(namirnica.getNaziv());
                    brojKalorija.setText(Integer.toString(namirnica.getBrojKalorija()) + " kcal");
            }
        return view;
    }
}
