package com.example.kanplan.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kanplan.Interfaces.RecyclerViewInterface;
import com.example.kanplan.Models.Task;
import com.example.kanplan.R;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskHolder>{

    private Context context;
    ArrayList<Task> tasks;
    private final RecyclerViewInterface recyclerViewInterface;

    public TaskAdapter(Context context, ArrayList<Task> tasks, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.tasks = tasks;
        this.recyclerViewInterface = recyclerViewInterface;
    }


    @NonNull
    @Override
    public TaskAdapter.TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TaskHolder(LayoutInflater.from(context).inflate(R.layout.item_task,parent,false),recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.TaskHolder holder, int position) {
        holder.taskName.setText(tasks.get(position).getTaskName());
        holder.taskDescription.setText(tasks.get(position).getTaskDescription());
        holder.taskComplexity.setText(tasks.get(position).getComplexityString());
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



        public TaskHolder(@NonNull View itemView , RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            findViewsHolder();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewInterface !=null){
                        int position = getAdapterPosition();
                        if(position!= RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(position);
                        }
                    }
                }
            });

        }

        public void findViewsHolder(){
            taskName = itemView.findViewById(R.id.TaskName);

            taskDescription=itemView.findViewById(R.id.TaskDescription);
            taskComplexity = itemView.findViewById(R.id.TaskComplexity);
            taskSize = itemView.findViewById(R.id.TaskSize);
            taskEmergency = itemView.findViewById(R.id.TaskEmergency);
        }
    }
}