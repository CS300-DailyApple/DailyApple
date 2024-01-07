package com.example.cs300_dailyapple.Fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs300_dailyapple.Models.Food;
import com.example.cs300_dailyapple.R;

import java.text.Normalizer;
import java.util.LinkedList;
import java.util.regex.Pattern;

public class SuggestedFoodAdapter extends RecyclerView.Adapter<SuggestedFoodAdapter.FoodViewHolder> {

    private final LinkedList<Food> foodList;
    private final Context context;

    public SuggestedFoodAdapter(Context context, LinkedList<Food> foodList) {
        this.context = context;
        this.foodList = foodList;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.suggested_food, parent, false);
        return new FoodViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        Food currentFood = foodList.get(position);

        String foodName = convertToNonAccent(currentFood.getName());
        setResized8080(context, foodName, holder.foodImage);

        holder.textViewName.setText(currentFood.getName());
        String attributes = currentFood.getNumberOfUnits() + " " + currentFood.getUnit() + " - " + currentFood.getNutritionPerUnit().getKcal() + " calo";
        holder.textViewAttributes.setText(attributes);
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    static class FoodViewHolder extends RecyclerView.ViewHolder {
        final ImageView foodImage;
        final TextView textViewName;
        final TextView textViewAttributes;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            foodImage = itemView.findViewById(R.id.foodImage);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewAttributes = itemView.findViewById(R.id.textViewAttributes);
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
