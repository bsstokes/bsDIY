package com.bsstokes.bsdiy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bsstokes.bsdiy.api.DiyApi;
import com.bsstokes.bsdiy.application.BsDiyApplication;
import com.bsstokes.bsdiy.db.BsDiyDatabase;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.internal.Preconditions;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class ChallengeActivity extends AppCompatActivity {

    @NonNull
    public static Intent createIntent(@NonNull Context context, long skillId, long challengeId) {
        final Intent intent = new Intent(context, ChallengeActivity.class);
        intent.putExtra(EXTRA_SKILL_ID, skillId);
        intent.putExtra(EXTRA_CHALLENGE_ID, challengeId);
        return intent;
    }

    private static final String EXTRA_SKILL_ID = "EXTRA_SKILL_ID";
    private static final String EXTRA_CHALLENGE_ID = "EXTRA_CHALLENGE_ID";

    @BindView(R.id.patch_image_view) ImageView patchImageView;
    @BindView(R.id.title_text_view) TextView titleTextView;
    @BindView(R.id.description_text_view) TextView descriptionTextView;

    @Inject BsDiyDatabase database;
    @Inject Picasso picasso;

    private long skillId;
    private long challengeId;
    private final CompositeSubscription subscriptions = new CompositeSubscription();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);
        ButterKnife.bind(this);

        BsDiyApplication.getApplication(this).appComponent().inject(this);
        Preconditions.checkNotNull(database);
        Preconditions.checkNotNull(picasso);

        if (null != getIntent()) {
            skillId = getIntent().getLongExtra(EXTRA_SKILL_ID, skillId);
            challengeId = getIntent().getLongExtra(EXTRA_CHALLENGE_ID, challengeId);
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

        final Subscription getChallenge = database.getChallenge(challengeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<DiyApi.Challenge>() {
                    @Override
                    public void call(DiyApi.Challenge challenge) {
                        onLoadChallenge(challenge);
                    }
                });
        subscriptions.add(getChallenge);
    }

    @Override
    protected void onPause() {
        super.onPause();
        subscriptions.clear();
    }

    private void onLoadSkill(DiyApi.Skill skill) {
        if (null != skill && null != skill.icons && null != skill.icons.medium) {
            picasso.load(DiyApi.Helper.normalizeUrl(skill.icons.medium)).into(patchImageView);
        }
    }

    private void onLoadChallenge(DiyApi.Challenge challenge) {
        setTitle("");
        titleTextView.setText(challenge.title);
        descriptionTextView.setText(challenge.description);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
