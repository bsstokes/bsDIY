package com.bsstokes.bsdiy.application.initializers;

import com.bsstokes.bsdiy.BsDiyApplication;
import com.facebook.stetho.Stetho;

public class StethoInitializer {
    public static void initialize(BsDiyApplication bsDiyApplication) {
        Stetho.initializeWithDefaults(bsDiyApplication);
    }
}
