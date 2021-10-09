package com.example.capstone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class NewActivity extends AppCompatActivity{


    private static String TAG = "phptest";

    private TextView mTextViewResult;
    private ArrayList<PersonalData> mArrayList;
    private UsersAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private String mJsonString;

    private String TAG_JSON="webnautes";

    private static String TAG_Lectname = "Lectname";
    private static String TAG_StartTime = "StartTime";
    private static String TAG_EndTime = "EndTime";
    private static String TAG_Location = "Location";
    private String location;

    SwipeRefreshLayout mSwipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newactivity);

        mTextViewResult = (TextView)findViewById(R.id.textView_main_result);
        mRecyclerView = (RecyclerView) findViewById(R.id.listView_main_list);

        mTextViewResult.setMovementMethod(new ScrollingMovementMethod ());


        mArrayList = new ArrayList<>();
        String lcation;

        mAdapter = new UsersAdapter(this, mArrayList);
        mRecyclerView.setAdapter(mAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setAutoMeasureEnabled(false);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        //로그인 여부 체크


        final Button Reservation = (Button) findViewById(R.id.goReservation);
        Reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = getIntent();
                String userID= intent1.getStringExtra("userID");
                System.out.println("userid="+userID);
                Intent intent = new Intent(NewActivity.this, Reservation.class);
                intent.putExtra("userID",userID);
                startActivity(intent);
            }
        });

        final Spinner spinner = (Spinner) findViewById(R.id.floor);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String WeekQuery;
                getData9029 task9029= new getData9029();
                switch(position){
                    case 0:
                        WeekQuery = "Y90";
                        mArrayList.clear();
                        mAdapter.notifyDataSetChanged();
                        task9029.execute(WeekQuery);
                        System.out.println(location);
                        break;

                    case 1:
                        WeekQuery = "Y91";
                        mArrayList.clear();
                        mAdapter.notifyDataSetChanged();
                        task9029.execute(WeekQuery);
                        break;

                    case 2:
                        WeekQuery = "Y92";
                        mArrayList.clear();
                        mAdapter.notifyDataSetChanged();
                        task9029.execute(WeekQuery);
                        break;

                    case 3:
                        WeekQuery = "Y93";
                        mArrayList.clear();
                        mAdapter.notifyDataSetChanged();
                        task9029.execute(WeekQuery);
                        break;

                    case 4:
                        WeekQuery = "Y94";
                        mArrayList.clear();
                        mAdapter.notifyDataSetChanged();
                        task9029.execute(WeekQuery);
                        break;

                    case 5:
                        WeekQuery = "Y95";
                        mArrayList.clear();
                        mAdapter.notifyDataSetChanged();
                        task9029.execute(WeekQuery);
                        break;

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        Button imageButton = (Button) findViewById(R.id.reservation);
        imageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), BuildingList.class);
                startActivity(intent);
            }
        });




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







    //Y9029 정보 파싱
    private class getData9029 extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(NewActivity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            mTextViewResult.setText(result);
            Log.d(TAG, "response - " + result);

            if (result == null){

                mTextViewResult.setText(errorString);
            }
            else {

                mJsonString = result;
                showResult9029();

            }
        }


        @Override
        protected String doInBackground(String... params) {

            String searchkeyword = params[0];
            String serverURL = "http://gongdoli.aws-exercise.net/함박관/B1층/testing2.php";
            String postParameters = "Location="+ searchkeyword;


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }

                bufferedReader.close();

                return sb.toString().trim();


            } catch (Exception e) {

                Log.d(TAG, "GetData : Error ", e);
                errorString = e.toString();

                return null;
            }

        }
    }
    private void showResult9029(){


        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            Calendar cal = Calendar.getInstance();
            int nWeek = cal.get(Calendar.DAY_OF_WEEK);
            System.out.println("nWeek = "+ nWeek);

            //현재 시간 구하는 함수
            long now = System.currentTimeMillis();
            Date date = new Date(now);
            SimpleDateFormat sdfNow = new SimpleDateFormat("HH:mm:ss");
            String formatDate = sdfNow.format(date);

            ArrayList<String> arrayList = new ArrayList<>();




            for(int i=0; i<jsonArray.length(); i++) {

                JSONObject item = jsonArray.getJSONObject(i);

                //  String Lectnum = item.optString(TAG_Lectnum);
                String Lectname = item.optString(TAG_Lectname);
                String StartTime = item.optString(TAG_StartTime);
                String EndTime = item.optString(TAG_EndTime);
                final String Location = item.optString(TAG_Location);
                PersonalData personalData = new PersonalData();


                personalData.setLectname(Lectname);
                personalData.setStartTime(StartTime);
                personalData.setEndTime(EndTime);
                personalData.setLocation(Location);



                mArrayList.add(personalData);
                mAdapter.notifyDataSetChanged();

                location= Location;



            }






        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }



}