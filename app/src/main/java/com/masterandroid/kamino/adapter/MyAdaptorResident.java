package com.masterandroid.kamino.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.masterandroid.kamino.R;
import com.masterandroid.kamino.activitykamino.ResidentDetailActivity;
import com.masterandroid.kamino.data.model.Resident;

import java.util.List;

public class MyAdaptorResident extends RecyclerView.Adapter<MyAdaptorResident.MyViewHolder> {

    private List<Resident> residents;
    private final Context context;

    public MyAdaptorResident(Context context) {
        this.context = context;
    }

    public void setResidents(List<Resident> residents) {
        this.residents = residents;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.single_items_list_resident, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (residents == null || position >= residents.size()) return;

        Resident resident = residents.get(position);
        holder.textViewName.setText(resident.getName());

        Glide.with(holder.itemView.getContext())
                .load(resident.getImageUrl())
                .placeholder(R.drawable.myprofile)
                .error(R.drawable.myprofile)
                .into(holder.imageView);

        if (position % 2 == 1) {
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.silver));
        } else {
            holder.itemView.setBackgroundColor(context.getResources().getColor(android.R.color.white));
        }

        holder.btnViewDetails.setOnClickListener(v -> {
            Intent intent = new Intent(context, ResidentDetailActivity.class);
            intent.putExtra("id", resident.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return residents != null ? residents.size() : 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;
        public ImageView imageView;
        Button btnViewDetails;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.txtname_id);
            imageView = itemView.findViewById(R.id.img_id);
            btnViewDetails = itemView.findViewById(R.id.btn_details);
        }
    }




}
