package com.example.fitapp.adapters;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.core.entities.KorisnikVjezba;
import com.example.core.entities.Namirnica;
import com.example.core.entities.NamirniceObroka;
import com.example.core.entities.TipVjezbe;
import com.example.core.entities.Vjezba;
import com.example.database.MyDatabase;
import com.example.fitapp.ExerciseConfiguration;
import com.example.fitapp.ExerciseRegime;
import com.example.fitapp.ExerciseSelection;
import com.example.fitapp.R;
import com.example.repository.NamirnicaDAL;
import com.example.repository.VjezbaDAL;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class ExerciseRegimeAdapter extends RecyclerView.Adapter<ExerciseRegimeAdapter.ExerciseRegimeHolder>{

    private List<KorisnikVjezba> korisnikVjezbas = new ArrayList<>();
    private Context context;

    @NonNull
    @Override
    public ExerciseRegimeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_regime_item, parent, false);

        return new ExerciseRegimeHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseRegimeHolder holder, int position) {

        KorisnikVjezba korisnikVjezba = korisnikVjezbas.get(position);
        Vjezba vjezba = VjezbaDAL.DohvatiVjezbu(korisnikVjezba.getIdVjezba(), context );

        //holder.tvBrojSetova.setText("3 sets");
        holder.idVjezbe = vjezba.getId();
        holder.tvNazivVjezbe.setText(vjezba.getNaziv());

    }
    @Override
    public int getItemCount() {
        return korisnikVjezbas.size();
    }

    public void setKorisnikVjezbas(List<KorisnikVjezba> korisnikVjezbas){
        this.korisnikVjezbas = korisnikVjezbas;
        notifyDataSetChanged();
    }

    public void setContext(Context context){
        this.context = context;
    }

    public KorisnikVjezba getKorisnikVjezbaAt(int position){
        return korisnikVjezbas.get(position);
    }

    public void removeItemAt(int position){
        korisnikVjezbas.remove(position);
        notifyItemRemoved(position);
    }

    class ExerciseRegimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private int idVjezbe;
        private TextView tvNazivVjezbe;
        private TextView tvBrojSetova;

        public ExerciseRegimeHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tvNazivVjezbe = itemView.findViewById(R.id.tvNazivVjezbe);
            tvBrojSetova = itemView.findViewById(R.id.tvBrojSetova);
        }

        @Override
        public void onClick(View v) {
            selekcijaVjezbi();
        }

        // Lose, kopirano iz ExerciseSelection - nisu vježbe pridružene tipu, ne može se dobiti naziv tipa vježbe preko upita.
        public void selekcijaVjezbi(){
            //Toast.makeText(context, Integer.toString(idVjezbe), Toast.LENGTH_SHORT).show();
            switch (idVjezbe){
                case 2:
                    //Toast.makeText(ExerciseSelection.this, "Running description", Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    //Toast.makeText(ExerciseSelection.this, "Deadlift description", Toast.LENGTH_SHORT).show();
                    Intent intentDeadlift = new Intent(context, ExerciseConfiguration.class);
                    intentDeadlift.putExtra("idVjezbe",idVjezbe);
                    intentDeadlift.putExtra("naziv", tvNazivVjezbe.getText());
                    context.startActivity(intentDeadlift);
                    break;
                case 4:
                    //Toast.makeText(ExerciseSelection.this, "Shoulder press description", Toast.LENGTH_SHORT).show();
                    Intent intentShoulderPress = new Intent(context, ExerciseConfiguration.class);
                    intentShoulderPress.putExtra("idVjezbe",idVjezbe);
                    intentShoulderPress.putExtra("naziv", tvNazivVjezbe.getText());
                    context.startActivity(intentShoulderPress);
                    break;
                case 5:
                    //Toast.makeText(context, "Bench press description", Toast.LENGTH_SHORT).show();
                    Intent intentBenchPress = new Intent(context, ExerciseConfiguration.class);
                    intentBenchPress.putExtra("idVjezbe", idVjezbe);
                    intentBenchPress.putExtra("naziv", tvNazivVjezbe.getText());
                    context.startActivity(intentBenchPress);
                    break;
                case 6:
                    //Toast.makeText(context, "Squat description", Toast.LENGTH_SHORT).show();
                    Intent intentSquat = new Intent(context, ExerciseConfiguration.class);
                    intentSquat.putExtra("idVjezbe", idVjezbe);
                    intentSquat.putExtra("naziv", tvNazivVjezbe.getText());
                    context.startActivity(intentSquat);
                    break;
            }
        }
    }
}
