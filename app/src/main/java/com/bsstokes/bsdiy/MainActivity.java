package com.bsstokes.bsdiy;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Inject DiyApi diyApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BsDiyApplication.getApplication(this).appComponent().inject(this);

        if (null == diyApi) {
            throw new NullPointerException("diyApi");
        }

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            downloadSomething();
        } else if (id == R.id.nav_gallery) {
            downloadSkills();
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
