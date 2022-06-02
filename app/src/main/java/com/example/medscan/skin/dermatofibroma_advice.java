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

public class dermatofibroma_advice extends AppCompatActivity {

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
        setContentView(R.layout.activity_dermatofibroma_advice);

        tabindicator=findViewById(R.id.tab_indicator);
        next=findViewById(R.id.Next_btn);
        backtohome=findViewById(R.id.covid_ok);
        btn_anim= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.btn_anim);

        List<screen_item> mlist = new ArrayList<>();
        if(templang == "ar")
        {
            mlist.add(new screen_item("كن لطيفا مع جسدك","كن لطيفا مع جسدك وعقلك. يمكن أن يساهم الحصول على قسط كافٍ من النوم وتناول نظام غذائي صحي واستغراق الوقت في القيام بأشياء تستمتع بها في الشعور العام بالتوازن في حياتك ويساعد في تقليل التوتر.",R.drawable.relax));
            mlist.add(new screen_item(
                    "اذهب الى الطبيب","في حين أنه عادة ما يكون حميدًا ، يجب المتابعة مع الطبيب لتجنب كونه ضارًا.",R.drawable.go_doctor));
            mlist.add(new screen_item("كريم واقي شمس","قبل قضاء الوقت في الهواء الطلق ، استخدم واقيًا من الشمس واسع الطيف مع عامل حماية من الشمس 15 على الأقل.",R.drawable.sunscreen));
            mlist.add(new screen_item("إزالة","إن إزالتها بالجراحة ستترك ندبة صغيرة دائمًا. يختار معظم الأطباء التخدير الموضعي لإجراء الجراحة. في 10٪ من الحالات ، يمكن أن يتكرر الورم.",R.drawable.skin_illness));
        }
        else
        {
            mlist.add(new screen_item("Be kind to your body","Be kind to your body and mind. Getting enough sleep, eating a healthy diet, and taking time to do things you enjoy can contribute to an overall feeling of balance in your life and help reduce stress.",R.drawable.relax));
            mlist.add(new screen_item(
                    "Go to the doctor","While it is usually benign, follow up with a doctor to avoid it being harmful.",R.drawable.go_doctor));
            mlist.add(new screen_item("Sunblock","Before spending time outdoors, use a broad-spectrum sunscreen with an SPF of at least 15.",R.drawable.sunscreen));
            mlist.add(new screen_item("Removed","Removing them by surgery will always leave a small scar. Most doctors choose local anesthesia to perform the surgery. In 10% of cases, the tumor can recur.",R.drawable.skin_illness));
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
                Intent i=new Intent(dermatofibroma_advice.this, HomeActivity.class);
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