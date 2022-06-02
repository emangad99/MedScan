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

public class actinic_keratosis_advice extends AppCompatActivity {
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
        setContentView(R.layout.activity_actinic_keratosis_advice);

        tabindicator=findViewById(R.id.tab_indicator);
        next=findViewById(R.id.Next_btn);
        backtohome=findViewById(R.id.covid_ok);
        btn_anim= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.btn_anim);

        List<screen_item> mlist = new ArrayList<>();
        if(templang == "ar")
        {
            mlist.add(new screen_item("تجنب الشمس ","تجنب البقاء في الشمس لفترة طويلة حتى تصاب بحروق الشمس أو تان لأن كلاهما يؤدي إلى تلف الجلد ",R.drawable.sun2));
            mlist.add(new screen_item("استخدم واقي من الشمس ","قبل قضاء الوقت في الهواء الطلق ، استخدم واقيًا من الشمس واسع الطيف مع عامل حماية من الشمس 15 على الأقل.",R.drawable.sunscreen));
            mlist.add(new screen_item("ارتدي ملابس للحماية ","لمزيد من الحماية من أشعة الشمس ، ارتدِ ملابس منسوجة بإحكام تغطي ذراعيك وساقيك.",R.drawable.walk));
            mlist.add(new screen_item("العلاج بالأدوية ","أدوية الكورتيكوستيرويد (Corticosteroid) هي الخيار الأول لعلاج التهاب الأوعية الدموية في الجسم، وأبرز دواء منها يُوصف في هذا الشأن هو دواء بريدنيزون (Prednisone).",R.drawable.cream));


        }
        else
        {
            mlist.add(new screen_item("Avoid sun","Avoid staying in the sun for too long until you get a sunburn or tan because both lead to skin damage ",R.drawable.sun2));
            mlist.add(new screen_item("Use sunscreen","Before spending time outdoors, use a broad-spectrum sunscreen with an SPF of at least 15.",R.drawable.sunscreen));
            mlist.add(new screen_item("Wear woven clothing","For extra protection from the sun, wear tightly woven clothing that covers your arms and legs.",R.drawable.walk));
            mlist.add(new screen_item("drug therapy","Corticosteroid medications are the first choice for treating dermatitis on the body, and the most commonly prescribed medication in this regard is prednisone.",R.drawable.cream));


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
                Intent i=new Intent(actinic_keratosis_advice.this, HomeActivity.class);
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
