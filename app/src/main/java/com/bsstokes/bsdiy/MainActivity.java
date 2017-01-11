package com.bsstokes.bsdiy;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.bsstokes.bsdiy.api.DiyApi;
import com.bsstokes.bsdiy.application.BsDiyApplication;
import com.bsstokes.bsdiy.db.BsDiyDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.internal.Preconditions;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.drawer_layout) DrawerLayout drawer;
    @BindView(R.id.nav_view) NavigationView navigationView;

    @BindView(R.id.skills_list) RecyclerView skillsListRecyclerView;

    @Inject BsDiyDatabase database;
    @Inject DiyApi diyApi;
    @Inject Picasso picasso;

    private final CompositeSubscription subscriptions = new CompositeSubscription();
    private SkillsAdapter skillsAdapter;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        BsDiyApplication.getApplication(this).appComponent().inject(this);
        Preconditions.checkNotNull(database);
        Preconditions.checkNotNull(diyApi);
        Preconditions.checkNotNull(picasso);

        setSupportActionBar(toolbar);

        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        );
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        skillsAdapter = new SkillsAdapter(this, picasso);
        skillsListRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        skillsListRecyclerView.setAdapter(skillsAdapter);
        skillsListRecyclerView.addItemDecoration(new GridDividerDecoration(this));
    }

    @Override
    protected void onResume() {
        super.onResume();

        final Observable<List<DiyApi.Skill>> getAllSkills = database.getAllSkills()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        final Subscription subscription = getAllSkills
                .subscribe(new Action1<List<DiyApi.Skill>>() {
                    @Override
                    public void call(List<DiyApi.Skill> skills) {
                        onLoadSkills(skills);
                    }
                });

        subscriptions.add(subscription);
    }

    private void onLoadSkills(List<DiyApi.Skill> skills) {
        skillsAdapter.loadSkills(skills);
    }

    @Override
    protected void onPause() {
        super.onPause();
        subscriptions.clear();
    }

    @OnClick(R.id.fab)
    void onClickFab(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        final int itemId = item.getItemId();

        if (R.id.nav_messages == itemId) {
            showSnackbar(item.getTitle());
        } else if (R.id.nav_to_dos == itemId) {
            showSnackbar(item.getTitle());
        } else if (R.id.nav_skills == itemId) {
            downloadSkills();
        } else if (R.id.nav_stream == itemId) {
            showSnackbar(item.getTitle());
        } else if (R.id.nav_explore == itemId) {
            showSnackbar(item.getTitle());
        } else {
            Log.e(TAG, "Unknown navigation item selected");
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showSnackbar(@NonNull CharSequence text) {
        Snackbar.make(skillsListRecyclerView, text, Snackbar.LENGTH_LONG).show();
    }

    private void downloadSkills() {
        SkillsSyncService.startActionSyncSkills(this);
    }
}
