package com.bsstokes.bsdiy.ui;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

public class ActionBarHelper {

    public static void setDisplayShowHomeEnabled(@NonNull AppCompatActivity activity) {
        if (null != activity.getSupportActionBar()) {
            activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }
}
