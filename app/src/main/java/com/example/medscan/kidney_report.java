package com.example.medscan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class kidney_report extends AppCompatActivity {

    RadioButton radioButton,radioButton2,radioButton3,radioButton4,radioButton5,radioButton6,
            radioButton7,radioButton8,radioButton9,radioButton10,radioButton11,radioButton12;
    Button send;
    TextView text;

    TextView blood_pressure,patient_Albumin,patient_blood,patient_serum,patient_white_blood,patient_pressure,sodium,
            patient_urea,patient_potassium,haemoglobin,PacketCellVolum,redbloodcellcount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kidney_report);

        send=findViewById(R.id.btn_submit);
        text=findViewById(R.id.text_try);

        blood_pressure=findViewById(R.id.blood_pressure);
        patient_Albumin=findViewById(R.id.patient_Albumin);
        patient_blood=findViewById(R.id.patient_blood);
        patient_serum=findViewById(R.id.patient_serum);
        patient_white_blood=findViewById(R.id.patient_white_blood);
        patient_pressure=findViewById(R.id.patient_pressure);
        sodium=findViewById(R.id.sodium);
        patient_urea=findViewById(R.id.patient_urea);
        patient_potassium=findViewById(R.id.patient_potassium);
        haemoglobin=findViewById(R.id.haemoglobin);
        PacketCellVolum=findViewById(R.id.PacketCellVolum);
        redbloodcellcount=findViewById(R.id.redbloodcellcount);



        radioButton=findViewById(R.id.normal);
        radioButton2=findViewById(R.id.non_normal);
        radioButton3=findViewById(R.id.non_normal2);
        radioButton4=findViewById(R.id.normal2);
        radioButton5=findViewById(R.id.normal3);
        radioButton6=findViewById(R.id.non_normal3);
        radioButton7=findViewById(R.id.normal4);
        radioButton8=findViewById(R.id.non_normal4);
        radioButton9=findViewById(R.id.normal5);
        radioButton10=findViewById(R.id.non_normal5);
        radioButton11=findViewById(R.id.normal6);
        radioButton12=findViewById(R.id.non_normal6);


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(radioButton.isChecked()){
                    text.setText("1");
                }
                if(radioButton2.isChecked()){
                    text.setText("0");
                }
                if(radioButton3.isChecked()){
                    text.setText("0");
                }
                if(radioButton4.isChecked()){
                    text.setText("1");
                }
                if(radioButton5.isChecked()){
                    text.setText("1");
                }
                if(radioButton6.isChecked()){
                    text.setText("0");
                }
                if(radioButton7.isChecked()){
                    text.setText("1");
                }
                if(radioButton8.isChecked()){
                    text.setText("0");
                }
                if(radioButton9.isChecked()){
                    text.setText("1");
                }
                if(radioButton10.isChecked()){
                    text.setText("0");
                }
                if(radioButton11.isChecked()){
                    text.setText("1");
                }
                if(radioButton12.isChecked()){
                    text.setText("0");
                }
            }
        });
    }
}