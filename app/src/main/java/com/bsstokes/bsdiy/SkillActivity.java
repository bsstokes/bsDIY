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
import com.bsstokes.bsdiy.sync.ApiSyncService;
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

    private static final String TAG = "SkillActivity";

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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();

        final Subscription getSkill = database.getSkill(skillId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<DiyApi.Skill>() {
                    @Override
                    public void call(DiyApi.Skill skill) {
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

    private void onLoadSkill(DiyApi.Skill skill) {
        if (null != skill && null != skill.url) {
            // TODO: Not the best place to put this because it might be firing
            // too often.
            ApiSyncService.syncChallenges(this, skill.url);
        }

        setTitle(skill.title);

        final String imageUrl = DiyApi.Helper.normalizeUrl(skill.images.large);
        picasso.load(imageUrl).into(patchImageView);

        descriptionTextView.setText(skill.description);
    }

    private void onLoadChallenges(List<DiyApi.Challenge> challenges) {
        challengesViewGroup.removeAllViews();
        for (final DiyApi.Challenge challenge : challenges) {
            onLoadChallenge(challenge);
        }
    }

    private void onLoadChallenge(DiyApi.Challenge challenge) {
        final View view = getLayoutInflater().inflate(R.layout.challenge_list_item_layout, challengesViewGroup, false);
        final TextView titleTextView = ButterKnife.findById(view, R.id.title_text_view);
        titleTextView.setText(challenge.title);
        challengesViewGroup.addView(view);
    }
}
