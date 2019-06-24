package com.medipass.medipass;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class Splash extends AppCompatActivity {
private TextView appName;
private TextView appSlogan;
private ImageView appLogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        appName = (TextView)findViewById(R.id.splash_appname);
        appSlogan = (TextView) findViewById(R.id.splash_slogan);
        appLogo = (ImageView)findViewById(R.id.splash_logo);



        Animation myanim = AnimationUtils.loadAnimation(this, R.anim.mytransition);
        appLogo.startAnimation(myanim);
        appName.startAnimation(myanim);
        appSlogan.startAnimation(myanim);


        final Intent i = new Intent(this, LoginActivity.class);

        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    startActivity(i);
                    finish();
                }
            }
        };
        timer.start();
    }
}
