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
import com.example.cs300_dailyapple.R;

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




    private PersonalInformation personalInformation;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Khởi tạo đối tượng PersonalInformation và load dữ liệu nếu có
        personalInformation = new PersonalInformation();
        // Load PersonalInformation ở đây
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
        if (personalInformation.getGender()) {
            // Nếu là nam, sử dụng hình ảnh male
            imageGender.setImageResource(R.drawable.gendermale);
        } else {
            // Nếu là nữ, sử dụng hình ảnh female
            imageGender.setImageResource(R.drawable.genderfemale);
        }

        // Hiển thị độ tuổi
        textViewAge.setText(String.valueOf(personalInformation.getAge()));

        // Hiển thị thông tin nước uống
        int progressWater = 0;// Set progress ở đây
                progressBarWater.setMax(personalInformation.getWater());
        progressBarWater.setProgress(progressWater);
        textProgressWater.setText(progressWater + "/" + personalInformation.getWater() + " ml");

        // Hiển thị thông tin calo
        int progressCalo = 0;// Set progress ở đây
                progressBarCalo.setMax(personalInformation.getCalo());
        progressBarCalo.setProgress(progressCalo);
        textProgressCalo.setText(progressCalo + "/" + personalInformation.getCalo() + " calo");
        Weight.setText(String.valueOf(personalInformation.getWeight()));
        Height.setText(String.valueOf(personalInformation.getHeight()));
        float BMI = personalInformation.getWeight()/(personalInformation.getHeight()*personalInformation.getHeight()/10000);
        textBMI.setText(String.valueOf(BMI));
    }
}
