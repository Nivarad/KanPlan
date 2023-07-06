package com.example.kanplan.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kanplan.Interfaces.RecyclerViewInterface;
import com.example.kanplan.Models.Task;
import com.example.kanplan.R;
import com.example.kanplan.SignalGenerator;
import com.example.kanplan.UI.EditTaskActivity;
import com.example.kanplan.Utils.MySP;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskHolder>{

    private Context context;
    private ArrayList<Task> tasks;

    private final RecyclerViewInterface recyclerViewInterface;



    public TaskAdapter(Context context, ArrayList<Task> tasks, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.tasks = tasks;
        this.recyclerViewInterface = recyclerViewInterface;
    }



    @NonNull
    @Override
    public TaskAdapter.TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TaskHolder(LayoutInflater.from(context).inflate(R.layout.item_task, parent, false),tasks,recyclerViewInterface);



    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.TaskHolder holder, int position) {
        int new_pos=position;


        holder.taskName.setText(tasks.get(position).getTaskName());
        holder.taskDescription.setText(tasks.get(position).getTaskDescription());
        holder.taskComplexity.setText(tasks.get(position).getComplexityString());
        if(tasks.get(position).getAssigned().contains(MySP.getInstance().getEmail())){
            holder.taskWarning.setVisibility(View.VISIBLE);
        }
        else{
            holder.taskWarning.setVisibility(View.INVISIBLE);
        }
        switch(tasks.get(position).getComplexityString()){
            case "Very Complex":
                holder.taskComplexity.setBackgroundResource(R.drawable.highlight_asap_vcomplex_vbig);
                break;
            case "Complex":
                holder.taskComplexity.setBackgroundResource(R.drawable.highlight_high_complex_big);
                break;
            case "Regular":
                holder.taskComplexity.setBackgroundResource(R.drawable.highlight_medium_regular_regular);
                break;
            case "Easy":
                holder.taskComplexity.setBackgroundResource(R.drawable.highlight_low_small_easy);
        }


        holder.taskEmergency.setText(tasks.get(position).getEmergencyString());
        switch(tasks.get(position).getEmergencyString()){
            case "ASAP":
                holder.taskEmergency.setBackgroundResource(R.drawable.highlight_asap_vcomplex_vbig);
                break;
            case "High":
                holder.taskEmergency.setBackgroundResource(R.drawable.highlight_high_complex_big);
                break;
            case "Medium":
                holder.taskEmergency.setBackgroundResource(R.drawable.highlight_medium_regular_regular);
                break;
            case "Low":
                holder.taskEmergency.setBackgroundResource(R.drawable.highlight_low_small_easy);
        }

        holder.taskSize.setText(tasks.get(position).getSizeString());

        switch(tasks.get(position).getSizeString()){
            case "Very Big":
                holder.taskSize.setBackgroundResource(R.drawable.highlight_asap_vcomplex_vbig);
                break;
            case "Big":
                holder.taskSize.setBackgroundResource(R.drawable.highlight_high_complex_big);
                break;
            case "Regular":
                holder.taskSize.setBackgroundResource(R.drawable.highlight_medium_regular_regular);
                break;
            case "Small":
                holder.taskSize.setBackgroundResource(R.drawable.highlight_low_small_easy);
        }



    }


    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public static class TaskHolder extends RecyclerView.ViewHolder {

        public MaterialTextView taskName;

        public MaterialTextView taskDescription;

        public MaterialTextView taskComplexity;
        public MaterialTextView taskSize;
        public MaterialTextView taskEmergency;
        public ShapeableImageView taskWarning;

        public Button editButton;

        public Button deleteButton;




        public TaskHolder(@NonNull View itemView,final ArrayList<Task> tasks, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            findViewsHolder();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recyclerViewInterface != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            recyclerViewInterface.onItemClick(position);
                        }
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (recyclerViewInterface != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            recyclerViewInterface.onItemLongClick(position);
                            return true; // Return true to consume the long click event
                        }
                    }
                    return false;
                }
            });

            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        // Get the task ID
                        String taskID = tasks.get(position).getTaskID();
                        String projectID = tasks.get(position).getProjectID();

                         //Create an intent to start the EditTaskActivity
                        Intent intent = new Intent(itemView.getContext(), EditTaskActivity.class);
                        intent.putExtra("taskID", taskID);
                        intent.putExtra("projectID",projectID);
                        itemView.getContext().startActivity(intent);
                    }
                }
            });

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Task task = tasks.get(position);
                        String taskID = task.getTaskID();
                        String projectID = task.getProjectID();

                        // Remove the task from Firebase
                        DatabaseReference taskRef = FirebaseDatabase.getInstance().getReference("tasks")
                                .child(taskID);
                        taskRef.removeValue();
                        SignalGenerator.getInstance().toast("Task deleted Successfully",1);

                    }
                }
            });

            editButton.setVisibility(View.GONE);
            deleteButton.setVisibility(View.GONE);
        }

        public void findViewsHolder(){
            taskName = itemView.findViewById(R.id.taskName);

            taskDescription=itemView.findViewById(R.id.taskDescription);
            taskComplexity = itemView.findViewById(R.id.taskComplexity);
            taskSize = itemView.findViewById(R.id.taskSize);
            taskEmergency = itemView.findViewById(R.id.taskEmergency);
            taskWarning = itemView.findViewById(R.id.taskWarning);
            editButton = itemView.findViewById(R.id.taskEditButton);

            deleteButton =itemView.findViewById(R.id.taskDeleteButton);

        }
    }
}
