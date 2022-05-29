package com.example.medscan.eyes;

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
import com.example.medscan.screen_item;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class uveitis_advice extends AppCompatActivity {

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
        setContentView(R.layout.activity_uveitis_advice);

        tabindicator=findViewById(R.id.tab_indicator);
        next=findViewById(R.id.Next_btn);
        backtohome=findViewById(R.id.covid_ok);
        btn_anim= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.btn_anim);

        List<screen_item> mlist = new ArrayList<>();

        if(templang == "ar")
        {
            mlist.add(new screen_item("قطرات العين","قطرات العين الموسعة لحدقة العين، تساعد في تسريع عملية الشفاء، وتخفيف شدة الألم.",R.drawable.eyes_advice1));
            mlist.add(new screen_item("الادوية","المضادات الحيوية ومضادات الفيروسات، في حال الإصابة بالعدوى.",R.drawable.eyes_advice3));
            mlist.add(new screen_item("نظارات شمسية","ارتداء نظارات شمسية داكنة اللون، في حال أصبحت عيني الشخص حساسة تجاه الضوء.",R.drawable.eyes_advice2));
            mlist.add(new screen_item("تهدئة العين","وضع قطعة قماش نظيفة ودافئة على العين، بهدف تهدئتها وتخفيف شدة الأعراض.",R.drawable.eyes_advice4));
        }
        else {
            mlist.add(new screen_item("Eye drops", " Eye drops that dilate the pupil of the eyes, help speed up the healing process and relieve pain.", R.drawable.eyes_advice1));
            mlist.add(new screen_item("pharmaceutical", "Antibiotics and antivirals, in case of infection.", R.drawable.eyes_advice3));
            mlist.add(new screen_item("Sunglasses", "Wear dark-colored sunglasses, in case the person's eyes become sensitive to light.", R.drawable.eyes_advice2));
            mlist.add(new screen_item("Soothe the eye", "Placing a clean, warm cloth over the eye, in order to soothe it and relieve symptoms. ", R.drawable.eyes_advice4));

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
                Intent i=new Intent(uveitis_advice.this, HomeActivity.class);
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