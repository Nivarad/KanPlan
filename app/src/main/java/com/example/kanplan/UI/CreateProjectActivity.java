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

import com.example.kanplan.Interfaces.UserDataCallback;
import com.example.kanplan.Models.Project;
import com.example.kanplan.Models.User;
import com.example.kanplan.R;
import com.example.kanplan.SignalGenerator;
import com.example.kanplan.Utils.MySP;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreateProjectActivity extends AppCompatActivity {


    private EditText projectName;
    private EditText projectDescription;
    private EditText projectTeam;
    private Spinner projectComplexity;
    private Spinner projectSize;
    private Spinner projectEmergency;

    private List<String> emails = new ArrayList<>();

    private FirebaseAuth auth;
    private User user;

    private final String[] COMPLEXITIES = {"Complexity","Easy","Regular","Complex","Very Complex"};
    private final String[] SIZES = {"Size","Small","Regular","Big","Very big"};
    private final String[] EMERGENCIES = {"Emergency","Low","Medium","High","ASAP"};


    private ShapeableImageView backArrow;

    private ShapeableImageView saveIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_project);

        findViews();
        setSpinners();


        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openProjectView();
            }
        });

        saveIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                    addProject(name, description, complexity, emergency, size, emails);
                }


            }
        });


    }

    private void addProject(String name, String description, String complexity, String emergency, String size, List<String> emails) {

        getUserData(new UserDataCallback() {
            @Override
            public void onCallback(User user) {
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
                Project project = new Project(name,user,description,emails,complex,siz,emerg);
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference projectsRef = database.getReference("projects");

                String key = projectsRef.push().getKey();

                // Use the key to set the new project data
                if (key != null) {
                    projectsRef.child(key).setValue(project)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    // Write was successful!
                                    SignalGenerator.getInstance().toast("Project created successfully!", 1);
                                    openProjectView();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Write failed
                                    SignalGenerator.getInstance().toast("Failed to create project", 1);
                                }
                            });
                }
            }
        });

    }

    private void getUserData(final UserDataCallback callback) {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("Users");

        Query query = usersRef.orderByChild("email").equalTo(MySP.getInstance().getEmail());

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot: dataSnapshot.getChildren()) {
                    user = userSnapshot.getValue(User.class);
                    callback.onCallback(user);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
//                Log.e(TAG, "onCancelled", databaseError.toException());
            }
        });
    }

    private void openProjectView() {
        Intent intent = new Intent(this,ProjectsActivity.class);
        startActivity(intent);
        finish();
    }

    public void findViews(){
        projectName = findViewById(R.id.editText_CreateProject_ProjectName);
        projectDescription = findViewById(R.id.editText_CreateProject_Description);
        projectTeam = findViewById(R.id.editText_CreateProject_TeamContent);
        projectComplexity = findViewById(R.id.spinner_CreateProject_Complexity);
        projectSize = findViewById(R.id.spinner_CreateProject_Size);
        projectEmergency = findViewById(R.id.spinner_CreateProject_Emergency);
        backArrow = findViewById(R.id.Image_CreateProject_backArrow);
        saveIcon = findViewById(R.id.Image_CreateProject_saveIcon);
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
    boolean isEmailValid(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}