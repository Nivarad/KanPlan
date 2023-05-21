package com.example.kanplan.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kanplan.Adapters.ProjectAdapter;
import com.example.kanplan.Adapters.TaskAdapter;
import com.example.kanplan.Interfaces.RecyclerViewInterface;
import com.example.kanplan.Interfaces.TaskDataCallback;
import com.example.kanplan.Models.Project;
import com.example.kanplan.Models.Task;
import com.example.kanplan.R;
import com.example.kanplan.UI.ProjectsActivity;
import com.example.kanplan.Utils.MySP;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class BacklogFragment extends Fragment implements RecyclerViewInterface {

    private RecyclerView backlogRecycler;
    private ArrayList<Task> storedTasks = new ArrayList<>();
    public BacklogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_backlog, container, false);

        backlogRecycler = getView().findViewById(R.id.recycler_Backlog_recycler);
        getProjectTasks(MySP.getInstance().getProjectID(), new TaskDataCallback() {
            @Override
            public void onCallback(ArrayList<Task> tasks) {
                backlogRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
                backlogRecycler.setAdapter(new TaskAdapter(getContext(), tasks, BacklogFragment.this));
            }
        });
        return view;



    }

    private void getProjectTasks(String projectID, TaskDataCallback taskDataCallback) {
        DatabaseReference projectRef = FirebaseDatabase.getInstance().getReference("tasks");
        projectRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Task> tasks = new ArrayList<>();
                for (DataSnapshot projectSnapshot : dataSnapshot.getChildren()) {
                    Task task = projectSnapshot.getValue(Task.class);
                    if (task.getProjectID().equals(projectID)) {
                        tasks.add(task);
                        storedTasks.add(task);

                    }
                }
                taskDataCallback.onCallback(tasks);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });
    }

    @Override
    public void onItemClick(int position) {

    }
}