package com.example.arafat.calendar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CalendarActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    private Integer noOfDates;
    private EditText email;
    private Map<String,String> dates =new HashMap<>();
    private RequestQueue requestQueue;
    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        noOfDates=0;
        email=findViewById(R.id.email);
        tv=findViewById(R.id.tv);
        requestQueue= Volley.newRequestQueue(this);

    }
    public void showDatePickerDialog(View view){
        DatePickerDialog datePickerDialog=new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        noOfDates++;
        String date=year+"-"+month+"-"+dayOfMonth;
        dates.put(noOfDates.toString(),date);
        tv.append(date+"\n");
    }


    public void sendDates(View view) {
        if(dates.isEmpty()){
            Toast.makeText(CalendarActivity.this,"Please Pick a date!",Toast.LENGTH_SHORT).show();
            return;
        }
        String receiversEmail=email.getText().toString();
        if(receiversEmail.isEmpty()){
            Toast.makeText(CalendarActivity.this,"Enter Recipient's Email",Toast.LENGTH_SHORT).show();
            return;
        }
        dates.put("receivers_email",receiversEmail);
        dates.put("count",noOfDates.toString());
        Intent intent=getIntent();
        String senders_email=intent.getStringExtra("email");
        dates.put("senders_email", senders_email);
        JSONObject jsonObject=new JSONObject(dates);
        final String URL="https://lucozade25.000webhostapp.com/send_dates.php";
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, URL, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getInt("success") == 1) {
                        Toast.makeText(CalendarActivity.this, "Dates Succesfully Sent", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(CalendarActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CalendarActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String,String> headers=new HashMap<>();
                headers.put("Content-Type","application/json; charset=utf-8");
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }
}
