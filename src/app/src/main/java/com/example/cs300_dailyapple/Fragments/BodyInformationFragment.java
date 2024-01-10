package com.example.cs300_dailyapple.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.cs300_dailyapple.Models.GlobalApplication;
import com.example.cs300_dailyapple.Models.PersonalInformation;
import com.example.cs300_dailyapple.Models.User;
import com.example.cs300_dailyapple.R;
import com.example.cs300_dailyapple.Services.AuthService;
import com.example.cs300_dailyapple.Services.DataService;

public class BodyInformationFragment extends Fragment {
    GlobalApplication globalApplication;
    private ImageView imageGender;
    private TextView textViewAge;
    private TextView textProgressWater;
    private ProgressBar progressBarWater;
    private TextView textProgressCalo;
    private ProgressBar progressBarCalo;
    private TextView textBMI;
    private TextView Weight;
    private TextView Height;
    private TextView weightEvaluation;
    private AppCompatImageButton settingButton;
    private User user;
    TextView textDateTime;

    NavController navController;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        globalApplication = (GlobalApplication) getActivity().getApplicationContext();
        user = globalApplication.getUser();
    }

    @Override
    public void onResume() {
        super.onResume();
        displayPersonalInformation();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textBMI = view.findViewById(R.id.textBMI);
        Weight = view.findViewById(R.id.Weight);
        Height = view.findViewById(R.id.Height);
        imageGender = view.findViewById(R.id.imageGender);
        textViewAge = view.findViewById(R.id.textViewAge);
        textProgressWater = view.findViewById(R.id.textProgressWater);
        progressBarWater = view.findViewById(R.id.progressBarWater);
        textProgressCalo = view.findViewById(R.id.textProgressCalo);
        progressBarCalo = view.findViewById(R.id.progressBarCalo);
        weightEvaluation = view.findViewById(R.id.weightEvaluation);

        textDateTime = view.findViewById(R.id.textDateTime);
        
        settingButton = view.findViewById(R.id.Settings);
        navController = Navigation.findNavController(view);
        // Hiển thị thông tin từ PersonalInformation
        displayPersonalInformation();

        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_bodyInformationFragment_to_settingBodyInformationFragment);
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_body_information, container, false);
        return view;
    }

    private void displayPersonalInformation() {
        // Hiển thị giới tính
        if (user.getPersonalInformation().getGender() == "male") {
            // Nếu là nam, sử dụng hình ảnh male
            imageGender.setImageResource(R.drawable.gendermale);
        } else {
            // Nếu là nữ, sử dụng hình ảnh female
            imageGender.setImageResource(R.drawable.genderfemale);
        }

        // Hiển thị độ tuổi
        textViewAge.setText(String.valueOf(user.getPersonalInformation().getAge()));

        // Hiển thị thông tin nước uống
        // Get water target and round it to integer
        PersonalInformation personalInformation = user.getPersonalInformation();
        int waterTarget = (int) Math.round(user.getWaterInformation().getWaterTarget());
        Log.d("waterTarget", String.valueOf(waterTarget));
        progressBarWater.setMax(waterTarget);
        // Get water absorbed and round it to integer
        int waterAbsorbed = (int) Math.round(user.getWaterInformation().getTotalWaterDrank());
        Log.d("waterAbsorbed", String.valueOf(waterAbsorbed));
        progressBarWater.setProgress(waterAbsorbed);
        textProgressWater.setText(waterAbsorbed + "/" + waterTarget + " ml");

        // Hiển thị thông tin calo
        // Get calo target and round it to integer
        int caloTarget = (int) Math.round(user.getNutritionOverall().getNutritionTarget().getKcal());
        progressBarCalo.setMax(caloTarget);
        // Get calo absorbed and round it to integer
        int caloAbsorbed = (int) Math.round(user.getNutritionOverall().getNutritionAbsorbed().getKcal());
        progressBarCalo.setProgress(caloAbsorbed);
        textProgressCalo.setText(caloAbsorbed + "/" + caloTarget + " calo");
        Weight.setText(String.valueOf(personalInformation.retrieveWeight()));
        Height.setText(String.valueOf(personalInformation.retrieveHeight()));
        textBMI.setText(String.valueOf(Math.round(personalInformation.calculateBMI())));
        weightEvaluation.setText(evaluateBMI(personalInformation.calculateBMI()));
        textDateTime.setText(personalInformation.retrieveTime());
        try {
            Double BMI = personalInformation.retrieveWeight() / (personalInformation.retrieveHeight() * personalInformation.retrieveHeight());
        }
        catch (ArithmeticException e){
            float BMI = 0;
            e.printStackTrace();
        }
    }
    private String evaluateBMI(Double BMI){
        if (BMI < 18.5){
            return "Thiếu cân";
        }
        else if (BMI >= 18.5 && BMI < 24.9){
            return "Bình thường";
        }
        else if (BMI >= 24.9 && BMI < 29.9){
            return "Thừa cân";
        }
        else {
            return "Béo phì";
        }
    }
}
