package com.example.kanplan.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.kanplan.Data.DataManager.Gender;
import com.example.kanplan.R;
import com.example.kanplan.SignalGenerator;
import com.example.kanplan.Utils.DrawerBaseActivity;
import com.example.kanplan.Utils.MySP;
import com.example.kanplan.databinding.ActivityEditTaskBinding;
import com.example.kanplan.databinding.ActivityPersonalizeBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.materialswitch.MaterialSwitch;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PersonalizeActivity extends DrawerBaseActivity {

    ActivityPersonalizeBinding activityPersonalizeBinding;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText firstnameEditText;
    private EditText lastnameEditText;
    private SwitchCompat genderSwitch;

    private MaterialButton updateButton;

    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityPersonalizeBinding = ActivityPersonalizeBinding.inflate(getLayoutInflater());
        setContentView(activityPersonalizeBinding.getRoot());
        allocateActivityTitle("Personalize");

        findViews();
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!firstnameEditText.getText().toString().equals(""))
                    updateFirstname();
                if(!emailEditText.getText().toString().equals(""))
                    updateEmail();
                if(!passwordEditText.getText().toString().equals(""))
                    updatePassword();
                if(!lastnameEditText.getText().toString().equals(""))
                    updateLastName();
                updateGender();
            }
        });
    }

    private void updateEmail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String newEmail = emailEditText.getText().toString();
        user.updateEmail(newEmail)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            SignalGenerator.getInstance().toast("Information updated",0);
                        }
                    }
                });


        if (user != null) {
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users")
                    .child(user.getUid());

            userRef.child("email").setValue(newEmail)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            SignalGenerator.getInstance().toast("Information updated",0);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            SignalGenerator.getInstance().toast("Information failed to update",0);
                        }
                    });
        }
    }

    private void updateLastName() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        String lastname = lastnameEditText.getText().toString();

        if(user!=null){
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
            userRef.child("lastname").setValue(lastname);

        }
    }

    private void updateFirstname() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());

        String newFirstname = firstnameEditText.getText().toString();

        userRef.child("firstname").setValue(newFirstname)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        SignalGenerator.getInstance().toast("Information updated",0);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        SignalGenerator.getInstance().toast("Failed to update first name", 0);
                    }
                });
    }

    private void updatePassword() {
        String newPassword = passwordEditText.getText().toString();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.updatePassword(newPassword)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            SignalGenerator.getInstance().toast("Password changed",1);
                        }
                    }
                });
    }

    private void updateGender() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        Gender gender = genderSwitch.isChecked() ? Gender.FEMALE : Gender.MALE;


        if(user!=null){
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
            userRef.child("gender").setValue(gender);
            SignalGenerator.getInstance().toast("Information updated",0);

        }
    }

    public void findViews(){
        emailEditText = findViewById(R.id.editText_Personalize_email);
        passwordEditText = findViewById(R.id.editText_Personalize_password);
        firstnameEditText = findViewById(R.id.editText_Personalize_firstname);
        lastnameEditText = findViewById(R.id.editText_Personalize_lastname);
        updateButton = findViewById(R.id.Button_Personalize_Update);
        genderSwitch = findViewById(R.id.SwitchButton_Personalize_Gender);

    }
}