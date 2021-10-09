package com.example.capstone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class BuildingList extends AppCompatActivity {

    private Button d1,d2,d3,d4,d5,d6;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_list);

        d1 = findViewById(R.id.d1);
        d2 = findViewById(R.id.d2);
        d3 = findViewById(R.id.d3);
        d4 = findViewById(R.id.d4);
        d5 = findViewById(R.id.d5);
        d6 = findViewById(R.id.d6);

        //d1버튼 누를시 실행
        d1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BuildingList.this, reListView.class);
                startActivity(intent);
            }
        });
        d2.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"준비중입니다.",Toast.LENGTH_SHORT).show();
            }
        });
        d3.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"준비중입니다.",Toast.LENGTH_SHORT).show();
            }
        });
        d4.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"준비중입니다.",Toast.LENGTH_SHORT).show();
            }
        });
        d5.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"준비중입니다.",Toast.LENGTH_SHORT).show();
            }
        });
        d6.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"준비중입니다.",Toast.LENGTH_SHORT).show();
            }
        });
    }

}