package com.example.medscan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class menu_doctors extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    MyAdapter myAdapter;
    ArrayList<UserHelper> list;
    FirebaseAuth fAuth;
    FirebaseDatabase Database;
    FirebaseUser rUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_doctors);

        recyclerView=findViewById(R.id.recyclerView);
        Database = FirebaseDatabase.getInstance();
        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        FirebaseUser rUser = fAuth.getCurrentUser();
        assert rUser != null;
        String userId =rUser.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("Donors");
        //databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("First Name");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list=new ArrayList<>();
        myAdapter=new MyAdapter(this,list);
        recyclerView.setAdapter(myAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot :snapshot.getChildren()){
                    UserHelper userHelper = dataSnapshot.getValue(UserHelper.class);
                    list.add(userHelper);

                }
                myAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void onBackPressed() {
        Intent donor=new Intent(menu_doctors.this, HomeActivity.class);
        startActivity(donor);
    }
}