package com.example.kanplan.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.kanplan.Adapters.ViewPagerTasksAdapter;
import com.example.kanplan.R;
import com.example.kanplan.Utils.DrawerBaseActivity;
import com.example.kanplan.Utils.MySP;
import com.example.kanplan.databinding.ActivityTasksBinding;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.tabs.TabLayout;

public class TasksActivity extends DrawerBaseActivity {

    ActivityTasksBinding activityTasksBinding;
    private TabLayout tab;
    private ViewPager viewPager;
    private ShapeableImageView backArrow;
    private ShapeableImageView addTask;
    public static String projectID;
    public static String projectManagerEmail;

    ViewPagerTasksAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTasksBinding = ActivityTasksBinding.inflate(getLayoutInflater());
        setContentView(activityTasksBinding.getRoot());
        allocateActivityTitle("Tasks");

        Intent intent = getIntent();
        if (intent != null) {
            projectID = intent.getStringExtra("projectID");
            String check=intent.getStringExtra("projectManager");
            if(check!=null)
                projectManagerEmail =check;
        }
        findViews();

        adapter = new ViewPagerTasksAdapter(getSupportFragmentManager(),projectID,projectManagerEmail);
        viewPager.setAdapter(adapter);

        tab.setupWithViewPager(viewPager);

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProjectsView();
            }
        });

        if(!MySP.getInstance().getEmail().equals(projectManagerEmail)){
            addTask.setVisibility(View.GONE);
        }
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewTask();
            }
        });


    }

    private void openNewTask() {
        Intent intent =new Intent(this,CreateTaskActivity.class);
        intent.putExtra("projectID",projectID);
        intent.putExtra("projectManager",projectManagerEmail);
        startActivity(intent);
    }

    private void openProjectsView() {
        Intent intent =new Intent(this,ProjectsActivity.class);
        startActivity(intent);
    }

    private void findViews() {
        tab = findViewById(R.id.tab_Tasks_tab);
        viewPager = findViewById(R.id.viewPager_Tasks_ViewPager);
        backArrow = findViewById(R.id.Image_Tasks_backArrow);
        addTask = findViewById(R.id.Image_Tasks_addTask);


    }


}