package com.dev.mostafa.maximummatching;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.dev.mostafa.maximummatching.database.DataBaseHelper;
import com.dev.mostafa.maximummatching.database.SessionManager;
import com.dev.mostafa.maximummatching.main.AboutActivity;
import com.dev.mostafa.maximummatching.main.DocumentActivity;
import com.dev.mostafa.maximummatching.main.DrawGraphFragment;

import com.dev.mostafa.maximummatching.main.OpenSourceActivity;
import com.google.android.material.navigation.NavigationView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private SessionManager manager;
    private DataBaseHelper dataBaseHelper;

//    private AppBarConfiguration mAppBarConfiguration;

    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setBackground(getResources().getDrawable(R.drawable.drawer_gradiant));
        toolbar.setTitle("Graph Theory");
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this , drawer , toolbar , R.string.navigation_drawer_open ,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_drawer);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setMainFrame(new DrawGraphFragment());
        manager = new SessionManager(this);
        dataBaseHelper = new DataBaseHelper(this);

        if (manager.getFirstTime()){
            dataBaseHelper.addAlgorithm("Maximum Matching" , "a" , "a");
            manager.setFirstTime(false);
        }



    }

    /**
     * setting main fragment of main activity
     * @param fragment target fragment
     */
    private void setMainFrame(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_document:
                    startActivity(new Intent(MainActivity.this , DocumentActivity.class));
                break;
            case R.id.nav_open_source:
                startActivity(new Intent(MainActivity.this , OpenSourceActivity.class));
                break;
            case R.id.nav_about:
                startActivity(new Intent(MainActivity.this , AboutActivity.class));

                break;
            case R.id.nav_exit:
                finishAndRemoveTask();
                break;

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
