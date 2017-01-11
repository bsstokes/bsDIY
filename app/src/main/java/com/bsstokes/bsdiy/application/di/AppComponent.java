package com.bsstokes.bsdiy.application.di;

import com.bsstokes.bsdiy.MainActivity;
import com.bsstokes.bsdiy.SkillsSyncService;
import com.bsstokes.bsdiy.application.BsDiyApplication;
import com.bsstokes.bsdiy.messages.MessagesFragment;
import com.bsstokes.bsdiy.skills.SkillsFragment;

public interface AppComponent {
    // Application
    void inject(BsDiyApplication application);

    // Activities
    void inject(MainActivity activity);

    // Fragments
    void inject(MessagesFragment fragment);

    void inject(SkillsFragment fragment);

    // Services
    void inject(SkillsSyncService service);
}
