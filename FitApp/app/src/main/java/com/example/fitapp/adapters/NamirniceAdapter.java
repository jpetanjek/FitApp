package com.example.fitapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.core.entities.Namirnica;
import com.example.fitapp.R;

import java.util.ArrayList;
import java.util.List;

public class NamirniceAdapter extends RecyclerView.Adapter<NamirniceAdapter.NamirniceHolder> {

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

    class NamirniceHolder extends RecyclerView.ViewHolder{
        private TextView tvNazivNamirnice;
        private TextView tvBrojKalorija;
        private Integer idNamirnice;

        public NamirniceHolder(@NonNull View itemView) {
            super(itemView);
            tvBrojKalorija = itemView.findViewById(R.id.tvBrojKalorija);
            tvNazivNamirnice = itemView.findViewById(R.id.tvNazivNamirnice);
        }
    }
}
