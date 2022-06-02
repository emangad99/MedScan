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

public class melanocytic_nevi_advice extends AppCompatActivity {

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
        setContentView(R.layout.activity_melanocytic_nevi_advice);

        tabindicator=findViewById(R.id.tab_indicator);
        next=findViewById(R.id.Next_btn);
        backtohome=findViewById(R.id.covid_ok);
        btn_anim= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.btn_anim);

        List<screen_item> mlist = new ArrayList<>();
        if(templang == "ar")
        {
            mlist.add(new screen_item("اذهب الى الطبيب","يجب فحص الشامة تحت المجهر. للتأكد من أنها ليست ضارة.",R.drawable.check_skin));
            mlist.add(new screen_item("احمي نفسك ","يجب عليك أيضًا حماية نفسك من التعرض المفرط للشمس. هذا لا يعني أنه لا يمكنك الذهاب في عطلة مشمسة مرة أخرى ؛ هذا يعني فقط أنك بحاجة إلى توخي الحذر لتجنب حمامات الشمس والحرق. يجب عليك تغطية نفسك واستخدام كريمات الحماية من الشمس بعامل وقاية من الشمس 30 أو أعلى.",R.drawable.protect));
            mlist.add(new screen_item("تجنب الشمس ","اقضِ بعض الوقت في الظل بين الساعة 11 صباحًا و 3 مساءً عندما يكون الجو مشمسًا. ابتعد عن الشمس قبل أن تتاح لجلدك فرصة للاحمرار أو الاحتراق.",R.drawable.summer1));
            mlist.add(new screen_item("فيتامين د","قد يكون من المفيد تناول أقراص مكملات فيتامين د (متوفرة في متاجر الأطعمة الصحية) لأن تجنب أشعة الشمس بشكل صارم يمكن أن يقلل من مستويات فيتامين د.",R.drawable.vitamin));
        }
        else
        {
            mlist.add(new screen_item("Go to the doctor","The mole should be examined under a microscope. To make sure it's not harmful.",R.drawable.check_skin));
            mlist.add(new screen_item("Protect yourself","You must also protect yourself from too much sun exposure. This does not mean that you cannot ever go on a sunny holiday again; it just means that you need to be careful to avoid sunbathing and burning. You should cover yourself up and use sun protection creams of SPF 30 or above.",R.drawable.protect));
            mlist.add(new screen_item("Avoid sun","Spend time in the shade between 11am and 3pm when it’s sunny. Step out of the sun before your skin has a chance to redden or burn.",R.drawable.summer1));
            mlist.add(new screen_item("Vitamin D","It may be worth taking Vitamin D supplement tablets (available from health food stores) as strictly avoiding sunlight can reduce Vitamin D levels.",R.drawable.vitamin));
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
                Intent i=new Intent(melanocytic_nevi_advice.this, HomeActivity.class);
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