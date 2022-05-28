package com.example.medscan.eyes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.medscan.R;
import com.example.medscan.screen_item;

import java.util.List;

public class cataract_Adapter  extends PagerAdapter {
    Context context;
    List<screen_item> mlistscreen;

    public cataract_Adapter(Context context, List<screen_item> mlistscreen) {
        this.context = context;
        this.mlistscreen = mlistscreen;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layoutscreen = inflater.inflate(R.layout.cataract_screen,null);

        ImageView imgslide = layoutscreen.findViewById(R.id.eyes2_img);
        TextView title = layoutscreen.findViewById(R.id.eyes2_title);
        TextView desc = layoutscreen.findViewById(R.id.eyes2_desc);

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

