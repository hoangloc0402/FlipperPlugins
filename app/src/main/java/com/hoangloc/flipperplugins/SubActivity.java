package com.hoangloc.flipperplugins;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SubActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(20000);
            }
        }).start();



    }
}
