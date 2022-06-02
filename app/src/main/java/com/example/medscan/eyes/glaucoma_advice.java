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

public class glaucoma_advice extends AppCompatActivity {

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
        setContentView(R.layout.activity_glaucoma_advice);

        tabindicator=findViewById(R.id.tab_indicator);
        next=findViewById(R.id.Next_btn);
        backtohome=findViewById(R.id.covid_ok);
        btn_anim= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.btn_anim);

        List<screen_item> mlist = new ArrayList<>();

        if(templang == "ar")
        {
            mlist.add(new screen_item("القيام ببعض التمارين","يُنصح عادةً المصابين بمرض المياه الزرقاء بالقيام ببعض التمارين التي تُساعدهم بشكل كبير في خفض ضغط العين، بالإضافة إلى الحفاظ على تدفق الدم إلى الأعصاب الموجودة في داخل العين.",R.drawable.eye_advice));
            mlist.add(new screen_item(" تناول الأطعمة الصحية","وُجد أن تناول كميات كبيرة من الأطعمة التي تحتوي على مضادات الأكسدة (Antioxidant) تُساعد بشكل كبير في حالة الإصابة بهذا النوع من الحالات الصحية، ومن أبرز هذه الأطعمة:\n" +
                    "( الخضروات ذات الأوراق الداكنة - الأسماك الغنية بأحماض أوميغا 3 الدهنية. ) ",R.drawable.fish));
            mlist.add(new screen_item("شرب سوائل","شرب كميات جيدة من السوائل المختلفة وبنسب معتدلة خلال اليوم.",R.drawable.drink_advice));
            mlist.add(new screen_item("الكافيين","استهلاك الكافيين بكميات محدودة فقط، فهي لها آثار سلبية على صحة العينين.",R.drawable.coffee));
        }
        else {
            mlist.add(new screen_item("Do some exercises", "People with glaucoma are usually advised to do some exercises that help them greatly in lowering intraocular pressure, in addition to maintaining blood flow to the nerves inside the eye. ", R.drawable.eye_advice));
            mlist.add(new screen_item("Eat healthy foods", "It has been found that eating large amounts of foods that contain antioxidants greatly help in the event of this type of health condition, and the most prominent of these foods are:\n" +
                    "(Dark leafy vegetables - fish rich in omega-3 fatty acids.)", R.drawable.fish));
            mlist.add(new screen_item("drink fluids", "Drink good amounts of different fluids and in moderate proportions during the day. ", R.drawable.drink_advice));
            mlist.add(new screen_item("Caffeine", "Caffeine is only consumed in limited quantities, as it has negative effects on the health of the eyes. ", R.drawable.coffee));

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
                Intent i=new Intent(glaucoma_advice.this, HomeActivity.class);
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