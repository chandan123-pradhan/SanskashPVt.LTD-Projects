package com.example.talkiee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.widget.Toolbar;

public class WelcomeActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);




        startMainActivity();
    }



    private void startMainActivity() {


        Handler mHander=new Handler();
        mHander.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent=new Intent(WelcomeActivity.this,MainActivity.class);
                startActivity(mainIntent);
                finish();


            }
        },2000L);







    }
}
