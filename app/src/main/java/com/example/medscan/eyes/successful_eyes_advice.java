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

public class successful_eyes_advice extends AppCompatActivity {

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
        setContentView(R.layout.activity_successful_eyes_advice);

        tabindicator=findViewById(R.id.tab_indicator);
        next=findViewById(R.id.Next_btn);
        backtohome=findViewById(R.id.covid_ok);
        btn_anim= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.btn_anim);

        List<screen_item> mlist = new ArrayList<>();

        if(templang == "ar")
        {
            mlist.add(new screen_item("تناول الطعام الصحي","بالإضافة إلى التنوع الكبير في تناول الخضروات والفاكهة، فإن النظام الغذائي الصحي يفيد صحتك العامة، كما قد يبقي على شبكيتك بصحة جيدة ولفترة طويلة نسبياً.",R.drawable.eyes_advice5));
            mlist.add(new screen_item("أشعة الشمس","إياك أن تنظر إلى الشمس مباشرة، حتى حين وقوع أمر مثير، كالكسوف، فقد يتسبب ذلك في تدمير لا رجعة فيه لعينيك، وقد يؤدى إلى العمى. كما أن التعرض للشمس يعد عامل خطر بالنسبة لعدستي العين.",R.drawable.eyes_advice7));
            mlist.add(new screen_item("الإقلاع عن التدخين","المدخنون أكثر عرضة للإصابة بالتنكس البقعي المرتبط بالعمر والمياه الزرقاء مقارنة بغير المدخنين، لذا راجع طبيبك إذا كنت تواجه مشكلة في الإقلاع عن التدخين.",R.drawable.eyes_advice8));
            mlist.add(new screen_item("الاختبارات المنتظمة","لن يخبرك اختبار العين فقط إذا كنت بحاجة لنظارات جديدة أولتغيير الوصفة الطبية، وإنما يعد أيضًا فحصًا ضروريًا لصحة العين.",R.drawable.eyes_advice6));
        }
        else {
            mlist.add(new screen_item("Healthy eating", "In addition to a wide variety of vegetables and fruits, a healthy diet benefits your overall health, and may also keep your retina healthy for a relatively long time. ", R.drawable.eyes_advice5));
            mlist.add(new screen_item("Sun rays", "Never look directly at the sun, even when something exciting happens, such as an eclipse, as it may cause irreversible damage to your eyes, and may even lead to blindness. Also, exposure to the sun is a risk factor for the lenses of the eyes.", R.drawable.eyes_advice7));
            mlist.add(new screen_item("Quit Smoking", "Smokers are more likely to develop age-related macular degeneration and glaucoma than non-smokers, so see your doctor if you're having trouble quitting. ", R.drawable.eyes_advice8));
            mlist.add(new screen_item("Regular tests", "An eye exam will not only tell you if you need new glasses or a change of prescription, but is also an essential eye health check. ", R.drawable.eyes_advice6));

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
                Intent i=new Intent(successful_eyes_advice.this, HomeActivity.class);
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