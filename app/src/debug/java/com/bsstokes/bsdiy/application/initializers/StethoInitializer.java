package com.bsstokes.bsdiy.application.initializers;

import com.bsstokes.bsdiy.application.BsDiyApplication;
import com.facebook.stetho.Stetho;

public class StethoInitializer {
    public static void initialize(BsDiyApplication bsDiyApplication) {
        Stetho.initializeWithDefaults(bsDiyApplication);
    }
}
