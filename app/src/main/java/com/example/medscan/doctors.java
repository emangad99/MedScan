package com.example.medscan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class doctors extends AppCompatActivity {

    private RelativeLayout mlayout ,mlayout2 ,mlayout3 ,mlayout4 ,mlayout5;
    private ImageView doc1 , doc2 , doc3 , doc4 , doc5;
    private TextView namdoc1, namdoc2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors);

        mlayout=(RelativeLayout) findViewById(R.id.relative1);
        mlayout2=(RelativeLayout) findViewById(R.id.relative2);
        mlayout3=(RelativeLayout)findViewById(R.id.relative3);
        mlayout4=(RelativeLayout)findViewById(R.id.relative4);
        mlayout5=(RelativeLayout)findViewById(R.id.relative5);

        doc1=(ImageView) findViewById(R.id.doctor1);
        namdoc1=(TextView) findViewById(R.id.name_doc1);

        doc2=(ImageView)findViewById(R.id.doctor2);

        doc3=(ImageView)findViewById(R.id.doctor3);

        doc4=(ImageView)findViewById(R.id.doctor4);

        doc5=(ImageView)findViewById(R.id.doctor5);



        mlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shared = new Intent(doctors.this,shared_doc1.class);



                android.util.Pair[] pairs = new android.util.Pair[2];
                pairs[0]= new android.util.Pair(doc1,"imagetransaction");
                pairs[1]= new android.util.Pair(namdoc1,"nametransaction");


                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(doctors.this , pairs);
                startActivity(shared, options.toBundle());
            }
        });

        mlayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shared2 = new Intent(doctors.this,shared_doc2.class);



                android.util.Pair[] pairs = new android.util.Pair[2];
                pairs[0]= new android.util.Pair(doc2,"imagetransaction2");
                pairs[1]= new android.util.Pair(namdoc1,"nametransaction2");


                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(doctors.this , pairs);
                startActivity(shared2, options.toBundle());

            }
        });

        mlayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shared3 = new Intent(doctors.this,shared_doc3.class);



                android.util.Pair[] pairs = new android.util.Pair[2];
                pairs[0]= new android.util.Pair(doc3,"imagetransaction3");
                pairs[1]= new android.util.Pair(namdoc1,"nametransaction3");


                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(doctors.this , pairs);
                startActivity(shared3, options.toBundle());

            }
        });

        mlayout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shared4 = new Intent(doctors.this,shared_doc4.class);



                android.util.Pair[] pairs = new android.util.Pair[2];
                pairs[0]= new android.util.Pair(doc4,"imagetransaction4");
                pairs[1]= new android.util.Pair(namdoc1,"nametransaction4");


                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(doctors.this , pairs);
                startActivity(shared4, options.toBundle());

            }
        });

        mlayout5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shared5 = new Intent(doctors.this,shared_doc5.class);



                android.util.Pair[] pairs = new android.util.Pair[2];
                pairs[0]= new android.util.Pair(doc5,"imagetransaction5");
                pairs[1]= new android.util.Pair(namdoc1,"nametransaction5");


                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(doctors.this , pairs);
                startActivity(shared5, options.toBundle());

            }
        });

    }
}