package com.example.medscan.skin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.example.medscan.HomeActivity;
import com.example.medscan.R;
import com.example.medscan.lungs.covidAdapter;
import com.example.medscan.lungs.successful_advice;
import com.example.medscan.screen_item;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class intraepithelial_carcinoma_advice extends AppCompatActivity {
    private ViewPager screenpager;
    covidAdapter slide_Adapter;
    TabLayout tabindicator ;
    Button next;
    int position = 0;
    Button backtohome;
    Animation btn_anim;
    String templang = Locale.getDefault().getLanguage();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intraepithelial_carcinoma_advice);
        tabindicator=findViewById(R.id.tab_indicator);
        next=findViewById(R.id.Next_btn);
        backtohome=findViewById(R.id.covid_ok);
        btn_anim= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.btn_anim);

        List<screen_item> mlist = new ArrayList<>();
        if(templang == "ar")
        {
            mlist.add(new screen_item("تأكد من المرض ","يأخذ الطبيب خزعة باستخدام إبرة طويلة رفيعة تسحب عينة من الكتلة أو الجسم المشتبه في إصابته بالساركوما لفحصها في المختبر. يقوم أخصائي علم الأمراض بتحليل العينة لتحديد ما إذا كانت سرطانًا ، وإذا كان الأمر كذلك ، فنوعها وشدتها.",R.drawable.pp));
            mlist.add(new screen_item("المتلازمات الجينية","خطر الإصابة بسرطان داخل الظهارة يمكن أن يكون موروثًا من كلا الوالدين.",R.drawable.dna));
            mlist.add(new screen_item("تعرض للاشعاع"," التعرض للإشعاع قد يؤدي العلاج الإشعاعي السابق لأنواع أخرى من السرطان إلى زيادة خطر الإصابة بساركوما الأنسجة الرخوة.",R.drawable.bed));
            mlist.add(new screen_item("التعرض للمواد الكيميائية","قد يؤدي التعرض لبعض المواد الكيميائية ، مثل مبيدات الأعشاب والزرنيخ والديوكسين ، إلى زيادة خطر الإصابة بسرطان داخل الظهارة",R.drawable.chemicals));
        }
        else
        {
            mlist.add(new screen_item("Make sure","The doctor takes a biopsy using a long, thin needle that withdraws a sample of the lump or body suspected of sarcoma for laboratory testing. A pathologist analyzes the sample to determine if it is cancer and, if so, its type and severity.",R.drawable.pp));
            mlist.add(new screen_item("genetic syndromes","Intraepithelial carcinoma risk it can be inherited from both parents. ",R.drawable.dna));
            mlist.add(new screen_item("Radiation exposure"," Radiation exposure Previous radiation treatment for other types of cancer may increase the risk of soft tissue sarcoma.",R.drawable.bed));
            mlist.add(new screen_item("Exposure to chemicals","Exposure to certain chemicals, such as herbicides, arsenic, and dioxin, may increase the risk of intraepithelial carcinoma",R.drawable.chemicals));
        }

        screenpager=findViewById(R.id.screen);
        slide_Adapter=new covidAdapter(this,mlist);
        screenpager.setAdapter(slide_Adapter);
        tabindicator.setupWithViewPager(screenpager);



        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            window.setStatusBarColor(this.getResources().getColor(R.color.color9));
        }

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                position = screenpager.getCurrentItem();
                if (position< mlist.size())
                {
                    position ++ ;
                    screenpager.setCurrentItem(position);
                }
                if (position==mlist.size()-1)
                {
                    loadlastscreen();
                }

            }
        });

        tabindicator.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == mlist.size()-1)
                {
                    loadlastscreen();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        backtohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(intraepithelial_carcinoma_advice.this, HomeActivity.class);
                startActivity(i);
            }
        });




    }

    private void loadlastscreen() {

        next.setVisibility(View.INVISIBLE);
        backtohome.setVisibility(View.VISIBLE);
        tabindicator.setVisibility(View.INVISIBLE);

        backtohome.setAnimation(btn_anim);

    }
}