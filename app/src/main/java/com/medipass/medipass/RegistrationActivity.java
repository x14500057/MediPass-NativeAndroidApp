package com.medipass.medipass;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kofigyan.stateprogressbar.StateProgressBar;
import com.kofigyan.stateprogressbar.components.StateItem;
import com.kofigyan.stateprogressbar.listeners.OnStateItemClickListener;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class RegistrationActivity extends AppCompatActivity {


    private RelativeLayout accountDetails, addressDetails, healthDetails, authDetails, confirmationDetails;
    private AppCompatEditText mDateET, fNameET, sNameET, mNameET, address1ET, address2ET, townET, countyET, countryET, emailET, passowrdET, cPasswordET;
    private TextView verifyEmailTV;
    private DatePickerDialog.OnDateSetListener mDateListener;
    private android.widget.ImageView ImageView;
    private ImageView homeIV;
    private TextToSpeech tts1;
    private Button nextBtn;
    private int result;
    private String introTTS;
    StateProgressBar stateProgressBar;
    private String descriptionData_STB[] = {"Profile", "Address", "Health", "Account", "Finish"};
    private int flag = 0;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private Boolean success;
    private RadioGroup medCard_rg, smoker_rg;
    private RadioButton medCardNo_rb, medCardYes_rb, smokerNo_rb, smokerYes_rb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        introTTS = "Hello. and welcome to Mehdee Pass. My name is sophie. To make things easier. I will be assisting throughout your application. . Lets start by filling out your name and date of birth";
        tts1 = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    result = tts1.setLanguage(Locale.UK);
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "This Language is not supported");
                    }
                    speak(introTTS);
                } else {
                    Log.e("TTS", "Initialization Failed");
                }
            }
        });

        //create local variables of components within layout.

        mAuth = FirebaseAuth.getInstance();
        ImageView = (ImageView) findViewById(R.id.circleImage);
        homeIV = (ImageView) findViewById(R.id.home_ic);
        fNameET = (AppCompatEditText) findViewById(R.id.fNameET);
        mNameET = (AppCompatEditText) findViewById(R.id.mNameET);
        sNameET = (AppCompatEditText) findViewById(R.id.sNameET);
        mDateET = (AppCompatEditText) findViewById(R.id.dobDatePicker);
        address1ET = (AppCompatEditText) findViewById(R.id.address1ET);
        address2ET = (AppCompatEditText) findViewById(R.id.address2ET);
        townET = (AppCompatEditText) findViewById(R.id.townET);
        countyET = (AppCompatEditText) findViewById(R.id.countyET);
        countryET = (AppCompatEditText) findViewById(R.id.countryET);
        emailET = (AppCompatEditText) findViewById(R.id.emailET);
        passowrdET = (AppCompatEditText) findViewById(R.id.passwordET);
        cPasswordET = (AppCompatEditText) findViewById(R.id.cPasswordET);
        verifyEmailTV = (TextView) findViewById(R.id.verifyEmailTV);
        accountDetails = (RelativeLayout) findViewById(R.id.accountContent);
        addressDetails = (RelativeLayout) findViewById(R.id.addressContent);
        healthDetails = (RelativeLayout) findViewById(R.id.healthContent);
        authDetails = (RelativeLayout) findViewById(R.id.authContent);
        confirmationDetails = (RelativeLayout) findViewById(R.id.confirmationContent);
        nextBtn = (Button) findViewById(R.id.reg_next1Btn);
        stateProgressBar = (StateProgressBar) findViewById(R.id.reg_statusBar);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        medCard_rg = (RadioGroup) findViewById(R.id.medCardRG);
        smoker_rg = (RadioGroup) findViewById(R.id.smokerRG);
        medCardNo_rb = (RadioButton) findViewById(R.id.medCardNo);
        medCardYes_rb = (RadioButton) findViewById(R.id.medCardYes);
        smokerNo_rb = (RadioButton) findViewById(R.id.smokerNo);
        smokerYes_rb = (RadioButton) findViewById(R.id.smokerYes);



        stateProgressBar.setStateDescriptionData(descriptionData_STB);

        //load animation & start animation on doctor image.
        Animation scale = AnimationUtils.loadAnimation(this, R.anim.scale);
        ImageView.startAnimation(scale);

        smokerYes_rb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smokerYes_rb.setTextColor(getResources().getColor(R.color.colorWhite));
                smokerNo_rb.setTextColor(getResources().getColor(R.color.colorLightGrey));
            }
        });

        smokerNo_rb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smokerNo_rb.setTextColor(getResources().getColor(R.color.colorWhite));
                smokerYes_rb.setTextColor(getResources().getColor(R.color.colorLightGrey));
            }
        });

        medCardYes_rb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                medCardYes_rb.setTextColor(getResources().getColor(R.color.colorWhite));
                medCardNo_rb.setTextColor(getResources().getColor(R.color.colorLightGrey));
            }
        });

        medCardNo_rb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                medCardNo_rb.setTextColor(getResources().getColor(R.color.colorWhite));
                medCardYes_rb.setTextColor(getResources().getColor(R.color.colorLightGrey));
            }
        });

//        medCard_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                if (smokerNo_rb.isChecked()) {
//                    smokerNo_rb.setTextColor(getResources().getColor(R.color.colorWhite));
//                    smokerYes_rb.setTextColor(getResources().getColor(R.color.colorLightGrey));
//                }
//
//                if (smokerYes_rb.isChecked()) {
//                    smokerYes_rb.setTextColor(getResources().getColor(R.color.colorWhite));
//                    smokerNo_rb.setTextColor(getResources().getColor(R.color.colorLightGrey));
//                }
//            }
//        });

        homeIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //Account State
                if (flag == 0) {

                    if (validateAccountInfo()) {
                        Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
                        stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
                        flag = 1;
                        changeLayout(addressDetails);
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "attempt failed", Toast.LENGTH_SHORT).show();
                    }
                }

                //Address State
                else if(flag == 1) {

                    if (validateAddressInfo()) {
                        Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
                        changeLayout(healthDetails);
                        stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
                        flag = 2;
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "attempt failed", Toast.LENGTH_SHORT).show();
                    }
                }

                //Health State
                else if(flag == 2) {

                    speak("Great Let's fill out your security details");
                    changeLayout(authDetails);
                    stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.FOUR);
                    flag = 3;

                    return;
                }

                //Security State
                else if (flag == 3) {

                    speak("Please enter your email address. and your password");

                    if (validateSecurityInfo()) {
                        Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
                        changeLayout(confirmationDetails);
                        stateProgressBar.setVisibility(View.GONE);
                        nextBtn.setText("Register");
                        flag = 4;
                    } else {
                        Toast.makeText(getApplicationContext(), "failed attempt", Toast.LENGTH_SHORT).show();
                    }
                }

                //Confirmation State
                else if (flag == 4) {
                    registerUser();

                }
            }
        });

        stateProgressBar.setOnStateItemClickListener(new OnStateItemClickListener() {
            @Override
            public void onStateItemClick(StateProgressBar stateProgressBar, StateItem stateItem, int stateNumber, boolean isCurrentState) {


                if (stateNumber == 1) {

                    if (stateNumber-1 <= flag) {
                        Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                        changeLayout(accountDetails);
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                    }

                }

                else if (stateNumber == 2) {

                    if (stateNumber-1 <= flag) {
                        Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                        changeLayout(addressDetails);
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                    }

                }

                else if (stateNumber == 3) {


                    if (stateNumber-1 <= flag) {
                        Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                        changeLayout(healthDetails);
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                    }

                }

                else if (stateNumber == 4) {

                    if (stateNumber-1 <= flag) {
                        Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                        changeLayout(authDetails);

                        nextBtn.setText("Register");
                        flag = 5;
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        mDateET.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                        Calendar calendar = Calendar.getInstance();
                        int year = calendar.get(Calendar.YEAR);
                        int month = calendar.get(Calendar.MONTH);
                        int day = calendar.get(Calendar.DAY_OF_MONTH);

                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN :

                                DatePickerDialog dialog = new DatePickerDialog(RegistrationActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateListener, year, month, day);
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                dialog.show();

                                break;

                            case MotionEvent.ACTION_UP :
                                break;
                        }

                        return true;

            }
        });

        mDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = dayOfMonth+"/"+month+"/"+year;
                mDateET.setText(date);
            }
        };
    }


    private void speak(String text) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tts1.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        }
        else {
            tts1.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }

    }

    public void changeLayout(RelativeLayout layout) {

        accountDetails.setVisibility(View.INVISIBLE);
        addressDetails.setVisibility(View.INVISIBLE);
        healthDetails.setVisibility(View.INVISIBLE);
        authDetails.setVisibility(View.INVISIBLE);
        confirmationDetails.setVisibility(View.INVISIBLE);

        RelativeLayout rLayout = layout;
        rLayout.setVisibility(View.VISIBLE);

        return;
    }

    @Override
    public void onDestroy() {
        if (tts1 != null) {
            tts1.stop();
            tts1.shutdown();
        }
        super.onDestroy();
    }

    private void checkEmailVerified () {

        //Sign in success, start register activity
        final FirebaseUser user = mAuth.getCurrentUser();

        if (user.isEmailVerified()) {
            verifyEmailTV.setText("Email Verified!");
        }
        else {
            verifyEmailTV.setText("Email Not Verified!... Click to verify");
            verifyEmailTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(getApplicationContext(), "Email has been Sent", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });

        }
    }

    private boolean validateAddressInfo() {

        success = true;
        String address1 = address1ET.getText().toString();
        String address2 = address2ET.getText().toString();
        String town = townET.getText().toString();
        String county = countyET.getText().toString();
        String country = countryET.getText().toString();

        if (address1.isEmpty()) {
            address1ET.setError("address 1 is required!");
            address1ET.requestFocus();

            success = false;
        }

        if (address2.isEmpty()) {
            address2ET.setError("address 2 is required!");
            address2ET.requestFocus();

            success = false;
        }

        if (town.isEmpty()) {
            townET.setError("town is required!");
            townET.requestFocus();

            success = false;
        }

        if (county.isEmpty()) {
            countyET.setError("County is required!");
            countyET.requestFocus();

            success = false;
        }

        if (country.isEmpty()) {
            countryET.setError("Country is required!");
            countryET.requestFocus();

            success = false;
        }

        return success;

    }

    private boolean validateAccountInfo() {

        success = true;

        String fName = fNameET.getText().toString();
        String mName = mNameET.getText().toString();
        String sName = sNameET.getText().toString();
        String dob = mDateET.getText().toString();

        if (fName.isEmpty()) {
            fNameET.setError("First name is required!");
            fNameET.requestFocus();

            success = false;
        }

        if (mName.isEmpty()) {
            mNameET.setError("Middle name is required!");
            mNameET.requestFocus();

            success = false;
        }

        if (sName.isEmpty()) {
            sNameET.setError("Surname is required!");
            sNameET.requestFocus();

            success = false;
        }

        if (dob.isEmpty()) {
            mDateET.setError("Date of Birth is required!");
            mDateET.requestFocus();

            success = false;
        }

        speak("That's Great "+fName+". Now let's enter your current address.");
        return success;

    }

    private boolean validateSecurityInfo() {

        success = true;
        String email = emailET.getText().toString();
        String password = passowrdET.getText().toString().trim();
        String cPassword = cPasswordET.getText().toString().trim();

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailET.setError("Please enter  valid email");
            emailET.requestFocus();
            success = false;
        }

        else if(password.length()<6) {
            passowrdET.setText("Password must be longer than 6 characters");
            passowrdET.requestFocus();
            success = false;
        }

        else if(!password.equals(cPassword)) {
            passowrdET.setError("Please make sure passwords match");
            passowrdET.requestFocus();
            success = false;
        }

            return success;


    }

    private void registerUser() {

        final String fName = fNameET.getText().toString().trim();
        final String mName = mNameET.getText().toString().trim();
        final String sName = sNameET.getText().toString().trim();
        final String dob = mDateET.getText().toString().trim();
        final String address1 = address1ET.getText().toString().trim();
        final String address2 = address2ET.getText().toString().trim();
        final String town = townET.getText().toString().trim();
        final String county = countyET.getText().toString().trim();
        final String country = countryET.getText().toString().trim();
        final String email = emailET.getText().toString().trim();
        String password = passowrdET.getText().toString().trim();

        changeLayout(confirmationDetails);
        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {


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

                    hashMap.put("fname", fName);
                    hashMap.put("mname", mName);
                    hashMap.put("sname", sName);
                    hashMap.put("dob", dob);
                    hashMap.put("address1", address1);
                    hashMap.put("address2", address2);
                    hashMap.put("town", town);
                    hashMap.put("county", county);
                    hashMap.put("country", country);
                    hashMap.put("smoker", "");
                    hashMap.put("mediCard", "");
                    hashMap.put("email", email);
                    hashMap.put("uid", uid);

                    //Firebase database instance
                    FirebaseDatabase database = FirebaseDatabase.getInstance();

                    //path to store user data named "Users"
                    DatabaseReference reference = database.getReference("Users");

                    //put data within hashmap in database
                    reference.child(uid).setValue(hashMap);

                    progressBar.setVisibility(View.GONE);
                    nextBtn.setVisibility(View.GONE);
                    stateProgressBar.setVisibility(View.GONE);
                    checkEmailVerified();

                }
                else {

                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        changeLayout(authDetails);
                        emailET.setError("Email is already registered");
                        emailET.requestFocus();
                        Toast.makeText(getApplicationContext(), "Email is already registered", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


}
