package com.example.capstone;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.CustomViewHolder> {

    private ArrayList<PersonalData> mList= null;
    private Activity context = null;
    private OnItemClickListener mListener = null;


    public UsersAdapter(Activity context, ArrayList<PersonalData> list){
        this.context = context;
        this.mList = list;
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position) ;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener ;
    }


    class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView Lectname;
        protected TextView StartTime;
        protected TextView EndTime;
        protected TextView Location;
        protected  ImageView result;
        protected  Button reservation;



        public CustomViewHolder(View view) {
            super(view);

            this.Location =(TextView) view.findViewById(R.id.Location);
            this.StartTime = (TextView) view.findViewById(R.id.StartTime);
            this.result = (ImageView) view.findViewById(R.id.result);




        }

    }
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull final CustomViewHolder viewholder, final int position){

        Calendar cal = Calendar.getInstance();
        int nWeek = cal.get(Calendar.DAY_OF_WEEK);
        System.out.println("nWeek = "+ nWeek);

        //현재 시간 구하는 함수
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdfNow = new SimpleDateFormat("HH:mm:ss");
        String formatDate = sdfNow.format(date);
        viewholder.Location.setText(mList.get(position).getLocation());
        viewholder.StartTime.setText(mList.get(position).getStartTime());
        System.out.println(mList.get(position).getEndTime());

        if(nWeek>=2 && nWeek <=6) {

            if (formatDate.compareTo(mList.get(position).getStartTime()) > 0 && formatDate.compareTo(mList.get(position).getEndTime()) < 0) {
                viewholder.result.setImageResource(R.drawable.notuse);
                viewholder.Location.setTextColor(Color.RED);
                viewholder.StartTime.setTextColor(Color.RED);

            }
            else{
                viewholder.result.setImageResource(R.drawable.check2);
                viewholder.Location.setTextColor(Color.parseColor("#00bfff"));
                viewholder.StartTime.setTextColor(Color.parseColor("#00bfff"));

            }


        }
        else{

            viewholder.result.setImageResource(R.drawable.check2);
            viewholder.Location.setTextColor(Color.parseColor("#00bfff"));
            viewholder.StartTime.setTextColor(Color.parseColor("#00bfff"));
        }

        viewholder.Location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailClassActivity.class);
                intent.putExtra("Location",mList.get(position).getLocation());
                context.startActivity(intent);

            }
        });








    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }



}

