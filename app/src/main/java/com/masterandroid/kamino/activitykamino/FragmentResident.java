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
import com.masterandroid.kamino.data.model.Resident;
import com.masterandroid.kamino.viewmodel.FragmentResidentViewModel;

import java.util.List;

public class FragmentResident extends Fragment {
    private FragmentResidentViewModel viewModel;
    private RecyclerView recyclerView;
    private MyAdaptorResident myAdaptorResident;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_resident, container, false);

        recyclerView = view.findViewById(R.id.recyclerview_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        myAdaptorResident = new MyAdaptorResident(getContext());
        recyclerView.setAdapter(myAdaptorResident);

        viewModel = new ViewModelProvider(this).get(FragmentResidentViewModel.class);
        viewModel.getResidents().observe(getViewLifecycleOwner(), new Observer<List<Resident>>() {
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
