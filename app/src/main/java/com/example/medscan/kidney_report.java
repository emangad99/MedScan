package com.example.medscan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.example.medscan.kidneys.KidneyApiInterface;
import com.example.medscan.kidneys.KidneyResult;
import com.example.medscan.kidneys.kidney_failed;
import com.example.medscan.kidneys.kidney_successful;
import com.example.medscan.skin.SkinApiInterface;
import com.example.medscan.skin.SkinResult;
import com.example.medscan.skin.intraepithelial_carcinoma;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class kidney_report extends AppCompatActivity {

    RadioGroup g1;

    RadioButton radioButton,radioButton2,radioButton3,radioButton4,radioButton5,radioButton6,
            radioButton7,radioButton8,radioButton9,radioButton10,radioButton11,radioButton12;
    Button send;
    TextView pus_cell, pus_cell_climps, hypertension, diabete, peda_adema, anemia;

    TextView blood_pressure,patient_Albumin,patient_blood,patient_serum,patient_white_blood,patient_pressure,sodium,
            patient_urea,patient_potassium,haemoglobin,packetCellVolum,redbloodcellcount;

    ProgressBar k_prog;

    String url = "https://ai-team2.herokuapp.com/kidney";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kidney_report);

        k_prog= findViewById(R.id.k_prog);
        k_prog.setVisibility(View.INVISIBLE);
        send=findViewById(R.id.btn_submit);
        pus_cell=findViewById(R.id.pc_text);
        pus_cell_climps=findViewById(R.id.pcc_text);
        hypertension=findViewById(R.id.htn_text);
        diabete=findViewById(R.id.dm_text);
        peda_adema=findViewById(R.id.pe_text);
        anemia=findViewById(R.id.ane_text);

        pus_cell.setVisibility(View.INVISIBLE);
        pus_cell_climps.setVisibility(View.INVISIBLE);
        hypertension.setVisibility(View.INVISIBLE);
        diabete.setVisibility(View.INVISIBLE);
        peda_adema.setVisibility(View.INVISIBLE);
        anemia.setVisibility(View.INVISIBLE);


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
        packetCellVolum=findViewById(R.id.PacketCellVolum);
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

                if (radioButton.isChecked()) {
                    pus_cell.setText("1");
                }
                if (radioButton2.isChecked()) {
                    pus_cell.setText("0");
                }
                if (radioButton3.isChecked()) {
                    pus_cell_climps.setText("0");
                }
                if (radioButton4.isChecked()) {
                    pus_cell_climps.setText("1");
                }
                if (radioButton5.isChecked()) {
                    hypertension.setText("1");
                }
                if (radioButton6.isChecked()) {
                    hypertension.setText("0");
                }
                if (radioButton7.isChecked()) {
                    diabete.setText("1");
                }
                if (radioButton8.isChecked()) {
                    diabete.setText("0");
                }
                if (radioButton9.isChecked()) {
                    peda_adema.setText("1");
                }
                if (radioButton10.isChecked()) {
                    peda_adema.setText("0");
                }
                if (radioButton11.isChecked()) {
                    anemia.setText("1");
                }
                if (radioButton12.isChecked()) {
                    anemia.setText("0");
                }


                send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        result(blood_pressure.getText().toString(), patient_pressure.getText().toString(), patient_Albumin.getText().toString(),
                                pus_cell.getText().toString(), pus_cell_climps.getText().toString(), patient_blood.getText().toString(), patient_urea.getText().toString(),
                                patient_serum.getText().toString(), sodium.getText().toString(), patient_potassium.getText().toString(),
                                haemoglobin.getText().toString(), packetCellVolum.getText().toString(), patient_white_blood.getText().toString(), redbloodcellcount.getText().toString(),
                                hypertension.getText().toString(), diabete.getText().toString(), peda_adema.getText().toString(), anemia.getText().toString());

                        //result();

                        k_prog.setVisibility(View.VISIBLE);


                    }



                });
            }


        });
    }

    // hit the API -> Volley

    //public void result(){

    public void result(String bp, String sg, String al, String pc, String pcc, String bgr, String bu, String sc, String sod, String pot,
                       String hemo, String pcv, String wc, String rc, String htn, String dm, String pe, String ane) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ai-team2.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        KidneyApiInterface kidneyApiInterface = retrofit.create(KidneyApiInterface.class);
        HashMap body = new HashMap<String, String>();
        body.put("blood_pressure", bp );
        body.put("patient_pressure", sg);
        body.put("patient_Albumin", al);
        body.put("pus_cell", pc);
        body.put("pus_cell_climps", pcc);
        body.put("patient_blood", bgr);
        body.put("patient_urea", bu);
        body.put("patient_serum", sc);
        body.put("sodium", sod);
        body.put("patient_potassium", pot);
        body.put("haemoglobin", hemo);
        body.put("PacketCellVolum", pcv);
        body.put("patient_white_blood", wc);
        body.put("redbloodcellcount", rc);
        body.put("hypertension", htn);
        body.put("diabete", dm);
        body.put("peda_adema", pe);
        body.put("anemia", ane);
        Call<KidneyResult> call = kidneyApiInterface.kidneyApi(body);
        call.enqueue(new Callback<KidneyResult>() {

            @Override
            public void onResponse(@NonNull Call<KidneyResult> call, @NonNull retrofit2.Response<KidneyResult> response) {
                if (response.isSuccessful()) {
                    if (response.body().getData() != null) {

                        if (response.body().getData().toString().equals("0")) {

                            Intent cdk = new Intent(kidney_report.this, kidney_failed.class);
                            startActivity(cdk);

                            //Toast.makeText(getApplicationContext(), "Sorry! Your Are Infected with Actinic Keratosis And Intraepithelial Carcinoma", Toast.LENGTH_LONG).show();
                            k_prog.setVisibility(View.INVISIBLE);
                        } else {

                            Intent cdk = new Intent(kidney_report.this, kidney_successful.class);
                            startActivity(cdk);

                            //Toast.makeText(getApplicationContext(), "Healthy Kidneys", Toast.LENGTH_LONG).show();
                            k_prog.setVisibility(View.INVISIBLE);
                        }

                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "sorry! somthing went wrong", Toast.LENGTH_LONG).show();
                    k_prog.setVisibility(View.INVISIBLE);
                }


            }

            @Override
            public void onFailure(Call<KidneyResult> call, Throwable t) {

                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();


            }

        });


    /*public void result(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {

                        try {
                            JSONObject jsonObject = new JSONObject((String) response);
                            String data = jsonObject.getString("data");
                            if(data.equals("0")){
                                Toast.makeText(getApplicationContext(), "Sorry! Your Are Infected with Actinic Keratosis And Intraepithelial Carcinoma", Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(getApplicationContext(), "Healthy Kidneys", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(kidney_report.this, "error message", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map getParams(){
                Map params = new HashMap();
                params.put("blood_pressure", blood_pressure.getText().toString());
                params.put("patient_pressure", patient_pressure.getText().toString());
                params.put("patient_Albumin", patient_Albumin.getText().toString());
                params.put("pus_cell", pus_cell.getText().toString());
                params.put("pus_cell_climps", pus_cell_climps.getText().toString());
                params.put("patient_blood", patient_blood.getText().toString());
                params.put("patient_urea", patient_urea.getText().toString());
                params.put("patient_serum", patient_serum.getText().toString());
                params.put("sodium", sodium.getText().toString());
                params.put("patient_potassium", patient_potassium.getText().toString());
                params.put("haemoglobin", haemoglobin.getText().toString());
                params.put("packetCellVolum", packetCellVolum.getText().toString());
                params.put("patient_white_blood", patient_white_blood.getText().toString());
                params.put("redbloodcellcount", redbloodcellcount.getText().toString());
                params.put("hypertension", hypertension.getText().toString());
                params.put("diabete", diabete.getText().toString());
                params.put("peda_adema ", peda_adema.getText().toString());
                params.put("anemia", anemia.getText().toString());


                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(kidney_report.this);
        queue.add(stringRequest);*/



    }


}