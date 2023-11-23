package com.example.cs300_dailyapple;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;

import com.example.cs300_dailyapple.R;

public class GetInformation extends Fragment {
    private EditText heightEditText;
    private ImageButton bigLeftHeightButton;
    private ImageButton smallLeftHeightButton;
    private ImageButton bigRightHeightButton;
    private ImageButton smallRightHeightButton;
    private EditText WeightEditText;
    private ImageButton bigLeftWeightButton;
    private ImageButton smallLeftWeightButton;
    private ImageButton bigRightWeightButton;
    private ImageButton smallRightWeightButton;
    private EditText AgeEditText;
    private ImageButton bigLeftAgeButton;
    private ImageButton smallLeftAgeButton;
    private ImageButton bigRightAgeButton;
    private ImageButton smallRightAgeButton;
    private AppCompatImageButton maleButton;
    private AppCompatImageButton femaleButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_get_information, container, false);

        maleButton = view.findViewById(R.id.Male);
        femaleButton = view.findViewById(R.id.Female);

        setButtonSelected(true);

        maleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setButtonSelected(true);
            }
        });

        femaleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setButtonSelected(false);
            }
        });
        heightEditText = view.findViewById(R.id.EditHeight);
        bigLeftHeightButton = view.findViewById(R.id.BigLeftHeight);
        smallLeftHeightButton = view.findViewById(R.id.SmallLeftHeight);
        bigRightHeightButton = view.findViewById(R.id.BigRightHeight);
        smallRightHeightButton = view.findViewById(R.id.SmallRightHeight);

        int defaultHeight = 150;
        heightEditText.setText(String.valueOf(defaultHeight));


        bigLeftHeightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decreaseHeight(10);
            }
        });

        smallLeftHeightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decreaseHeight(1);
            }
        });

        bigRightHeightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increaseHeight(10);
            }
        });

        smallRightHeightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increaseHeight(1);
            }
        });
        WeightEditText = view.findViewById(R.id.EditWeight);
        bigLeftWeightButton = view.findViewById(R.id.BigLeftWeight);
        smallLeftWeightButton = view.findViewById(R.id.SmallLeftWeight);
        bigRightWeightButton = view.findViewById(R.id.BigRightWeight);
        smallRightWeightButton = view.findViewById(R.id.SmallRightWeight);

        int defaultWeight = 50;
        WeightEditText.setText(String.valueOf(defaultWeight));


        bigLeftWeightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decreaseWeight(10);
            }
        });

        smallLeftWeightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decreaseWeight(1);
            }
        });

        bigRightWeightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increaseWeight(10);
            }
        });

        smallRightWeightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increaseWeight(1);
            }
        });
        AgeEditText = view.findViewById(R.id.EditAge);
        bigLeftAgeButton = view.findViewById(R.id.BigLeftAge);
        smallLeftAgeButton = view.findViewById(R.id.SmallLeftAge);
        bigRightAgeButton = view.findViewById(R.id.BigRightAge);
        smallRightAgeButton = view.findViewById(R.id.SmallRightAge);

        int defaultAge = 20;
        AgeEditText.setText(String.valueOf(defaultAge));


        bigLeftAgeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decreaseAge(10);
            }
        });

        smallLeftAgeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decreaseAge(1);
            }
        });

        bigRightAgeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increaseAge(10);
            }
        });

        smallRightAgeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increaseAge(1);
            }
        });



        return view;
    }

    private void setButtonSelected(boolean isMaleSelected) {

        maleButton.setSelected(isMaleSelected);


        femaleButton.setSelected(!isMaleSelected);
    }
    private void decreaseHeight(int decrement) {
        int currentHeight = Integer.parseInt(heightEditText.getText().toString());
        int newHeight = Math.max(0, currentHeight - decrement);
        heightEditText.setText(String.valueOf(newHeight));
    }

    private void increaseHeight(int increment) {
        int currentHeight = Integer.parseInt(heightEditText.getText().toString());
        int newHeight = currentHeight + increment;
        heightEditText.setText(String.valueOf(newHeight));
    }
    private void decreaseWeight(int decrement) {
        int currentWeight = Integer.parseInt(WeightEditText.getText().toString());
        int newWeight = Math.max(0, currentWeight - decrement);
        WeightEditText.setText(String.valueOf(newWeight));
    }

    private void increaseWeight(int increment) {
        int currentWeight = Integer.parseInt(WeightEditText.getText().toString());
        int newWeight = currentWeight + increment;
        WeightEditText.setText(String.valueOf(newWeight));
    }
    private void decreaseAge(int decrement) {
        int currentAge = Integer.parseInt(AgeEditText.getText().toString());
        int newAge = Math.max(0, currentAge - decrement);
        AgeEditText.setText(String.valueOf(newAge));
    }

    private void increaseAge(int increment) {
        int currentAge = Integer.parseInt(AgeEditText.getText().toString());
        int newAge = currentAge + increment;
        AgeEditText.setText(String.valueOf(newAge));
    }

}
