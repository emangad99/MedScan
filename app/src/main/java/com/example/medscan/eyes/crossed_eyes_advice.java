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

public class crossed_eyes_advice extends AppCompatActivity {

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
        setContentView(R.layout.activity_crossed_eyes_advice);

        tabindicator=findViewById(R.id.tab_indicator);
        next=findViewById(R.id.Next_btn);
        backtohome=findViewById(R.id.covid_ok);
        btn_anim= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.btn_anim);

        List<screen_item> mlist = new ArrayList<>();
        if(templang == "ar")
        {
            mlist.add(new screen_item("عين نعسانة","إذا كانت عينك ناتجة عن عين كسولة ، فقد يطلب منك طبيبك ارتداء رقعة على عينك القوية لإجبار عضلات عينك الأضعف على العمل بجهد أكبر.",R.drawable.lazy));
            mlist.add(new screen_item("النظارات","العدسات التصحيحية مثل النظارات أو العدسات اللاصقة.",R.drawable.glasses));
            mlist.add(new screen_item("جراحة في العين ","جراحة في عضلات معينة في العين ، خاصة إذا لم تصحح العدسات التصحيحية الحالة.",R.drawable.eyes_advice));
            mlist.add(new screen_item("قطرات العين ","قد يصف طبيبك أيضًا قطرات للعين لتعتيم الرؤية في عينك القوية. يمكنهم أيضًا استخدام حقن البوتوكس لإضعاف العضلات التي تفرط في الحركة وتتسبب في انعطاف العين.",R.drawable.eye_drops));
        }
        else {
            mlist.add(new screen_item("Lazy eye", " If your crossed eyes have resulted from a lazy eye, your doctor may have you wear a patch over your stronger eye to force the muscles of your weaker eye to work harder.", R.drawable.lazy));
            mlist.add(new screen_item("Eyeglasses", "corrective lenses, such as eyeglasses or contact lenses.", R.drawable.glasses));
            mlist.add(new screen_item("Eye surgery", " surgery on certain eye muscles, particularly if corrective lenses haven’t corrected the condition.", R.drawable.eyes_advice));
            mlist.add(new screen_item("Eye drops", " Your doctor might also prescribe eye drops to blur the vision in your stronger eye. They can also use Botox injections to weaken the muscle that’s overacting and causing the eye turn.", R.drawable.eye_drops));

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
                Intent i=new Intent(crossed_eyes_advice.this, HomeActivity.class);
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