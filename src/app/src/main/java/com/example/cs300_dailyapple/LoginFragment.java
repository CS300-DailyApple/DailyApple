package com.example.cs300_dailyapple;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.cs300_dailyapple.R;

public class LoginFragment extends Fragment {

    private EditText editTextUsername;
    private EditText editTextPassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.login_fragment, container, false);

        editTextUsername = view.findViewById(R.id.editTextUsername);
        editTextPassword = view.findViewById(R.id.editTextPassword);

        Button loginButton = view.findViewById(R.id.LoginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                handleLoginButtonClick();
            }
        });

        return view;
    }

    private void handleLoginButtonClick() {
        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();


    }
}
