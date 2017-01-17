package com.bsstokes.bsdiy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bsstokes.bsdiy.api.DiyApi;
import com.bsstokes.bsdiy.application.BsDiyApplication;
import com.bsstokes.bsdiy.db.BsDiyDatabase;
import com.bsstokes.bsdiy.db.Skill;
import com.bsstokes.bsdiy.sync.ApiSyncService;
import com.bsstokes.bsdiy.ui.ActionBarHelper;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.internal.Preconditions;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class SkillActivity extends AppCompatActivity {

    @NonNull
    public static Intent createIntent(@NonNull Context context, long skillId) {
        final Intent intent = new Intent(context, SkillActivity.class);
        intent.putExtra(EXTRA_SKILL_ID, skillId);
        return intent;
    }

    private static final String EXTRA_SKILL_ID = "EXTRA_SKILL_ID";

    @BindView(R.id.patch_image_view) ImageView patchImageView;
    @BindView(R.id.description_text_view) TextView descriptionTextView;
    @BindView(R.id.challenges_layout) ViewGroup challengesViewGroup;

    @Inject BsDiyDatabase database;
    @Inject Picasso picasso;

    private long skillId = 0;
    private final CompositeSubscription subscriptions = new CompositeSubscription();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skill);
        ButterKnife.bind(this);

        BsDiyApplication.getApplication(this).appComponent().inject(this);
        Preconditions.checkNotNull(database);
        Preconditions.checkNotNull(picasso);

        if (null != getIntent()) {
            skillId = getIntent().getLongExtra(EXTRA_SKILL_ID, skillId);
        }

        ActionBarHelper.setDisplayShowHomeEnabled(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        final Subscription getSkill = database.getSkill(skillId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Skill>() {
                    @Override
                    public void call(Skill skill) {
                        onLoadSkill(skill);
                    }
                });
        subscriptions.add(getSkill);

        final Subscription getChallenges = database.getChallenges(skillId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<DiyApi.Challenge>>() {
                    @Override
                    public void call(List<DiyApi.Challenge> challenges) {
                        onLoadChallenges(challenges);
                    }
                });
        subscriptions.add(getChallenges);
    }

    @Override
    protected void onPause() {
        super.onPause();
        subscriptions.clear();
    }

    private void onLoadSkill(Skill skill) {
        if (null == skill) {
            // TODO: Handle missing skill better
            return;
        }

        if (null != skill.getUrl()) {
            // TODO: Not the best place to put this because it might be firing
            // too often.
            ApiSyncService.syncChallenges(this, skill.getUrl());
        }

        setTitle(skill.getTitle());

        if (null != skill.getImageLarge()) {
            final String imageUrl = DiyApi.Helper.normalizeUrl(skill.getImageLarge());
            picasso.load(imageUrl).into(patchImageView);
        }

        descriptionTextView.setText(skill.getDescription());
    }

    private void onLoadChallenges(List<DiyApi.Challenge> challenges) {
        challengesViewGroup.removeAllViews();
        for (final DiyApi.Challenge challenge : challenges) {
            onLoadChallenge(challenge);
        }
    }

    private void onLoadChallenge(final DiyApi.Challenge challenge) {
        if (null == challenge) {
            return;
        }

        final View view = getLayoutInflater().inflate(R.layout.challenge_list_item_layout, challengesViewGroup, false);

        final TextView titleTextView = ButterKnife.findById(view, R.id.title_text_view);
        titleTextView.setText(challenge.title);

        final ImageView heroImageView = ButterKnife.findById(view, R.id.hero_image_view);
        if (null != challenge.image && null != challenge.image.ios_600 && null != challenge.image.ios_600.url) {
            picasso.load(challenge.image.ios_600.url).into(heroImageView);
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickChallenge(challenge.id);
            }
        });

        challengesViewGroup.addView(view);
    }

    private void onClickChallenge(long challengeId) {
        startActivity(ChallengeActivity.createIntent(this, skillId, challengeId));
    }
}
