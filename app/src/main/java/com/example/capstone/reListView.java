package com.example.capstone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class reListView extends AppCompatActivity {

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_re_list_view);
        final TextView textView = (TextView) findViewById(R.id.textView);
        final ImageView imageView = (ImageView) findViewById(R.id.imageView);
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        final Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);


        final ArrayList<Integer> imgList = new ArrayList<>();


        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String floor2 = spinner1.getSelectedItem().toString();
                if(floor2.contains("2F")) {
                    spinner.setVisibility(View.VISIBLE);

                    imgList.add(R.drawable.y9201);
                    imgList.add(R.drawable.y9203);
                    imgList.add(R.drawable.y9205);
                    imgList.add(R.drawable.y9206);
                    imgList.add(R.drawable.y9207);
                    imgList.add(R.drawable.y9212);
                    imgList.add(R.drawable.y9214);
                    imgList.add(R.drawable.y9216);
                    imgList.add(R.drawable.y9222);
                    imgList.add(R.drawable.y9224);
                    imgList.add(R.drawable.y9226);
                    imgList.add(R.drawable.y9228);
                    imgList.add(R.drawable.y9230);
                    imgList.add(R.drawable.y9232);
                    imgList.add(R.drawable.y9236);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });






        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                textView.setText("강의실: " + spinner.getItemAtPosition(position));
                imageView.setImageResource(imgList.get(position));



                // todo
            }



            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();

        if (action == MotionEvent.ACTION_DOWN) {
            // 다음 화면으로 넘어갈 클래스 지정한다.
            Intent intent = new Intent(getApplicationContext(), Reservation.class);

            startActivity(intent);  // 다음 화면으로 넘어간다.
        }

        return super.onTouchEvent(event);
    }

}