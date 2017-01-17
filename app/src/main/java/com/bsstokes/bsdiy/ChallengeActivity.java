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
import com.bsstokes.bsdiy.db.Challenge;
import com.bsstokes.bsdiy.db.Skill;
import com.bsstokes.bsdiy.ui.ActionBarHelper;
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

        final Subscription getChallenge = database.getChallenge(challengeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Challenge>() {
                    @Override
                    public void call(Challenge challenge) {
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

    private void onLoadSkill(Skill skill) {
        if (null != skill && null != skill.getIconMedium()) {
            picasso.load(DiyApi.Helper.normalizeUrl(skill.getIconMedium())).into(patchImageView);
        }
    }

    private void onLoadChallenge(Challenge challenge) {
        setTitle("");
        titleTextView.setText(challenge.getTitle());
        descriptionTextView.setText(challenge.getDescription());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
