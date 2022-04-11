package com.example.medscan.skin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.medscan.HomeActivity;
import com.example.medscan.R;
import com.example.medscan.choose_field;
import com.example.medscan.menu_doctors;
import com.example.medscan.skin_upload;

public class skin_menu extends AppCompatActivity {

    Button ok;

    TextView skin1 , skin2 , skin3 , skin4 , skin5 , skin6 , skin7 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skin_menu);

        skin1=findViewById(R.id.actinic_keratosis);
        skin2=findViewById(R.id.intraepithelial_carcinoma);
        skin3=findViewById(R.id.basal_cell_carcinoma);
        skin4=findViewById(R.id.seborrheic_keratosis);
        skin5=findViewById(R.id.dermatofibroma);
        skin6=findViewById(R.id.melanoma);
        skin7=findViewById(R.id.melanocytic_nevi);
        ok=findViewById(R.id.con_btn);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent skin = new Intent(skin_menu.this, skin_upload.class);
                startActivity(skin);
            }
        });

        skin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent skin1 = new Intent(skin_menu.this, actinic_keratosis_info.class);
                startActivity(skin1);
            }
        });

        skin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent skin1 = new Intent(skin_menu.this, intraepithelial_carcinoma_info.class);
                startActivity(skin1);

            }
        });

        skin3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent skin1 = new Intent(skin_menu.this, basal_cell_carcinoma_info.class);
                startActivity(skin1);

            }
        });

        skin4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent skin1 = new Intent(skin_menu.this, seborrheic_keratosis_info.class);
                startActivity(skin1);

            }
        });

        skin5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent skin1 = new Intent(skin_menu.this, dermatofibroma_info.class);
                startActivity(skin1);

            }
        });

        skin6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent skin1 = new Intent(skin_menu.this, melanoma_info.class);
                startActivity(skin1);

            }
        });

        skin7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent skin1 = new Intent(skin_menu.this, melanocytic_nevi_info.class);
                startActivity(skin1);

            }
        });


    }
    public void onBackPressed() {
        Intent donor=new Intent(skin_menu.this, HomeActivity.class);
        startActivity(donor);
    }
}