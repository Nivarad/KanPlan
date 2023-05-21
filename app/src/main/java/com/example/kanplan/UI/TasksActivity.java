package com.example.kanplan.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.kanplan.Adapters.ViewPagerTasksAdapter;
import com.example.kanplan.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.tabs.TabLayout;

public class TasksActivity extends AppCompatActivity {

    private TabLayout tab;
    private ViewPager viewPager;
    private ShapeableImageView backArrow;
    private ShapeableImageView addTask;

    ViewPagerTasksAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        findViews();

        adapter = new ViewPagerTasksAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        tab.setupWithViewPager(viewPager);

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProjectsView();
            }
        });

        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewTask();
            }
        });
    }

    private void openNewTask() {
//        Intent intent =new Intent(this,.class);
//        startActivity(intent);
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