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

import com.example.fitapp.R;


public class VjezbeAdapter extends ArrayAdapter<String> {

    Context context;
    String rTitle[];
    String rDescription[];
    int rImgs[];

    public VjezbeAdapter(Context c, String title[], int imgs[]) {
        super(c, R.layout.adapter_view_layout, R.id.textView1, title);
        this.context = c;
        this.rTitle = title;
        this.rImgs = imgs;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.adapter_view_layout, parent, false);
        ImageView images = row.findViewById(R.id.image);
        TextView myTitle = row.findViewById(R.id.textView1);

        images.setImageResource(rImgs[position]);
        myTitle.setText(rTitle[position]);

        if(position==0) myTitle.setTextColor(context.getResources().getColor(R.color.green));

        return row;
    }
}



