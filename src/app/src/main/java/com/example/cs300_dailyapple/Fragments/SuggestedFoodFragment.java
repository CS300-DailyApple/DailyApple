package com.example.cs300_dailyapple.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs300_dailyapple.Fragments.SuggestedFoodAdapter;
import com.example.cs300_dailyapple.Models.Food;
import com.example.cs300_dailyapple.R;

import java.util.LinkedList;

public class SuggestedFoodFragment extends Fragment {

    private LinkedList<Food> foodList;
    private SuggestedFoodAdapter adapter;

    public SuggestedFoodFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize your food list here, you can call loadFoodList or provide data manually
        foodList = new LinkedList<>();
        adapter = new SuggestedFoodAdapter(requireContext(), foodList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_suggested_food, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewFood);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }
}
