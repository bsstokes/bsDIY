package com.bsstokes.bsdiy.application.di;

import com.bsstokes.bsdiy.BsDiyApplication;
import com.bsstokes.bsdiy.MainActivity;

public interface AppComponent {
    // Application
    void inject(BsDiyApplication application);

    // Activities
    void inject(MainActivity activity);
}