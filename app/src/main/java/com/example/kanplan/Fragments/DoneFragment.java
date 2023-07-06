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

import com.example.kanplan.Adapters.TaskAdapter;
import com.example.kanplan.Interfaces.RecyclerViewInterface;
import com.example.kanplan.Interfaces.TaskDataCallback;
import com.example.kanplan.Models.Task;
import com.example.kanplan.R;
import com.example.kanplan.SignalGenerator;
import com.example.kanplan.UI.TaskActivity;
import com.example.kanplan.Utils.MySP;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.kanplan.Data.DataManager.Status;
import java.util.ArrayList;


public class DoneFragment extends Fragment implements RecyclerViewInterface {


    private RecyclerView doneRecycler;
    private ArrayList<Task> storedTasks = new ArrayList<>();
    private String projectID;
    private String projectManagerEmail;
    public DoneFragment() {
        // Required empty public constructor
    }
    public DoneFragment(String projectID,String projectManagerEmail) {
        this.projectID=projectID;
        this.projectManagerEmail=projectManagerEmail;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_done, container, false);

        doneRecycler = view.findViewById(R.id.recycler_Done_recycler);
        if(projectID.equals("0")){
            getAllProjectsTasks(new TaskDataCallback() {
                @Override
                public void onCallback(ArrayList<Task> tasks) {
                    doneRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
                    TaskAdapter adapter = new TaskAdapter(getContext(), tasks, DoneFragment.this);
                    doneRecycler.setAdapter(adapter);
                }
            });
        }
        else {
            getProjectTasks(projectID, new TaskDataCallback() {
                @Override
                public void onCallback(ArrayList<Task> tasks) {
                    doneRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
                    TaskAdapter adapter = new TaskAdapter(getContext(), tasks, DoneFragment.this);
                    doneRecycler.setAdapter(adapter);
                }
            });
        }
        return view;

    }

    private void getAllProjectsTasks(TaskDataCallback taskDataCallback){
        DatabaseReference projectRef = FirebaseDatabase.getInstance().getReference("tasks");
        projectRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Task> tasks = new ArrayList<>();
                for (DataSnapshot projectSnapshot : dataSnapshot.getChildren()) {
                    Task task = projectSnapshot.getValue(Task.class);
                    if (task.getAssigned().contains(MySP.getInstance().getEmail()) && task.getStatus() ==Status.DONE) {
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

    private void getProjectTasks(String projectID, TaskDataCallback taskDataCallback) {
        DatabaseReference projectRef = FirebaseDatabase.getInstance().getReference("tasks");
        projectRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Task> tasks = new ArrayList<>();
                for (DataSnapshot projectSnapshot : dataSnapshot.getChildren()) {
                    Task task = projectSnapshot.getValue(Task.class);
                    if (task.getProjectID().equals(projectID) && task.getStatus() ==Status.DONE) {
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
        intent.putExtra("projectID",task.getProjectID());
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(int position) {
        // Get the long-pressed item view from the RecyclerView
        RecyclerView.ViewHolder viewHolder = doneRecycler.findViewHolderForAdapterPosition(position);
        if (viewHolder instanceof TaskAdapter.TaskHolder) {
            TaskAdapter.TaskHolder TaskHolder = (TaskAdapter.TaskHolder) viewHolder;
            Button deleteButton = TaskHolder.deleteButton;
            Button editButton = TaskHolder.editButton;
            if(!MySP.getInstance().getEmail().equals(projectManagerEmail)) {
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