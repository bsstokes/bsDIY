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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bsstokes.bsdiy.api.DiyApi;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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

    @Inject DiyApi diyApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BsDiyApplication.getApplication(this).appComponent().inject(this);

        if (null == diyApi) {
            throw new NullPointerException("diyApi");
        }

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int itemId = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (itemId == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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

    private static final String TAG = "MainActivity";

    private void displayResponse(Response<DiyApi.DiyResponse<DiyApi.DiyInfo>> response) {
        if (null != response) {
            if (null != response.body()) {
                displayResponse(response.body());
            }
        }
    }

    private void displayResponse(DiyApi.DiyResponse<DiyApi.DiyInfo> diyResponse) {
        if (null != diyResponse) {
            Log.d(TAG, "displayResponse: diyResponse=" + diyResponse);
            Toast.makeText(this, "diyResponse=" + diyResponse, Toast.LENGTH_SHORT).show();
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

    private void onDownloadSkills(List<DiyApi.Skill> skills) {
        for (final DiyApi.Skill skill : skills) {
            Log.d(TAG, "onDownloadSkills: skill=" + skill);
        }
    }
}
