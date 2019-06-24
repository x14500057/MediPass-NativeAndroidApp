package com.medipass.medipass;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Parcelable;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.UnsupportedEncodingException;
import java.util.Locale;
import pl.droidsonroids.gif.GifImageView;

public class Emergency1Activity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private TextToSpeech tts1;
    private int result;
    private String introTTS;
    String tagContent = null;
    String tagContentSpace = null;
    NfcAdapter nfcAdapter;
    GifImageView successTick, scanIllustrationV;
    ImageView successTN;
    TextView nfcStatusTV, status, medikeyTV;
    Animation slideDown, slideUp;
    Boolean nfcStatus;
    Boolean nfclastStaus = false;
    int flag = 0;
    RelativeLayout myLayout, scannerRL;
    AnimationDrawable animationDrawable;
    Button proceedBtn;
    private Double latitude, longitude;

    private FusedLocationProviderClient mFusedLocationProviderClent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emergency_scan_nfc);




        introTTS = "Testing";
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

        mFusedLocationProviderClent = LocationServices.getFusedLocationProviderClient(this);

        proceedBtn = (Button) findViewById(R.id.proceedBtn);
        proceedBtn.setEnabled(false);

        proceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Emergency1Activity.this, MapBoxExample.class);
                startActivity(intent);
                finish();
//                Toast.makeText(getApplicationContext(), "clicked", Toast.LENGTH_LONG).show();
            }
        });




        myLayout = (RelativeLayout) findViewById(R.id.myLayout);
        status = (TextView) findViewById(R.id.status);
        scannerRL = (RelativeLayout) findViewById(R.id.scannerRL);
        nfcStatusTV = (TextView) findViewById(R.id.nfcStatus);
        scanIllustrationV = (GifImageView) findViewById(R.id.scanGIF);
        medikeyTV = (TextView) findViewById(R.id.medikeyTV);

        slideDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
        slideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);

        animationDrawable = (AnimationDrawable) myLayout.getBackground();
        animationDrawable.setEnterFadeDuration(4500);
        animationDrawable.setExitFadeDuration(4500);
        animationDrawable.start();

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

//        if(nfcAdapter!= null && nfcAdapter.isEnabled()){
//            Toast.makeText(this, "NFC Available", Toast.LENGTH_LONG).show();
//        }
//
//        else {
//            Toast.makeText(this, "NFC Not Available", Toast.LENGTH_LONG).show();
//        }


        final Handler handler = new Handler();
        final int delay = 1000;


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, delay);



                if(nfcAdapter!= null && nfcAdapter.isEnabled()){
                        nfcStatus = true;
                        if (tagContent == null) {
                            changeNFCScannerContainer(nfcStatus);
                        }

                }else {
                        nfcStatus = false;
                    if (tagContent == null) {
                        changeNFCScannerContainer(nfcStatus);
                    }
                                        }
            }
        }
        ,delay);

    }

    private void fetchLocation() {

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(Emergency1Activity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(Emergency1Activity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                new AlertDialog.Builder(this)
                        .setTitle("Required Location Permission")
                        .setMessage("Tou have to give permission to access the feature")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(Emergency1Activity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create()
                        .show();

            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(Emergency1Activity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
            mFusedLocationProviderClent.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {


                                latitude = location.getLatitude();
                                longitude = location.getLongitude();

                                speak("Thank You Paul! Your mehdee key is has been obtained");
                               medikeyTV.setText("Latitude: " + latitude+"\n Longitude: "+longitude);

                               location = null;
                            }
                        }
                    });
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            }
            else {

            }
        }
    }

    // Step 2
    @Override
    protected void onNewIntent(Intent intent){
        super.onNewIntent(intent);

        if (intent.hasExtra(nfcAdapter.EXTRA_TAG)) {
            Toast.makeText(this,"NFC intent!", Toast.LENGTH_SHORT).show();


                Parcelable[] parcelables = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

                if (parcelables != null && parcelables.length > 0) {
                    readTextFromMessage((NdefMessage)parcelables[0]);

                    speak("Thank You Paul! Your mehdee key is has been obtained");
                    fetchLocation();
                    proceedBtn.setBackground(getDrawable(R.drawable.mybutton));
                    proceedBtn.setEnabled(true);

//                    medikeyTV.setText(tagContentSpace);
                }
                else {
                    Toast.makeText(this, "No NDEF Messages Found!" , Toast.LENGTH_SHORT).show();
                }
            }

    }

    private void readTextFromMessage(NdefMessage ndefMessage) {

        NdefRecord[] ndefRecords = ndefMessage.getRecords();

        if(ndefRecords != null && ndefRecords.length > 0) {
            NdefRecord ndefRecord = ndefRecords[0];

            tagContent = getTextFromNdefRecord(ndefRecord);
            tagContentSpace = (tagContent.replaceAll(".(?!$)", "$0 "));
            successTick = findViewById(R.id.successTickGIF);
            successTN = findViewById(R.id.successTN);

//            instructionTV = (TextView)findViewById(R.id.instructionTV);
//            scan_step1 = findViewById(R.id.instructionsScanTV);
//            scan_step2 = findViewById(R.id.instructionsScanTV2);
//            emerg_nextBtn = findViewById(R.id.emerg_nextBtn);
//
            status.setText("Medi-Key");
            nfcStatusTV.setText("Medi-Key Obtained");
            scanIllustrationV.setVisibility(View.INVISIBLE);
            successTick.setVisibility(View.VISIBLE);
//            instructionTV.setText("MediKey Obtained!");
//            instructionTV.setTextColor(getResources().getColor(R.color.colorPrimary));
//            scan_step1.setVisibility(View.INVISIBLE);
//            scan_step2.setVisibility(View.INVISIBLE);
//            emerg_nextBtn.setBackgroundColor(getResources().getColor(R.color.colorPrimary));




            new CountDownTimer(900, 900) { // 5000 = 5 sec

                public void onTick(long millisUntilFinished) {
                }

                public void onFinish() {
                    successTN.setVisibility(View.VISIBLE);
                    successTick.setVisibility(View.INVISIBLE);


                }
            }.start();
        }
        else {
            Toast.makeText(this, "No NDEF messages found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {

        Intent intent = new Intent(this, Emergency1Activity.class);

        intent.addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        IntentFilter[] intentFilter = new IntentFilter[]{};

        nfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFilter, null);

        super.onResume();

    }


    @Override
    protected void onPause() {

        nfcAdapter.disableForegroundDispatch(this);
        super.onPause();
    }


    public String getTextFromNdefRecord (NdefRecord ndefRecord) {


        try {
            byte[] payload = ndefRecord.getPayload();
            String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16";
            int languageSize = payload[0] & 0063;
            tagContent = new String(payload, languageSize + 1, payload.length - languageSize - 1, textEncoding);
        } catch (UnsupportedEncodingException e){
            Log.e("getTextFromNdefRecord", e.getMessage(), e);
        }
        return tagContent;
    }


    public void openHomeActivity() {
        Intent intent = new Intent(this, Emergency2Activity.class);
        Bundle extras = new Bundle();
        extras.putString("Medi_Key", tagContent);
        intent.putExtras(extras);
        startActivity(intent);

    }

    public void changeNFCScannerContainer(Boolean status) {


        if (nfcStatus != nfclastStaus) {
            if (nfcStatus == false) {
                scannerRL.startAnimation(slideDown);
                scannerRL.setVisibility(View.VISIBLE);
                scanIllustrationV.setVisibility(View.INVISIBLE);
                nfcStatusTV.setText("NFC Disabled");

                nfclastStaus = nfcStatus;
            } else if (nfcStatus == true) {
                scannerRL.setVisibility(View.VISIBLE);
                scannerRL.startAnimation(slideUp);
                scanIllustrationV.setVisibility(View.VISIBLE);
//                Toast.makeText(getApplicationContext(), "NFC STATUS : Available", Toast.LENGTH_SHORT).show();
                nfcStatusTV.setText("Scanning...");


                nfclastStaus = nfcStatus;

            }
        }


        else {

//                Toast.makeText(getApplicationContext(), "NFC STATUS HASNT CHANGED", Toast.LENGTH_SHORT).show();

        }
    }

    private void speak(String text) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tts1.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        }
        else {
            tts1.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }

    }

    @Override
    public void onDestroy() {
        if (tts1 != null) {
            tts1.stop();
            tts1.shutdown();
        }
        super.onDestroy();
    }



}

