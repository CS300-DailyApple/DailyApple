package com.example.cs300_dailyapple.Fragments;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cs300_dailyapple.R;
import com.example.cs300_dailyapple.Services.AuthService;
import com.google.firebase.auth.FirebaseUser;
import com.google.rpc.context.AttributeContext;


public class RepasswordFragment extends Fragment {
    EditText editTextEmail;
    AppCompatImageButton buttonSend;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_repassword, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editTextEmail = view.findViewById(R.id.editTextEmail);
        buttonSend = view.findViewById(R.id.SendButton);
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString();
                if (email.isEmpty()) {
                    editTextEmail.setError("Email is required");
                    editTextEmail.requestFocus();
                    return;
                } else {
                    // Check if email is valid
                    if (!isValidEmail(email)) {
                        editTextEmail.setError("Invalid email");
                        editTextEmail.requestFocus();
                        return;
                    }
                    else {
                        // Send email to reset password and navigate to login fragment
                        AuthService.getInstance().resetPassword(email, new AuthService.AuthCallback() {
                            @Override
                            public void onSuccess(FirebaseUser user) {
                                // Navigate to login fragment
                                Navigation.findNavController(view).navigate(R.id.action_repasswordFragment_to_loginFragment);
                                Toast.makeText(getContext(), "Email sent", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Exception e) {
                                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });
    }
    private boolean isValidEmail(CharSequence email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}