package com.example.cs300_dailyapple.Fragments;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cs300_dailyapple.R;
import com.example.cs300_dailyapple.Services.AuthService;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseUser;
import com.google.rpc.context.AttributeContext;


public class CreateAccountFragment extends Fragment {
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextUsername;
    private AppCompatImageButton buttonCreateAccount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_account, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        editTextUsername = view.findViewById(R.id.editTextUsername);
        buttonCreateAccount = view.findViewById(R.id.createAccountButton);
        buttonCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleCreateAccount(v);
            }
        });
    }

    private void handleCreateAccount(View view) {
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        String username = editTextUsername.getText().toString();
        if (email.isEmpty() || password.isEmpty() || username.isEmpty()) {
            Toast.makeText(getContext(), "Email, password and username cannot be empty", Toast.LENGTH_SHORT).show();
        }
        else {
            Bundle bundle = new Bundle();
            bundle.putString("username", username);
            bundle.putString("email", email);
            bundle.putString("password", password);
            Navigation.findNavController(view).navigate(R.id.action_createAccountFragment_to_getInformation, bundle);
        }
    }
}