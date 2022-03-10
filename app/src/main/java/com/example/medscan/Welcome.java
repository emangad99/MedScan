package com.example.medscan;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class Welcome extends AppCompatActivity {
Button btn_start;

boolean lang_selected=true;
Context context;
Resources resources;
TextView txt1,txt2,txt3 , lan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        txt1=findViewById(R.id.txt1);
        txt2=findViewById(R.id.txt2);
        txt3=findViewById(R.id.txt3);
        lan=findViewById(R.id.lan_dialog);

        lan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] language ={"English","Arabic"};

                int checked_item;

                if(lang_selected)
                {
                    checked_item=0;
                }
                else {
                    checked_item=1;
                }
                final AlertDialog.Builder builder= new AlertDialog.Builder(Welcome.this);
                builder.setTitle("Select a language")
                        .setSingleChoiceItems(language, checked_item, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                lan.setText(language[which]);
                                if(language[which].equals("English"))
                                {
                                    context=localHelper.setLocale(Welcome.this,"en");

                                    resources=context.getResources();

                                    txt1.setText(resources.getString(R.string.Near_to_you));
                                    txt2.setText(resources.getString(R.string.text));
                                    txt3.setText(resources.getString(R.string.text2));
                                    btn_start.setText(resources.getString(R.string.get_started));

                                }
                                if(language[which].equals("Arabic"))
                                {
                                    context=localHelper.setLocale(Welcome.this,"ar");
                                    resources=context.getResources();

                                    txt1.setText(resources.getString(R.string.Near_to_you));
                                    txt2.setText(resources.getString(R.string.text));
                                    txt3.setText(resources.getString(R.string.text2));
                                    btn_start.setText(resources.getString(R.string.get_started));


                                }

                            }
                        })
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.create().show();
            }
        });



        btn_start=findViewById(R.id.btn_started);
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Welcome.this,Login.class);
                startActivity(i);

            }
        });
    }


}