package com.example.kanplan.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.kanplan.R;
import com.example.kanplan.Utils.MySP;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    private ShapeableImageView logout;
    private ShapeableImageView projects;
    private ShapeableImageView tasks;

    private FirebaseAuth auth;

    private MaterialTextView welcomeMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        findViews();
        welcomeMessage.setText(MySP.getInstance().getName());


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();

            }
        });

        projects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enterProjectsActivity();

            }
        });
    }

    private void enterProjectsActivity() {
        Intent intent = new Intent(this,ProjectsActivity.class);
        startActivity(intent);
        finish();
    }
    public void logout(){
        auth.getInstance().signOut();
        MySP.getInstance().saveName("");
        MySP.getInstance().saveEmail("");
        Intent intent = new Intent(this , SignInActivity.class);
        startActivity(intent);
        finish();
    }

    public void findViews(){

        logout = findViewById(R.id.Image_Home_Logout);

        projects = findViewById(R.id.Image_Home_Projects);

        tasks = findViewById(R.id.Image_Home_Tasks);

        welcomeMessage = findViewById(R.id.LBL_Home_welcomeMessage);
    }

}