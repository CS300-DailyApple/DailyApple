package com.example.cs300_dailyapple.Fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs300_dailyapple.Models.Food;
import com.example.cs300_dailyapple.Models.User;
import com.example.cs300_dailyapple.R;

import java.util.List;

public class AdminFoodListItemAdapter extends RecyclerView.Adapter<AdminFoodListItemAdapter.AdminFoodListViewHolder> {
    public interface OnFoodItemClickListener {
        void onFoodItemClick(Food food);
    }
    private List<Food> foodList;
    private OnFoodItemClickListener listener;

    public AdminFoodListItemAdapter(List<Food> foodList) {
        this.foodList = foodList;
    }

    public void setFoodList(List<Food> foodList, OnFoodItemClickListener listener) {
        this.foodList = foodList;
        this.listener = listener;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdminFoodListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_food_list_item, parent, false);
        return new AdminFoodListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminFoodListViewHolder holder, int position) {
        Food food = foodList.get(position);

        holder.foodImage.setImageResource(R.drawable.food_photo_placeholder); // Set your placeholder image
        holder.textViewName.setText(food.getName());
        holder.textViewAttributes.setText(food.getAmount() + " - " + food.getKcal() + " calo");

        // Handle item click
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onFoodItemClick(food);
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    static class AdminFoodListViewHolder extends RecyclerView.ViewHolder {
        ImageView foodImage;
        TextView textViewName;
        TextView textViewAttributes;

        AdminFoodListViewHolder(@NonNull View itemView) {
            super(itemView);
            foodImage = itemView.findViewById(R.id.foodImage);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewAttributes = itemView.findViewById(R.id.textViewAttributes);
        }
    }
}

