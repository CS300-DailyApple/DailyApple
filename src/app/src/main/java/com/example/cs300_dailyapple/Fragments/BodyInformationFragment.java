package com.example.cs300_dailyapple.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.cs300_dailyapple.Models.PersonalInformation;
import com.example.cs300_dailyapple.Models.User;
import com.example.cs300_dailyapple.R;
import com.example.cs300_dailyapple.Services.AuthService;
import com.example.cs300_dailyapple.Services.DataService;

public class BodyInformationFragment extends Fragment {

    private ImageView imageGender;
    private TextView textViewAge;
    private TextView textProgressWater;
    private ProgressBar progressBarWater;
    private TextView textProgressCalo;
    private ProgressBar progressBarCalo;
    private TextView textBMI;
    private TextView Weight;
    private TextView Height;
    private User user;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = DataService.getInstance().getUser(AuthService.getInstance().getCurrentUser().getUid());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_body_information, container, false);
        textBMI = view.findViewById(R.id.textBMI);
        Weight = view.findViewById(R.id.Weight);
        Height = view.findViewById(R.id.Height);
        imageGender = view.findViewById(R.id.imageGender);
        textViewAge = view.findViewById(R.id.textViewAge);
        textProgressWater = view.findViewById(R.id.textProgressWater);
        progressBarWater = view.findViewById(R.id.progressBarWater);
        textProgressCalo = view.findViewById(R.id.textProgressCalo);
        progressBarCalo = view.findViewById(R.id.progressBarCalo);

        // Hiển thị thông tin từ PersonalInformation
        displayPersonalInformation();

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
        int progressWater = 0;// Set progress ở đây
        // Get water target and round it to integer
        PersonalInformation personalInformation = user.getPersonalInformation();
        int waterTarget = (int) Math.round(user.getWaterOverall().getWaterTarget());
        progressBarWater.setMax(waterTarget);
        // Get water absorbed and round it to integer
        int waterAbsorbed = (int) Math.round(user.getWaterOverall().getWaterAbsorbed());
        progressBarWater.setProgress(waterAbsorbed);
        textProgressWater.setText(waterAbsorbed + "/" + waterTarget + " ml");

        // Hiển thị thông tin calo
        int progressCalo = 0;// Set progress ở đây
        // Get calo target and round it to integer
        int caloTarget = (int) Math.round(user.getNutritionOverall().getNutritionTarget().getCalories());
        progressBarCalo.setMax(caloTarget);
        // Get calo absorbed and round it to integer
        int caloAbsorbed = (int) Math.round(user.getNutritionOverall().getNutritionAbsorbed().getCalories());
        progressBarCalo.setProgress(caloAbsorbed);
        textProgressCalo.setText(caloAbsorbed + "/" + caloTarget + " calo");
        Weight.setText(String.valueOf(personalInformation.getWeight()));
        Height.setText(String.valueOf(personalInformation.getHeight()));
        try {
            Double BMI = personalInformation.getWeight() / (personalInformation.getHeight() * personalInformation.getHeight());
        }
        catch (ArithmeticException e){
            float BMI = 0;
            e.printStackTrace();
        }
    }
}
