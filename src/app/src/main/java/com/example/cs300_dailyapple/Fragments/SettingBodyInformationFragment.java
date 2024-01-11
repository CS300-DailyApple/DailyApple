package com.example.cs300_dailyapple.Fragments;

import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cs300_dailyapple.R;
import com.example.cs300_dailyapple.Services.AuthService;

public class SettingBodyInformationFragment extends Fragment {
    TextView updateInformation;
    TextView history;
    TextView changePassword;
    TextView logout;
    NavController navController;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateInformation= view.findViewById(R.id.updateInformation);
        SpannableString updateInformationString = new SpannableString("Cập nhật thông tin");
        updateInformationString.setSpan(new StyleSpan(Typeface.BOLD), 0, updateInformationString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        updateInformationString.setSpan(new UnderlineSpan(), 0, updateInformationString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        updateInformation.setText(updateInformationString);
        navController = Navigation.findNavController(view);
        updateInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Here to navigate
                navController.navigate(R.id.action_settingBodyInformationFragment_to_updateBodyStatisticFragment);
            }
        });

        history= view.findViewById(R.id.history);
        SpannableString historyString = new SpannableString("Lịch sử thay đổi");
        historyString.setSpan(new StyleSpan(Typeface.BOLD), 0, historyString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        historyString.setSpan(new UnderlineSpan(), 0, historyString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        history.setText(historyString);
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Here to navigate
                navController.navigate(R.id.action_settingBodyInformationFragment_to_historyFragment);
            }
        });
        changePassword= view.findViewById(R.id.changePassword);
        SpannableString changePasswordString = new SpannableString("Thay đổi mật khẩu");
        changePasswordString.setSpan(new StyleSpan(Typeface.BOLD), 0, changePasswordString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        changePasswordString.setSpan(new UnderlineSpan(), 0, changePasswordString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        changePassword.setText(changePasswordString);
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Here to navigate
                navController.navigate(R.id.action_settingBodyInformationFragment_to_changePasswordFragment);
            }
        });

        logout= view.findViewById(R.id.logout);
        SpannableString logoutString = new SpannableString("Đăng xuất");
        logoutString.setSpan(new StyleSpan(Typeface.BOLD), 0, logoutString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        logoutString.setSpan(new UnderlineSpan(), 0, logoutString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        logout.setText(logoutString);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthService.getInstance().logoutUser();
                // Here to navigate
                navController.navigate(R.id.action_settingBodyInformationFragment_to_loginFragment);
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_setting_body_information, container, false);
        return view;
    }
}