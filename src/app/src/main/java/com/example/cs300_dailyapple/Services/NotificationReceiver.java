package com.example.cs300_dailyapple.Services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.cs300_dailyapple.Services.NotificationScheduler;

public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");

        // Show the notification when the broadcast is received
        NotificationScheduler.showNotification(context, title, content);
    }
}