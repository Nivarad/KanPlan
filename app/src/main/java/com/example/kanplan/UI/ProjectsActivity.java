package com.example.kanplan.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.kanplan.Adapters.ProjectAdapter;
import com.example.kanplan.Interfaces.ProjectDataCallback;
import com.example.kanplan.Interfaces.RecyclerViewInterface;
import com.example.kanplan.Models.Project;
import com.example.kanplan.R;
import com.example.kanplan.Utils.MySP;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProjectsActivity extends AppCompatActivity implements RecyclerViewInterface {

    private ShapeableImageView backArrow;
    private ShapeableImageView addProject;
    private RecyclerView recyclerView;

    private ArrayList<Project> storedProjects = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);

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
    }

    @Override
    public void onItemClick(int position) {
        Intent intent =new Intent(this,TasksActivity.class);
        intent.putExtra("projectID",storedProjects.get(position).getProjectName());
        Log.d("project clicked", String.valueOf(storedProjects.get(position)));
        startActivity(intent);

    }
}
