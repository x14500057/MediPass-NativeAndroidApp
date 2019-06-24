package com.medipass.medipass;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EmailVerificationActivity extends AppCompatActivity {

    private Button sendEmailBtn;
    private TextView emailStatusTV;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_verification);

        //init
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        sendEmailBtn = (Button) findViewById(R.id.sendEmailBtn);
        emailStatusTV = (TextView) findViewById(R.id.emailStatusTV);

        if (user.isEmailVerified()) {
            emailStatusTV.setText("Email Verified");
        }

        sendEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEmailVerified();
            }
        });

    }

    private void checkEmailVerified () {

        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getApplicationContext(), "Email has been Sent", Toast.LENGTH_SHORT).show();
                emailStatusTV.setText("Thank you \n a verification email has been sent to your address");
                sendEmailBtn.setText("Send email Again");
            }
        });
    }
}
