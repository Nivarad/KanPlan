package com.example.kanplan.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.kanplan.Adapters.ProjectAdapter;
import com.example.kanplan.Interfaces.ProjectDataCallback;
import com.example.kanplan.Interfaces.RecyclerViewInterface;
import com.example.kanplan.Models.Project;
import com.example.kanplan.R;
import com.example.kanplan.SignalGenerator;
import com.example.kanplan.Utils.DrawerBaseActivity;
import com.example.kanplan.Utils.MySP;
import com.example.kanplan.databinding.ActivityHomeBinding;
import com.example.kanplan.databinding.ActivityProjectsBinding;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProjectsActivity extends DrawerBaseActivity implements RecyclerViewInterface, NavigationView.OnNavigationItemSelectedListener {

    ActivityProjectsBinding activityProjectsBinding;
    private ShapeableImageView backArrow;
    private ShapeableImageView addProject;
    private RecyclerView recyclerView;

    private ArrayList<Project> storedProjects = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityProjectsBinding = activityProjectsBinding.inflate(getLayoutInflater());
        setContentView(activityProjectsBinding.getRoot());
        allocateActivityTitle("Projects");

        findViews();


        getUserProjects(MySP.getInstance().getEmail(), new ProjectDataCallback() {
            @Override
            public void onCallback(ArrayList<Project> projects) {
                recyclerView.setLayoutManager(new LinearLayoutManager(ProjectsActivity.this));
                recyclerView.setAdapter(new ProjectAdapter(getApplicationContext(), projects, ProjectsActivity.this));
            }
        });



        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHomeScreen();
            }
        });

        addProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCreateProjectView();
            }
        });
    }

    private void openCreateProjectView() {
        Intent intent = new Intent(this, CreateProjectActivity.class);
        startActivity(intent);
        finish();
    }

    public void getUserProjects(String userEmail, final ProjectDataCallback callback) {
        DatabaseReference projectRef = FirebaseDatabase.getInstance().getReference("projects");

        projectRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Project> projects = new ArrayList<>();
                for (DataSnapshot projectSnapshot : dataSnapshot.getChildren()) {
                    Project project = projectSnapshot.getValue(Project.class);
                    if (project.getTeam().contains(userEmail)) {
                        projects.add(project);
                        storedProjects.add(project);
                    }
                }
                callback.onCallback(projects);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });
    }

    public void findViews() {
        backArrow = findViewById(R.id.Image_Project_backArrow);
        addProject = findViewById(R.id.Image_Project_addProject);
        recyclerView = findViewById(R.id.recycler_Project_recycler);

    }

    public void openHomeScreen() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onItemClick(int position) {
        Project project = storedProjects.get(position);
        Intent intent =new Intent(this,TasksActivity.class);
        intent.putExtra("projectID",project.getProjectID());
        intent.putExtra("projectManager",project.getProjectManagerEmail());
        Log.d("project clicked", String.valueOf(storedProjects.get(position)));
        startActivity(intent);
        finish();

    }

    @Override
    public void onItemLongClick(int position) {
        // Get the long-pressed item view from the RecyclerView
        RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(position);
        if (viewHolder instanceof ProjectAdapter.ProjectHolder) {
            ProjectAdapter.ProjectHolder projectHolder = (ProjectAdapter.ProjectHolder) viewHolder;
            Button deleteButton = projectHolder.deleteButton;
            Button editButton = projectHolder.editButton;
            if(!MySP.getInstance().getEmail().equals(projectHolder.leaderEmail)) {
                deleteButton.setVisibility(View.GONE);
                editButton.setVisibility(View.GONE);// Make the button invisible
                SignalGenerator.getInstance().toast("You are not project Manager",0);
            }
            else{
                if (deleteButton.getVisibility() == View.VISIBLE) {
                    deleteButton.setVisibility(View.GONE);
                    editButton.setVisibility(View.GONE);// Make the button invisible
                } else {
                    deleteButton.setVisibility(View.VISIBLE);
                    editButton.setVisibility(View.VISIBLE); // Make the button visible
                }


            }
        }
    }


}

