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
import com.example.cs300_dailyapple.Services.AuthService;
import com.example.cs300_dailyapple.Services.DataService;
import com.google.firebase.auth.FirebaseUser;
import com.google.rpc.context.AttributeContext;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    NavController navController;
    
    /**
     * Called when the activity is starting. This is where most initialization should go:
     * calling setContentView(int) to inflate the activity's UI, using findViewById(int) to
     * programmatically interact with widgets in the UI, and initializing any other necessary
     * components.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being
     *                            shut down then this Bundle contains the data it most recently
     *                            supplied in onSaveInstanceState(Bundle). Note: Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();
        AuthService.getInstance().logoutUser();
        FirebaseUser currentUser = AuthService.getInstance().getCurrentUser();
        if (currentUser != null) {
            // set the main activity to the home page corresponding to the user's role
            DataService db = DataService.getInstance();
            String role = db.getUserRole(currentUser.getUid());
            if (role.equals("admin")) {
                // change to admin home page using action in nav graph
                navController.navigate(R.id.action_loginFragment_to_homeAdminFragment);
                navController.popBackStack(R.id.loginFragment, false);
            }
            else if (role.equals("user")) {
                // inflate user home page
                navController.navigate(R.id.action_loginFragment_to_homeScreenUserFragment);
                navController.popBackStack(R.id.loginFragment, false);
            }
            else {
                Log.d("MainActivity", "Unknown role");
            }
        }
    }
}