package com.bsstokes.bsdiy.application.di;

import com.bsstokes.bsdiy.app.challenge.ChallengeActivity;
import com.bsstokes.bsdiy.MainActivity;
import com.bsstokes.bsdiy.app.skill.SkillActivity;
import com.bsstokes.bsdiy.application.BsDiyApplication;
import com.bsstokes.bsdiy.app.explore.ExploreFragment;
import com.bsstokes.bsdiy.app.messages.MessagesFragment;
import com.bsstokes.bsdiy.app.skills.SkillsFragment;
import com.bsstokes.bsdiy.app.stream.StreamFragment;
import com.bsstokes.bsdiy.sync.ApiSyncService;
import com.bsstokes.bsdiy.app.to_dos.ToDosFragment;

public interface AppComponent {
    // Application
    void inject(BsDiyApplication application);

    // Activities
    void inject(ChallengeActivity activity);

    void inject(MainActivity activity);

    void inject(SkillActivity activity);

    // Fragments
    void inject(ExploreFragment fragment);

    void inject(MessagesFragment fragment);

    void inject(SkillsFragment fragment);

    void inject(StreamFragment fragment);

    void inject(ToDosFragment fragment);

    // Services
    void inject(ApiSyncService service);
}
