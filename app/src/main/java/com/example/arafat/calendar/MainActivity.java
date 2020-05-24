package com.example.arafat.calendar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Button  loginButton;
    TextView SignupButton;
    static EditText email,password;
    RequestQueue requestQueue_main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main);
        loginButton=findViewById( R.id.login_button );
        SignupButton=findViewById( R.id.signup );
        email=findViewById( R.id.email_main );
        password=findViewById( R.id.password );
        requestQueue_main= Volley.newRequestQueue(this);

    }

    public void signup_main(View view) {
        startActivity( new Intent( this,Signup.class ) );
    }

    public void login_MainActivity(View view) {
        if(!email.getText().toString().contains("@") || !email.getText().toString().contains(".")){
            Toast.makeText(MainActivity.this,"Invalid email address provided",Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String,String> object_main =new HashMap<>();
        object_main.put("email",""+email.getText().toString().trim());
        object_main.put("password",""+password.getText().toString().trim());
        JSONObject jsonObject_main=new JSONObject(object_main);
        final String URL_main="https://lucozade25.000webhostapp.com/login.php";
        JsonObjectRequest jsonObjectRequest_main=new JsonObjectRequest( Request.Method.POST, URL_main, jsonObject_main, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getInt( "success" )==1){
                        Intent intent=new Intent( MainActivity.this,finalResult.class);
                        intent.putExtra("email",email.getText().toString());
                        startActivity( intent );
                        finish();
                    }
                    else

                        Toast.makeText(MainActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error in MainActivity:",""+error);
            }
        }){
            @Override
            public Map<String, String> getHeaders()  {
                HashMap<String,String> headers=new HashMap<String, String>();
                headers.put("Content-Type","application/json; charset=utf-8");
                return headers;
            }
        };
        requestQueue_main.add(jsonObjectRequest_main);
    }
}

