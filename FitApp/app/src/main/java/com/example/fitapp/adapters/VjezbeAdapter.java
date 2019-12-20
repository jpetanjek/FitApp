package com.example.fitapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.core.entities.Vjezba;
import com.example.fitapp.R;

import java.util.ArrayList;


public class VjezbeAdapter extends ArrayAdapter<Vjezba> {

    Context context;
    String rTitle[];
    String rDescription[];
    int rImgs[];
    ArrayList<Vjezba> dohvaceneVjezbe;

    public VjezbeAdapter(Context context, ArrayList<Vjezba> vjezbas){
        super(context,R.layout.adapter_view_layout,R.id.textView1,vjezbas);
        this.context = context;
        this.dohvaceneVjezbe = vjezbas;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.adapter_view_layout, parent, false);
        ImageView images = row.findViewById(R.id.image);
        TextView myTitle = row.findViewById(R.id.textView1);

        images.setImageResource(dohvaceneVjezbe.get(position).getIkona());
        myTitle.setText(dohvaceneVjezbe.get(position).getNaziv());

        if(position==0) myTitle.setTextColor(context.getResources().getColor(R.color.green));

        return row;
    }
}



