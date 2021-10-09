package com.example.capstone;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private EditText et_id,et_pass;
    private Button btn_login,btn_register;
    InputMethodManager imm;
    EditText et;
    EditText ep;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );


        imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        et = (EditText)findViewById(R.id.et_id);
        ep = (EditText)findViewById(R.id.et_pass);



        btn_register = findViewById( R.id.btn_register);
//회원가입버튼을 클릭시 수행
        btn_register.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( LoginActivity.this , RegisterActivity.class );
                startActivity ( intent );

            }
        });
        btn_login =  findViewById( R.id. btn_login );
        btn_login.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                //EditTest에 현재 입력되어있는 값을 가져온다
                String userID = et.getText().toString();
                String userPass = ep.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject( response );
                            boolean success = jsonObject.getBoolean( "success" );

                            if(success) {//로그인 성공시

                                String userID = jsonObject.getString( "userID" );
                                String userPass = jsonObject.getString( "userPassword" );



                                Toast.makeText( getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT ).show();
                                Intent intent = new Intent( LoginActivity.this, SubPage2.class );
                                intent.putExtra( "userID", userID );
                                intent.putExtra( "userPass", userPass );
                                startActivity(intent);

                            } else {//로그인 실패시
                                Toast.makeText( getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT ).show();
                                return;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest( userID, userPass, responseListener );
                RequestQueue queue = Volley.newRequestQueue( LoginActivity.this );
                queue.add( loginRequest );
                SaveSharedPreference.setUserName(LoginActivity.this, et.getText().toString());
            }
        });

    }

    public void linearOnClick(View v) {
        imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
    }
}


