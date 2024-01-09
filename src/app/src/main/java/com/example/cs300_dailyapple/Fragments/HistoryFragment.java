package com.example.cs300_dailyapple.Fragments;

// HistoryFragment.java
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs300_dailyapple.Models.GlobalApplication;
import com.example.cs300_dailyapple.Models.PersonalInformation;
import com.example.cs300_dailyapple.R;

import java.util.LinkedList;

public class HistoryFragment extends Fragment {

    public PersonalInformation Data;
    public HistoryAdapter adapter;
    public RecyclerView recyclerView;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerViewHistory);

        //Data loading
        GlobalApplication GA = (GlobalApplication) this.getActivity().getApplication();

        Data = GA.getUser().getPersonalInformation();

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        adapter = new HistoryAdapter(Data.getHistoryPI());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

}
