package com.example.cs300_dailyapple.Fragments;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.cs300_dailyapple.MainActivity;
import com.example.cs300_dailyapple.Models.GlobalApplication;
import com.example.cs300_dailyapple.R;
import com.example.cs300_dailyapple.Services.AuthService;
import com.example.cs300_dailyapple.Services.DataService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class HomeScreenUserFragment extends Fragment {
    private TextView monthYear;
    private TextView dateTextView1;
    private TextView dayOfWeekTextView1;
    private TextView dateTextView2;
    private TextView dayOfWeekTextView2;
    private TextView dateTextView3;
    private TextView dayOfWeekTextView3;
    private TextView dateTextView4;
    private TextView dayOfWeekTextView4;
    private TextView dateTextView5;
    private TextView dayOfWeekTextView5;
    private ImageButton nutritionDiaryButton;
    private ImageButton bodyInfoButton;
    private ImageButton foodListButton;
    Calendar calendar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home_screen_user, container, false);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        monthYear= view.findViewById(R.id.monthYearTextView);
        String monthyearstring= "Tháng "+ month+", "+year;
        monthYear.setText(monthyearstring);

        calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -2);
        Date day1 = calendar.getTime();
        calendar.setTime(day1);
        int dayOfMonth1 = calendar.get(Calendar.DAY_OF_MONTH);
        dateTextView1=view.findViewById(R.id.dateTextView1);
        dateTextView1.setText(String.valueOf(dayOfMonth1));

        int dayOfWeek1 = calendar.get(Calendar.DAY_OF_WEEK);
        String dayOfWeekString1 = getDayOfWeekString(dayOfWeek1);
        dayOfWeekTextView1= view.findViewById(R.id.dayOfWeekTextView1);
        dayOfWeekTextView1.setText(dayOfWeekString1);

        calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date day2 = calendar.getTime();
        calendar.setTime(day2);
        int dayOfMonth2 = calendar.get(Calendar.DAY_OF_MONTH);
        dateTextView2=view.findViewById(R.id.dateTextView2);
        dateTextView2.setText(String.valueOf(dayOfMonth2));
        int dayOfWeek2 = calendar.get(Calendar.DAY_OF_WEEK);
        String dayOfWeekString2 = getDayOfWeekString(dayOfWeek2);
        dayOfWeekTextView2= view.findViewById(R.id.dayOfWeekTextView2);
        dayOfWeekTextView2.setText(dayOfWeekString2);

        calendar = Calendar.getInstance();
        Date day3 = calendar.getTime();
        calendar.setTime(day3);
        int dayOfMonth3 = calendar.get(Calendar.DAY_OF_MONTH);
        dateTextView3=view.findViewById(R.id.dateTextView3);
        dateTextView3.setText(String.valueOf(dayOfMonth3));
        int dayOfWeek3 = calendar.get(Calendar.DAY_OF_WEEK);
        String dayOfWeekString3 = getDayOfWeekString(dayOfWeek3);
        dayOfWeekTextView3= view.findViewById(R.id.dayOfWeekTextView3);
        dayOfWeekTextView3.setText(dayOfWeekString3);

        calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date day4 = calendar.getTime();
        calendar.setTime(day4);
        int dayOfMonth4 = calendar.get(Calendar.DAY_OF_MONTH);
        dateTextView4=view.findViewById(R.id.dateTextView4);
        dateTextView4.setText(String.valueOf(dayOfMonth4));
        int dayOfWeek4 = calendar.get(Calendar.DAY_OF_WEEK);
        String dayOfWeekString4 = getDayOfWeekString(dayOfWeek4);
        dayOfWeekTextView4= view.findViewById(R.id.dayOfWeekTextView4);
        dayOfWeekTextView4.setText(dayOfWeekString4);

        calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 2);
        Date day5 = calendar.getTime();
        calendar.setTime(day5);
        int dayOfMonth5 = calendar.get(Calendar.DAY_OF_MONTH);
        dateTextView5=view.findViewById(R.id.dateTextView5);
        dateTextView5.setText(String.valueOf(dayOfMonth5));
        int dayOfWeek5 = calendar.get(Calendar.DAY_OF_WEEK);
        String dayOfWeekString5 = getDayOfWeekString(dayOfWeek5);
        dayOfWeekTextView5= view.findViewById(R.id.dayOfWeekTextView5);
        dayOfWeekTextView5.setText(dayOfWeekString5);

        nutritionDiaryButton = view.findViewById(R.id.NutritionDiaryButton);
        nutritionDiaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_homeScreenUserFragment_to_calorieRecord);
            }
        });
        bodyInfoButton = view.findViewById(R.id.BodyInformationButton);
        bodyInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_homeScreenUserFragment_to_bodyInformationFragment);
            }
        });
        foodListButton = view.findViewById(R.id.FoodButton);
        foodListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_homeScreenUserFragment_to_foodFragment);
            }
        });

        // check if user is banned
        DataService db = DataService.getInstance();
        if (db.getUser(AuthService.getInstance().getCurrentUser().getUid()).isBanned()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Tài khoản của bạn đã bị cấm");
            builder.setMessage("Vui lòng liên hệ với quản trị viên để biết thêm chi tiết");
            builder.setPositiveButton("Đóng", null);
            AuthService.getInstance().logoutUser();
            Navigation.findNavController(view).navigate(R.id.action_homeScreenUserFragment_to_loginFragment);
            builder.show();
        }

    }

    private static String getDayOfWeekString(int dayOfWeek) {
        switch (dayOfWeek) {
            case Calendar.SUNDAY:
                return "Su";
            case Calendar.MONDAY:
                return "Mo";
            case Calendar.TUESDAY:
                return "Tu";
            case Calendar.WEDNESDAY:
                return "We";
            case Calendar.THURSDAY:
                return "Th";
            case Calendar.FRIDAY:
                return "Fr";
            case Calendar.SATURDAY:
                return "Sa";
            default:
                return "";
        }
    }
    @Override
    public void onResume() {
        super.onResume();

        // Override the onBackPressed behavior for this fragment
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Create an AlertDialog to ask the user if they want to exit or logout
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Thoát");
                builder.setMessage("Bạn có muốn thoát khỏi ứng dụng?");
                builder.setPositiveButton("Thoát", (dialog, which) -> {
                    // Exit the app
                    DataService dataService = DataService.getInstance();
                    GlobalApplication globalApplication = (GlobalApplication) GlobalApplication.getInstance();
                    if (dataService.isCalled()){
                        dataService.saveUser(globalApplication.getUser());
                        dataService.addSuggestedFood(globalApplication.getUserSuggestedFoodList());
                        dataService.setCustomFood(globalApplication.getUserCustomList());
                        dataService.setCalled(false);
                    }
                    requireActivity().finish();
                });
                builder.setNegativeButton("Đăng xuất", (dialog, which) -> {
                    // Logout the user
                    DataService dataService = DataService.getInstance();
                    GlobalApplication globalApplication = (GlobalApplication) GlobalApplication.getInstance();
                    if (dataService.isCalled()){
                        dataService.saveUser(globalApplication.getUser());
                        dataService.addSuggestedFood(globalApplication.getUserSuggestedFoodList());
                        dataService.setCustomFood(globalApplication.getUserCustomList());
                        dataService.setCalled(false);
                    }

                    AuthService.getInstance().logoutUser();
                    // Navigate to login page
                    Navigation.findNavController(getView()).navigate(R.id.action_homeScreenUserFragment_to_loginFragment);
                });
                builder.setNeutralButton("Hủy", null);
                builder.show();
            }
        });
    }
}