package com.example.medscan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class health extends AppCompatActivity {
private ViewPager mSliderviewpager;
private LinearLayout mDotslayout;

private TextView[] mDots;
private  Button next,prev;
private int mCurrentpage;
private slideAdapter slideAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);
         next = (Button) findViewById(R.id.nxtbtn);
         prev=(Button) findViewById(R.id.prevbtn);
        mSliderviewpager=findViewById(R.id.slideViewPager);
        mDotslayout=findViewById(R.id.dotsLayout);

        slideAdapter = new slideAdapter(this);
        mSliderviewpager.setAdapter(slideAdapter);

        addDots(0);
        mSliderviewpager.addOnPageChangeListener(viewlistener);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSliderviewpager.setCurrentItem(mCurrentpage+1);
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSliderviewpager.setCurrentItem(mCurrentpage -1 );

            }
        });


    }

    public void addDots(int position){
        mDots = new TextView[3];
        mDotslayout.removeAllViews();
        for(int i =0 ; i <mDots.length ; i++)
        {
            mDots[i]= new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.transpatent));

            mDotslayout.addView(mDots[i]);


        }
        if(mDots.length>0 ){
            mDots[position].setTextColor(getResources().getColor(R.color.white));


        }
    }

    ViewPager.OnPageChangeListener viewlistener =new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
                addDots(position);
                mCurrentpage=position;

                if(position==0){
                    next.setEnabled(true);
                    prev.setEnabled(false);
                    prev.setVisibility(View.INVISIBLE);


                    next.setText("Next");
                    prev.setText("");

                }else if (position==mDots.length-1 ){
                    next.setEnabled(true);
                    prev.setEnabled(true);
                    prev.setVisibility(View.VISIBLE);


                    next.setText("");
                    prev.setText("Back");

                }else{
                    next.setEnabled(true);
                    prev.setEnabled(true);
                    prev.setVisibility(View.VISIBLE);


                    next.setText("Next");
                    prev.setText("Back");
                }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


}