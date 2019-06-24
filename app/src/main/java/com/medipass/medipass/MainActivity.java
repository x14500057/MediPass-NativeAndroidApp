package com.medipass.medipass;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;


import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Firebase Auth
    FirebaseAuth mAuth;

    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Actionbar and it's title
        actionBar = getSupportActionBar();
        actionBar.setTitle("Profile");

        //init
        mAuth = FirebaseAuth.getInstance();

        //bottom navigation
        BottomNavigationView navigationView = findViewById(R.id.navigationBottom);
        navigationView.setOnNavigationItemSelectedListener(selectedListener);



        actionBar.setTitle("Profile");
        ProfileFragment profileFragment = new ProfileFragment();
        FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
        ft1.replace(R.id.content, profileFragment, "" );
        ft1.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuLogout:
                mAuth.signOut();
                finish();
                startActivity(new Intent(this, LoginActivity.class));

                break;
        }

        return true;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener selectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            //handle item clicks
            switch (menuItem.getItemId()) {
                case R.id.nav_medi_records:
                    // medi records fragment transaction
                    actionBar.setTitle("Medical Records");
                    RecordsFragment recordsFragment = new RecordsFragment();
                    FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
                    ft1.replace(R.id.content, recordsFragment, "" );
                    ft1.commit();
                    return true;

                case R.id.nav_profile:
                    // profile fragment transaction
                    actionBar.setTitle("Profile");
                    ProfileFragment profileFragment = new ProfileFragment();
                    FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
                    ft2.replace(R.id.content, profileFragment, "" );
                    ft2.commit();
                    return true;

                case R.id.nav_prescriptions:
                    // prescriptions fragment transaction
                    actionBar.setTitle("Prescriptions");
                    PrescriptionFragment prescFragment = new PrescriptionFragment();
                    FragmentTransaction ft3 = getSupportFragmentManager().beginTransaction();
                    ft3.replace(R.id.content, prescFragment, "" );
                    ft3.commit();
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
        else if (mAuth.getCurrentUser() != null){
            if (mAuth.getCurrentUser().isEmailVerified()) {
                Toast.makeText(getApplicationContext(), "Email verified", Toast.LENGTH_SHORT).show();
            }
            else {
                finish();
                Toast.makeText(getApplicationContext(), "Email not verified", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, EmailVerificationActivity.class));
            }
        }

        return;
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_logoutBtn:

                mAuth.signOut();
                finish();
                startActivity(new Intent(this, LoginActivity.class));

                break;
        }
    }
}

