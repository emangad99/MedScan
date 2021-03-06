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
import com.example.medscan.screen_item;
import com.example.medscan.skin.actinic_keratosis_advice;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class kidney_successful_advice extends AppCompatActivity {

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
        setContentView(R.layout.activity_kidney_successful_advice);

        tabindicator=findViewById(R.id.tab_indicator);
        next=findViewById(R.id.Next_btn);
        backtohome=findViewById(R.id.covid_ok);
        btn_anim= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.btn_anim);
        String templang = Locale.getDefault().getLanguage();

        List<screen_item> mlist = new ArrayList<>();
        if(templang == "ar")
        {
            mlist.add(new screen_item("اشرب مياه","الماء هو أساس عنصر الحياة ، وهو مهم للكلى وعملها. إن شرب كميات كافية من الماء ، على الأقل من 2 إلى 3 لترات في اليوم ، يضمن أن الكلى تؤدي وظائفها على أكمل وجه.",R.drawable.water));
            mlist.add(new screen_item("لا تدخن ","يدمر التدخين الأوعية الدموية ، ويقلل من تدفق الدم إلى الكلى. عندما لا يكون لدى الكلى ما يكفي من تدفق الدم ، فإنها لا تستطيع العمل بشكل جيد. يزيد التدخين من مخاطر الإصابة بارتفاع ضغط الدم وكذلك خطر الإصابة بسرطان الكلى.",R.drawable.smoke));
            mlist.add(new screen_item("مارس الرياضة","يمنع النشاط البدني المنتظم زيادة الوزن وارتفاع ضغط الدم ويحافظ على صحة الكلى.",R.drawable.sport));
            mlist.add(new screen_item("أكل صحي","تناول نظامًا غذائيًا متوازنًا حتى يتمكن جسمك من العمل في أفضل حالاته .",R.drawable.eat));

        }
        else
        {
            mlist.add(new screen_item("Drink water","Water is the basis of the element of life, and it is important for the kidneys and their work. Drinking sufficient quantities of water, at least 2 to 3 liters per day, ensures that the kidneys perform their functions to the fullest.",R.drawable.water));
            mlist.add(new screen_item("No Smoking","Smoking damages blood vessels, reducing blood flow to the kidneys. When the kidneys do not have enough blood flow, they cannot function well. Smoking increases the risk of high blood pressure as well as the risk of kidney cancer.",R.drawable.smoke));
            mlist.add(new screen_item("Do sport","Regular physical activity prevents weight gain and high blood pressure, and keeps the kidneys healthy.",R.drawable.sport));
            mlist.add(new screen_item("Healthy food","Eat a balanced diet so your body can function at its best.",R.drawable.eat));

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
                Intent i=new Intent(kidney_successful_advice.this, HomeActivity.class);
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