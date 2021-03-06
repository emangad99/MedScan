package com.example.medscan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medscan.chat.chat_home;
import com.example.medscan.login.Login;
import com.example.medscan.menu.Feedback;
import com.example.medscan.menu.Instruction;
import com.example.medscan.menu.PatientEdit;
import com.example.medscan.menu.best_doctors;
import com.example.medscan.menu.delete_account;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;


public class HomeActivity extends AppCompatActivity {

    Button patient, donor;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;
    NavigationView navigationView;
    private long backpressedtime;
    private Toast backtoast;
    SessionManager sessionManager;
    CircleImageView userImg;
    TextView name , email ;
    FirebaseAuth authProfile;
    FirebaseUser firebaseUser ;
    DatabaseReference databaseReference;
    String profileUrl,username,useremail;
    //TextView or;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        authProfile = FirebaseAuth.getInstance();
        firebaseUser = authProfile.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sessionManager=new SessionManager(getApplicationContext());

        navigationView=findViewById(R.id.navigationview);
        navigationView.setItemIconTintList(null);
        drawerLayout =findViewById(R.id.drawerlayout);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
        navigationView.bringToFront();

        View view = navigationView.inflateHeaderView(R.layout.menu_header);

        userImg=view.findViewById(R.id.pic);
        name=view.findViewById(R.id.user_name);
        email=view.findViewById(R.id.user_email);



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

                        sessionManager.setLogin(false);
                        sessionManager.setUsername("");
                        status("Offline");
                        startActivity(new Intent(HomeActivity.this,MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        break;

                    case R.id.feedback:
                        startActivity(new Intent(HomeActivity.this, Feedback.class));
                        break;

                    case R.id.recommend:
                        startActivity(new Intent(HomeActivity.this, best_doctors.class));
                        break;

                    case R.id.about_us:
                        startActivity(new Intent(HomeActivity.this, Instruction.class));
                        break;

                    case R.id.edit_profile:
                        startActivity(new Intent(HomeActivity.this, PatientEdit.class));
                        break;

                    case R.id.doctors:
                        startActivity(new Intent(HomeActivity.this, menu_doctors.class));
                        break;

                    case R.id.chat:
                        startActivity(new Intent(HomeActivity.this, chat_home.class));
                        break;

                    case R.id.nav_delete:

                            Intent delete = new Intent(HomeActivity.this, delete_account.class);
                         startActivity(delete);

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
                Intent patient=new Intent(HomeActivity.this,choose_field.class);
                startActivity(patient);
            }
        });

        donor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent donor=new Intent(HomeActivity.this, DetailsDoctor.class);
                startActivity(donor);

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        String templang = Locale.getDefault().getLanguage();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    profileUrl=snapshot.child("image").getValue().toString();
                    username=snapshot.child("Full_Name").getValue().toString();
                    useremail=snapshot.child("Email").getValue().toString();

                    Picasso.get().load(profileUrl).into(userImg);
                    name.setText(username);
                    email.setText(useremail);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                if(templang == "ar")
                {
                    Toast.makeText(HomeActivity.this, "?????? ??????", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(HomeActivity.this, "sorry", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        String templang = Locale.getDefault().getLanguage();

        if(backpressedtime + 2000 >System.currentTimeMillis())
        {
            finishAffinity();
            super.onBackPressed();
            return;

        }else{
            if(templang == "ar")
            {
                backtoast= Toast.makeText(getBaseContext(), "???????? ?????????? ?????????????? ????????????", Toast.LENGTH_SHORT);
                backtoast.show();
            }
            else
            {
                backtoast= Toast.makeText(getBaseContext(), "double click to exit ", Toast.LENGTH_SHORT);
                backtoast.show();
            }
        }
        backpressedtime =System.currentTimeMillis();

    }

    private void status (String status)
    {
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        String savecurrentDate, savecurrentTime;

        Calendar calForDate =Calendar.getInstance();
        SimpleDateFormat currenrDate= new SimpleDateFormat("MMM dd, yyyy");
        savecurrentDate=currenrDate.format(calForDate.getTime());

        Calendar calFortime =Calendar.getInstance();
        SimpleDateFormat currenrtime= new SimpleDateFormat("hh:mm a");
        savecurrentTime=currenrtime.format(calFortime.getTime());

        HashMap<String ,Object> map = new HashMap<>();
        map.put("currentTime",savecurrentTime);
        map.put("currentdate",savecurrentDate);
        map.put("type",status);

        databaseReference.updateChildren(map);


    }

    @Override
    protected void onResume() {
        super.onResume();
        status("Online");
    }

    @Override
    protected void onPause() {
        super.onPause();
        status("Offline");
    }
}