package com.bsstokes.bsdiy.application.di;

import com.bsstokes.bsdiy.MainActivity;
import com.bsstokes.bsdiy.SkillsSyncService;
import com.bsstokes.bsdiy.application.BsDiyApplication;

public interface AppComponent {
    // Application
    void inject(BsDiyApplication application);

    // Activities
    void inject(MainActivity activity);

    // Services
    void inject(SkillsSyncService service);
}
