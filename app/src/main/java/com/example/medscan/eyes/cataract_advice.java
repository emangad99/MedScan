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
import com.example.medscan.lungs.covidAdapter;
import com.example.medscan.screen_item;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class cataract_advice extends AppCompatActivity {

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
        setContentView(R.layout.activity_cataract_advice);

        tabindicator=findViewById(R.id.tab_indicator);
        next=findViewById(R.id.Next_btn);
        backtohome=findViewById(R.id.covid_ok);
        btn_anim= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.btn_anim);

        List<screen_item> mlist = new ArrayList<>();

        if(templang == "ar")
        {
            mlist.add(new screen_item("غير قادر على الجراحة","إذا كنت غير قادر على الجراحة أو غير مهتم بها ، فقد يكون طبيبك قادرًا على مساعدتك في إدارة الأعراض. قد يقترحون نظارات أقوى أو عدسات مكبرة أو نظارات شمسية ذات طلاء مضاد للوهج.",R.drawable.surgery));
            mlist.add(new screen_item("اخضع لفحوصات عين منتظمة","يمكن أن تساعد فحوصات العين في الكشف عن المياه البيضاء ومشكلات العين الأخرى في مراحلها الأولى، فاستشر طبيبك عن عدد مرات فحوصات العين التي يجب أن تجريها.",R.drawable.eyes_exam));
            mlist.add(new screen_item(" الامتناع عن شرب الكحوليات","يمكن أن يزيد الإفراط في تناول الكحول من مخاطر الإصابة بمرض المياه البيضاء.",R.drawable.drink));
            mlist.add(new screen_item("اختيار نظام غذائي صحي","تضمن إضافة مجموعة متنوعة من الفاكهة والخضروات الملونة إلى نظامك الغذائي أنك تحصل على العديد من الفيتامينات والمواد الغذائية، حيث تحتوي الفاكهة والخضروات على العديد من مضادات الأكسدة التي تساعد على الحفاظ على صحة عينيك.",R.drawable.fruits));
        }
        else
        {
            mlist.add(new screen_item("Unable in surgery","If you’re unable or uninterested in surgery, your doctor may be able to help you manage your symptoms. They may suggest stronger eyeglasses, magnifying lenses, or sunglasses with an anti-glare coating.",R.drawable.surgery));
            mlist.add(new screen_item("Get regular eye exams","Eye exams can help detect cataracts and other eye problems in their early stages, so check with your doctor about how often you should have eye exams.",R.drawable.eyes_exam));
            mlist.add(new screen_item("Refrain from drinking alcohol","Excessive alcohol consumption can increase the risk of developing cataracts.",R.drawable.drink));
            mlist.add(new screen_item("Choose a healthy diet","Adding a variety of colorful fruits and vegetables to your diet ensures that you are getting many vitamins and nutrients, as fruits and vegetables contain many antioxidants that help keep your eyes healthy.",R.drawable.fruits));
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
                Intent i=new Intent(cataract_advice.this, HomeActivity.class);
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