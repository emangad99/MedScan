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

public class seborrheic_keratosis_advice extends AppCompatActivity {

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
        setContentView(R.layout.activity_seborrheic_keratosis_advice);

        tabindicator=findViewById(R.id.tab_indicator);
        next=findViewById(R.id.Next_btn);
        backtohome=findViewById(R.id.covid_ok);
        btn_anim= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.btn_anim);

        List<screen_item> mlist = new ArrayList<>();
        if(templang == "ar")
        {
            mlist.add(new screen_item("قم بتغطيته بضمادة","إذا كانت الملابس تزعج التقران الدهني ، فقم بتغطيتها بضمادة لمنع الاحتكاك والنزيف.",R.drawable.bandage));
            mlist.add(new screen_item("الماء والصابون ","إذا تمت إزالة التقرن الدهني ، فقم بتنظيف المنطقة بالماء والصابون مرتين في اليوم ما لم يعطيك طبيبك تعليمات مختلفة. لا تستخدم بيروكسيد الهيدروجين أو الكحول ، حيث يمكن أن يبطئ الشفاء.",R.drawable.soap));
            mlist.add(new screen_item("استخدم واقي من الشمس","استخدم واقيًا من الشمس واسع الطيف مع عامل حماية من الشمس لا يقل عن 30 ، حتى في الأيام الملبدة بالغيوم. يوصى بوضع كمية كبيرة من الكريم الواقي من الشمس ، وتكرار ذلك كل ساعتين أو أقل عند السباحة أو التعرق.",R.drawable.cream));
            mlist.add(new screen_item("فازلين","يمكنك تغطية الجرح بطبقة رقيقة من الفازلين مثل الفازلين وضمادة غير لاصقة.",R.drawable.vasline));
        }
        else
        {
            mlist.add(new screen_item("Cover it with a bandage","If clothing irritates your seborrheic keratosis, cover it with a bandage to prevent rubbing and bleeding.",R.drawable.bandage));
            mlist.add(new screen_item("Soap and water","If you have a seborrheic keratosis removed, clean the area with soap and water two times a day unless your doctor gives you different instructions. Don't use hydrogen peroxide or alcohol, which can slow healing.",R.drawable.soap));
            mlist.add(new screen_item("Use sunblock","Use a broad spectrum sunscreen with an SPF of at least 30, even on cloudy days. It is recommended to apply a generous amount of sunscreen, and to repeat this every two hours or less when swimming or sweating.",R.drawable.cream));
            mlist.add(new screen_item("Vaseline","You may cover the wound with a thin layer of petroleum jelly, such as Vaseline, and a non-stick bandage.",R.drawable.vasline));
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
                Intent i=new Intent(seborrheic_keratosis_advice.this, HomeActivity.class);
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