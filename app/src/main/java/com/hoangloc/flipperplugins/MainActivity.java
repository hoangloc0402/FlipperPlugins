package com.hoangloc.flipperplugins;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.facebook.litho.ComponentContext;
import com.facebook.litho.LithoView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        final ComponentContext c = new ComponentContext(this);
        setContentView(
                LithoView.create(
                        c,
                        RootComponent.create(c).build()));

//        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ImageView imageView = findViewById(R.id.imageView);
//                Glide.with(getApplicationContext())
//                        .load("http://goo.gl/gEgYUd")
//                        .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true))
//                        .into(imageView);
//            }
//        });


        getSharedPreferences("sample", Context.MODE_PRIVATE).edit().putString("3", "modify").apply();
        //startActivity(new Intent(this, SubActivity.class));

    }
}
