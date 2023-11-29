package com.example.cs300_dailyapple;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;
import java.util.List;

public class DishAdapter extends RecyclerView.Adapter<DishAdapter.DishViewHolder>{
    private LinkedList<Dish> dishList;
    private Context context;
    private MealFragment mealFragment;

    public DishAdapter(LinkedList<Dish> dishList, Context context, MealFragment mealFragment) {
        this.dishList = dishList;
        this.context = context;
        this.mealFragment = mealFragment;
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
    public Dish getDishAtPosition(int position) {
        return dishList.get(position);
    }
    @Override
    public void onBindViewHolder(@NonNull DishViewHolder holder, int position) {
        Dish dish = dishList.get(position);




        holder.textViewName.setText(dish.getName());
        String attributes = dish.getGram() + " g - " + dish.getCalo() + " calo";
        holder.textViewAttributes.setText(attributes);


    }

    @Override
    public int getItemCount() {
        return dishList.size();
    }

    public void removeItem(int position) {
        dishList.remove(position);
        notifyItemRemoved(position);
    }

    public LinkedList<Dish> getDishList() {
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
            this.adapter=adapter;
            this.context = itemView.getContext();
            cancelButton = itemView.findViewById(R.id.cancelButton);

            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {

                        adapter.removeItem(position);
                        LinkedList<Dish> updatedDishList = adapter.getDishList();
                        adapter.mealFragment.updateTotalCalories(updatedDishList);
                        Dish.saveDishList(updatedDishList,context);
                    }
                }
            });
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewAttributes = itemView.findViewById(R.id.textViewAttributes);
        }

    }
}
