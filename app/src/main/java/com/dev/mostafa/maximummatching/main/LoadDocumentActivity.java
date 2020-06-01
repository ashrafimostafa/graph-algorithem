package com.dev.mostafa.maximummatching.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.dev.mostafa.maximummatching.R;

public class LoadDocumentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_document);
        getSupportActionBar().setBackgroundDrawable(getDrawable(R.drawable.drawer_gradiant));
        getSupportActionBar().setTitle("Maximum Matching");
    }
}