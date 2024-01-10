package com.example.cs300_dailyapple.Fragments;

import android.content.Context;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs300_dailyapple.Models.Food;
import com.example.cs300_dailyapple.Models.FoodCompound;
import com.example.cs300_dailyapple.Models.GlobalApplication;
import com.example.cs300_dailyapple.R;

import java.util.ArrayList;
import java.util.LinkedList;

public class DishAdapter extends RecyclerView.Adapter<DishAdapter.DishViewHolder>{
    private LinkedList<Food> dishList;
    private Context context;
    private MealFragment mealFragment;
    NavController navController;
    static GlobalApplication globalApplication;
    public DishAdapter(LinkedList<Food> dishList, Context context, MealFragment mealFragment) {
        this.dishList = dishList;
        this.context = context;
        this.mealFragment = mealFragment;
        globalApplication = (GlobalApplication) GlobalApplication.getInstance();
    }
    public void deleteDish(int position) {
        dishList.remove(position);
        notifyDataSetChanged();
    }
    @NonNull

    @Override
    public DishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dish, parent, false);
        return new DishViewHolder(view, this);
    }
    public Food getFoodAtPosition(int position) {
        return dishList.get(position);
    }
    @Override
    public void onBindViewHolder(@NonNull DishViewHolder holder, int position) {
        Food dish = dishList.get(position);




        holder.textViewName.setText(dish.getName());
        String attributes = dish.getNumberOfUnits() + " " + dish.getUnit() + " - "+ dish.getNutritionPerUnit().getKcal()+" calo";
        holder.textViewAttributes.setText(attributes);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() != R.id.favoriteButton) {
                    navController = Navigation.findNavController(v);
                    navigateToFoodDetail(dish);
                }
            }
        });


    }
    private void navigateToFoodDetail(Food selectedFood) {
        // Use NavController to navigate to FoodDetailFragment
        globalApplication.setCurrentFoodChoosing(selectedFood);
        navController.navigate(R.id.action_mealFragment_to_customFoodinMealFragment);
    }

    @Override
    public int getItemCount() {
        return dishList.size();
    }

    public void removeItem(int position) {
        dishList.remove(position);
        notifyItemRemoved(position);
    }

    public LinkedList<Food> getDishList() {
        return dishList;
    }

    static class DishViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        TextView textViewAttributes;
        ImageButton cancelButton;
        DishAdapter adapter;
        Context context;
        public DishViewHolder(@NonNull View itemView, DishAdapter adapter) {
            super(itemView);
            this.adapter = adapter;
            this.context = itemView.getContext();
            cancelButton = itemView.findViewById(R.id.cancelButton);

            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {

                        adapter.removeItem(position);
                        LinkedList<Food> updatedDishList = adapter.getDishList();
                        ArrayList<Food> newMeal = new ArrayList<>(updatedDishList);
                        FoodCompound meal = new FoodCompound(newMeal);
                        String currentMealUsing = globalApplication.getCurrentMealChoosing();
                        globalApplication.setMeal(currentMealUsing, meal);
                        adapter.mealFragment.updateTotalCalories(updatedDishList);

                    }
                }
            });
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewAttributes = itemView.findViewById(R.id.textViewAttributes);
        }

    }
}
