package com.medipass.medipass;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    //Vars
    private ArrayList<String> mRec_titles = new ArrayList<>();
    private ArrayList<Integer> mRec_ids = new ArrayList<>();
    private OnRecordDataListener mOnRecordDataListener;
    private Context mContext;

    public RecyclerViewAdapter( Context context, ArrayList<String> medTitles, ArrayList<Integer> medIds, OnRecordDataListener onRecordDataListener){
        mRec_titles = medTitles;
        mRec_ids = medIds;
        mContext = context;
        this.mOnRecordDataListener = onRecordDataListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_medrecords_list, viewGroup, false);

        return new ViewHolder(view, mOnRecordDataListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        Log.d(TAG, "onCreateViewHolder: called.");

        if (i == 1) {
            viewHolder.resultsBtn.setText("View X-Ray");


        }

        viewHolder.titleTV.setText(mRec_titles.get(i));

//        viewHolder.resultsBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d(TAG, "onClick: clicked button: " + mRec_titles.get(i));
//                Toast.makeText(mContext, mRec_titles.get(i), Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    public int getItemCount() {

        return mRec_titles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView titleTV;
        Button resultsBtn;
        OnRecordDataListener onRecordDataListener;

        public ViewHolder(@NonNull View itemView, OnRecordDataListener onRecordDataListener) {
            super(itemView);

            titleTV = itemView.findViewById(R.id.titleTV);
            resultsBtn = itemView.findViewById(R.id.resultsBtn);
            this.onRecordDataListener = onRecordDataListener;


            resultsBtn.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onRecordDataListener.onDataBtnClick(getAdapterPosition());
        }
    }
    public interface OnRecordDataListener {
        void onDataBtnClick(int i);
    }

}
