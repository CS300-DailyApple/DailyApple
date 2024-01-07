package com.example.cs300_dailyapple.Fragments;

import static java.lang.Integer.min;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.cs300_dailyapple.Models.WaterHistoryItem;
import com.example.cs300_dailyapple.Models.WaterInformation;
import com.example.cs300_dailyapple.R;

import java.util.ArrayList;

public class WaterDrinking extends Fragment {

    ProgressBar water_drinking_bar;
    TextView water_amount, add_button, cool_down;
    LinearLayout change_cup_button;
    RecyclerView drink_history;
    WaterInformation Data;
    int inputCap;

    public void UpdateView() {
        String conCap = "+ " + Data.getContainerCapacity() + " ml";
        add_button.setText(conCap);
        String waterAmount = String.valueOf(Data.getTotalWaterDrank()) + " / " + String.valueOf(Data.getWaterTarget());
        water_amount.setText(waterAmount);
        water_drinking_bar.setProgress(min(Data.getTotalWaterDrank(), Data.getWaterTarget())  * 100 / Data.getWaterTarget());


    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Id binding
        water_amount = view.findViewById(R.id.water_amount_drinking);
        water_drinking_bar = view.findViewById(R.id.water_drinking_progress);
        cool_down = view.findViewById(R.id.cooldown);
        add_button = view.findViewById(R.id.plus_water);
        change_cup_button = view.findViewById(R.id.autorenew);
        drink_history = view.findViewById(R.id.item_water_list); //implement ?

        //data
        Data =  new WaterInformation(2300, 2200, 250, new ArrayList<WaterHistoryItem>());

        //init view value
        UpdateView();
        drink_history.setLayoutManager(new LinearLayoutManager(this.getContext()));
        WaterHistoryAdapter adapter = new WaterHistoryAdapter(Data.getWaterHistory());
        drink_history.setAdapter(adapter);

        //button setting
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Data.addWaterHistoryItem();
                UpdateView();
                drink_history.getAdapter().notifyItemInserted(0);
            }
        });

        change_cup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Title");

// Set up the input
                final EditText input = new EditText(getContext());
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                builder.setView(input);

// Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        inputCap = Integer.parseInt(input.getText().toString());
                        Data.setContainerCapacity(inputCap);
                        UpdateView();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_water_drinking, container, false);
    }
}