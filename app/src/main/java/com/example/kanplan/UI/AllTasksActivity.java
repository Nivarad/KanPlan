package com.example.kanplan.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.kanplan.Adapters.ViewPagerAllTasksAdapter;
import com.example.kanplan.Adapters.ViewPagerTasksAdapter;
import com.example.kanplan.R;
import com.example.kanplan.Utils.DrawerBaseActivity;
import com.example.kanplan.Utils.MySP;
import com.example.kanplan.databinding.ActivityAllTasksBinding;
import com.example.kanplan.databinding.ActivityTasksBinding;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.tabs.TabLayout;

public class AllTasksActivity extends DrawerBaseActivity {

    ActivityAllTasksBinding activityAllTasksBinding;
    private TabLayout tab;
    private ViewPager viewPager;
    private ShapeableImageView backArrow;
    private ShapeableImageView addTask;
    public static String projectID;
    private String email;

    ViewPagerAllTasksAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAllTasksBinding = ActivityAllTasksBinding.inflate(getLayoutInflater());
        setContentView(activityAllTasksBinding.getRoot());
        allocateActivityTitle("All Tasks");

//        Intent intent = getIntent();

        findViews();
        email = MySP.getInstance().getEmail();
        adapter = new ViewPagerAllTasksAdapter(getSupportFragmentManager(),email);
        viewPager.setAdapter(adapter);

        tab.setupWithViewPager(viewPager);

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHomeView();
            }
        });

    }

    private void openHomeView() {
        Intent intent =new Intent(this,HomeActivity.class);
        startActivity(intent);
    }

    private void findViews() {
        tab = findViewById(R.id.tab_AllTasks_tab);
        viewPager = findViewById(R.id.viewPager_AllTasks_ViewPager);
        backArrow = findViewById(R.id.Image_AllTasks_backArrow);


    }


}