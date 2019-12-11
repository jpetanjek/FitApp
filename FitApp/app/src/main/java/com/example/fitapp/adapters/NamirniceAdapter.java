package com.example.fitapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.http.SslCertificate;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.core.entities.Namirnica;
import com.example.fitapp.AddFoodToMeal;
import com.example.fitapp.Glavni_Izbornik;
import com.example.fitapp.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NamirniceAdapter extends RecyclerView.Adapter<NamirniceAdapter.NamirniceHolder> {

    private String prosljedeniDatum;
    private String prosljedeniObrok;

    public NamirniceAdapter(){

    }
    public  NamirniceAdapter(String datum, String obrok){
        this.prosljedeniDatum = datum;
        this.prosljedeniObrok = obrok;
    }
    public void setContext(Context context) {
        this.context = context;
    }

    public void setNamirnice(List<Namirnica> namirnice) {
        this.namirnice = namirnice;
        notifyDataSetChanged();
    }

    private List<Namirnica> namirnice = new ArrayList<>();
    private Context context;

    @NonNull
    @Override
    public NamirniceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.namirnica_obroka_item, parent, false);
        return new NamirniceAdapter.NamirniceHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NamirniceHolder holder, int position) {
        Namirnica trenutnaNamirnica = namirnice.get(position);

        holder.tvBrojKalorija.setText(String.valueOf(trenutnaNamirnica.getBrojKalorija()));
        holder.tvNazivNamirnice.setText(trenutnaNamirnica.getNaziv());
        holder.idNamirnice = trenutnaNamirnica.getId();
    }

    @Override
    public int getItemCount() {
        return namirnice.size();
    }

    class NamirniceHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView tvNazivNamirnice;
        private TextView tvBrojKalorija;
        private Integer idNamirnice;
        private View pogled;

        public NamirniceHolder(@NonNull View itemView) {
            super(itemView);
            pogled = itemView;
            itemView.setOnClickListener(this);
            tvBrojKalorija = itemView.findViewById(R.id.tvBrojKalorija);
            tvNazivNamirnice = itemView.findViewById(R.id.tvNazivNamirnice);

        }
        @Override
        public void onClick(View v) {
            Bundle bundlePretrage = new Bundle();
            bundlePretrage.putString("Obrok",prosljedeniObrok);
            bundlePretrage.putString("Datum",prosljedeniDatum);
            Intent i = new Intent(context, Glavni_Izbornik.class);
            
            i.putExtras(bundlePretrage);
            context.startActivity(i);

        }
    }
}
