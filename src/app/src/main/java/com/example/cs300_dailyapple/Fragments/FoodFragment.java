package com.example.cs300_dailyapple.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.cs300_dailyapple.Models.Food;
import com.example.cs300_dailyapple.R;

import java.util.LinkedList;


public class FoodFragment extends Fragment {

    private RecyclerView recyclerViewFood;
    private FoodAdapter foodAdapter;
    ImageButton backButton;
    private LinkedList<Food> fullFoodList;
    private TextView noResultTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_food, container, false);

        backButton=view.findViewById(R.id.BackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        Context context = this.getContext();
        LinkedList<Food> foodList = Food.loadFoodList(context);
        fullFoodList = new LinkedList<>(foodList);
        recyclerViewFood = view.findViewById(R.id.recyclerViewFood);
        foodAdapter = new FoodAdapter(Food.loadFoodList(context),context,this);
        recyclerViewFood.setLayoutManager(new LinearLayoutManager(context));
        recyclerViewFood.setAdapter(foodAdapter);
        noResultTextView = view.findViewById(R.id.NoResult);
        noResultTextView.setVisibility(View.GONE);


        SearchView searchView = view.findViewById(R.id.searchView);
        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint("Tìm kiếm món ăn...");
        // Xử lý sự kiện khi người dùng thay đổi nội dung tìm kiếm
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Xử lý khi người dùng nhấn nút Search trên bàn phím
                performSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Xử lý khi người dùng thay đổi nội dung tìm kiếm
                performSearch(newText);
                return false;
            }
        });


        return view;
    }
    private void performSearch(String query) {
        LinkedList<Food> filteredList = new LinkedList<>();

        // Nếu query rỗng, hiển thị toàn bộ danh sách
        if (query.isEmpty()) {
            filteredList.addAll(fullFoodList);
        } else {
            // Lọc danh sách dựa trên query
            for (Food food : fullFoodList) {
                if (food.getName().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(food);
                }
            }
        }

        // Cập nhật danh sách hiển thị trong adapter
        foodAdapter.setFoodList(filteredList);
        foodAdapter.notifyDataSetChanged();

        // Hiển thị thông báo nếu không có kết quả
        if (filteredList.isEmpty()) {
            noResultTextView.setVisibility(View.VISIBLE); // Hiển thị khi không có kết quả
        } else {
            noResultTextView.setVisibility(View.GONE); // Ẩn khi có kết quả
        }
    }

}