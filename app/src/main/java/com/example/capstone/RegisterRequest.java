package com.example.capstone;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {

    //서버 url 설정(php파일 연동)
    final static  private String URL="http://gongdoli.aws-exercise.net/회원가입/Register.php";
    private Map<String,String> map;

    public RegisterRequest(String userID, String userPassword, String userName, String department, Response.Listener<String> listener) {
        super( Method.POST, URL, listener, null);

        map = new HashMap<> ();
        map.put("userID", userID);
        map.put("userPassword", userPassword);
        map.put("userName", userName);
        map.put("department", department);
        System.out.println( "결과 :"+map.put("department", department));
    }

    @Override
    protected Map<String, String> getParams() {
        return map;
    }
}