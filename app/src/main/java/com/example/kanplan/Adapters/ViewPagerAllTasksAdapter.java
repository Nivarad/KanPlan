package com.example.kanplan.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.kanplan.Fragments.BacklogFragment;
import com.example.kanplan.Fragments.DoingFragment;
import com.example.kanplan.Fragments.DoneFragment;

public class ViewPagerAllTasksAdapter extends FragmentPagerAdapter {

    private String projectID;
    private String projectManagerEmail;

    public ViewPagerAllTasksAdapter(@NonNull FragmentManager fm, String mail) {
        super(fm);
        this.projectID=projectID;
        this.projectManagerEmail=projectManagerEmail;

    }

    @Override
    public Fragment getItem(int position) {
        if(position ==0){
            return new BacklogFragment("0",projectManagerEmail);
        }
        else if(position==1){
            return new DoingFragment("0",projectManagerEmail);
        }
        else{
            return new DoneFragment("0",projectManagerEmail);
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
