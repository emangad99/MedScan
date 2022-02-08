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
    private TextView namdoc1, namdoc2 ,namdoc3 , namdoc4 ,namdoc5;
    private TextView desc1, desc2 ,desc3 , desc4 ,desc5;


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
        desc1=(TextView) findViewById(R.id.des1);

        doc2=(ImageView)findViewById(R.id.doctor2);
        namdoc2=(TextView) findViewById(R.id.nam_doc2);
        desc2=(TextView) findViewById(R.id.des2);

        doc3=(ImageView)findViewById(R.id.doctor3);
        namdoc3=(TextView) findViewById(R.id.nam_doc3);
        desc3=(TextView) findViewById(R.id.des3);

        doc4=(ImageView)findViewById(R.id.doctor4);
        namdoc4=(TextView) findViewById(R.id.nam_doc4);
        desc4=(TextView) findViewById(R.id.des4);

        doc5=(ImageView)findViewById(R.id.doctor5);
        namdoc5=(TextView) findViewById(R.id.nam_doc5);
        desc5=(TextView) findViewById(R.id.des5);



        mlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shared = new Intent(doctors.this,shared_doc1.class);



                android.util.Pair[] pairs = new android.util.Pair[3];
                pairs[0]= new android.util.Pair(doc1,"imagetransaction");
                pairs[1]= new android.util.Pair(namdoc1,"nametransaction");
                pairs[2]= new android.util.Pair(desc1,"desctransaction");


                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(doctors.this , pairs);
                startActivity(shared, options.toBundle());
            }
        });

        mlayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shared2 = new Intent(doctors.this,shared_doc2.class);



                android.util.Pair[] pairs = new android.util.Pair[3];
                pairs[0]= new android.util.Pair(doc2,"imagetransaction2");
                pairs[1]= new android.util.Pair(namdoc2,"nametransaction2");
                pairs[2]= new android.util.Pair(desc2,"desctransaction2");


                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(doctors.this , pairs);
                startActivity(shared2, options.toBundle());

            }
        });

        mlayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shared3 = new Intent(doctors.this,shared_doc3.class);



                android.util.Pair[] pairs = new android.util.Pair[3];
                pairs[0]= new android.util.Pair(doc3,"imagetransaction3");
                pairs[1]= new android.util.Pair(namdoc3,"nametransaction3");
                pairs[2]= new android.util.Pair(desc3,"desctransaction3");


                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(doctors.this , pairs);
                startActivity(shared3, options.toBundle());

            }
        });

        mlayout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shared4 = new Intent(doctors.this,shared_doc4.class);



                android.util.Pair[] pairs = new android.util.Pair[3];
                pairs[0]= new android.util.Pair(doc4,"imagetransaction4");
                pairs[1]= new android.util.Pair(namdoc4,"nametransaction4");
                pairs[2]= new android.util.Pair(desc4,"desctransaction4");


                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(doctors.this , pairs);
                startActivity(shared4, options.toBundle());

            }
        });

        mlayout5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shared5 = new Intent(doctors.this,shared_doc5.class);



                android.util.Pair[] pairs = new android.util.Pair[3];
                pairs[0]= new android.util.Pair(doc5,"imagetransaction5");
                pairs[1]= new android.util.Pair(namdoc5,"nametransaction5");
                pairs[2]= new android.util.Pair(desc5,"desctransaction5");


                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(doctors.this , pairs);
                startActivity(shared5, options.toBundle());

            }
        });

    }
}