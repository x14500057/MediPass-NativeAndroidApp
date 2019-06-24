package com.medipass.medipass;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    ProgressBar progressBar;
    EditText editTextEmail, editTextPassword;
    private FirebaseAuth mAuth;
    ImageView logoIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editTextEmail = (EditText) findViewById(R.id.signUp_emailET);
        editTextPassword = (EditText) findViewById(R.id.signUp_passwordET);
        progressBar = (ProgressBar) findViewById(R.id.signUp_progressBar);
        logoIV = findViewById(R.id.signUp_logoIV);

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.signUp_signUpBtn).setOnClickListener(this);
        findViewById(R.id.signUp_loginTV).setOnClickListener(this);
        findViewById(R.id.signUp_emergencyBtn).setOnClickListener(this);
    }

    private void registerUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(email.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextPassword.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please enter  valid email");
            editTextEmail.requestFocus();
            return;
        }

        if(password.isEmpty()) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length()<6) {
            editTextPassword.setError("Minimum length of password should be 6");
            editTextPassword.requestFocus();
            return;
        }

        logoIV.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                logoIV.setVisibility(View.VISIBLE);
                if (task.isSuccessful()) {
                    //Sign in success, start register activity
                    FirebaseUser user = mAuth.getCurrentUser();

                    //Get user email and uid from auth
                    String email = user.getEmail();
                    String uid = user.getUid();

                    //When user is registered store the user info in the firebase realtime database too.

                    //Using hashMap
                    HashMap<Object, String> hashMap = new HashMap<>();
                    //Put info into hashMap

                    hashMap.put("email", email);
                    hashMap.put("uid", uid);
                    hashMap.put("fname", "");  //added later (e.g. edit profile).
                    hashMap.put("sname", "");  //added later (e.g. edit profile).
                    hashMap.put("phone", "");  //added later (e.g. edit profile).
                    hashMap.put("image", "");  //added later (e.g. edit profile).

                    //Firebase database instance
                    FirebaseDatabase database = FirebaseDatabase.getInstance();

                    //path to store user data named "Users"
                    DatabaseReference reference = database.getReference("Users");

                    //put data within hashmap in database
                    reference.child(uid).setValue(hashMap);


                    finish();
                    startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                }
                else {

                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), "Email is already registered", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signUp_signUpBtn:
                registerUser();
                break;

            case R.id.signUp_loginTV:
                finish();
                startActivity(new Intent(this, LoginActivity.class));
                break;

            case R.id.signUp_emergencyBtn:
                finish();
                startActivity(new Intent(this, RegistrationActivity.class));

                break;
        }
    }
}
