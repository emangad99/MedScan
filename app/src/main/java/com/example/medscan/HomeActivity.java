package com.example.medscan;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

public class HomeActivity extends AppCompatActivity {
Button patient, donor;

DrawerLayout drawerLayout;
ActionBarDrawerToggle toggle;
Toolbar toolbar;
NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        navigationView=findViewById(R.id.navigationview);
        navigationView.setItemIconTintList(null);
        drawerLayout =findViewById(R.id.drawerlayout);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NotNull MenuItem item) {
                int id=item.getItemId();

                switch (id)
                {


                    case  R.id.nav_share:

                        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                        sharingIntent.setType("text/plain");
                        String shareBody =  "http://play.google.com/store/apps/detail?id=" + getPackageName();
                        String shareSub = "Try now";
                        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
                        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                        startActivity(Intent.createChooser(sharingIntent, "Share using"));
                        break;

                    case R.id.nav_logout:
                        startActivity(new Intent(HomeActivity.this,MainActivity.class));
                        break;

                    case R.id.feedback:
                        startActivity(new Intent(HomeActivity.this,Feedback.class));
                        break;


                }
                return false;
            }
        });





        patient=findViewById(R.id.btn_patient);
        donor=findViewById(R.id.btn_donor);

        patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent patient=new Intent(HomeActivity.this,DetailsPatientActivity.class);
                startActivity(patient);
            }
        });

        donor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent donor=new Intent(HomeActivity.this,DetailsDonor.class);
                startActivity(donor);

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NotNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_logout:
        }

        return super.onOptionsItemSelected(item);

    }
}