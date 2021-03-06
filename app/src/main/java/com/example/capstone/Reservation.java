package com.example.capstone;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Reservation extends AppCompatActivity {

    private static String TAG = "phptest";
    private String TAG_JSON = "webnautes";

    private TextView mTextViewResult;
    ArrayList<HashMap<String, String>> mArrayList;
    private static String TAG_StartTime = "StartTime";
    private static String TAG_EndTime = "EndTime";
    private static String TAG_Week = "Week";
    private static String TAG_Location = "Location";
    private static String TAG_currentDate = "currentDate";


    String mJsonString;
    ArrayList<String> lecttime = new ArrayList<String>();
    final String[] SelectSt = new String[1];
    final String[] SelectEd = new String[1];
    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");




    //????????? ?????? ??????????????? ?????? ??????

    ArrayList<HashMap<String,String>> rArraylist;
    ListView rListViewList;
    String rJsonString;

    int count=0;


    // ?????????
    Boolean[] isExistLect = new Boolean[25];
    List<TextView> textViewList = new ArrayList<>();
    private String userID;




    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservation);

        final String[] selectlectureroom = new String[1];
        final String[] selectWeek = new String[1];

        mTextViewResult = (TextView) findViewById(R.id.textView_main_result);
        final Button checkReservation = (Button) findViewById(R.id.checkReservation);
        final Button delete = (Button) findViewById(R.id.delete);
        rListViewList = (ListView) findViewById(R.id.userReservation);
        userID = SaveSharedPreference.getUserName(this);
        System.out.println("userid="+userID);

        TextView setUserID = (TextView) findViewById(R.id.setUserID);
        setUserID.setText(userID);

        //????????? ?????? ??????
        if(SaveSharedPreference.getUserName(Reservation.this).length()==0){
            Intent intent = new Intent(Reservation.this, LoginActivity.class);
            Toast.makeText(this,"????????? ????????? ?????? ????????? ???????????? ???????????????. ????????? ??? ?????? ??????????????????",Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }

        //????????? ??????
        final Spinner spinner = (Spinner) findViewById(R.id.ltroom);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectlectureroom[0] = spinner.getSelectedItem().toString();
                System.out.println("selectlectureroom=" + selectlectureroom[0]);
                GetData getData = new GetData();
                getData.execute(selectWeek[0], selectlectureroom[0]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


//?????? ??????
        final Spinner spinner1 = (Spinner) findViewById(R.id.Week);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectWeek[0] = spinner1.getSelectedItem().toString();
                System.out.println("selectWeek=" + selectWeek[0]);
                GetData getData = new GetData();
                getData.execute(selectWeek[0], selectlectureroom[0]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //?????? ?????? ??????
        final Spinner StReservation = (Spinner) findViewById(R.id.StReveration);
        StReservation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SelectSt[0] = StReservation.getSelectedItem().toString();
                System.out.println("SelectSt = " + SelectSt[0]);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //?????? ?????? ??????

        final Spinner EdReservation = (Spinner) findViewById(R.id.EdReveration);
        EdReservation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SelectEd[0] = EdReservation.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });






        //   DatePick
        //   erDialog dialog = (DatePickerDialog) findViewById(R.id.DPicker);

        mArrayList = new ArrayList<>();
        lecttime = new ArrayList<>();
        rArraylist= new ArrayList<>();

        checkReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    //?????? ?????? ?????? ??????
                    getCheckReservation(SelectSt[0], SelectEd[0], isExistLect, selectWeek[0], selectlectureroom[0]);


                    //????????? ?????? ?????? ????????? ????????? ??????????????? ??????
                    GetReservationData task2 = new GetReservationData();
                    task2.execute(userID);


                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });


        //?????? ?????? ??????
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final AlertDialog.Builder deleteBuilder = new AlertDialog.Builder(Reservation.this);
                deleteBuilder.setTitle("?????? ??????");
                deleteBuilder.setMessage("????????? ??????????????????????");
                deleteBuilder.setPositiveButton("???", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        reservationdelete(userID); //?????? ?????? ??????

                        //????????? ????????? ?????? ??????
                        GetData getData = new GetData();
                        getData.execute(selectWeek[0], selectlectureroom[0]);
                        GetReservationData task2 = new GetReservationData();
                        task2.execute(userID);
                    }
                });

                deleteBuilder.setNegativeButton("?????????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });


                deleteBuilder.show();
            }
        });


        //????????? ?????? ????????? ?????? ??????

        GetReservationData task2 = new GetReservationData();
        task2.execute(userID);

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
                Toast.makeText(this, "?????? ???????????? ???????????????.", Toast.LENGTH_SHORT).show();
                SaveSharedPreference.clearUserName(this);
                startActivity(intent2);

                break;

        }

        return super.onOptionsItemSelected(item);
    }


    //??????????????? ?????? ??????(AsyncTask)

    private  class GetReservationData extends  AsyncTask<String, Void, String>{
        ProgressDialog progressDialog;
        String errorString =null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(Reservation.this,
                    "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();

            Log.d(TAG, "response - " + result);

            if (result == null) {


            } else {

                rJsonString = result;
                showReservationResult();
            }
        }

        @Override
        protected String doInBackground(String... params) {

            String userID = params[0];

            String serverURL  = "http://gongdoli.aws-exercise.net/?????????/B1???/ReservationList.php";
            String postParameters = "userID="+userID;

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
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString().trim();


            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);
                errorString = e.toString();

                return null;
            }
        }
    }

    private void showReservationResult(){
        try {


            JSONObject jsonObject = new JSONObject(rJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            rArraylist.clear();

            for (int i = 0; i < jsonArray.length(); i++) {

                count = i;
                JSONObject item = jsonArray.getJSONObject(i);


                String Week = item.optString(TAG_Week);
                String StartTime = item.optString(TAG_StartTime);
                String EndTime = item.optString(TAG_EndTime);
                String Location = item.optString(TAG_Location);
                String currentDate = item.optString(TAG_currentDate);

                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put(TAG_Week, Week);
                hashMap.put(TAG_StartTime, StartTime);
                hashMap.put(TAG_EndTime, EndTime);
                hashMap.put(TAG_Location, Location);
                hashMap.put(TAG_currentDate, currentDate);

                rArraylist.add(hashMap);


                Log.d("count", String.valueOf(count));

            }

            ListAdapter adapter = new SimpleAdapter(Reservation.this, rArraylist, R.layout.reservation_list,
                    new String[]{TAG_Week, TAG_StartTime, TAG_EndTime, TAG_Location, TAG_currentDate},
                    new int[]{R.id.lectWeek, R.id.StartTime, R.id.EndTime, R.id.Location, R.id.currentDate});

            rListViewList.setAdapter(adapter);




        } catch (JSONException e) {

            rArraylist.clear();

            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put(TAG_Week,"??????");
            hashMap.put(TAG_StartTime,"??????");
            hashMap.put(TAG_EndTime, "??????");
            hashMap.put(TAG_Location, "??????");
            hashMap.put(TAG_currentDate, "??????");

            rArraylist.add(hashMap);

            ListAdapter adapter = new SimpleAdapter(Reservation.this, rArraylist, R.layout.reservation_list,
                    new String[]{TAG_Week, TAG_StartTime, TAG_EndTime, TAG_Location, TAG_currentDate},
                    new int[]{R.id.lectWeek, R.id.StartTime, R.id.EndTime, R.id.Location, R.id.currentDate});

            rListViewList.setAdapter(adapter);
            Log.d(TAG,"showresult :", e);
        }
    }



    //Y9029 ?????? ??????
    private class GetData extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(Reservation.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            //  mTextViewResult.setText(result);
            Log.d(TAG, "response - " + result);

            if (result == null) {

                mTextViewResult.setText(errorString);
            } else {

                mJsonString = result;

                //????????? ?????????
                Arrays.fill(isExistLect, false);

                showResult();

            }
        }


        @Override
        protected String doInBackground(String... params) {

            String searchkeyword = params[0];
            String searchkeyword1 = params[1];
            String serverURL = "http://gongdoli.aws-exercise.net/?????????/B1???/gettable.php";
            String postParameters = "Week=" + searchkeyword + "&Location=" + searchkeyword1;


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
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
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

    private void showResult() {


        try {

            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            Date[] St = new Date[jsonArray.length()];
            Date[] Ed = new Date[jsonArray.length()];


            for (int i = 0; i < jsonArray.length(); i++) {


                JSONObject item = jsonArray.getJSONObject(i);

                String StartTime = item.optString(TAG_StartTime);
                String EndTime = item.optString(TAG_EndTime);

                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put(TAG_StartTime, StartTime);
                hashMap.put(TAG_EndTime, EndTime);

                mArrayList.add(hashMap);


                St[i] = dateFormat.parse(StartTime);
                Ed[i] = dateFormat.parse(EndTime);


                //?????? ????????? ?????? ????????? date??? ???????????? ?????? int??? ????????? boolean??? ??????. ????????? ????????? ??? ????????? ??????
                Calendar c = Calendar.getInstance();
                c.setTime(St[i]);
                int startHour = c.get(Calendar.HOUR_OF_DAY);
                c.setTime(Ed[i]);
                int endHour = c.get(Calendar.HOUR_OF_DAY);

                for (int j = startHour; j <= endHour; j++) {
                    isExistLect[j] = true;
                }

            }

//            getLectTable(St,Ed);
            getLectTable2(isExistLect);


        } catch (JSONException | ParseException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }


    //??????+?????? ????????? ?????? true??? ??????, false ??? ?????????
    private void getLectTable2(Boolean[] isExistLect) {
        textViewList.add((TextView) findViewById(R.id.tableTime0));
        textViewList.add((TextView) findViewById(R.id.tableTime1));
        textViewList.add((TextView) findViewById(R.id.tableTime2));
        textViewList.add((TextView) findViewById(R.id.tableTime3));
        textViewList.add((TextView) findViewById(R.id.tableTime4));
        textViewList.add((TextView) findViewById(R.id.tableTime5));
        textViewList.add((TextView) findViewById(R.id.tableTime6));
        textViewList.add((TextView) findViewById(R.id.tableTime7));
        textViewList.add((TextView) findViewById(R.id.tableTime8));
        textViewList.add((TextView) findViewById(R.id.tableTime9));
        textViewList.add((TextView) findViewById(R.id.tableTime10));
        textViewList.add((TextView) findViewById(R.id.tableTime11));
        textViewList.add((TextView) findViewById(R.id.tableTime12));


        for (int i = 9; i <= 21; i++) {

            if (isExistLect[i]) {
                textViewList.get(i - 9).setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.edge3));
                textViewList.get(i - 9).setTextColor(Color.WHITE);

            } else {
                textViewList.get(i - 9).setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.edge));
                textViewList.get(i - 9).setTextColor(Color.BLACK);


            }
        }
    }


    //???????????? ?????? ??????
    private void getCheckReservation(final String SelectSt, final String SelectEd, Boolean[] isExistLect, final String selectweek, final String selectLocation) throws ParseException {

        boolean itExistLect = false;
        // Toast.makeText(getApplicationContext(),"?????????????????????",Toast.LENGTH_SHORT).show();

        Date selectst = dateFormat.parse(SelectSt);
        Date selectEd = dateFormat.parse(SelectEd);

        Intent intent = getIntent();

        //userID = intent.getStringExtra("userID");
        final String Lectname = "??????";

        for (int i = 9; i < 21; i++) {
            System.out.println("i="
                    + i + "\t isExistLect= " + isExistLect[i]);
        }

        Calendar c = Calendar.getInstance();
        c.setTime(selectst);
        int startHour = c.get(Calendar.HOUR_OF_DAY);
        c.setTime(selectEd);
        int endHour = c.get(Calendar.HOUR_OF_DAY);

        System.out.println("starthour=" + startHour + " \t Endhour=" + endHour);

        if (selectst.compareTo(selectEd) >= 0) {//?????? ?????? ????????? ?????? ???????????? ??? ???
            Toast.makeText(getApplicationContext(), "?????? ????????? ???????????? ?????? ?????????. ?????? ????????? ?????????", Toast.LENGTH_SHORT).show();
        } else if (endHour - startHour >= 3) {
            Toast.makeText(getApplicationContext(), "?????? ?????? ????????? ????????? 3???????????????.", Toast.LENGTH_SHORT).show();
        }
        else if (count>0){
            Toast.makeText(getApplicationContext(), "??? ??? ????????? ????????? ??? ??? ????????????.", Toast.LENGTH_SHORT).show();
        }
        else {

            for (int j = startHour; j <= endHour; j++) {
                System.out.println("?????? ?????? ?????? \t " + (isExistLect[j]));
                //boolean ????????? ????????? ?????? ?????? false??? ????????? ?????? ?????? ????????? ?????? ?????? ??????
                if (isExistLect[j] == true) {
                    Toast.makeText(getApplicationContext(), "?????? ?????? ?????? ????????? ?????????????????????. ?????? ?????? ??????????????????.", Toast.LENGTH_SHORT).show();
                    itExistLect = true;
                    break;
                }
            }
            if (itExistLect == false) {
                final AlertDialog.Builder myAlertBuilder =
                        new AlertDialog.Builder(Reservation.this);

                myAlertBuilder.setTitle("??? ????????? ?????? ??????");
                myAlertBuilder.setMessage("????????? ???????????????.\n\n" + "????????? ?????? : \t" + SelectSt + " - " + SelectEd +
                        "\n????????? ?????? : " + selectweek + "\n????????? ????????? : " + selectLocation + "\t ??? ???????????");

                myAlertBuilder.setPositiveButton("???", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Calendar c = Calendar.getInstance();
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
                        String dayweek;

                        switch (selectweek) {
                            case "???":
                                c.set(Calendar.DAY_OF_WEEK, Calendar.MONTH);
                                dayweek = formatter.format(c.getTime());
                                System.out.println("week=" + dayweek);

                                break;
                            case "???":
                                c.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
                                dayweek = formatter.format(c.getTime());
                                System.out.println("week=" + dayweek);

                                break;

                            case "???":
                                c.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
                                dayweek = formatter.format(c.getTime());

                                System.out.println("week=" + dayweek);
                                break;


                            case "???":
                                c.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
                                dayweek = formatter.format(c.getTime());
                                System.out.println("week=" + dayweek);
                                break;

                            case "???":
                                c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
                                dayweek = formatter.format(c.getTime());
                                System.out.println("week=" + dayweek);
                                break;


                            default:
                                throw new IllegalStateException("Unexpected value: " + selectweek);
                        }


                        //???????????? ??????
                        insertReservation(Lectname, selectweek, SelectSt, SelectEd, selectLocation, userID, dayweek);

                        //tablerow??? ?????? ?????? ????????? ?????? ????????? ??????
                        GetData getData = new GetData();
                        getData.execute(selectweek, selectLocation);

                        //?????? ????????? ??????????????? ??????
                        GetReservationData task2 = new GetReservationData();
                        task2.execute(userID);


                    }
                });

                //????????? ?????? ??? ??????
                myAlertBuilder.setNegativeButton("?????????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                AlertDialog alertDialog = myAlertBuilder.create();
                alertDialog.show();

            }

        }


    }

    //?????? ?????? ??????
    private void reservationdelete(String userID){
        class DeleteData extends AsyncTask<String,Void,String>{
            ProgressDialog loading;
            String errorString = null;


            @Override
            protected void onPreExecute(){
                super.onPreExecute();
                loading = ProgressDialog.show(Reservation.this, "Please Wait",null,true,true);

            }

            @Override
            protected  void onPostExecute(String s){
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(String... params){
                try {
                    String userID = params[0];
                    String link = "http://gongdoli.aws-exercise.net/?????????/B1???/DeleteReservation.php";
                    String data = "userID="+userID;

                    System.out.println("data="+data);

                    URL url = new URL(link);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setReadTimeout(5000);
                    httpURLConnection.setConnectTimeout(5000);
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.connect();


                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    outputStream.write(data.getBytes("UTF-8"));
                    outputStream.flush();
                    outputStream.close();


                    int responseStatusCode = httpURLConnection.getResponseCode();
                    Log.d(TAG, "response code - " + responseStatusCode);

                    InputStream inputStream;
                    if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                        inputStream = httpURLConnection.getInputStream();
                    } else {
                        inputStream = httpURLConnection.getErrorStream();
                    }


                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                    StringBuilder sb = new StringBuilder();
                    String line;

                    while ((line = bufferedReader.readLine()) != null) {
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

        DeleteData  task = new DeleteData();
        task.execute(userID);





    }


    //?????? ?????? ??????
    private  void insertReservation(String Lectname, String Week, String StartTime, String EndTime, String Location , String userID ,String currentDate){
        class InsertData extends  AsyncTask<String ,Void, String>{
            ProgressDialog loading;
            String errorString = null;

            @Override
            protected void onPreExecute(){
                super.onPreExecute();
                loading = ProgressDialog.show(Reservation.this, "Please Wait",null,true,true);

            }

            @Override
            protected  void onPostExecute(String s){
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(String... params){
                try {
                    String Lectname = params[0];
                    String Week = params[1];
                    String StartTime = params[2];
                    String EndTime = params[3];
                    String Location = params[4];
                    String userID = params[5];
                    String currentDate = params[6];

                    String link = "http://gongdoli.aws-exercise.net/?????????/B1???/Reservation2.php";
                    String data = "Lectname="+Lectname+"&Week="+Week+"&StartTime="+StartTime+"&EndTime="+EndTime+
                            "&Location="+Location+"&userID="+userID+"&currentDate="+currentDate;

                    System.out.println("data="+data);

                    URL url = new URL(link);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setReadTimeout(5000);
                    httpURLConnection.setConnectTimeout(5000);
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.connect();


                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    outputStream.write(data.getBytes("UTF-8"));
                    outputStream.flush();
                    outputStream.close();


                    int responseStatusCode = httpURLConnection.getResponseCode();
                    Log.d(TAG, "response code - " + responseStatusCode);

                    InputStream inputStream;
                    if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                        inputStream = httpURLConnection.getInputStream();
                    } else {
                        inputStream = httpURLConnection.getErrorStream();
                    }


                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                    StringBuilder sb = new StringBuilder();
                    String line;

                    while ((line = bufferedReader.readLine()) != null) {
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

        InsertData task = new InsertData();
        task.execute(Lectname,Week,StartTime,EndTime,Location,userID,currentDate);


    }



}





