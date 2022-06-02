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

public class melanoma_advice extends AppCompatActivity {

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
        setContentView(R.layout.activity_melanoma_advice);

        tabindicator=findViewById(R.id.tab_indicator);
        next=findViewById(R.id.Next_btn);
        backtohome=findViewById(R.id.covid_ok);
        btn_anim= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.btn_anim);

        List<screen_item> mlist = new ArrayList<>();
        if(templang == "ar")
        {
            mlist.add(new screen_item("اشرب سوائل","اشرب الكثير من السوائل للوقاية من الجفاف. اختر الماء والسوائل الصافية الأخرى. إذا كنت تعاني من أمراض الكلى أو القلب أو الكبد وتضطر إلى الحد من تناول السوائل ، فتحدث مع طبيبك قبل زيادة كمية السوائل التي تتناولها.",R.drawable.water3));
            mlist.add(new screen_item("حساء صافي ","عندما تكون قادرًا على تناول الطعام ، جرب الحساء الصافي والأطعمة الخفيفة والسوائل حتى تختفي جميع الأعراض لمدة 12 إلى 48 ساعة.",R.drawable.soup));
            mlist.add(new screen_item("لا تدخن ","لا تدخن. يمكن للتدخين أن يبطئ الشفاء. إذا كنت بحاجة إلى مساعدة في الإقلاع ، فتحدث إلى طبيبك حول برامج الإقلاع عن التدخين والأدوية. يمكن أن يزيد ذلك من فرصك في الإقلاع عن التدخين للأبد.",R.drawable.smoke));
            mlist.add(new screen_item("احمي نفسك ","ارتدِ قبعة واسعة الحواف وقميصًا بأكمام طويلة وبنطالًا إذا كنت ستبقى بالخارج لفترة طويلة جدًا.",R.drawable.wear2));

        }
        else
        {
            mlist.add(new screen_item("Drink fluids","Drink plenty of fluids to prevent dehydration. Choose water and other clear liquids. If you have kidney, heart, or liver disease and have to limit fluids, talk with your doctor before you increase the amount of fluids you drink.",R.drawable.water3));
            mlist.add(new screen_item("Clear soups","When you are able to eat, try clear soups, mild foods, and liquids until all symptoms are gone for 12 to 48 hours.",R.drawable.soup));
            mlist.add(new screen_item("No Smoking","Do not smoke. Smoking can slow healing. If you need help quitting, talk to your doctor about stop-smoking programs and medicines. These can increase your chances of quitting for good.",R.drawable.smoke));
            mlist.add(new screen_item("Protect yourself","Wear a wide-brimmed hat, a long-sleeved shirt, and pants if you are going to be outdoors for very long.",R.drawable.wear2));

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
                Intent i=new Intent(melanoma_advice.this, HomeActivity.class);
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