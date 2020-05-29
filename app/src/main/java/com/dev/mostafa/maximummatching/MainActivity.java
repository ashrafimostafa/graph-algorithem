package com.dev.mostafa.maximummatching;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;

import com.dev.mostafa.maximummatching.database.DataBaseHelper;
import com.dev.mostafa.maximummatching.database.SessionManager;
import com.dev.mostafa.maximummatching.main.DrawGraphFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;



import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;

public class MainActivity extends AppCompatActivity {
    private SessionManager manager;
    private DataBaseHelper dataBaseHelper;

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();

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

}
