package com.example.medscan.lungs;

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
import com.example.medscan.screen_item;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class covid_advice extends AppCompatActivity {

    private ViewPager screenpager;
    covidAdapter slide_Adapter;
    TabLayout tabindicator ;
    Button next;
    int position = 0;
    Button backtohome;
    Animation btn_anim;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid_advice);

        tabindicator=findViewById(R.id.tab_indicator);
        next=findViewById(R.id.Next_btn);
        backtohome=findViewById(R.id.covid_ok);
        btn_anim= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.btn_anim);
        String templang = Locale.getDefault().getLanguage();


        List<screen_item> mlist = new ArrayList<>();
        if(templang == "ar")
        {
            mlist.add(new screen_item("اعزل نفسك","ابق في المنزل واعزل نفسك" +
                    " حتى لو كان لديك أعراض خفيفة مثل السعال "+
                    "، صداع وحمى خفيفة ، حتى تتعافى.",R.drawable.isolate));
            mlist.add(new screen_item("غسل اليدين","نظف يديك جيدًا بانتظام باستخدام معقم لليدين يحتوي على الكحول أو اغسلهما بالماء والصابون. هذا يزيل الجراثيم ، بما في ذلك الفيروسات.",R.drawable.wash));
            mlist.add(new screen_item("ارتدي الكمامة ","يساعد ارتداء الكمامة على إبطاء انتشار مرض فيروس كورونا.\n" +
                    "لا تنسى ارتداء كمامة لتجنب نقل العدوى للآخرين.",R.drawable.mask));
            mlist.add(new screen_item("عقم يديك","استخدم معقم اليدين وابتعد مسافة متر واحد على الأقل عن الآخرين لتقليل خطر الإصابة بالعدوى عند السعال أو العطس أو التحدث. حافظ على مسافة أكبر بينك وبين الآخرين عندما تكون في الداخل. كلما زادت المسافة ، كان ذلك أفضل.",R.drawable.hand));
        }
        else
        {
            mlist.add(new screen_item("Isolate yourself","Stay home and isolate yourself\n even if you have mild symptoms such as cough\n, headache and mild fever, until you recover.",R.drawable.isolate));
            mlist.add(new screen_item("Wash hands","Regularly clean your hands thoroughly with an alcohol-based hand sanitizer or wash them with soap and water. This removes germs, including viruses.",R.drawable.wash));
            mlist.add(new screen_item("Wear a mask","Wearing a mask helps slow the spread of coronavirus disease.\n Do not forget to wear a mask to avoid transmitting the infection to others.",R.drawable.mask));
            mlist.add(new screen_item("Hand sanitizer","Use hand sanitizer and keep at least 1 meter away from others to reduce the risk of infection when they cough, sneeze or speak. Keep an even greater distance from others when indoors. The greater the distance, the better.",R.drawable.hand));
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
                Intent i=new Intent(covid_advice.this, HomeActivity.class);
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