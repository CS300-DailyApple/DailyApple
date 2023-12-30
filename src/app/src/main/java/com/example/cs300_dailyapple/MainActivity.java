package com.example.cs300_dailyapple;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.cs300_dailyapple.Fragments.HomeScreenUserFragment;
import com.example.cs300_dailyapple.Fragments.LoginFragment;
import com.example.cs300_dailyapple.Services.AuthService;
import com.example.cs300_dailyapple.Services.DataService;
import com.google.firebase.auth.FirebaseUser;
import com.google.rpc.context.AttributeContext;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = AuthService.getInstance().getCurrentUser();
        if (currentUser != null) {
            // set the main activity to the home page corresponding to the user's role
            DataService db = DataService.getInstance();
            String role = db.getUserRole(currentUser.getUid());
            if (role.equals("admin")) {
                // inflate admin home page
            }
            else if (role.equals("user")) {
                // inflate user home page
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view, new HomeScreenUserFragment()).commit();
            }
            else {
                Log.d("MainActivity", "Unknown role");
            }
        } else {
            // inflate login page
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view, new LoginFragment()).commit();
        }
    }
}