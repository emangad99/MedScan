package com.example.medscan.kidneys;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.medscan.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class doctors extends AppCompatActivity {

    private RelativeLayout mlayout ,mlayout2 ,mlayout3 ,mlayout4 ,mlayout5 , mlayout6;
    private ImageView doc1 , doc2 , doc3 , doc4 , doc5 , doc6;
    private TextView namdoc1, namdoc2 ,namdoc3 , namdoc4 ,namdoc5 , namdoc6;
    private TextView desc1, desc2 ,desc3 , desc4 ,desc5 , desc6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors);

        final FloatingActionButton button = findViewById(R.id.fab);

        mlayout=(RelativeLayout) findViewById(R.id.relative1);
        mlayout2=(RelativeLayout) findViewById(R.id.relative2);
        mlayout3=(RelativeLayout)findViewById(R.id.relative3);
        mlayout4=(RelativeLayout)findViewById(R.id.relative4);
        mlayout5=(RelativeLayout)findViewById(R.id.relative5);
        mlayout6=(RelativeLayout)findViewById(R.id.relative6);


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

        doc6=(ImageView)findViewById(R.id.doctor6);
        namdoc6=(TextView) findViewById(R.id.nam_doc6);
        desc6=(TextView) findViewById(R.id.des6);



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

        mlayout6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shared6 = new Intent(doctors.this,shared_doc6.class);



                android.util.Pair[] pairs = new android.util.Pair[3];
                pairs[0]= new android.util.Pair(doc6,"imagetransaction6");
                pairs[1]= new android.util.Pair(namdoc6,"nametransaction6");
                pairs[2]= new android.util.Pair(desc6,"desctransaction6");


                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(doctors.this , pairs);
                startActivity(shared6, options.toBundle());

            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(doctors.this, hospital.class));

            }
        });



    }
}