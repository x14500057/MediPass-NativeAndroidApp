package com.medipass.medipass;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

public class MedicalRecordListActivity extends AppCompatActivity implements MD_RecyclerViewAdapter.OnRecordListener{


    private static final String TAG = "MainActivity";

    //vars
    private ArrayList<String> mRec_titles = new ArrayList<>();
    private ArrayList<String> mRec_dates = new ArrayList<>();
    private ArrayList<Integer> mRec_ids = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_record_list);

        initMedicalRecords();
    }

    private void initMedicalRecords () {

        Log.d(TAG, "initMedicalRecords: preparing med records");

        mRec_titles.add("Cold flu");
        mRec_dates.add("11/06/2019");
        mRec_ids.add(1);

        mRec_titles.add("throat flu");
        mRec_dates.add("11/06/2019");

        mRec_ids.add(2);

        mRec_titles.add("head flu");
        mRec_dates.add("11/06/2019");

        mRec_ids.add(3);

        mRec_titles.add("arm flu");
        mRec_dates.add("11/06/2019");

        mRec_ids.add(4);

        mRec_titles.add("leg flu");
        mRec_dates.add("11/06/2019");

        mRec_ids.add(5);

        mRec_titles.add("Cold flu");
        mRec_dates.add("11/06/2019");

        mRec_ids.add(6);

        mRec_titles.add("toothache flu");
        mRec_dates.add("11/06/2019");

        mRec_ids.add(7);

        mRec_titles.add("back pain");
        mRec_dates.add("11/06/2019");

        mRec_ids.add(8);

        mRec_titles.add("Cold flu");
        mRec_dates.add("11/06/2019");

        mRec_ids.add(9);

        mRec_titles.add("Cold flu");
        mRec_dates.add("11/06/2019");

        mRec_ids.add(10);

        initRecycleView();
    }

    private void initRecycleView() {
        Log.d(TAG, "initRecycleView: init RecycleView");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = findViewById(R.id.medRecordsRV);
        recyclerView.setLayoutManager(layoutManager);
        MD_RecyclerViewAdapter adapter = new MD_RecyclerViewAdapter(this, mRec_titles, mRec_dates, mRec_ids, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onRecordClick(int i) {
        Log.d(TAG, "onRecordClick: clicked!"+ i);

        Intent intent = new Intent(this, MedicalRecordsActivity.class );
        intent.putExtra("mID", mRec_ids.get(i).toString());
        startActivity(intent);
    }
}
