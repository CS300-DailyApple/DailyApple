package com.example.cs300_dailyapple.Fragments;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.cs300_dailyapple.R;

public class LoginFragment extends Fragment {


    private EditText editTextPassword;
    TextView forgotPassword;
    TextView createAccount;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.login_fragment, container, false);

        forgotPassword=view.findViewById(R.id.forgotPassword);
        String textfp= "Quên mật khẩu";
        SpannableString spannableStringfp = new SpannableString(textfp);
        spannableStringfp.setSpan(new UnderlineSpan(), 0, textfp.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        forgotPassword.setText(spannableStringfp);
        createAccount=view.findViewById(R.id.createAccount);
        String text= "Tạo tài khoản mới";
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new UnderlineSpan(), 0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        createAccount.setText(spannableString);

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

        String password = editTextPassword.getText().toString();
        // Save data here


    }
}
