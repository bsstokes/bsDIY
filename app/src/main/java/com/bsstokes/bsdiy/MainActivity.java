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

import com.bsstokes.bsdiy.application.BsDiyApplication;
import com.bsstokes.bsdiy.skills.SkillsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.drawer_layout) DrawerLayout drawer;
    @BindView(R.id.nav_view) NavigationView navigationView;
    @BindView(R.id.content_view) View contentView;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        BsDiyApplication.getApplication(this).appComponent().inject(this);

        setSupportActionBar(toolbar);

        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        );
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
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
            onSkillsNavigationItemSelected();
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
        Snackbar.make(contentView, text, Snackbar.LENGTH_LONG).show();
    }

    private void onSkillsNavigationItemSelected() {
        SkillsSyncService.startActionSyncSkills(this);

        if (!isFragmentLoaded(SkillsFragment.TAG)) {
            final SkillsFragment skillsFragment = SkillsFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_view, skillsFragment, SkillsFragment.TAG)
                    .commit();
        }
    }

    private boolean isFragmentLoaded(String tag) {
        return null != getSupportFragmentManager().findFragmentByTag(tag);
    }
}
