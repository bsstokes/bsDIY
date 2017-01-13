package com.bsstokes.bsdiy;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.bsstokes.bsdiy.application.BsDiyApplication;
import com.bsstokes.bsdiy.explore.ExploreFragment;
import com.bsstokes.bsdiy.messages.MessagesFragment;
import com.bsstokes.bsdiy.skills.SkillsFragment;
import com.bsstokes.bsdiy.stream.StreamFragment;
import com.bsstokes.bsdiy.to_dos.ToDosFragment;

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

        if (null == getSupportFragmentManager().getFragments()) {
            onSkillsNavigationItemSelected();
        }
    }

    @OnClick(R.id.fab)
    void onClickFab(View view) {
        Snackbar.make(view, R.string.post_a_project, Snackbar.LENGTH_LONG)
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
            onMessagesNavigationItemSelected();
        } else if (R.id.nav_to_dos == itemId) {
            onToDosNavigationItemSelected();
        } else if (R.id.nav_skills == itemId) {
            onSkillsNavigationItemSelected();
        } else if (R.id.nav_stream == itemId) {
            onStreamNavigationItemSelected();
        } else if (R.id.nav_explore == itemId) {
            onExploreNavigationItemSelected();
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
        if (!isFragmentLoaded(SkillsFragment.TAG)) {
            loadFragment(SkillsFragment.newInstance(), SkillsFragment.TAG);
        }
    }

    private void onMessagesNavigationItemSelected() {
        if (!isFragmentLoaded(MessagesFragment.TAG)) {
            loadFragment(MessagesFragment.newInstance(), MessagesFragment.TAG);
        }
    }

    private void onToDosNavigationItemSelected() {
        if (!isFragmentLoaded(ToDosFragment.TAG)) {
            loadFragment(ToDosFragment.newInstance(), ToDosFragment.TAG);
        }
    }

    private void onStreamNavigationItemSelected() {
        if (!isFragmentLoaded(StreamFragment.TAG)) {
            loadFragment(StreamFragment.newInstance(), StreamFragment.TAG);
        }
    }

    private void onExploreNavigationItemSelected() {
        if (!isFragmentLoaded(ExploreFragment.TAG)) {
            loadFragment(ExploreFragment.newInstance(), ExploreFragment.TAG);
        }
    }

    private void loadFragment(Fragment fragment, @NonNull String tag) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_view, fragment, tag)
                .commit();
    }

    private boolean isFragmentLoaded(String tag) {
        return null != getSupportFragmentManager().findFragmentByTag(tag);
    }
}
