package com.example.arafat.calendar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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

import static com.example.arafat.calendar.R.drawable.cal_style;

public class ReceivedDates extends AppCompatActivity {
    Intent intent;
    RequestQueue requestQueue;
    String email;
    LinearLayout ll;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_received_dates);
        intent=getIntent();
        email=intent.getStringExtra("email");
        requestQueue= Volley.newRequestQueue(this);
        ll=findViewById(R.id.ll);
        tv=findViewById(R.id.tv);
        fetchDates();
    }

    public void fetchDates() {
        Map<String,String> object_main =new HashMap<>();
        object_main.put("email",email);
        JSONObject jsonObject_main=new JSONObject(object_main);
        final String URL_main="https://lucozade25.000webhostapp.com/dates_received.php";
        JsonObjectRequest jsonObjectRequest_main=new JsonObjectRequest( Request.Method.POST, URL_main, jsonObject_main, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(final JSONObject response) {
                try {
                    if(response.getInt( "success" )==1){
                        Toast.makeText(ReceivedDates.this,response.getString("message"),Toast.LENGTH_SHORT).show();
                        for(Integer i=0;i<response.getInt("count");i++) {
                            final Button button=new Button(ReceivedDates.this);
                            button.setText(response.getString(i.toString()));
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    tv.append(button.getText().toString()+"\n");
                                }
                            });
                            ll.addView(button);
                        }
                    }
                    else
                        Toast.makeText(ReceivedDates.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(ReceivedDates.this,"NoBody has sent you any dates to pick!!",Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error in MainActivity:",""+error);
                Toast.makeText(ReceivedDates.this,"NoBody has sent you any dates to pick!!",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders()  {
                HashMap<String,String> headers=new HashMap<String, String>();
                headers.put("Content-Type","application/json; charset=utf-8");
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest_main);
    }
}
