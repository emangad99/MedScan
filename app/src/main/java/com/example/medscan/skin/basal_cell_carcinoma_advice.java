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

public class basal_cell_carcinoma_advice extends AppCompatActivity {

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
        setContentView(R.layout.activity_basal_cell_carcinoma_advice);

        tabindicator=findViewById(R.id.tab_indicator);
        next=findViewById(R.id.Next_btn);
        backtohome=findViewById(R.id.covid_ok);
        btn_anim= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.btn_anim);

        List<screen_item> mlist = new ArrayList<>();
        if(templang == "ar")
        {
            mlist.add(new screen_item("ملابس واقية","غطي بشرتك بملابس داكنة منسوجة بإحكام لتغطية ذراعيك وساقيك ، وارتداء قبعة واسعة الحواف ؛ هذا يوفر حماية أكثر من قبعة بيسبول أو غطاء الوجه." +
                    " لا تنس ارتداء النظارات الشمسية. ابحث عن النظارات التي تحجب نوعي الأشعة فوق البنفسجية - الأشعة فوق البنفسجية الطويلة والمتوسطة.",R.drawable.hat));
            mlist.add(new screen_item("استخدم الواقي من الشمس","استخدم واقيًا من الشمس واسع الطيف مع عامل حماية من الشمس لا يقل عن 30 ، حتى في الأيام الملبدة بالغيوم. يوصى بوضع كمية كبيرة من الكريم الواقي من الشمس ، وتكرار ذلك كل ساعتين أو أقل عند السباحة أو التعرق.",R.drawable.sunblock));
            mlist.add(new screen_item("افحص بشرتك ","افحص بشرتك بانتظام وأبلغ عن أي تغييرات. يبحث أحيانًا عن زيادات جديدة في الجلد أو عن التحسينات في المنطقة المجاورة. مرايا لفحص وجهك وعنقك وأذنيك وفروة رأسك.",R.drawable.check2));
            mlist.add(new screen_item("تجنب أشعة الشمس ","تجنب الشمس خلال فترة الظهيرة. في كثير من الأماكن ، تكون أشعة الشمس أقوى وأقوى بين الساعة 10 صباحًا و 4 مساءً. قم بجدولة الأنشطة الخارجية لأوقات أخرى من اليوم ، خلال فصل الشتاء أو عندما تكون السماء ملبدة بالغيوم.",R.drawable.sun));
        }
        else
        {
            mlist.add(new screen_item("Protective clothing","Cover your skin with dark, tightly woven clothing to cover your arms and legs, and wear a wide-brimmed hat; This provides more protection than a baseball cap or face cap.\n" +
                    "Don't forget to wear sunglasses. Look for ones that block both types of UV rays — UVA and UVB rays.",R.drawable.hat));
            mlist.add(new screen_item("Use sunblock","Use a broad spectrum sunscreen with an SPF of at least 30, even on cloudy days. It is recommended to apply a generous amount of sunscreen, and to repeat this every two hours or less when swimming or sweating.",R.drawable.sunblock));
            mlist.add(new screen_item("Check your skin ","Check your skin regularly and report any changes. Sometimes looking for new growths in the skin or for improvements in the vicinity. Mirrors to check your face, neck, ears, and scalp.",R.drawable.check2));
            mlist.add(new screen_item("Avoid sunlight","Avoid the sun during the midday period. In many places, the sun's rays are strongest and strongest between 10 am and 4 pm. Schedule outdoor activities for other times of the day, during the winter season or when the sky is cloudy.",R.drawable.sun));
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
                Intent i=new Intent(basal_cell_carcinoma_advice.this, HomeActivity.class);
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