package com.example.kanplan.UI;

import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.kanplan.R;
import com.example.kanplan.SignalGenerator;
import com.example.kanplan.Utils.DrawerBaseActivity;
import com.example.kanplan.Utils.MySP;
import com.example.kanplan.databinding.ActivityHomeBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends DrawerBaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    ActivityHomeBinding activityHomeBinding;

    private CardView logout;
    private CardView projects;
    private CardView tasks;

    private CardView share;

    private CardView aboutMe;

    private CardView personalize;

    private FirebaseAuth auth;

    private MaterialTextView welcomeMessage;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityHomeBinding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(activityHomeBinding.getRoot());
        allocateActivityTitle("Home");

        findViews();
        settingListeners();

    }

    private void openPersonalizePage(){
        Intent intent =new Intent(this,PersonalizeActivity.class);
        startActivity(intent);
        finish();
    }

    private void enterAllTasksActivity() {
        Intent intent = new Intent(this,AllTasksActivity.class);
        startActivity(intent);
        finish();
    }

    private void shareApplication() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String shareBody = "Have you heard about KanPlan ?! Download it now ";
        String shareSub = "KanPlan Project Management";
        intent.putExtra(intent.EXTRA_SUBJECT,shareSub);
        intent.putExtra(Intent.EXTRA_TEXT,shareBody);
        startActivity(Intent.createChooser(intent,"Share using"));
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

        share =findViewById(R.id.Image_Home_Share);

        personalize=findViewById(R.id.Image_Home_Personalize);

        aboutMe = findViewById(R.id.Image_Home_About_Me);


    }
    private void settingListeners(){
        projects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enterProjectsActivity();

            }
        });

        share.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                shareApplication();
            }
        });

        tasks.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                enterAllTasksActivity();
            }
        });
        aboutMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://ardos.netlify.app"));
                startActivity(browserIntent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
                SignalGenerator.getInstance().toast("Logged out",0);
            }
        });

        personalize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPersonalizePage();
            }
        });
    }


}