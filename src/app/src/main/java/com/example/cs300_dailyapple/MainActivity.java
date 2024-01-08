package com.example.cs300_dailyapple;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;
import android.util.Log;

import com.example.cs300_dailyapple.Fragments.HomeAdminFragment;
import com.example.cs300_dailyapple.Fragments.HomeScreenUserFragment;
import com.example.cs300_dailyapple.Fragments.LoginFragment;
import com.example.cs300_dailyapple.Models.Food;
import com.example.cs300_dailyapple.Models.GlobalApplication;
import com.example.cs300_dailyapple.Services.AuthService;
import com.example.cs300_dailyapple.Services.DataService;
import com.google.firebase.auth.FirebaseUser;
import com.google.rpc.context.AttributeContext;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    NavController navController;
    GlobalApplication globalApplication;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();
        FirebaseUser currentUser = AuthService.getInstance().getCurrentUser();
        if (currentUser != null) {
            // set the main activity to the home page corresponding to the user's role
            DataService db = DataService.getInstance();
            String role = db.getUserRole(currentUser.getUid());
            if (role.equals("admin")) {
                // change to admin home page using action in nav graph
                globalApplication = (GlobalApplication) this.getApplication();
                globalApplication.queryForAdminLists();
                DataService.getInstance().setCalled(true);
                navController.navigate(R.id.action_loginFragment_to_homeAdminFragment);
            }
            else if (role.equals("user")) {
                // inflate user home page
                DataService.getInstance().setCalled(true);
                navController.navigate(R.id.action_loginFragment_to_homeScreenUserFragment);
            }
            else {
                Log.d("MainActivity", "Unknown role");
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        DataService dataService = DataService.getInstance();
        GlobalApplication globalApplication = (GlobalApplication) this.getApplication();
        if (dataService.isCalled()){
            dataService.saveUser(globalApplication.getUser());
        }
    }
}