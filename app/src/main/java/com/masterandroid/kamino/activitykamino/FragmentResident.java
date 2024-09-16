package com.masterandroid.kamino.activitykamino;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.masterandroid.kamino.R;
import com.masterandroid.kamino.adapter.MyAdaptorResident;
import com.masterandroid.kamino.data.model.Resident;
import com.masterandroid.kamino.data.repository.PlanetRepository;
import com.masterandroid.kamino.viewmodel.ResidentViewModel;

import java.util.List;

public class FragmentResident extends Fragment {


    private ResidentViewModel residentViewModel;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_resident, container, false);

        recyclerView = view.findViewById(R.id.recyclerview_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        MyAdaptorResident myAdaptorResident = new MyAdaptorResident(getContext());
        recyclerView.setAdapter(myAdaptorResident);

        residentViewModel = new ViewModelProvider(this).get(ResidentViewModel.class);

       residentViewModel.setPlanetId(10); //this data is hardcode, later i will change it

        residentViewModel.getAllResidentsByPlanet().observe(getViewLifecycleOwner(), new Observer<List<Resident>>() {
            @Override
            public void onChanged(List<Resident> residents) {
                if (residents != null) {
                    myAdaptorResident.setResidents(residents);

                }
            }
        });

        return view;
    }
}
