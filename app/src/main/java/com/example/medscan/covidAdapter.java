package com.example.medscan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import org.w3c.dom.Text;

import java.util.List;

public class covidAdapter extends PagerAdapter {

    Context context;
    List<screen_item> mlistscreen;

    public covidAdapter(Context context, List<screen_item> mlistscreen) {
        this.context = context;
        this.mlistscreen = mlistscreen;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layoutscreen = inflater.inflate(R.layout.covid_screen,null);

        ImageView imgslide = layoutscreen.findViewById(R.id.covid_img);
        TextView title = layoutscreen.findViewById(R.id.covid_title);
        TextView desc = layoutscreen.findViewById(R.id.covid_desc);

        title.setText(mlistscreen.get(position).getTitle());
        desc.setText(mlistscreen.get(position).getDesc());
        imgslide.setImageResource(mlistscreen.get(position).getScreen_img());

        container.addView(layoutscreen);
        return layoutscreen;
    }

    @Override
    public int getCount() {
        return mlistscreen.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((View) object);
    }
}
