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

    public void removeItemAt(int position){
        namirnicaObrokas.remove(position);
        notifyItemRemoved(position);
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
}
