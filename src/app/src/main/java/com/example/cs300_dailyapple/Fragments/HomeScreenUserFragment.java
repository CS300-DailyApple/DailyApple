package com.example.cs300_dailyapple.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cs300_dailyapple.R;

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
    Calendar calendar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home_screen_user, container, false);

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


        return view;
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
}