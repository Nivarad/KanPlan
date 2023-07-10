package com.example.kanplan.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.kanplan.Fragments.BacklogFragment;
import com.example.kanplan.Fragments.DoingFragment;
import com.example.kanplan.Fragments.DoneFragment;

/**
 * Adapter for ViewPager to display task fragments based on their status.
 */
public class ViewPagerTasksAdapter extends FragmentPagerAdapter {

    private String projectID;
    private String projectManagerEmail;

    /**
     * Constructs a ViewPagerTasksAdapter.
     *
     * @param fm                  The FragmentManager instance.
     * @param projectID           The ID of the project.
     * @param projectManagerEmail The email of the project manager.
     */
    public ViewPagerTasksAdapter(@NonNull FragmentManager fm, String projectID, String projectManagerEmail) {
        super(fm);
        this.projectID = projectID;
        this.projectManagerEmail = projectManagerEmail;
    }

    /**
     * Returns the fragment for the specified position in the ViewPager.
     *
     * @param position The position of the fragment.
     * @return The fragment to be displayed.
     */
    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            // Display BacklogFragment
            return new BacklogFragment(projectID, projectManagerEmail);
        } else if (position == 1) {
            // Display DoingFragment
            return new DoingFragment(projectID, projectManagerEmail);
        } else {
            // Display DoneFragment
            return new DoneFragment(projectID, projectManagerEmail);
        }
    }

    /**
     * Returns the number of tabs.
     *
     * @return The number of tabs.
     */
    @Override
    public int getCount() {
        return 3; // Number of tabs
    }

    /**
     * Returns the title of the tab at the specified position.
     *
     * @param position The position of the tab.
     * @return The title of the tab.
     */
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "Backlog";
        } else if (position == 1) {
            return "Doing";
        } else {
            return "Done";
        }
    }
}

