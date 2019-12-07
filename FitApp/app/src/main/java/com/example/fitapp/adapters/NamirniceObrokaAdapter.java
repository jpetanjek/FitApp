package com.example.fitapp.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.core.entities.Namirnica;
import com.example.core.entities.NamirniceObroka;
import com.example.database.MyDatabase;
import com.example.fitapp.Glavni_Izbornik;
import com.example.fitapp.R;
import com.example.repository.NamirnicaDAL;

import java.util.ArrayList;
import java.util.List;


public class NamirniceObrokaAdapter extends RecyclerView.Adapter<NamirniceObrokaAdapter.NamirniceObrokaHolder> {

    private List<NamirniceObroka> namirnicaObrokas = new ArrayList<>();
    private Context context;

    @NonNull
    @Override
    public NamirniceObrokaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.namirnica_obroka_item, parent, false);

        return new NamirniceObrokaHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NamirniceObrokaHolder holder, int position) {
        NamirniceObroka trenutnaNamirnicaObroka = namirnicaObrokas.get(position);
        Namirnica namirnica = NamirnicaDAL.DohvatiLokalno(trenutnaNamirnicaObroka.getIdNamirnica(), context);

        holder.tvBrojKalorija.setText(String.valueOf(namirnica.getBrojKalorija()));
        holder.tvNazivNamirnice.setText(namirnica.getNaziv());
    }

    @Override
    public int getItemCount() {
        return namirnicaObrokas.size();
    }

    public void setNamirnicas(List<NamirniceObroka> namirnicas){
        this.namirnicaObrokas = namirnicas;
        notifyDataSetChanged();
    }

    public void setContext(Context context){
        this.context = context;
    }

    public NamirniceObroka getNamirnicaObrokaAt(int position){
        return namirnicaObrokas.get(position);
    }

    class NamirniceObrokaHolder extends RecyclerView.ViewHolder{
        private TextView tvNazivNamirnice;
        private TextView tvBrojKalorija;

        public NamirniceObrokaHolder(@NonNull View itemView) {
            super(itemView);
            tvBrojKalorija = itemView.findViewById(R.id.tvBrojKalorija);
            tvNazivNamirnice = itemView.findViewById(R.id.tvNazivNamirnice);
        }
    }
















    /*
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
    }*/
}
