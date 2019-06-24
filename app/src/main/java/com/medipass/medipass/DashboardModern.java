package com.medipass.medipass;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.Toast;

public class DashboardModern extends AppCompatActivity implements View.OnClickListener{

    private CardView cardDetails, cardMedRec, cardPresc, cardAllerg, cardAddDoctor, cardAppoint, cardConsentC, cardEmergContacts, cardMediRing, cardEmemergency;
    private LinearLayout medRecs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_modern);

        cardDetails = (CardView) findViewById(R.id.cardProfile);
        cardMedRec = (CardView) findViewById(R.id.cardMedRec);
        cardPresc = (CardView) findViewById(R.id.cardPresc);
        cardAllerg = (CardView) findViewById(R.id.cardAllerg);
        cardAddDoctor = (CardView) findViewById(R.id.cardAddDoctor);
        cardAppoint = (CardView) findViewById(R.id.cardAppoints);
        cardConsentC = (CardView) findViewById(R.id.cardConsentPortal);
        cardMediRing = (CardView) findViewById(R.id.cardMediRing);
//        cardEmemergency = (CardView) findViewById(R.id.cardEmergency);

        medRecs = (LinearLayout) findViewById(R.id.medRecs);

        medRecs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardModern.this, MedicalRecordListActivity.class));
                Toast.makeText(getApplicationContext(), "Medical Records Clicked", Toast.LENGTH_SHORT).show();

            }
        });


        Animation cardAnim = AnimationUtils.loadAnimation(this, R.anim.card_transition);
        cardMedRec.startAnimation(cardAnim);
        cardPresc.startAnimation(cardAnim);

        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {

                }
            }
        };
        timer.start();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.details :
                Toast.makeText(getApplicationContext(), "profile", Toast.LENGTH_SHORT).show();

                break;

            case R.id.medRecs :
                Toast.makeText(getApplicationContext(), "medical records", Toast.LENGTH_SHORT).show();

                break;
        }
    }
}
