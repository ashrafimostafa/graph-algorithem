package com.dev.mostafa.maximummatching.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.dev.mostafa.maximummatching.R;

public class DocumentActivity extends AppCompatActivity {

    ListView listView;
    ArrayAdapter adapter;
    String[] algorithm = {"Maximum Matching", "Kruskal", "Dijkstra", "Bellman Ford"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document);
        getSupportActionBar().setBackgroundDrawable(getDrawable(R.drawable.drawer_gradiant));
        getSupportActionBar().setTitle("Document");

        listView = findViewById(R.id.document_list);

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, algorithm);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(DocumentActivity.this, "Add in next update", Toast.LENGTH_SHORT).show();
            }
        });

    }
}