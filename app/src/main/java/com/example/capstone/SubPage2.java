package com.example.capstone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SubPage2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_page2);
        Button imageButton1 = (Button) findViewById(R.id.btn2);

        imageButton1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        Button imageButton2 = (Button) findViewById(R.id.btn3);

        imageButton2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ConvienientPage.class);
                startActivity(intent);
            }
        });




        String userID = SaveSharedPreference.getUserName(this);
        Log.d("userID",userID);
        System.out.println("userID="+userID);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_bottomnavigationview,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        switch(item.getItemId()){
            case R.id.t1:
                Intent intent = new Intent(this, NewActivity.class);
                startActivity(intent);
                break;

            case R.id.t2:
                Intent intent1 = new Intent(this,Reservation.class);
                startActivity(intent1);
                break;

            case R.id.t3:
                Intent intent2 = new Intent(this, LoginActivity.class);
                Toast.makeText(this, "초기 화면으로 돌아갑니다.", Toast.LENGTH_SHORT).show();
                SaveSharedPreference.clearUserName(this);
                startActivity(intent2);

                break;

        }

        return super.onOptionsItemSelected(item);
    }
}