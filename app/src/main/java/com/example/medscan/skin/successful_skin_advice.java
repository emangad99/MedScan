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

public class successful_skin_advice extends AppCompatActivity {

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
        setContentView(R.layout.activity_successful_skin_advice);

        tabindicator=findViewById(R.id.tab_indicator);
        next=findViewById(R.id.Next_btn);
        backtohome=findViewById(R.id.covid_ok);
        btn_anim= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.btn_anim);

        List<screen_item> mlist = new ArrayList<>();
        if(templang == "ar")
        {
            mlist.add(new screen_item("ترطيب بشرتك","ضعِى كريم ترطيب خاليا من الزيوت على وجهك، يخلق المرطب حاجزًا وقائيًا على بشرتك ويمنع البشرة من التأثير القاسي للبيئة التي يمكن أن تجعل بشرتك جافة وباهتة.",R.drawable.skin_skin));
            mlist.add(new screen_item("التقليل من تناول السكر","إن الحد من السكر قد تكون قادرًا على تقليل كمية الأنسولين ونتيجة لذلك سيقل إنتاج الزيوت وحب الشباب أيضًا كما أن تناول كميات أقل من السكر يقلل من التهاب الجلد.",R.drawable.suger));
            mlist.add(new screen_item("استشر الطبيب بانتظام","كلما كان لديك مشكلة في الجلد. يجب أن تستشير طبيبا.",R.drawable.doctor_sskkiinn));
            mlist.add(new screen_item("استخدام المنتجات بعناية","عندما يتعلق الأمر بالجلد ، فإن الإفراط في منتجات العناية ليس دائمًا هو الأفضل، لكن في الواقع ، استخدمى القليل من المكونات واستخدميها كما هو محدد.",R.drawable.beauty));

        }
        else
        {
            mlist.add(new screen_item("Moisturizing your skin","Apply an oil-free moisturizing cream to your face. A moisturizer creates a protective barrier on your skin and prevents the skin from the harsh impact of the environment that can make your skin dry and dull.",R.drawable.skin_skin));
            mlist.add(new screen_item("Reducing sugar intake","Reducing sugar you may be able to reduce the amount of insulin and as a result oil production and acne will also decrease and eating less sugar reduces skin inflammation.",R.drawable.suger));
            mlist.add(new screen_item("Consult a dermatologist regularly","Whenever you have a skin problem. You should consult a doctor.",R.drawable.doctor_sskkiinn));
            mlist.add(new screen_item("Use products carefully","When it comes to skin, over-care isn't always best, but in fact, use fewer ingredients and use them as directed.",R.drawable.beauty));

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
                Intent i=new Intent(successful_skin_advice.this, HomeActivity.class);
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