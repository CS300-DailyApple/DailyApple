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
import androidx.navigation.Navigation;

import com.example.cs300_dailyapple.Models.GlobalApplication;
import com.example.cs300_dailyapple.Models.User;
import com.example.cs300_dailyapple.R;
import com.example.cs300_dailyapple.Services.DataService;

import java.util.ArrayList;

public class AdminUserItemDetail extends Fragment {
    private GlobalApplication globalApplication;
    private ArrayList<User> userList;
    private TextView textViewName;
    private TextView textViewAttributes;
    private Button banButton;
    Bundle bundle;
    String userName;
    int creditPoints;

    public AdminUserItemDetail() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        globalApplication = (GlobalApplication)this.getActivity().getApplication();
        userList = globalApplication.getForAdminUserList();
        bundle = getArguments();
        userName = bundle.getString("username");
        creditPoints = bundle.getInt("creditPoints");
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


        // Set data to UI elements
        textViewName.setText("Tên người dùng: " + userName);
        textViewAttributes.setText("Uy tín: " + creditPoints);
        // Set ban button UI
        updateBanButtonUI();

        // Set onClickListener for ban button
        banButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // find user in userList
                for (User user : userList) {
                    if (user.getUsername().equals(userName)) {
                        // ban/unban user
                        if (user.isBanned()) {
                            user.setBanned(false);
                            banButton.setText("Ban");
                        } else {
                            user.setBanned(true);
                            banButton.setText("Unban");
                        }
                        // set user in userList
                        globalApplication.setForAdminUserList(userList);
                        // update ban button UI
                        updateBanButtonUI();
                    }
                }
            }
        });
    }
    private void updateBanButtonUI() {
        for (User user : userList) {
            if (user.getUsername().equals(userName)) {
                if (user.isBanned()) {
                    banButton.setText("Unban");
                } else {
                    banButton.setText("Ban");
                }
                break;
            }
        }
    }
}
