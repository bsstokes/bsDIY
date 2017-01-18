package com.bsstokes.bsdiy.app.main

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.MenuItem
import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.bsstokes.bsdiy.R
import com.bsstokes.bsdiy.app.explore.ExploreFragment
import com.bsstokes.bsdiy.app.messages.MessagesFragment
import com.bsstokes.bsdiy.app.skills.SkillsFragment
import com.bsstokes.bsdiy.app.stream.StreamFragment
import com.bsstokes.bsdiy.app.to_dos.ToDosFragment

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    companion object {
        private const val TAG = "MainActivity"
    }

    @BindView(R.id.toolbar) internal lateinit var toolbar: Toolbar
    @BindView(R.id.drawer_layout) internal lateinit var drawer: DrawerLayout
    @BindView(R.id.nav_view) internal lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)

        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        navigationView.setNavigationItemSelectedListener(this)
    }

    override fun onResume() {
        super.onResume()
        supportFragmentManager.fragments ?: onSkillsNavigationItemSelected()
    }

    @OnClick(R.id.fab)
    internal fun onClickFab(view: View) {
        Snackbar.make(view, R.string.post_a_project, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_messages -> onMessagesNavigationItemSelected()
            R.id.nav_to_dos -> onToDosNavigationItemSelected()
            R.id.nav_skills -> onSkillsNavigationItemSelected()
            R.id.nav_stream -> onStreamNavigationItemSelected()
            R.id.nav_explore -> onExploreNavigationItemSelected()
            else -> Log.e(TAG, "Unknown navigation item selected")
        }

        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    private fun onSkillsNavigationItemSelected() {
        if (!isFragmentLoaded(SkillsFragment.TAG)) {
            loadFragment(SkillsFragment.newInstance(), SkillsFragment.TAG)
        }
    }

    private fun onMessagesNavigationItemSelected() {
        if (!isFragmentLoaded(MessagesFragment.TAG)) {
            loadFragment(MessagesFragment.newInstance(), MessagesFragment.TAG)
        }
    }

    private fun onToDosNavigationItemSelected() {
        if (!isFragmentLoaded(ToDosFragment.TAG)) {
            loadFragment(ToDosFragment.newInstance(), ToDosFragment.TAG)
        }
    }

    private fun onStreamNavigationItemSelected() {
        if (!isFragmentLoaded(StreamFragment.TAG)) {
            loadFragment(StreamFragment.newInstance(), StreamFragment.TAG)
        }
    }

    private fun onExploreNavigationItemSelected() {
        if (!isFragmentLoaded(ExploreFragment.TAG)) {
            loadFragment(ExploreFragment.newInstance(), ExploreFragment.TAG)
        }
    }

    private fun loadFragment(fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.content_view, fragment, tag)
                .commit()
    }

    private fun isFragmentLoaded(tag: String): Boolean {
        return null != supportFragmentManager.findFragmentByTag(tag)
    }
}
