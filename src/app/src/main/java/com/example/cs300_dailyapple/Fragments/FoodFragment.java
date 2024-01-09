package com.example.cs300_dailyapple.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.cs300_dailyapple.Models.Food;
import com.example.cs300_dailyapple.Models.GlobalApplication;
import com.example.cs300_dailyapple.R;

import java.util.LinkedList;


public class FoodFragment extends Fragment {

    private RecyclerView recyclerViewFood;
    private FoodAdapter foodAdapter;
    private TextView noResultTextView;

    private LinkedList<Food> foodList;
    private AppCompatImageButton settingButton;

    private GlobalApplication globalApplication;
    NavController navController;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Context context = this.getContext();
        globalApplication = (GlobalApplication)this.getActivity().getApplication();
        foodList = globalApplication.getFoodList();
        System.out.println("The size of the list of food is: " + foodList.size());
        navController = Navigation.findNavController(view);
        recyclerViewFood = view.findViewById(R.id.recyclerViewFood);
        foodAdapter = new FoodAdapter(foodList, context, this);
        recyclerViewFood.setLayoutManager(new LinearLayoutManager(context));
        recyclerViewFood.setAdapter(foodAdapter);
        noResultTextView = view.findViewById(R.id.NoResult);
        noResultTextView.setVisibility(View.GONE);
        settingButton = view.findViewById(R.id.Settings);
        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_foodFragment_to_settingDishFragment);
            }
        });

        androidx.appcompat.widget.SearchView searchView = view.findViewById(R.id.searchView);
        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint("Tìm kiếm món ăn...");
        // Xử lý sự kiện khi người dùng thay đổi nội dung tìm kiếm
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Xử lý khi người dùng ấn nút tìm kiếm trên bàn phím
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Xử lý khi người dùng thay đổi nội dung tìm kiếm
                performSearch(newText);
                return false;
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_food, container, false);
        return view;
    }
    private void performSearch(String query) {
        LinkedList<Food> filteredList = new LinkedList<>();

        // Nếu query rỗng, hiển thị toàn bộ danh sách
        if (query.isEmpty()) {
            filteredList.addAll(foodList);
        } else {
            // Lọc danh sách dựa trên query
            for (Food food : foodList) {
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