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
import com.example.kanplan.Utils.DrawerBaseActivity;
import com.example.kanplan.Utils.MySP;
import com.example.kanplan.databinding.ActivityEditProjectBinding;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.kanplan.Data.DataManager.Emergency;
import com.example.kanplan.Data.DataManager.Size;
import com.example.kanplan.Data.DataManager.Complexity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Activity for editing a project.
 */
public class EditProjectActivity extends DrawerBaseActivity {

    ActivityEditProjectBinding activityEditProjectBinding;
    private EditText projectName;
    private EditText projectDescription;
    private EditText projectTeam;
    private Spinner projectComplexity;
    private Spinner projectSize;
    private Spinner projectEmergency;

    private List<String> emails = new ArrayList<>();

    private User user;

    private final String[] COMPLEXITIES = {"Complexity", "Easy", "Regular", "Complex", "Very Complex"};
    private final String[] SIZES = {"Size", "Small", "Regular", "Big", "Very big"};
    private final String[] EMERGENCIES = {"Emergency", "Low", "Medium", "High", "ASAP"};

    private ShapeableImageView backArrow;
    private ShapeableImageView saveIcon;
    private String projectID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityEditProjectBinding = ActivityEditProjectBinding.inflate(getLayoutInflater());
        setContentView(activityEditProjectBinding.getRoot());
        allocateActivityTitle("Edit Project");

        Intent intent = getIntent();
        if (intent != null) {
            projectID = intent.getStringExtra("projectID");
        }

        findViews();
        setSpinners();
        populateData();

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

    /**
     * Populates the data of the project from the database.
     * This function retrieves the project object from the database and sets the values in the views accordingly.
     * It also sets the selected items in the spinners based on the project's data.
     */
    private void populateData() {
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

    /**
     * Saves the updated project data.
     */
    private void saveUpdatedData() {
        String name = projectName.getText().toString();
        String description = projectDescription.getText().toString();
        String complexity = projectComplexity.getSelectedItem().toString();
        String emergency = projectEmergency.getSelectedItem().toString();
        String size = projectSize.getSelectedItem().toString();
        String teamString = projectTeam.getText().toString();
        emails.clear(); // Clear the list before adding emails

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(description) || complexity.equals("Complexity") ||
                emergency.equals("Emergency") || size.equals("Size")) {
            SignalGenerator.getInstance().toast("You need to fill all fields", 1);
        } else {
            if (!teamString.isEmpty()) {
                String[] emailArray = teamString.split("[ \n]+");
                for (String email : emailArray) {
                    if (isEmailValid(email)) {
                        emails.add(email);
                    } else {
                        SignalGenerator.getInstance().toast("One of your emails is not formatted correctly", 1);
                        return;
                    }
                }
            }
            saveProject(name, description, complexity, emergency, size, emails);
        }
    }

    /**
     * Saves the project with the provided information.
     *
     * @param name        The name of the project.
     * @param description The description of the project.
     * @param complexity  The complexity of the project.
     * @param emergency   The emergency level of the project.
     * @param size        The size of the project.
     * @param emails      The list of team member emails for the project.
     */
    private void saveProject(String name, String description, String complexity, String emergency, String size, List<String> emails) {
        Complexity complex = null;
        Size siz = null;
        Emergency emerg = null;

        switch (complexity) {
            case "Easy":
                complex = Complexity.EASY;
                break;
            case "Regular":
                complex = Complexity.REGULAR;
                break;
            case "Complex":
                complex = Complexity.COMPLEX;
                break;
            case "Very Complex":
                complex = Complexity.VERY_COMPLEX;
                break;
        }

        switch (size) {
            case "Small":
                siz = Size.SMALL;
                break;
            case "Regular":
                siz = Size.REGULAR;
                break;
            case "Big":
                siz = Size.BIG;
                break;
            case "Very big":
                siz = Size.VERY_BIG;
                break;
        }

        switch (emergency) {
            case "Low":
                emerg = Emergency.LOW;
                break;
            case "Medium":
                emerg = Emergency.MEDIUM;
                break;
            case "High":
                emerg = Emergency.HIGH;
                break;
            case "ASAP":
                emerg = Emergency.ASAP;
                break;
        }


        if (!emails.contains(MySP.getInstance().getEmail())) {
            emails.add(MySP.getInstance().getEmail());

            Project project = new Project(name, user, description, emails, complex, siz, emerg, "");

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
            SignalGenerator.getInstance().toast("Project updated successfully", 1);

            // Show a success message or perform any other desired actions

            // Return to the ProjectsActivity
            openProjectsActivity();
        }
    }

    /**
     * Checks if an email address is valid.
     *
     * @param email The email address to check.
     * @return True if the email is valid, false otherwise.
     */
    private boolean isEmailValid(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    /**
     * Opens the ProjectsActivity.
     * This function creates an intent to start the ProjectsActivity and finishes the current activity.
     */
    private void openProjectsActivity() {
        Intent intent = new Intent(this, ProjectsActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Finds and initializes the views used in the activity layout.
     */
    public void findViews() {
        projectName = findViewById(R.id.editText_EditProject_ProjectName);
        projectDescription = findViewById(R.id.editText_EditProject_Description);
        projectTeam = findViewById(R.id.editText_EditProject_TeamContent);
        projectComplexity = findViewById(R.id.spinner_editProject_Complexity);
        projectSize = findViewById(R.id.spinner_editProject_Size);
        projectEmergency = findViewById(R.id.spinner_editProject_Emergency);
        backArrow = findViewById(R.id.Image_EditProject_backArrow);
        saveIcon = findViewById(R.id.Image_EditProject_saveIcon);
    }

    /**
     * Sets up the spinners with their respective values.
     * This function creates an ArrayAdapter for each spinner and sets it as the adapter for the spinner.
     * It also sets the default selection for each spinner.
     */
    public void setSpinners() {
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
    /**
     * Sets the selected item of a spinner based on the selected value.
     *
     * @param spinner      The spinner to set the selection on.
     * @param selectedItem The selected value to set.
     * @param items        The array of items in the spinner.
     */
    private void setSpinnerSelection(Spinner spinner, String selectedItem, String[] items) {
        if (selectedItem != null) {
            int index = Arrays.asList(items).indexOf(selectedItem);
            if (index >= 0) {
                spinner.setSelection(index);
            }
        }
    }


}