package com.example.cs300_dailyapple.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cs300_dailyapple.Models.GlobalApplication;
import com.example.cs300_dailyapple.Models.Nutrition;
import com.example.cs300_dailyapple.Models.NutritionOverall;
import com.example.cs300_dailyapple.Models.PersonalInformation;
import com.example.cs300_dailyapple.Models.User;
import com.example.cs300_dailyapple.Models.WaterInformation;
import com.example.cs300_dailyapple.R;
import com.example.cs300_dailyapple.Services.AuthService;
import com.example.cs300_dailyapple.Services.DataService;
import com.google.firebase.auth.FirebaseUser;
import com.google.rpc.context.AttributeContext;

import java.time.format.DateTimeFormatter;

public class SummaryStatistic extends Fragment {
    NavController navController;
    TextView calorie;
    private TextView textBMI;
    private TextView textDateTime;
    private TextView height;
    private TextView weight;
    private TextView weightEvaluation;
    private TextView waterText;
    private AppCompatButton continueButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_summary_statistic, container, false);
        return view;
    }
    // OnViewCreated
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // get nav controller
        navController = Navigation.findNavController(view);
        calorie = view.findViewById(R.id.caloriesCount);
        textBMI = view.findViewById(R.id.textBMI);
        textDateTime = view.findViewById(R.id.textDateTime);
        height = view.findViewById(R.id.Height);
        weight = view.findViewById(R.id.Weight);
        weightEvaluation = view.findViewById(R.id.weightEvaluation);
        waterText = view.findViewById(R.id.Water);
        continueButton = view.findViewById(R.id.Continue);
        // get today's date
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        textDateTime.setText(dtf.format(java.time.LocalDate.now()));
        // get height and weight
        Bundle bundle = getArguments();
        Double heightVal = bundle.getDouble("height");
        Double weightVal = bundle.getDouble("weight");
        int age = bundle.getInt("age");
        String gender = bundle.getString("gender");
        PersonalInformation PI = new PersonalInformation();
        PI.setAge(age);
        PI.setGender(gender);
        PI.addNewBodyInformation(weightVal, heightVal);
        calorie.setText(String.valueOf(Math.round(PI.calculateTDEE())));
        height.setText(Math.round(heightVal) + "cm");
        weight.setText(Math.round(weightVal) + "kg");
        // get BMI, round to integer
        textBMI.setText(String.valueOf(Math.round(PI.calculateBMI())));
        // get weight evaluation
        weightEvaluation.setText(getWeightEvaluation(PI.calculateBMI()));
        // get water
        waterText.setText(String.valueOf(Math.round(PI.calculateWater())));
        // continue button
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // register user
                AuthService.getInstance().registerUser(bundle.getString("email"), bundle.getString("password"), new AuthService.AuthCallback() {
                    @Override
                    public void onSuccess(FirebaseUser user) {
                        // create user object
                        User newUser = new User();
                        newUser.setPersonalInformation(PI);
                        newUser.setUsername(bundle.getString("username"));
                        newUser.setEmail(bundle.getString("email"));
                        WaterInformation waterInformation = new WaterInformation();
                        waterInformation.setWaterTarget(Integer.parseInt(waterText.getText().toString()));
                        newUser.setWaterInformation(waterInformation);
                        NutritionOverall nutritionOverall = new NutritionOverall();
                        Nutrition nutrition = new Nutrition();
                        nutrition.setKcal(PI.calculateTDEE());
                        nutrition.setProtein(PI.calculateProtein());
                        nutrition.setFat(PI.calculateFat());
                        nutrition.setCarbs(PI.calculateCarbs());
                        nutritionOverall.setNutritionTarget(nutrition);
                        newUser.setNutritionOverall(nutritionOverall);
                        // save user to database
                        DataService.getInstance().addUser(user.getUid(), bundle.getString("email"), bundle.getString("username"), PI, waterInformation);
                        // save user to global application
                        GlobalApplication globalApplication = (GlobalApplication) getActivity().getApplication();
                        globalApplication.setUser(newUser);
                        globalApplication.setFoodList();
                        globalApplication.setUserCustomList();
                        DataService.getInstance().setCalled(true);
                        // log in user
                        AuthService.getInstance().loginUser(bundle.getString("email"), bundle.getString("password"), new AuthService.AuthCallback() {
                            @Override
                            public void onSuccess(FirebaseUser user) {
                                // do nothing
                            }
                            @Override
                            public void onFailure(Exception e) {
                                // do nothing
                            }
                        });
                        // navigate to home screen
                        navController.navigate(R.id.action_summaryStatistic_to_homeScreenUserFragment);
                    }
                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(getContext(), "Tạo người dùng thất bại.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    private String getWeightEvaluation(Double BMI){
        if(BMI<18.5){
            return "Thiếu cân";
        }
        else if(BMI<24.9){
            return "Bình thường";
        }
        else if(BMI<29.9){
            return "Thừa cân";
        }
        else{
            return "Béo phì";
        }
    }
}