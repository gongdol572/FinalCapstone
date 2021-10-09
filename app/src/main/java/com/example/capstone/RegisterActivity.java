package com.example.capstone;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    InputMethodManager IPM;
    EditText ET1;
    EditText ET2;
    EditText ET3;
    EditText ET4;
    private EditText et_id, et_pass, et_name, et_department;
    private Button btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //아이디값 찾아주기
        IPM = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        ET1 = findViewById(R.id.et_id);
        ET2 = findViewById(R.id.et_pass);
        ET3 = findViewById(R.id.et_name);
        ET4 = findViewById(R.id.et_department);

        //회원가입 버튼 클릭 시 수행
        btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //EditTest에 현재 입력되어있는 값을 가져온다
                IPM = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                String userID = ET1.getText().toString();
                String userPass = ET2.getText().toString();
                String userName = ET3.getText().toString();
                String department = ET4.getText().toString();


                if (TextUtils.isEmpty(userID) || TextUtils.isEmpty(userPass) || TextUtils.isEmpty(userName)) {
                    Toast.makeText(getApplicationContext(), "칸이 비어있습니다. 다시한번 확인해주세요", Toast.LENGTH_SHORT).show();

                } else if (userPass.length() < 2 || userPass.length() > 12) {
                    Toast.makeText(getApplicationContext(), "비밀번호 자릿수가 맞지 않습니다 규격에 맞게 다시 작성해주세요.", Toast.LENGTH_SHORT).show();
                }
/*
                else if(userPass.contains("!")==false || userPass.contains("@")==false || userPass.contains("#")==false || userPass.contains("$")==false || userPass.contains("%")==false || userPass.contains("^")==false || userPass.contains("&")==false || userPass.contains("*")==false){
                    Toast.makeText(getApplicationContext(), "비밀번호에 특수문자가 포함되어 있지 않습니다. 규격에 맞게 다시 작성해주세요.", Toast.LENGTH_SHORT).show();
                }

 */
                else {
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                boolean success = jsonObject.getBoolean("success");

                                //회원가입 성공시
                                if (success) {

                                    Toast.makeText(getApplicationContext(), "회원등록에 성공했습니다.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(intent);


                                    //회원가입 실패시
                                } else {
                                    Toast.makeText(getApplicationContext(), "회원등록에 실패했습니다.", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    };
                    //서버로 Volley를 이용해서 요청


                    RegisterRequest registerRequest = new RegisterRequest(userID, userPass, userName, department, responseListener);
                    System.out.println("Registerrequest =" + registerRequest);
                    RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                    queue.add(registerRequest);


                }

            }

        });


    }

    public void linearOnClick(View v) {
        IPM.hideSoftInputFromWindow(ET1.getWindowToken(), 0);
    }

}
