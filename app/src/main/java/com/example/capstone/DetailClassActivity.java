package com.example.capstone;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class DetailClassActivity extends Activity {

    private String TAG_JSON = "webnautes";
    private static String TAG = "phpquerytest";

    private static String TAG_Lectname = "Lectname";
    private static String TAG_StartTime = "StartTime";
    private static String TAG_EndTime = "EndTime";
    private static String TAG_Location = "Location";

    ArrayList<HashMap<String, String>> mArraylist;
    ListView mListViewList;
    String mJsonString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup);

        Intent intent = getIntent();
        String Location = intent.getStringExtra("Location");
        System.out.println(Location);

        TextView location = (TextView) findViewById(R.id.Location);

        location.setText(Location);


        mListViewList = (ListView) findViewById(R.id.detailClass);

        GetData task = new GetData();
        task.execute(Location);


        Button Finish = (Button) findViewById(R.id.finish);
        Finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "세부 클래스 창을 종료합니다", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        mArraylist = new ArrayList<>();
    }

    private class GetData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(DetailClassActivity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();

            Log.d(TAG, "response - " + result);

            if (result == null) {


            } else {

                mJsonString = result;
                showResult();
            }
        }


        @Override
        protected String doInBackground(String... params) {

            String searchKeyword1 = params[0];

            String serverURL = "http://gongdoli.aws-exercise.net/함박관/B1층/query.php";
            String postParameters = "Location=" + searchKeyword1;

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


    private void showResult() {
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            mArraylist.clear();

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject item = jsonArray.getJSONObject(i);

                String Lectname = item.optString(TAG_Lectname);
                String StartTime = item.optString(TAG_StartTime);
                String EndTime = item.optString(TAG_EndTime);


                HashMap<String, String> hashMap = new HashMap<>();

                hashMap.put(TAG_Lectname, Lectname);
                hashMap.put(TAG_StartTime, StartTime);
                hashMap.put(TAG_EndTime, EndTime);

                mArraylist.add(hashMap);
            }

            ListAdapter adapter = new SimpleAdapter(
                    DetailClassActivity.this, mArraylist, R.layout.detailclass,
                    new String[]{TAG_Lectname, TAG_StartTime, TAG_EndTime},
                    new int[]{R.id.lectname, R.id.Starttime, R.id.EndTime}
            );

            mListViewList.setAdapter(adapter);

        } catch (JSONException e) {

            mArraylist.clear();

            HashMap<String, String> hashMap = new HashMap<>();

            hashMap.put(TAG_Lectname, "없음");
            hashMap.put(TAG_StartTime,"없음");
            hashMap.put(TAG_EndTime, "없음");

            mArraylist.add(hashMap);


            ListAdapter adapter = new SimpleAdapter(
                    DetailClassActivity.this, mArraylist, R.layout.detailclass,
                    new String[]{TAG_Lectname, TAG_StartTime, TAG_EndTime},
                    new int[]{R.id.lectname, R.id.Starttime, R.id.EndTime}
            );

            mListViewList.setAdapter(adapter);



            Log.d(TAG, "showResult : ", e);
        }

    }
}
