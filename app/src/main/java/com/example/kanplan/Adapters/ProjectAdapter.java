package com.example.kanplan.Adapters;



import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kanplan.Holders.ProjectHolder;
import com.example.kanplan.Models.Project;
import com.example.kanplan.R;

import java.util.ArrayList;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectHolder> {


    private Context context;
    ArrayList<Project> projects;

    public ProjectAdapter(Context context, ArrayList<Project> projects) {
        this.context = context;
        this.projects = projects;
    }

    @NonNull
    @Override
    public ProjectHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProjectHolder(LayoutInflater.from(context).inflate(R.layout.item_project,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectHolder holder, int position) {
        holder.projectName.setText(projects.get(position).getProjectName());
        holder.projectDescription.setText(projects.get(position).getDescription());
        holder.projectLeader.setText(projects.get(position).getProjectManager().getUserName());
        holder.projectComplexity.setText(projects.get(position).getComplexityString());
        switch(projects.get(position).getComplexityString()){
            case "Very Complex":
                holder.projectComplexity.setBackgroundResource(R.drawable.highlight_asap_vcomplex_vbig);
                break;
            case "Complex":
                holder.projectComplexity.setBackgroundResource(R.drawable.highlight_high_complex_big);
                break;
            case "Regular":
                holder.projectComplexity.setBackgroundResource(R.drawable.highlight_medium_regular_regular);
                break;
            case "Easy":
                holder.projectComplexity.setBackgroundResource(R.drawable.highlight_low_small_easy);
        }


        holder.projectEmergency.setText(projects.get(position).getEmergencyString());
        switch(projects.get(position).getEmergencyString()){
            case "ASAP":
                holder.projectEmergency.setBackgroundResource(R.drawable.highlight_asap_vcomplex_vbig);
                break;
            case "High":
                holder.projectEmergency.setBackgroundResource(R.drawable.highlight_high_complex_big);
                break;
            case "Medium":
                holder.projectEmergency.setBackgroundResource(R.drawable.highlight_medium_regular_regular);
                break;
            case "Low":
                holder.projectEmergency.setBackgroundResource(R.drawable.highlight_low_small_easy);
        }

        holder.projectSize.setText(projects.get(position).getSizeString());

        switch(projects.get(position).getSizeString()){
            case "Very Big":
                holder.projectSize.setBackgroundResource(R.drawable.highlight_asap_vcomplex_vbig);
                break;
            case "Big":
                holder.projectSize.setBackgroundResource(R.drawable.highlight_high_complex_big);
                break;
            case "Regular":
                holder.projectSize.setBackgroundResource(R.drawable.highlight_medium_regular_regular);
                break;
            case "Small":
                holder.projectSize.setBackgroundResource(R.drawable.highlight_low_small_easy);
        }

    }

    @Override
    public int getItemCount() {
        return projects.size();
    }
}
