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

import com.example.kanplan.Models.Task;
import com.example.kanplan.Models.User;
import com.example.kanplan.R;
import com.example.kanplan.SignalGenerator;
import com.example.kanplan.Utils.MySP;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.kanplan.Data.DataManager.Emergency;
import com.example.kanplan.Data.DataManager.Size;
import com.example.kanplan.Data.DataManager.Complexity;
import com.example.kanplan.Data.DataManager.Status;
import java.util.ArrayList;
import java.util.List;

public class CreateTaskActivity extends AppCompatActivity {

    private ShapeableImageView backArrow;
    private ShapeableImageView saveIcon;
    private EditText taskName;
    private EditText taskDescription;
    private EditText taskTeam;
    private Spinner taskComplexity;
    private Spinner taskSize;
    private Spinner taskEmergency;
    private Spinner taskStatus;

    private List<String> emails = new ArrayList<>();

    private User user;
    private String projectID;

    private final String[] COMPLEXITIES = {"Complexity","Easy","Regular","Complex","Very Complex"};
    private final String[] SIZES = {"Size","Small","Regular","Big","Very big"};
    private final String[] EMERGENCIES = {"Emergency","Low","Medium","High","ASAP"};

    private final String[] STATUSES = {"Status","Backlog","Doing","Done"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);


        Intent intent = getIntent();
        if (intent != null) {
            projectID = intent.getStringExtra("projectID");
        }
        findViews();
        setSpinners();



        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTasksView();
            }
        });

        saveIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = taskName.getText().toString();
                String description = taskDescription.getText().toString();
                String complexity = taskComplexity.getSelectedItem().toString();
                String emergency = taskEmergency.getSelectedItem().toString();
                String status = taskStatus.getSelectedItem().toString();
                String size = taskSize.getSelectedItem().toString();
                String teamString = taskTeam.getText().toString();
                emails.clear(); // Clear the list before adding emails

                if(TextUtils.isEmpty(name) || TextUtils.isEmpty(description) ||complexity.equals("Complexity") || emergency.equals("Emergency") || size.equals("Size") ||status.equals("Status"))
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
                    addTask(name, description, complexity, emergency, size,status, emails);
                }


            }
        });

    }

    private void addTask(String name, String description, String complexity, String emergency, String size, String status, List<String> emails) {
        Complexity complex = null;
        Size siz=null;
        Emergency emerg=null;
        Status stat = null;
        switch(complexity){
            case "Easy":
                complex= Complexity.EASY;
                break;
            case "Regular":
                complex=Complexity.REGULAR;
                break;
            case "Complex":
                complex=Complexity.COMPLEX;
                break;
            case "Very Complex":
                complex=Complexity.VERY_COMPLEX;
        }
        switch(size){
            case "Small":
                siz= Size.SMALL;
                break;
            case "Regular":
                siz=Size.REGULAR;
                break;
            case "Big":
                siz=Size.BIG;
                break;
            case "Very big":
                siz=Size.VERY_BIG;
        }
        switch(emergency){
            case "Low":
                emerg= Emergency.LOW;
                break;
            case "Medium":
                emerg=Emergency.MEDIUM;
                break;
            case "High":
                emerg=Emergency.HIGH;
                break;
            case "ASAP":
                emerg=Emergency.ASAP;
        }
        switch(status){
            case "Backlog":
                stat= Status.BACKLOG;
                break;
            case "Doing":
                stat=Status.DOING;
                break;
            case "Done":
                stat=Status.DONE;
                break;
        }
//        if(!emails.contains(MySP.getInstance().getEmail()))
//            emails.add(MySP.getInstance().getEmail());
        Task task = new Task(name,description,emails,complex,siz,emerg,stat,projectID);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference taskRef = database.getReference("tasks");

        String key = taskRef.push().getKey();


        // Use the key to set the new project data
        if (key != null) {
            task.setTaskID(key);
            taskRef.child(key).setValue(task)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Write was successful!
                            SignalGenerator.getInstance().toast("Task created successfully!", 1);
                            openTasksView();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Write failed
                            SignalGenerator.getInstance().toast("Failed to create task", 1);
                        }
                    });
        }
    }

    private void openTasksView() {
        Intent intent = new Intent(this,TasksActivity.class);
        intent.putExtra("projectID",TasksActivity.projectID);
        startActivity(intent);
        finish();
    }

    private void setSpinners() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, COMPLEXITIES);
        taskComplexity.setAdapter(adapter);
        taskComplexity.setSelection(0);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, SIZES);
        taskSize.setAdapter(adapter);
        taskSize.setSelection(0);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, EMERGENCIES);
        taskEmergency.setAdapter(adapter);
        taskEmergency.setSelection(0);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, STATUSES);
        taskStatus.setAdapter(adapter);
        taskStatus.setSelection(0);
    }
    boolean isEmailValid(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    private void findViews() {
        taskName = findViewById(R.id.LBL_CreateTask_taskName);
        taskDescription = findViewById(R.id.editText_CreateTask_Description);
        taskTeam = findViewById(R.id.editText_CreateTask_AssignTeam);
        taskComplexity = findViewById(R.id.spinner_CreateTask_Complexity);
        taskSize = findViewById(R.id.spinner_CreateTask_Size);
        taskEmergency = findViewById(R.id.spinner_CreateTask_Emergency);
        backArrow = findViewById(R.id.Image_CreateTask_backArrow);
        saveIcon = findViewById(R.id.Image_CreateTask_saveIcon);
        taskStatus = findViewById(R.id.spinner_CreateTask_Status);
    }
}