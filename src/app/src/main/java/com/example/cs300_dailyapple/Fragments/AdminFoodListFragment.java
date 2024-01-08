package com.example.cs300_dailyapple.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs300_dailyapple.Models.Food;
import com.example.cs300_dailyapple.R;
import com.example.cs300_dailyapple.Services.DataService;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class AdminFoodListFragment extends Fragment implements AdminFoodListItemAdapter.OnFoodItemClickListener {

    private RecyclerView recyclerViewFood;
    private AdminFoodListItemAdapter foodListAdapter;
    private AppCompatImageButton adminFoodSettingBtn;
    private SearchView searchView;
    private LinkedList<Food> foodList;
    private TextView noResultTextView;
    public AdminFoodListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_food_list, container, false);

        recyclerViewFood = view.findViewById(R.id.recyclerViewFood);
        recyclerViewFood.setLayoutManager(new LinearLayoutManager(getContext()));
        noResultTextView = view.findViewById(R.id.NoResult);
        foodListAdapter = new AdminFoodListItemAdapter(new ArrayList<>());
        recyclerViewFood.setAdapter(foodListAdapter);
        adminFoodSettingBtn = view.findViewById(R.id.Settings);
        adminFoodSettingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getView()).navigate(R.id.action_adminFoodList_to_adminFoodSetting);
            }
        });

        loadFoods("");

        searchView = view.findViewById(R.id.searchView);
        searchView.setQueryHint("Search by food name");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) { // When user press search button
                loadFoods(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) { // When user type in search bar
                loadFoods(newText);
                return false;
            }
        });


        return view;
    }
    private void loadFoods(String query) {
        foodList = DataService.getInstance().searchSharedFoods(query);
        if (foodList.isEmpty()) {
            noResultTextView.setVisibility(View.VISIBLE);
            recyclerViewFood.setVisibility(View.GONE);
        } else {
            noResultTextView.setVisibility(View.GONE);
            recyclerViewFood.setVisibility(View.VISIBLE);
            foodListAdapter.setFoodList(foodList, this::onFoodItemClick);
        }
    }
    @Override
    public void onFoodItemClick(Food food) {
        Bundle bundle = new Bundle();
        // get the food id for later use
        bundle.putString("foodId", DataService.getInstance().getFoodId(food.getName()));
        Navigation.findNavController(getView()).navigate(R.id.action_adminFoodList_to_adminFoodDetail, bundle);
    }
    @Override
    public void onResume() {
        super.onResume();
        loadFoods("");
    }
}
