package com.example.cs300_dailyapple.Fragments;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NavigationRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.cs300_dailyapple.Models.GlobalApplication;
import com.example.cs300_dailyapple.R;
import com.example.cs300_dailyapple.Services.AuthService;
import com.example.cs300_dailyapple.Services.DataService;
import com.google.firebase.auth.FirebaseUser;

public class LoginFragment extends Fragment {
    private EditText editTextEmail;
    private EditText editTextPassword;
    TextView forgotPassword;
    TextView createAccount;
    GlobalApplication globalApplication;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.login_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        globalApplication = (GlobalApplication)this.getActivity().getApplication();
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

        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextPassword = view.findViewById(R.id.editTextPassword);

        AppCompatImageButton loginButton = view.findViewById(R.id.LoginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLoginButtonClick();
            }
        });

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getView()).navigate(R.id.action_loginFragment_to_createAccountFragment);
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getView()).navigate(R.id.action_loginFragment_to_repasswordFragment);
            }
        });
    }

    private void handleLoginButtonClick() {
        String username = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(getContext(), "Tên đăng nhập và mật khẩu không thể để trống", Toast.LENGTH_SHORT).show();
            return;
        }
        AuthService.getInstance().loginUser(username, password, new AuthService.AuthCallback() {
            @Override
            public void onSuccess(FirebaseUser user) {
                DataService db = DataService.getInstance();
                String role = db.getUserRole(user.getUid());
                // check if user is banned

                if (role.equals("admin")) {
                    // TODO: Navigate to admin page
                    Log.d("LoginFragment", "Admin login");
                    DataService.getInstance().setCalled(true);
                    GlobalApplication globalApplication = (GlobalApplication) requireActivity().getApplication();
                    globalApplication.queryForAdminLists();
                    Navigation.findNavController(getView()).navigate(R.id.action_loginFragment_to_homeAdminFragment);
                } else {
                    System.out.println(db.getUser(user.getUid()).isBanned());
                    if (db.getUser(user.getUid()).isBanned()) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        System.out.println("Account banned");
                        builder.setTitle("Tài khoản của bạn đã bị cấm");
                        builder.setMessage("Vui lòng liên hệ với quản trị viên để biết thêm chi tiết");
                        builder.setPositiveButton("Đóng", null);
                        AuthService.getInstance().logoutUser();
                        builder.show();
                        return;
                    }
                }

                if (role.equals("user")) {

                    // TODO: Navigate to user page
                    Log.d("LoginFragment", "User login");
                    DataService.getInstance().setCalled(true);
                    String id = AuthService.getInstance().getCurrentUser().getUid();
                    globalApplication.setUser(DataService.getInstance().getUser(id));
                    globalApplication.setFoodList();
                    globalApplication.setUserCustomList();
                    Log.d("GlobalApplication", "Successfully loaded data");
                    // action_LoginFragment_to_HomeScreenUserFragment
                    Navigation.findNavController(getView()).navigate(R.id.action_loginFragment_to_homeScreenUserFragment);
                }
            }
            @Override
            public void onFailure(Exception exception) {
                Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        // Override the onBackPressed behavior for this fragment
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Create an AlertDialog to ask the user if they want to exit or logout
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Thoát");
                builder.setMessage("Bạn có muốn thoát khỏi ứng dụng?");
                builder.setPositiveButton("Thoát", (dialog, which) -> {
                    // Exit the app
                    requireActivity().finish();
                });
                builder.setNeutralButton("Hủy", null);
                builder.show();
            }
        });
    }
}
