package com.medipass.medipass;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class MedicalRecordsActivity extends AppCompatActivity implements RecyclerViewAdapter.OnRecordDataListener{

    private static final String TAG = "MedicalRecordSingle";

    //vars
    private ArrayList<String> mRec_titles = new ArrayList<>();
    private ArrayList<Integer> mRec_ids = new ArrayList<>();
    RecyclerView recyclerView;
    ImageView xrayIV;

    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21 ) {
//            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setStatusBarColor(Color.TRANSPARENT);

        }
        setContentView(R.layout.activity_medical_records);



        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        xrayIV = (ImageView) findViewById(R.id.xrayIV);
        //Actionbar and it's title
        actionBar = getSupportActionBar();
        actionBar.setTitle("Medical Records");


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initMedicalRecords();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void initMedicalRecords () {

        Log.d(TAG, "initMedicalRecords: preparing med records");

        mRec_titles.add("Summary");
        mRec_ids.add(1);

        mRec_titles.add("X-Ray");
        mRec_ids.add(2);


        initRecycleView();
    }

    private void initRecycleView() {
        Log.d(TAG, "initRecycleView: init RecycleView");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mRec_titles, mRec_ids, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDataBtnClick(int i) {
        if (i == 1) {
            recyclerView.setVisibility(View.INVISIBLE);
            actionBar.hide();
            getWindow().setNavigationBarColor(Color.BLACK);
            xrayIV.setVisibility(View.VISIBLE);
        }
        Log.d(TAG, "onDataBtnClick: clicked" + i);
    }
}
