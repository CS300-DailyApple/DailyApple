package com.example.cs300_dailyapple.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs300_dailyapple.Fragments.SuggestedFoodAdapter;
import com.example.cs300_dailyapple.Models.Food;
import com.example.cs300_dailyapple.Models.GlobalApplication;
import com.example.cs300_dailyapple.Models.SuggestedFood;
import com.example.cs300_dailyapple.R;

import java.util.LinkedList;

public class SuggestedFoodFragment extends Fragment implements SuggestedFoodAdapter.OnFoodItemClickListener {

    GlobalApplication globalApplication;
    LinkedList<SuggestedFood> suggestedFoodList;
    private SuggestedFoodAdapter adapter;

    public SuggestedFoodFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        globalApplication = (GlobalApplication) getActivity().getApplicationContext();
        suggestedFoodList = globalApplication.getForAdminSuggestedFoodList();
        adapter = new SuggestedFoodAdapter(requireContext(), suggestedFoodList, this);
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
    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }
    @Override
    public void onFoodItemClick(SuggestedFood food) {
        Bundle bundle = new Bundle();
        bundle.putString("foodName", food.getName());
        Navigation.findNavController(getView()).navigate(R.id.action_suggestedFoodFragment_to_suggestFoodDetailsFragment, bundle);
    }
}
