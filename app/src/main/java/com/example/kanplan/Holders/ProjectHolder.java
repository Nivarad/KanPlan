package com.example.kanplan.Holders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kanplan.R;
import com.google.android.material.textview.MaterialTextView;

public class ProjectHolder extends RecyclerView.ViewHolder {

    public MaterialTextView projectName;
    public MaterialTextView projectLeader;
    public MaterialTextView projectDescription;

    public MaterialTextView projectComplexity;
    public MaterialTextView projectSize;
    public MaterialTextView projectEmergency;



    public ProjectHolder(@NonNull View itemView) {
        super(itemView);

        findViews();

    }

    public void findViews(){
        projectName = itemView.findViewById(R.id.projectName);
        projectLeader=itemView.findViewById(R.id.projectLeader);
        projectDescription=itemView.findViewById(R.id.projectDescription);
        projectComplexity = itemView.findViewById(R.id.ProjectComplexity);
        projectSize = itemView.findViewById(R.id.ProjectSize);
        projectEmergency = itemView.findViewById(R.id.ProjectEmergency);
    }
}
