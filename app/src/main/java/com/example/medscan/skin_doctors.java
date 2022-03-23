package com.example.medscan;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class skin_doctors extends AppCompatActivity {

    private RelativeLayout mlayout ,mlayout2 ,mlayout3 ,mlayout4 ,mlayout5 , mlayout6;
    private ImageView doc1 , doc2 , doc3 , doc4 , doc5 , doc6;
    private TextView namdoc1, namdoc2 ,namdoc3 , namdoc4 ,namdoc5 , namdoc6;
    private TextView desc1, desc2 ,desc3 , desc4 ,desc5 , desc6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skin_doctors);

        final FloatingActionButton button = findViewById(R.id.fab3);


        mlayout=(RelativeLayout) findViewById(R.id.skin_relative1);
        mlayout2=(RelativeLayout) findViewById(R.id.skin_relative2);

        doc1=(ImageView) findViewById(R.id.skin_doc1);
        namdoc1=(TextView) findViewById(R.id.skin_name_doc1);
        desc1=(TextView) findViewById(R.id.skin_des1);

        doc2=(ImageView)findViewById(R.id.skin_doc2);
        namdoc2=(TextView) findViewById(R.id.skin_name_doc2);
        desc2=(TextView) findViewById(R.id.skin_des2);

        mlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shared = new Intent(skin_doctors.this,skin_doc1.class);



                android.util.Pair[] pairs = new android.util.Pair[3];
                pairs[0]= new android.util.Pair(doc1,"imagetransaction");
                pairs[1]= new android.util.Pair(namdoc1,"nametransaction");
                pairs[2]= new android.util.Pair(desc1,"desctransaction");


                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(skin_doctors.this , pairs);
                startActivity(shared, options.toBundle());
            }
        });
        mlayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shared2 = new Intent(skin_doctors.this,skin_doc2.class);



                android.util.Pair[] pairs = new android.util.Pair[3];
                pairs[0]= new android.util.Pair(doc2,"imagetransaction2");
                pairs[1]= new android.util.Pair(namdoc2,"nametransaction2");
                pairs[2]= new android.util.Pair(desc2,"desctransaction2");


                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(skin_doctors.this , pairs);
                startActivity(shared2, options.toBundle());

            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(skin_doctors.this,skin_hospitals.class));

            }
        });

    }
}