package com.medipass.medipass;


import android.content.pm.PackageManager;
import android.location.Location;
import android.os.CountDownTimer;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

//import com.google.android.gms.location.FusedLocationProviderClient;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.tasks.OnSuccessListener;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;


public class Emergency2Activity extends AppCompatActivity {

//    private FusedLocationProviderClient client;
    TextView textView1, etaTV, ambStatus;
    ImageView ambPendingGIF;
    ImageView ambDispachGIF;
    ImageView ambGoneView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency2);
        requestPermission();

        changeAmbulanceGIF();

//        client = LocationServices.getFusedLocationProviderClient(this);
//        Button button = findViewById(R.id.getLocationBtn);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (ActivityCompat.checkSelfPermission(Emergency2Activity.this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//
//                    return;
//                }
//                client.getLastLocation().addOnSuccessListener(Emergency2Activity.this, new OnSuccessListener<Location>() {
//                    @Override
//                    public void onSuccess(Location location) {
//
//                        if (location != null) {
//                            TextView textView = findViewById(R.id.locationTV);
//                            textView.setText("Latitude: "+(float) location.getLatitude() + "\nLongitude:" +(float) location.getLongitude());
//                        }
//                    }
//                });
//            }
//        });



        textView1 = findViewById(R.id.textView1);
        Bundle intentExtras = getIntent().getExtras();
        String mediKey = intentExtras.getString("Medi_Key");


        textView1.setText("MediKey: "+mediKey);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION},1 );
    }

    public void changeAmbulanceGIF() {

        ambPendingGIF = findViewById(R.id.ambPendingGIF);
        ambDispachGIF = findViewById(R.id.ambDispatchGIF);

        new CountDownTimer(5000, 1000) { // 5000 = 5 sec

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {

                    ambPendingGIF.setVisibility(View.INVISIBLE);
                    ambDispachGIF.setVisibility(View.VISIBLE);
                    changeAmbulanceIV();
            }
        }.start();
    }

    public void changeAmbulanceIV() {

        ambPendingGIF = findViewById(R.id.ambPendingGIF);
        ambDispachGIF = findViewById(R.id.ambDispatchGIF);
        ambGoneView = findViewById(R.id.ambGoneView);
        etaTV = findViewById(R.id.etaTV);
        ambStatus = findViewById(R.id.ambStatusTV);

        new CountDownTimer(3000, 1000) { // 5000 = 5 sec

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {

                ambPendingGIF.setVisibility(View.INVISIBLE);
                ambDispachGIF.setVisibility(View.INVISIBLE);
                ambGoneView.setVisibility(View.VISIBLE);
                etaTV.setText("ETA: 10 Minutes");
                ambStatus.setText("Thank You! \n We are on our way");

            }
        }.start();
    }

}
