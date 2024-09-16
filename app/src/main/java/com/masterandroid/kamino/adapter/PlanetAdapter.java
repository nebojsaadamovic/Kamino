package com.masterandroid.kamino.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.masterandroid.kamino.R;
import com.masterandroid.kamino.data.model.Planet;

import java.util.ArrayList;
import java.util.List;


//not disturb
public class PlanetAdapter extends RecyclerView.Adapter<PlanetAdapter.PlanetHolder> {
    private List<Planet> planets = new ArrayList<>();

    @NonNull
    @Override
    public PlanetHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.planet_item, parent,false);
        return new PlanetHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PlanetHolder holder, int position) {
        Planet currentPlanet=planets.get(position);
        holder.textViewTitle.setText(currentPlanet.getName());
        holder.textViewDescription.setText(currentPlanet.getPopulation());
        holder.textViewPriority.setText(currentPlanet.getPopulation());
    }

    @Override
    public int getItemCount() {
        return planets.size();
    }

    public void setPlanets(List<Planet>planets){
        this.planets=planets;
        notifyDataSetChanged();
    }


    class PlanetHolder extends RecyclerView.ViewHolder{
        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewPriority;

        public PlanetHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle=itemView.findViewById(R.id.txt_view_title);
            textViewDescription = itemView.findViewById(R.id.txt_view_description);
            textViewPriority = itemView.findViewById(R.id.txt_view_priority);
        }

    }


}
