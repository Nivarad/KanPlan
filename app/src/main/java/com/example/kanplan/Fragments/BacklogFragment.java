package com.example.kanplan.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.kanplan.Adapters.ProjectAdapter;
import com.example.kanplan.Adapters.TaskAdapter;
import com.example.kanplan.Interfaces.RecyclerViewInterface;
import com.example.kanplan.Interfaces.TaskDataCallback;
import com.example.kanplan.Models.Task;
import com.example.kanplan.R;
import com.example.kanplan.SignalGenerator;
import com.example.kanplan.UI.HomeActivity;
import com.example.kanplan.UI.TaskActivity;
import com.example.kanplan.UI.TasksActivity;
import com.example.kanplan.Utils.MySP;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.kanplan.Data.DataManager.Status;
import java.util.ArrayList;


public class BacklogFragment extends Fragment implements RecyclerViewInterface {

    private RecyclerView backlogRecycler;
    private ArrayList<Task> storedTasks = new ArrayList<>();

    private String projectID;
    private String projectManagerEmail;

    public BacklogFragment() {
        // Required empty public constructor
    }

    public BacklogFragment(String projectID, String projectManagerEmail) {
        this.projectID = projectID;
        this.projectManagerEmail = projectManagerEmail;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_backlog, container, false);
        backlogRecycler = view.findViewById(R.id.recycler_Backlog_recycler);

        if (projectID.equals("0")) {
            getAllProjectsTasks(new TaskDataCallback() {
                @Override
                public void onCallback(ArrayList<Task> tasks) {
                    backlogRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
                    TaskAdapter adapter = new TaskAdapter(getContext(), tasks, BacklogFragment.this);
                    backlogRecycler.setAdapter(adapter);
                }
            });
        } else {
            getProjectTasks(projectID, new TaskDataCallback() {
                @Override
                public void onCallback(ArrayList<Task> tasks) {
                    backlogRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
                    TaskAdapter adapter = new TaskAdapter(getContext(), tasks, BacklogFragment.this);
                    backlogRecycler.setAdapter(adapter);
                }
            });
        }

        return view;
    }

    /**
     * Retrieves all tasks for all projects where the current user is assigned and the status is BACKLOG.
     *
     * @param taskDataCallback The callback to be called with the list of tasks.
     */
    private void getAllProjectsTasks(TaskDataCallback taskDataCallback) {
        DatabaseReference projectRef = FirebaseDatabase.getInstance().getReference("tasks");
        projectRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Task> tasks = new ArrayList<>();
                for (DataSnapshot projectSnapshot : dataSnapshot.getChildren()) {
                    Task task = projectSnapshot.getValue(Task.class);
                    if (task.getAssigned().contains(MySP.getInstance().getEmail()) && task.getStatus() == Status.BACKLOG) {
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

    /**
     * Retrieves all tasks for a specific project where the status is BACKLOG.
     *
     * @param projectID        The ID of the project.
     * @param taskDataCallback The callback to be called with the list of tasks.
     */
    private void getProjectTasks(String projectID, TaskDataCallback taskDataCallback) {
        DatabaseReference projectRef = FirebaseDatabase.getInstance().getReference("tasks");
        projectRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Task> tasks = new ArrayList<>();
                for (DataSnapshot projectSnapshot : dataSnapshot.getChildren()) {
                    Task task = projectSnapshot.getValue(Task.class);
                    if (task.getProjectID().equals(projectID) && task.getStatus() == Status.BACKLOG) {
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
        Task task = storedTasks.get(position);
        Intent intent = new Intent(getActivity(), TaskActivity.class);
        intent.putExtra("taskID", task.getTaskID());
        intent.putExtra("projectID", task.getProjectID());
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(int position) {
        // Get the long-pressed item view from the RecyclerView
        RecyclerView.ViewHolder viewHolder = backlogRecycler.findViewHolderForAdapterPosition(position);
        if (viewHolder instanceof TaskAdapter.TaskHolder) {
            TaskAdapter.TaskHolder taskHolder = (TaskAdapter.TaskHolder) viewHolder;
            Button deleteButton = taskHolder.deleteButton;
            Button editButton = taskHolder.editButton;

            if (!MySP.getInstance().getEmail().equals(projectManagerEmail)) {
                deleteButton.setVisibility(View.GONE);
                editButton.setVisibility(View.GONE);
                SignalGenerator.getInstance().toast("You are not the project manager", 0);
            } else {
                if (deleteButton.getVisibility() == View.VISIBLE) {
                    deleteButton.setVisibility(View.GONE);
                    editButton.setVisibility(View.GONE);
                } else {
                    deleteButton.setVisibility(View.VISIBLE);
                    editButton.setVisibility(View.VISIBLE);
                }
            }
        }
    }
}
