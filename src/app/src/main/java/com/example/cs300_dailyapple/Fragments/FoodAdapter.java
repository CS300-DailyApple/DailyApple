package com.example.cs300_dailyapple.Fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs300_dailyapple.Models.Food;
import com.example.cs300_dailyapple.R;

import java.text.Normalizer;
import java.util.LinkedList;
import java.util.regex.Pattern;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder>{
    private LinkedList<Food> FoodList;
    private Context context;
    private FoodFragment foodFragment;

    public FoodAdapter(LinkedList<Food> FoodList, Context context, FoodFragment foodFragment) {
        this.FoodList = FoodList;
        this.context = context;
        this.foodFragment = foodFragment;
    }
    public void setFoodList(LinkedList<Food> foodList) {
        this.FoodList = foodList;
    }

    public void deleteFood(int position) {
        FoodList.remove(position);
        notifyDataSetChanged();
    }
    @NonNull

    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false);
        return new FoodViewHolder(view, this);
    }
    public Food getFoodAtPosition(int position) {
        return FoodList.get(position);
    }
    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        Food food = FoodList.get(position);
        String foodName=convertToNonAccent(food.getName());
        setResized8080(context,foodName,holder.foodImage);

        holder.textViewName.setText(food.getName());
        String attributes = food.getNumberOfUnits() + " " + food.getUnit() + " - "+ food.getNutritionPerUnit().getCalories()+" calo";
        holder.textViewAttributes.setText(attributes);
        if (food.getFavorite()==true){
            holder.favoriteButton.setSelected(true);
        } else {
            holder.favoriteButton.setSelected(false);
        }

    }

    @Override
    public int getItemCount() {
        return FoodList.size();
    }

    public void removeItem(int position) {
        FoodList.remove(position);
        notifyItemRemoved(position);
    }

    public LinkedList<Food> getFoodList() {
        return FoodList;
    }

    static class FoodViewHolder extends RecyclerView.ViewHolder {
        ImageView foodImage;
        TextView textViewName;
        TextView textViewAttributes;
        ImageButton favoriteButton;
        FoodAdapter adapter;
        Context context;
        public FoodViewHolder(@NonNull View itemView, FoodAdapter adapter) {
            super(itemView);
            this.adapter=adapter;
            this.context = itemView.getContext();
            favoriteButton = itemView.findViewById(R.id.favoriteButton);

            favoriteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {

                        Food selectedFood = adapter.getFoodAtPosition(position);

                        // Đảo ngược trạng thái favorite của Food
                        selectedFood.toggleFavorite();

                        // Cập nhật trạng thái selected hoặc no selected của Food
                        if (selectedFood.getFavorite()) {
                            favoriteButton.setSelected(true);
                        } else {
                            favoriteButton.setSelected(false);
                        }

                        // Cập nhật giao diện người dùng tại vị trí đã nhấn nút
                        adapter.notifyItemChanged(position);

                        // Cập nhật danh sách thức ăn trong fragment
                        LinkedList<Food> updatedFoodList = adapter.getFoodList();
                        adapter.mealFragment.updateTotalCalories(updatedFoodList);
                        Food.saveFoodList(updatedFoodList, context);
                    }
                }
            });
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewAttributes = itemView.findViewById(R.id.textViewAttributes);
            foodImage= itemView.findViewById(R.id.foodImage);
        }

    }
    public static String convertToNonAccent(String input) {
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);

        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        String result = pattern.matcher(normalized)
                .replaceAll("")
                .replaceAll("[^\\p{Alnum}]", ""); // chỉ giữ lại chữ cái và chữ số

        // Chuyển đổi thành chữ thường
        return result.toLowerCase();
    }
    public static void setResized8080(Context context, String imageName, ImageView imageView) {
        // Lấy ID của tài nguyên hình ảnh từ tên tài nguyên
        int resourceId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());

        if (resourceId != 0) {
            // Đọc hình ảnh từ tài nguyên
            Bitmap originalBitmap = BitmapFactory.decodeResource(context.getResources(), resourceId);

            // Resize hình ảnh thành kích thước mong muốn (ở đây là 80x80)
            Bitmap resizedBitmap = Bitmap.createScaledBitmap(originalBitmap, 80, 80, false);

            // Đặt hình ảnh đã resize vào ImageView
            imageView.setImageBitmap(resizedBitmap);
        }
    }
}
