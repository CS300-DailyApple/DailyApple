package com.example.cs300_dailyapple.Fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
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

import java.text.Normalizer;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.regex.Pattern;


public class FoodFragment extends Fragment {

    private RecyclerView recyclerViewFood;
    private FoodAdapter foodAdapter;
    private TextView noResultTextView;

    private LinkedList<Food> foodList;
    private AppCompatImageButton settingButton;

    private GlobalApplication globalApplication;
    NavController navController;
    private class SearchAsyncTask extends AsyncTask<String, Void, LinkedList<Food>> {

        private FoodFragment foodFragment;

        public SearchAsyncTask(FoodFragment foodFragment) {
            this.foodFragment = foodFragment;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Hiển thị tiến trình tìm kiếm (nếu cần)
        }

        @Override
        protected LinkedList<Food> doInBackground(String... params) {
            String query = params[0];
            LinkedList<Food> filteredList = new LinkedList<>();

            // Thực hiện công việc tìm kiếm ở đây (tương tự như trong hàm performSearch)
            // Chuyển đổi query và tên thức ăn về chữ thường
            String lowerCaseQuery = convertToNonAccent(query);
            for (Food food : foodFragment.foodList) {
                if (convertToNonAccent(food.getName()).contains(lowerCaseQuery)) {
                    filteredList.add(food);
                }
            }
            Collections.sort(filteredList, new Comparator<Food>() {
                @Override
                public int compare(Food food1, Food food2) {
                    // Sắp xếp theo trạng thái favorite giảm dần
                    return Boolean.compare(food2.getFavorite(), food1.getFavorite());
                }
            });
            return filteredList;
        }

        @Override
        protected void onPostExecute(LinkedList<Food> result) {
            super.onPostExecute(result);
            // Ẩn tiến trình tìm kiếm (nếu cần)
            // Cập nhật RecyclerView và hiển thị kết quả
            foodFragment.updateFoodList(result);
        }
        private String convertToNonAccent(String input) {
            String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);

            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            String result = pattern.matcher(normalized)
                    .replaceAll("")
                    .replaceAll("[^\\p{Alnum}]", ""); // chỉ giữ lại chữ cái và chữ số

            // Chuyển đổi thành chữ thường
            return result.toLowerCase();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Context context = this.getContext();
        globalApplication = (GlobalApplication)this.getActivity().getApplication();
        foodList = globalApplication.getFoodList();
        Collections.sort(foodList, new Comparator<Food>() {
            @Override
            public int compare(Food food1, Food food2) {
                // Sắp xếp theo trạng thái favorite giảm dần
                return Boolean.compare(food2.getFavorite(), food1.getFavorite());
            }
        });
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
                onNavigationFragment();
                navController.navigate(R.id.action_foodFragment_to_settingDishFragment);
            }
        });

        OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                onNavigationFragment();
            }
        };
        androidx.appcompat.widget.SearchView searchView = view.findViewById(R.id.searchView);
        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint("Tìm kiếm món ăn...");
        // Xử lý sự kiện khi người dùng thay đổi nội dung tìm kiếm
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Xử lý khi người dùng ấn nút tìm kiếm trên bàn phím
                performSearchAsync(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Xử lý khi người dùng thay đổi nội dung tìm kiếm
                performSearchAsync(newText);
                return false;
            }
        });
    }

    public void onNavigationFragment(){
        globalApplication.setFoodList(foodList);
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
        if (query.equals("")) {
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
    private void performSearchAsync(String query) {
        SearchAsyncTask searchAsyncTask = new SearchAsyncTask(this);
        searchAsyncTask.execute(query);
    }

    private void updateFoodList(LinkedList<Food> filteredList) {
        // Cập nhật danh sách hiển thị trong adapter
        foodAdapter.setFoodList(filteredList);
        foodAdapter.notifyDataSetChanged();

        // Hiển thị thông báo nếu không có kết quả
        noResultTextView.setVisibility(filteredList.isEmpty() ? View.VISIBLE : View.GONE);
    }

}