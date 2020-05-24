package com.example.arafat.calendar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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

public class Signup extends AppCompatActivity {
    EditText name,username,email,password,confirmpassword;
    RequestQueue requestQueue_signup;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_signup );
        name=findViewById( R.id.name_signup );
        username=findViewById( R.id.username_signup );
        email=findViewById( R.id.email_signup );
        password=findViewById( R.id.password_signup );
        confirmpassword=findViewById( R.id.confirm_password_signup );
        requestQueue_signup= Volley.newRequestQueue( this );
    }


    public void signup_2(View view) {
        if(!(confirmpassword.getText().toString().trim().equals( password.getText().toString().trim() ))){
            Toast.makeText( this, "password is not same in confirm password field", Toast.LENGTH_LONG ).show();
            return;
        }
        if(!email.getText().toString().contains("@") || !email.getText().toString().contains(".")){
            Toast.makeText(Signup.this,"Invalid email address provided",Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String,String> msignup =new HashMap<>();
        msignup.put( "name",""+this.name.getText().toString().trim() );
        msignup.put( "username",""+this.username.getText().toString().trim() );
        msignup.put( "email",""+this.email.getText().toString().trim() );
        msignup.put( "password",""+this.password.getText().toString().trim() );
        JSONObject jsonObject_signup=new JSONObject(msignup);

        final String URL_signup="https://lucozade25.000webhostapp.com/signup.php";
        JsonObjectRequest jsonObjectRequest_main=new JsonObjectRequest( Request.Method.POST, URL_signup, jsonObject_signup, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                int success=0;
                try {
                    success=response.getInt( "success" );
                    if(success==1){
                        intent=new Intent( Signup.this,finalResult.class);
                        intent.putExtra("email",email.getText().toString());
                        startActivity( intent );
                        finish();
                    }
                    else{
                        Toast.makeText(Signup.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("some error :",""+error);
            }
        }){
            @Override
            public Map<String, String> getHeaders()  {
                HashMap<String,String> headers=new HashMap<>();
                headers.put("Content-Type","application/json; charset=utf-8");
                return headers;
            }
        };
        requestQueue_signup.add(jsonObjectRequest_main);

    }
}
