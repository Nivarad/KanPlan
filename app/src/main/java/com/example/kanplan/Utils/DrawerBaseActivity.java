package com.example.kanplan.Utils;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.kanplan.R;
import com.example.kanplan.SignalGenerator;
import com.example.kanplan.UI.AllTasksActivity;
import com.example.kanplan.UI.HomeActivity;
import com.example.kanplan.UI.ProjectsActivity;
import com.example.kanplan.UI.SignInActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

/**
 * DrawerBaseActivity is a base activity class that provides a navigation drawer layout.
 * Other activities can inherit from this class to have a consistent navigation drawer behavior.
 */
public class DrawerBaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private FirebaseAuth auth;

    @Override
    public void setContentView(View view) {
        drawerLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_drawer_base, null);
        FrameLayout container = drawerLayout.findViewById(R.id.activityContainer);
        container.addView(view);
        super.setContentView(drawerLayout);

        Toolbar toolbar = drawerLayout.findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = drawerLayout.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        switch (item.getItemId()) {
            case R.id.nav_home:
                enterHomeActivity();
                break;
            case R.id.nav_projects:
                enterProjectsActivity();
                break;
            case R.id.nav_tasks:
                enterTasksActivity();
                break;
            case R.id.nav_logout:
                logout();
                break;
            case R.id.nav_share:
                shareApplication();
                break;
            case R.id.nav_rate:
                openDeveloperWebsite();
                break;
        }
        return false;
    }

    private void enterTasksActivity() {
        Intent intent= new Intent(this, AllTasksActivity.class);
        startActivity(intent);
    }

    /**
     * Opens the developer's website in a web browser.
     */
    private void openDeveloperWebsite() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://ardos.netlify.app/"));
        startActivity(browserIntent);
    }

    /**
     * Shares the application through various platforms.
     */
    private void shareApplication() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String shareBody = "Have you heard about KanPlan? Download it now!";
        String shareSub = "KanPlan Project Management";
        intent.putExtra(intent.EXTRA_SUBJECT, shareSub);
        intent.putExtra(Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(intent, "Share using"));
    }

    /**
     * Sets the title of the activity in the ActionBar.
     *
     * @param titleString The title to set.
     */
    protected void allocateActivityTitle(String titleString) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(titleString);
        }
    }

    /**
     * Navigates to the ProjectsActivity.
     */
    private void enterProjectsActivity() {
        Intent intent = new Intent(this, ProjectsActivity.class);
        startActivity(intent);
    }

    /**
     * Logs out the user and navigates to the SignInActivity.
     */
    public void logout() {
        auth.getInstance().signOut();
        MySP.getInstance().saveName("");
        MySP.getInstance().saveEmail("");
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Navigates to the HomeActivity.
     */
    public void enterHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
