package com.example.kanplan.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.kanplan.Models.Comment;
import com.example.kanplan.Models.Task;
import com.example.kanplan.R;
import com.example.kanplan.SignalGenerator;
import com.example.kanplan.Utils.MySP;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class TaskActivity extends AppCompatActivity {

    private MaterialTextView taskName;
    private MaterialTextView taskDescription;
    private MaterialTextView taskComplexity;
    private MaterialTextView taskSize;
    private MaterialTextView taskEmergency;
    private MaterialTextView taskTeam;


    private String taskID;
    private String projectID;
    private ShapeableImageView backArrow;
    private EditText commentText;
    private ShapeableImageView addComment;

    private RecyclerView comments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        Intent intent =getIntent();
        if(intent !=null){
            taskID = intent.getStringExtra("taskID");
            projectID = intent.getStringExtra("projectID");
        }

        findViews();

        setTaskData();
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTasksView();
            }
        });

        addComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = commentText.getText().toString().trim();
                if (!comment.isEmpty()) {
                    addCommentToTask(comment);
                } else {
                    SignalGenerator.getInstance().toast("Comment is empty",0);
                }
            }
        });
    }

    private void addCommentToTask(String comment) {
        DatabaseReference taskRef = FirebaseDatabase.getInstance().getReference("tasks").child(taskID);
        DatabaseReference commentsRef = taskRef.child("comments");

        // Generate a unique key for the new comment
        String commentID = commentsRef.push().getKey();
        if (commentID != null) {
            Comment newComment = new Comment(MySP.getInstance().getName(),commentText.getText().toString(), comment);

            commentsRef.child(commentID).setValue(newComment, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@NonNull DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                    if (databaseError == null) {
                        SignalGenerator.getInstance().toast("Comment was added successfully",1);
                        commentText.setText(""); // Clear the comment text field
                    } else {
                        SignalGenerator.getInstance().toast("Failed to add comment",1);
                    }
                }
            });
        }
    }

    private void openTasksView() {
        Intent intent = new Intent(this,TasksActivity.class);
        intent.putExtra("projectID",this.projectID);
        startActivity(intent);
        finish();

    }

    private void setTaskData() {
        DatabaseReference taskRef = FirebaseDatabase.getInstance().getReference("tasks").child(taskID);
        taskRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Task task = dataSnapshot.getValue(Task.class);
                    if (task != null) {
                        // Update the UI with the task data
                        taskName.setText(task.getTaskName());
                        taskDescription.setText(task.getTaskDescription());
                        taskComplexity.setText(task.getComplexityString());
                        taskEmergency.setText(task.getEmergencyString());
                        taskSize.setText(task.getSizeString());
                        List<String> team = task.getAssigned();
                        String members ="";
                        for(int i=0;i<team.size();i++){
                            members+=team.get(i)+"\n";
                        }
                        taskTeam.setText(members);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
            }
        });

    }
    private void populateComments() {

    }

    private void findViews() {
        taskName = findViewById(R.id.LBL_Task_taskName);
        taskDescription = findViewById(R.id.LBL_TaskView_taskDescription);
        taskComplexity = findViewById(R.id.LBL_TaskView_Complexity);
        taskEmergency = findViewById(R.id.LBL_TaskView_Emergency);
        taskSize = findViewById(R.id.LBL_TaskView_Size);
        taskTeam = findViewById(R.id.LBL_TaskView_Team);
        backArrow = findViewById(R.id.Image_Task_backArrow);
        addComment = findViewById(R.id.addComment);
        commentText = findViewById(R.id.commentText);
        comments = findViewById(R.id.taskComments);


    }
}