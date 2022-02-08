package com.example.medscan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class slideAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;
    public slideAdapter(Context context){
        this.context=context;
    }

    public int[] slide_images = {
            R.drawable.sport,
            R.drawable.water,
            R.drawable.eat

    };
    public String[] slide_heading = {
            "Do Sport",
            "Drink Water",
            "Healthy Food"
    };

    public String[] slide_Desc = {
            "Keep fit  " +
                    "Regular and light physical activity such as brisk walking," +
                    " jogging, jumping, swimming, cycling, etc. " +
                    "is recommended to maintain kidney health. ",

            "You should drink a lot of water, " +
                    "as the water works to move the salts and help their descent." +
                    " Water plays a major role in expelling toxins from the body. ",

            "You must eat healthy and balanced diets" +
                    "Avoid foods that are high in sodium such as chips, fried foods, " +
                    "crackers, salted nuts, pickles, sausages, certain types of meat, etc."
    };

    @Override
    public int getCount() {
        return slide_heading.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view ==(RelativeLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout,container,false);

        ImageView slideimageview = (ImageView) view.findViewById(R.id.slide_image);
        TextView slideHeading = (TextView) view.findViewById(R.id.slide_heading);
        TextView slideDescription = (TextView) view.findViewById(R.id.slide_desc);

        slideimageview.setImageResource(slide_images[position]);
        slideHeading.setText(slide_heading[position]);
        slideDescription.setText(slide_Desc[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
    }
}
