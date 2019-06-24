package com.medipass.medipass;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MD_RecyclerViewAdapter extends RecyclerView.Adapter<MD_RecyclerViewAdapter.ViewHolder>{

    private static final String TAG = "RecyclerViewAdapter";

    //Vars
    private ArrayList<String> mTitles = new ArrayList<>();
    private ArrayList<String> mDates = new ArrayList<>();
    private ArrayList<Integer> mIds = new ArrayList<>();
    private OnRecordListener mOnRecordListener;
    private Context mContext;

    public MD_RecyclerViewAdapter( Context context, ArrayList<String> mRec_titles, ArrayList<String> mRec_dates, ArrayList<Integer> mRec_ids, OnRecordListener onRecordListener){
        mTitles = mRec_titles;
        mDates = mRec_dates;
        mIds = mRec_ids;
        this.mOnRecordListener = onRecordListener;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_medical_record_list, viewGroup, false);

        return new MD_RecyclerViewAdapter.ViewHolder(view, mOnRecordListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {


        Log.d(TAG, "onCreateViewHolder: called.");

        viewHolder.recTitle.setText(mTitles.get(i));
        viewHolder.recDate.setText(mDates.get(i));
//        viewHolder.viewRecBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d(TAG, "onClick: clicked on button with id: " +mTitles.get(i));
//                Toast.makeText(mContext, mIds.get(i).toString(), Toast.LENGTH_SHORT).show();
//
//            }
//        });

//        viewHolder.mRecCV.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d(TAG, "onClick: clicked on button with id: " +mTitles.get(i));
//                Toast.makeText(mContext, mIds.get(i).toString(), Toast.LENGTH_SHORT).show();
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mIds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView recTitle,recDate;
        Button viewRecBtn;
        CardView mRecCV;
        OnRecordListener onRecordListener;


        public ViewHolder(@NonNull View itemView, OnRecordListener onRecordListener ) {

            super(itemView);

            mRecCV = itemView.findViewById(R.id.mRecCV);
            recTitle = itemView.findViewById(R.id.recTitleTV);
            recDate = itemView.findViewById(R.id.recDateTV);
            viewRecBtn = itemView.findViewById(R.id.viewRecBtn);
            this.onRecordListener = onRecordListener;

            viewRecBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onRecordListener.onRecordClick(getAdapterPosition());

        }
    }
    public interface OnRecordListener {
        void onRecordClick (int i);
    }
}
