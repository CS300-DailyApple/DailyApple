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
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(getContext(), "Email and password cannot be empty", Toast.LENGTH_SHORT).show();
        }
        else {
            AuthService.getInstance().registerUser(email, password, new AuthService.AuthCallback() {
                @Override
                public void onSuccess(FirebaseUser user) {
                    // get the user id and add it to bundle
                    // then navigate to the getInformation
                    String uid = user.getUid();
                    Bundle bundle = new Bundle();
                    bundle.putString("uid", uid);
                    Toast.makeText(getContext(), "Account created successfully", Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(view).navigate(R.id.action_createAccountFragment_to_getInformation, bundle);
                }

                @Override
                public void onFailure(Exception exception) {
                    Toast.makeText(getContext(), "Account creation failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}