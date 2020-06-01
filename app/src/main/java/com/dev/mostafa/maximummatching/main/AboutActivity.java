package com.dev.mostafa.maximummatching.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.dev.mostafa.maximummatching.R;

public class AboutActivity extends AppCompatActivity {

    TextView link;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        getSupportActionBar().setBackgroundDrawable(getDrawable(R.drawable.drawer_gradiant));
        getSupportActionBar().setTitle("About");


        link = findViewById(R.id.about_link);

        link.setMovementMethod(LinkMovementMethod.getInstance());



    }
}