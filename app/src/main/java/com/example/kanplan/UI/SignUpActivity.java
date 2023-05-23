package com.example.kanplan.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.example.kanplan.Models.Name;
import com.example.kanplan.Models.User;
import com.example.kanplan.R;
import com.example.kanplan.SignalGenerator;
import com.example.kanplan.Utils.MySP;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;

    private EditText firstnameEditText;
    private EditText lastnameEditText;
    private MaterialButton signInButton;
    private MaterialButton signUpButton;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        findViews();
        auth = FirebaseAuth.getInstance();

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password))
                    SignalGenerator.getInstance().toast("You need to fill all fields",1);
                else if(password.length()<6)
                    SignalGenerator.getInstance().toast("Password length has to be more than 6 ",1);
                else if(!isEmailValid(email))
                    SignalGenerator.getInstance().toast("There is a problem with your email",1);
                else{
                    String firstname = firstnameEditText.getText().toString();
                    String lastname = lastnameEditText.getText().toString();
                    signUpUser(email,password,firstname,lastname);
                }

            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignInView();
            }
        });
    }

    private void openSignInView() {
        Intent intent = new Intent(this,SignInActivity.class);
        startActivity(intent);
        finish();
    }

    private void signUpUser(String email, String password, String firstname, String lastname) {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference usersRef = database.getReference("Users");
                    User user = new User(firstname,lastname,email,task.getResult().getUser().getUid());
                    SignalGenerator.getInstance().toast("Sign up made successfully",1);
                    usersRef.child(user.getUserID()).setValue(user);
                    MySP.getInstance().saveEmail(user.getEmail());
                    MySP.getInstance().saveName(user.getFirstname()+" "+user.getLastname());
                    openHomeScreen();

                }
                else{
                            SignalGenerator.getInstance().toast("Sign up failed",1);
                }
            }
        });
    }

    boolean isEmailValid(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public void findViews(){
        emailEditText = findViewById(R.id.editText_SignUp_email);
        passwordEditText = findViewById(R.id.editText_SignUp_password);
        firstnameEditText = findViewById(R.id.editText_SignUp_firstname);
        lastnameEditText = findViewById(R.id.editText_SignUp_lastname);
        signInButton = findViewById(R.id.Button_SignUp_signInButton);
        signUpButton = findViewById(R.id.Button_SignUp_SignUp);

    }
    private void openHomeScreen() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}