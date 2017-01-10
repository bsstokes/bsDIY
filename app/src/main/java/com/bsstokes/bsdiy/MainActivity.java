package com.bsstokes.bsdiy;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bsstokes.bsdiy.api.DiyApi;
import com.bsstokes.bsdiy.application.BsDiyApplication;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.internal.Preconditions;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.drawer_layout) DrawerLayout drawer;
    @BindView(R.id.nav_view) NavigationView navigationView;

    @BindView(R.id.diy_info_text_view) TextView diyInfoTextView;
    @BindView(R.id.skills_text_view) TextView skillsTextView;

    @Inject DiyApi diyApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        BsDiyApplication.getApplication(this).appComponent().inject(this);
        Preconditions.checkNotNull(diyApi);

        setSupportActionBar(toolbar);

        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        );
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
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

        if (itemId == R.id.nav_diy_info) {
            downloadSomething();
        } else if (itemId == R.id.nav_skills) {
            downloadSkills();
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void downloadSomething() {
        diyApi.getApiInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<DiyApi.DiyResponse<DiyApi.DiyInfo>>>() {
                    private static final String TAG = "DiyApi getApiInfo";

                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: d=" + d);
                    }

                    @Override
                    public void onNext(Response<DiyApi.DiyResponse<DiyApi.DiyInfo>> response) {
                        displayResponse(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError", e);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });
    }

    private void displayResponse(Response<DiyApi.DiyResponse<DiyApi.DiyInfo>> response) {
        if (null != response) {
            if (null != response.body()) {
                displayResponse(response.body());
            }
        }
    }

    private void displayResponse(DiyApi.DiyResponse<DiyApi.DiyInfo> diyResponse) {
        if (null != diyResponse && null != diyResponse.response) {
            diyInfoTextView.setText(diyResponse.response.toString());
        }
    }

    private void downloadSkills() {
        diyApi.getSkills()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<DiyApi.DiyResponse<List<DiyApi.Skill>>>>() {
                    private static final String TAG = "DiyApi getSkills";

                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: d=" + d);
                    }

                    @Override
                    public void onNext(Response<DiyApi.DiyResponse<List<DiyApi.Skill>>> response) {
                        onDownloadSkillsResponse(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: diyApi getSkills", e);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });
    }

    private void onDownloadSkillsResponse(Response<DiyApi.DiyResponse<List<DiyApi.Skill>>> response) {
        if (null != response && null != response.body()) {
            onDownloadSkillsResponse(response.body());
        }
    }

    private void onDownloadSkillsResponse(DiyApi.DiyResponse<List<DiyApi.Skill>> skillsResponse) {
        if (null != skillsResponse && null != skillsResponse.response) {
            onDownloadSkills(skillsResponse.response);
        }
    }

    private static final String TAG = "MainActivity";

    private void onDownloadSkills(List<DiyApi.Skill> skills) {
        Log.d(TAG, "onDownloadSkills: Downloaded " + skills.size() + " skills");

        final StringBuilder stringBuilder = new StringBuilder();

        for (final DiyApi.Skill skill : skills) {
            stringBuilder.append(skill.toString());
            stringBuilder.append("\n");
        }

        final String skillsText = stringBuilder.toString();
        skillsTextView.setText(skillsText);
    }
}
