package com.example.kanplan.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.kanplan.Models.Project;
import com.example.kanplan.Models.User;
import com.example.kanplan.R;
import com.example.kanplan.SignalGenerator;
import com.example.kanplan.Utils.MySP;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EditProjectActivity extends AppCompatActivity {


    private EditText projectName;
    private EditText projectDescription;
    private EditText projectTeam;
    private Spinner projectComplexity;
    private Spinner projectSize;
    private Spinner projectEmergency;

    private List<String> emails = new ArrayList<>();

    private User user;

    private final String[] COMPLEXITIES = {"Complexity","Easy","Regular","Complex","Very Complex"};
    private final String[] SIZES = {"Size","Small","Regular","Big","Very big"};
    private final String[] EMERGENCIES = {"Emergency","Low","Medium","High","ASAP"};


    private ShapeableImageView backArrow;

    private ShapeableImageView saveIcon;
    private String projectID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_project);

        Intent intent = getIntent();
        if (intent != null) {
            projectID = intent.getStringExtra("projectID");
        }

        findViews();
        setSpinners();
        PopulateData();

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProjectsActivity();
            }
        });
        saveIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUpdatedData();
            }
        });
    }

    private void saveUpdatedData() {
        String name = projectName.getText().toString();
        String description = projectDescription.getText().toString();
        String complexity = projectComplexity.getSelectedItem().toString();
        String emergency = projectEmergency.getSelectedItem().toString();
        String size = projectSize.getSelectedItem().toString();
        String teamString = projectTeam.getText().toString();
        emails.clear(); // Clear the list before adding emails

        if(TextUtils.isEmpty(name) || TextUtils.isEmpty(description) ||complexity.equals("Complexity") || emergency.equals("Emergency") || size.equals("Size"))
            SignalGenerator.getInstance().toast("You need to fill all fields",1);
        else {
            if (!teamString.isEmpty()) {
                String[] emailArray = teamString.split("[ \n]+");
                for (String email : emailArray) {
                    if (isEmailValid(email)) {
                        emails.add(email);
                    } else {
                        SignalGenerator.getInstance().toast("One of your emails is not rightly formatted", 1);
                        return;
                    }
                }
            }
            saveProject(name, description, complexity, emergency, size, emails);
        }
    }

    private void saveProject(String name, String description, String complexity, String emergency, String size, List<String> emails) {
        Project.Complexity complex = null;
        Project.Size siz=null;
        Project.Emergency emerg=null;
        switch(complexity){
            case "Easy":
                complex= Project.Complexity.EASY;
                break;
            case "Regular":
                complex=Project.Complexity.REGULAR;
                break;
            case "Complex":
                complex=Project.Complexity.COMPLEX;
                break;
            case "Very Complex":
                complex=Project.Complexity.VERY_COMPLEX;
        }
        switch(size){
            case "Small":
                siz= Project.Size.SMALL;
                break;
            case "Regular":
                siz=Project.Size.REGULAR;
                break;
            case "Big":
                siz=Project.Size.BIG;
                break;
            case "Very big":
                siz=Project.Size.VERY_BIG;
        }
        switch(emergency){
            case "Low":
                emerg= Project.Emergency.LOW;
                break;
            case "Medium":
                emerg=Project.Emergency.MEDIUM;
                break;
            case "High":
                emerg=Project.Emergency.HIGH;
                break;
            case "ASAP":
                emerg=Project.Emergency.ASAP;
        }
        if(!emails.contains(MySP.getInstance().getEmail()))
            emails.add(MySP.getInstance().getEmail());
        Project project = new Project(name,user,description,emails,complex,siz,emerg,"");

        DatabaseReference projectRef = FirebaseDatabase.getInstance().getReference("projects").child(projectID);
        projectRef.child("projectName").setValue(project.getProjectName());
        projectRef.child("description").setValue(project.getDescription());
        projectRef.child("team").setValue(project.getTeam());
        projectRef.child("complexityString").setValue(project.getComplexityString());
        projectRef.child("complexity").setValue(project.getComplexity());
        projectRef.child("sizeString").setValue(project.getSizeString());
        projectRef.child("size").setValue(project.getSize());
        projectRef.child("emergencyString").setValue(project.getEmergencyString());
        projectRef.child("emergency").setValue(project.getEmergency());
        SignalGenerator.getInstance().toast("Project updated successfully",1);

        // Show a success message or perform any other desired actions

        // Return to the ProjectsActivity
        openProjectsActivity();
    }

    private boolean isEmailValid(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void openProjectsActivity() {
        Intent intent = new Intent(this,ProjectsActivity.class);
        startActivity(intent);
        finish();

    }

    public void findViews(){
        projectName = findViewById(R.id.editText_EditProject_ProjectName);
        projectDescription = findViewById(R.id.editText_EditProject_Description);
        projectTeam = findViewById(R.id.editText_EditProject_TeamContent);
        projectComplexity = findViewById(R.id.spinner_editProject_Complexity);
        projectSize = findViewById(R.id.spinner_editProject_Size);
        projectEmergency = findViewById(R.id.spinner_editProject_Emergency);
        backArrow = findViewById(R.id.Image_EditProject_backArrow);
        saveIcon = findViewById(R.id.Image_EditProject_saveIcon);
    }

    public void setSpinners(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, COMPLEXITIES);
        projectComplexity.setAdapter(adapter);
        projectComplexity.setSelection(0);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, SIZES);
        projectSize.setAdapter(adapter);
        projectSize.setSelection(0);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, EMERGENCIES);
        projectEmergency.setAdapter(adapter);
        projectEmergency.setSelection(0);

    }
    public void PopulateData() {
        DatabaseReference projectRef = FirebaseDatabase.getInstance().getReference("projects").child(projectID);
        projectRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Retrieve the project object from the dataSnapshot
                    Project project = dataSnapshot.getValue(Project.class);
                    if (project != null) {
                        // Set the values in the views
                        projectName.setText(project.getProjectName());
                        projectDescription.setText(project.getDescription());
                        projectTeam.setText(TextUtils.join("\n", project.getTeam()));

                        // Set the selected items in the spinners
                        setSpinnerSelection(projectComplexity, project.getComplexityString(), COMPLEXITIES);
                        setSpinnerSelection(projectSize, project.getSizeString(), SIZES);
                        setSpinnerSelection(projectEmergency, project.getEmergencyString(), EMERGENCIES);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }

    private void setSpinnerSelection(Spinner spinner, String selectedItem, String[] items) {
        if (selectedItem != null) {
            int index = Arrays.asList(items).indexOf(selectedItem);
            if (index >= 0) {
                spinner.setSelection(index);
            }
        }
    }

}