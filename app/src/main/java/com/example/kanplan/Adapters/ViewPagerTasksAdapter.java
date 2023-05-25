package com.example.kanplan.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.kanplan.Fragments.BacklogFragment;
import com.example.kanplan.Fragments.DoingFragment;
import com.example.kanplan.Fragments.DoneFragment;

public class ViewPagerTasksAdapter extends FragmentPagerAdapter {

    private String projectID;
    private String projectManagerEmail;

    public ViewPagerTasksAdapter(@NonNull FragmentManager fm, String projectID,String projectManagerEmail) {
        super(fm);
        this.projectID=projectID;
        this.projectManagerEmail=projectManagerEmail;

    }

    @Override
    public Fragment getItem(int position) {
        if(position ==0){
            return new BacklogFragment(projectID,projectManagerEmail);
        }
        else if(position==1){
            return new DoingFragment(projectID,projectManagerEmail);
        }
        else{
            return new DoneFragment(projectID,projectManagerEmail);
        }
    }

    @Override
    public int getCount() {
        return 3; //no of tabs
    }


    @Override
    public CharSequence getPageTitle(int position) {
        if(position==0){
            return "Backlog";
        }
        else if(position==1){
            return "Doing";
        }
        else{
            return "Done";
        }
    }
}
