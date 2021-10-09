package com.example.capstone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class FirstPage extends Activity {

    private Handler handler;
    ViewFlipper v_fllipper;


    Runnable runnable = new Runnable() {
        @Override
        public void run() {

            Intent intent;
            if(SaveSharedPreference.getUserName(FirstPage.this).length() == 0) {
                // call Login Activity
                intent = new Intent(FirstPage.this, LoginActivity.class);
                Toast.makeText(getApplicationContext(),"로그인 창으로 넘어갑니다",Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
            } else {
                // Call Next Activity
                intent = new Intent(FirstPage.this, SubPage2.class);
                intent.putExtra("STD_NUM", SaveSharedPreference.getUserName(getApplicationContext()).toString());
                Toast.makeText(getApplicationContext(),"현재는 자동로그인이 되어있어 로그인 없이 넘어갑니다.",Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
            }
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);



        init();


        int images[] = {

                R.drawable.school,
                R.drawable.school2,
                R.drawable.logo2


        };
        v_fllipper = findViewById(R.id.image_slide);




        for(int image : images) {
            fllipperImages(image);
        }




        handler.postDelayed(runnable, 4499);



    }
    public void fllipperImages(int image) {
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(image);





        v_fllipper.addView(imageView);      // 이미지 추가
        v_fllipper.setFlipInterval(1500);       // 자동 이미지 슬라이드 딜레이시간(1000 당 1초)
        v_fllipper.setAutoStart(true);          // 자동 시작 유무 설정

        // animation
        v_fllipper.setInAnimation(this,android.R.anim.slide_in_left);
        v_fllipper.setOutAnimation(this,android.R.anim.slide_out_right);
    }



    public void init() {
        handler = new Handler();
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        handler.removeCallbacks(runnable);
        finish();
    }

}



