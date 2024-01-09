package com.example.cs300_dailyapple.Fragments;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cs300_dailyapple.R;
import com.example.cs300_dailyapple.Services.AuthService;
import com.google.firebase.auth.FirebaseUser;


public class ChangePasswordFragment extends Fragment {
    EditText oldPassword, newPassword;
    AppCompatImageButton changePassword;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_change_password, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        oldPassword = view.findViewById(R.id.editTextCurrentPassword);
        newPassword = view.findViewById(R.id.editTextNewPassword);
        changePassword = view.findViewById(R.id.changePasswordButton);
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPass = oldPassword.getText().toString();
                String newPass = newPassword.getText().toString();
                AuthService.getInstance().changePassword(oldPass, newPass, new AuthService.AuthCallback() {
                    @Override
                    public void onSuccess(FirebaseUser user) {
                        // show success message and navigate back to login page
                        Toast.makeText(getContext(), "Đổi mật khẩu thành công, vui lòng đăng nhập lại.", Toast.LENGTH_SHORT).show();
                        Navigation.findNavController(v).navigate(R.id.action_changePasswordFragment_to_loginFragment);
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(getContext(), "Đổi mật khẩu thất bại, vui lòng thử lại.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}