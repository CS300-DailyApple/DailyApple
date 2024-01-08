package com.example.cs300_dailyapple.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cs300_dailyapple.R;
import com.example.cs300_dailyapple.Services.DataService;

public class AdminUserItemDetail extends Fragment {

    private TextView textViewName;
    private TextView textViewAttributes;
    private Button banButton;

    public AdminUserItemDetail() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_admin_user_item_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize UI elements
        textViewName = view.findViewById(R.id.textViewName);
        textViewAttributes = view.findViewById(R.id.textViewAttributes);
        banButton = view.findViewById(R.id.AdminUserDetailBanBtn);

        // Dummy data (replace with actual user data)
        Bundle bundle = new Bundle();
        bundle = getArguments();
        String userName = bundle.getString("username");
        int creditPoints = bundle.getInt("creditPoints");

        // Set data to UI elements
        textViewName.setText("Tên người dùng: " + userName);
        textViewAttributes.setText("Uy tín: " + creditPoints);

        // Set onClickListener for ban button
        banButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement ban user action here
                DataService.getInstance().banUser(userName);
            }
        });
    }
}
