package com.example.arafat.calendar;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class finalResult extends AppCompatActivity {

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    private Button b1,b2;
    private String email;

    public void setupNavigationDrawer(){
        dl = (DrawerLayout)findViewById(R.id.drawer_layout);
        t = new ActionBarDrawerToggle(this, dl,R.string.open,
                R.string.close);

        dl.addDrawerListener(t);
        t.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        nv = (NavigationView)findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.account:{
                        Intent i=new Intent(finalResult.this,CalendarActivity.class);
                        i.putExtra("email",email);
                        startActivity(i);
                        break;
                    }

                    case R.id.settings:{
                        Intent i=new Intent(finalResult.this,ReceivedDates.class);
                        i.putExtra("email",email);
                        startActivity(i);
                        break;
                    }

                    case R.id.mycart:
                    {
                        finish();
                        break;
                    }
                    default:
                        return true;
                }


                return true;

            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_result);
        setupNavigationDrawer();
        b1=findViewById(R.id.b1);
        b2=findViewById(R.id.b2);

        Intent intent=getIntent();
        email=intent.getStringExtra("email");
    }
    public void b1Click(View v){
        Intent i=new Intent(finalResult.this,CalendarActivity.class);
        i.putExtra("email",email);
        startActivity(i);
    }


    public void b2Click(View view) {

        Intent i=new Intent(finalResult.this,ReceivedDates.class);
        i.putExtra("email",email);
        startActivity(i);
    }


}