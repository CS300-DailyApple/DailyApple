package com.example.cs300_dailyapple.Services;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthService {
    private static final String TAG = "AuthService";
    private FirebaseAuth mAuth;
    // Singleton
    private static AuthService instance = null;

    private AuthService() {
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }
    public static AuthService getInstance() {
        if (instance == null) {
            instance = new AuthService();
        }
        return instance;
    }
    public void registerUser(String email, String password, final AuthCallback callback) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Sign in success, update UI with the signed-in user's information
                 Log.d(TAG, "createUserWithEmail:success");
                 FirebaseUser user = mAuth.getCurrentUser();
                 callback.onSuccess(user);
                // updateUI(user);
            } else {
                // If sign in fails, display a message to the user.
                 Log.w(TAG, "createUserWithEmail:failure", task.getException());
                 callback.onFailure(task.getException());
                // updateUI(null);
            }
        });
    }
    public void loginUser(String email, String password, final AuthCallback callback) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Sign in success, update UI with the signed-in user's information
                 Log.d(TAG, "signInWithEmail:success");
                 FirebaseUser user = mAuth.getCurrentUser();
                 callback.onSuccess(user);
                // updateUI(user);
            } else {
                // If sign in fails, display a message to the user.
                 Log.w(TAG, "signInWithEmail:failure", task.getException());
                 callback.onFailure(task.getException());
                // updateUI(null);
            }
        });
    }
    public void logoutUser() {
        mAuth.signOut();
    }

    public void resetPassword(String email, final AuthCallback callback) {
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Sign in success, update UI with the signed-in user's information
                 Log.d(TAG, "resetPasswordWithEmail:success");
                 FirebaseUser user = mAuth.getCurrentUser();
                 callback.onSuccess(user);
                // updateUI(user);
            } else {
                // If sign in fails, display a message to the user.
                 Log.w(TAG, "resetPasswordWithEmail:failure", task.getException());
                 callback.onFailure(task.getException());
                // updateUI(null);
            }
        });
    }
    public FirebaseUser getCurrentUser() {
        return mAuth.getCurrentUser();
    }
    public interface AuthCallback {
        void onSuccess(FirebaseUser user);
        void onFailure(Exception e);
    }

}
