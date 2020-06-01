package com.dev.mostafa.maximummatching.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.dev.mostafa.maximummatching.R;

public class OpenSourceActivity extends AppCompatActivity {

    TextView icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_source);
        getSupportActionBar().setBackgroundDrawable(getDrawable(R.drawable.drawer_gradiant));
        getSupportActionBar().setTitle("About");

        icon = findViewById(R.id.open_source_icon);
        icon.setMovementMethod(LinkMovementMethod.getInstance());

    }
}