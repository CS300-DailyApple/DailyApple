package com.example.cs300_dailyapple.Fragments;

// HistoryFragment.java
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs300_dailyapple.Models.PersonalInformation;
import com.example.cs300_dailyapple.R;

import java.util.LinkedList;

public class HistoryFragment extends Fragment implements HistoryAdapter.OnCancelButtonClickListener {

    private LinkedList<PersonalInformation> personalInformationList;
    private HistoryAdapter historyAdapter;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewHistory);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Load personal information list
        personalInformationList = new PersonalInformation().loadPersonalInformationList(getActivity());

        historyAdapter = new HistoryAdapter(getActivity(), personalInformationList, this);
        recyclerView.setAdapter(historyAdapter);

        return view;
    }

    @Override
    public void onCancelButtonClick(int position) {
        removeItem(position);
    }

    private void removeItem(int position) {
        personalInformationList.remove(position);
        historyAdapter.notifyItemRemoved(position);
        Context context= this.getContext();
        PersonalInformation.savePersonalInformationList(personalInformationList, context);
    }
}
