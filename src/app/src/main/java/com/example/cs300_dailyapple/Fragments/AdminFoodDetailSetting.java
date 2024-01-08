package com.example.cs300_dailyapple.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.cs300_dailyapple.R;
import com.example.cs300_dailyapple.Services.DataService;

public class AdminFoodDetailSetting extends Fragment {

    private TextView adminFoodDetailSettingName;
    private Button adminFoodDetailSettingDeleteBtn;
    private Button adminFoodDetailSettingEditBtn;

    public AdminFoodDetailSetting() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_food_detail_setting, container, false);

        adminFoodDetailSettingName = view.findViewById(R.id.AdminFoodDetailSettingName);
        adminFoodDetailSettingDeleteBtn = view.findViewById(R.id.AdminFoodDetailSettingDeleteBtn);
        adminFoodDetailSettingEditBtn = view.findViewById(R.id.AdminFoodDetailSettingEditBtn);

        // get data from bundle or your data source
        Bundle bundle = getArguments();
        if (bundle != null) {
            String foodName = bundle.getString("foodName");
            adminFoodDetailSettingName.setText(foodName);
        }

        adminFoodDetailSettingDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle delete button click
                onDeleteButtonClick();
            }
        });

        adminFoodDetailSettingEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle edit button click
                onEditButtonClick();
            }
        });

        return view;
    }

    private void onDeleteButtonClick() {
        // Implement your logic for deleting the food item
        Bundle bundle = getArguments();
        String foodId = bundle.getString("foodId");
        DataService.getInstance().deleteSharedFoodById(foodId);
        Toast.makeText(getContext(), "Delete successfully", Toast.LENGTH_SHORT).show();
        Navigation.findNavController(getView()).navigate(R.id.action_adminFoodDetailSetting_to_adminFoodList);
    }

    private void onEditButtonClick() {
        // Implement your logic for editing the food item
        Bundle bundle = getArguments();
        String foodId = bundle.getString("foodId");
        Bundle bundle1 = new Bundle();
        bundle1.putString("foodId", foodId);
        bundle1.putString("foodName", adminFoodDetailSettingName.getText().toString());
        Navigation.findNavController(getView()).navigate(R.id.action_adminFoodDetailSetting_to_adminFoodEditingFirst, bundle1);
    }
}
