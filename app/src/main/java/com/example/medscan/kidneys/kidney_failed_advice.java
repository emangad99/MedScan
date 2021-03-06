package com.example.medscan.kidneys;

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
import com.example.medscan.lungs.failed_advice;
import com.example.medscan.screen_item;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class kidney_failed_advice extends AppCompatActivity {

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
        setContentView(R.layout.activity_kidney_failed_advice);
        tabindicator=findViewById(R.id.tab_indicator);
        next=findViewById(R.id.Next_btn);
        backtohome=findViewById(R.id.covid_ok);
        btn_anim= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.btn_anim);
        String templang = Locale.getDefault().getLanguage();

        List<screen_item> mlist = new ArrayList<>();
        if(templang == "ar")
        {
            mlist.add(new screen_item("تناول البروتين","يجب أن تراقب بعناية كمية البروتين التي تتناولها ، يمكنك تناول 1 جرام من البروتين لكل كيلوغرام من وزنك المثالي يوميًا.",R.drawable.protein));
            mlist.add(new screen_item("الأدوية","إذا كنت تدخن ، توقف.\n" +
                    " اسأل طبيبك عن مجموعات الدعم والأدوية والطرق الأخرى لمساعدتك على الإقلاع عن التدخين.",R.drawable.kf));
            mlist.add(new screen_item("اشرب ماء","اشرب عدة أكواب من الماء يوميًا ، لأن السوائل تساعد على ترقيق المخاط في رئتيك وحلقك.",R.drawable.water2));
            mlist.add(new screen_item("ضغط الدم","راقب ضغط دمك بعناية. من المهم أن نفهم أن ارتفاع ضغط الدم يمكن أن يؤدي إلى تضييق أو ضعف أو تصلب الشرايين حول الكلى بمرور الوقت.",R.drawable.blood));


        }
        else
        {
            mlist.add(new screen_item("Eat protein","You should carefully monitor your protein intake.\n" +
                    "You can eat 1 gram of protein per kilogram of your ideal body weight per day.",R.drawable.protein));
            mlist.add(new screen_item("pharmaceutical","If you smoke, stop.\n Ask your doctor about support groups, medications and other ways to help you quit.",R.drawable.kf));
            mlist.add(new screen_item("Drink water","Drink several glasses of water daily, as fluids help thin the mucus in your lungs and throat.",R.drawable.water2));
            mlist.add(new screen_item("blood pressure","Monitor your blood pressure carefully. It's important to understand that high blood pressure can lead to narrowing, weakening or hardening of the arteries around the kidneys over time.",R.drawable.blood));


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
                Intent i=new Intent(kidney_failed_advice.this, HomeActivity.class);
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