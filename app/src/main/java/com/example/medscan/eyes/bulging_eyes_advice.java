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
import com.example.medscan.kidneys.kidney_failed_advice;
import com.example.medscan.lungs.covidAdapter;
import com.example.medscan.screen_item;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class bulging_eyes_advice extends AppCompatActivity {

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
        setContentView(R.layout.activity_bulging_eyes_advice);

        tabindicator=findViewById(R.id.tab_indicator);
        next=findViewById(R.id.Next_btn);
        backtohome=findViewById(R.id.covid_ok);
        btn_anim= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.btn_anim);
        String templang = Locale.getDefault().getLanguage();
        List<screen_item> mlist = new ArrayList<>();

        if(templang == "ar")
        {
            mlist.add(new screen_item("استخدم الدواء","الأدوية لتصحيح مستوى هرمونات الغدة الدرقية في الدم - لن يؤدي ذلك بالضرورة إلى تحسين مشاكل عينيك ، ولكنه قد يمنعها من التفاقم.",R.drawable.med_eyes));
            mlist.add(new screen_item("الجراحة التصحيحية","يمكن القيام بذلك لتحسين مظهر عينيك بمجرد السيطرة على الالتهاب.",R.drawable.correct_eyes));
            mlist.add(new screen_item("نصائح سريعة","يجب الإقلاع عن التدخين واستخدام القطرات الطبية  لتقليل جفاف العين وتهيّجها وارتداء عدسات خاصة لتصحيح الرؤية المزدوجة.",R.drawable.tips_eyes));
            mlist.add(new screen_item("لا تأخذ أي علاج","يجب ألا تتناول أي علاج لأن العلاج يختلف باختلاف السبب الأساسي. على سبيل المثال ، قد يُوصى بعلاجات مثل العلاج الإشعاعي أو العلاج الكيميائي أو الجراحة إذا كان جحوظ العين ناتجًا عن ورم.",R.drawable.tips2_eyes));


        }
        else
        {
            mlist.add(new screen_item("Use medicine","Medication to correct the level of thyroid hormones in your blood – this won't necessarily improve the problems with your eyes, but may stop them getting worse.",R.drawable.med_eyes));
            mlist.add(new screen_item("Corrective surgery","This may be carried out to improve the appearance of your eyes once the inflammation is under control.",R.drawable.correct_eyes));
            mlist.add(new screen_item("Quick Tips","You should quit smoking, use artificial tears to reduce eye dryness and irritation, and wear special lenses to correct double vision.",R.drawable.tips_eyes));
            mlist.add(new screen_item("Not take any treatment","You should not take any treatment because the treatment varies depending on the underlying cause. For example, treatments such as radiation therapy, chemotherapy, or surgery may be recommended if exophthalmos is caused by a tumor.",R.drawable.tips2_eyes));

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
                Intent i=new Intent(bulging_eyes_advice.this, HomeActivity.class);
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