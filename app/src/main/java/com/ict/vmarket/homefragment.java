package com.ict.vmarket;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabItem;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

public class homefragment extends Fragment {

    private CardView c1, c2, c3, c4, cardView;
    //private ViewPager2 viewPager2;
    private Button bt, conn;
    private TextView textView;
    private CircleImageView ci1, ci2, ci3, ci4, ci5, ci6;
    private Handler sliderHandler = new Handler();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_home , container, false);

         c1 = v.findViewById(R.id.Cann);
         c2 = v.findViewById(R.id.Cprod);
         c3 = v.findViewById(R.id.Cpart);
         c4 = v.findViewById(R.id.Csms);
         cardView = v.findViewById(R.id.Cprod_red_moin);

        ci1 = v.findViewById(R.id.circleimageview10_apple);
        ci2 = v.findViewById(R.id.circleimageview10_sony);
        ci3 = v.findViewById(R.id.circleimageview310_ugreen);
        ci4 = v.findViewById(R.id.circleimageview110_sam);
        ci5 = v.findViewById(R.id.circleimageview410_nike);
        ci6 = v.findViewById(R.id.circleimageview410_xiaomi);

        //viewPager2 = v.findViewById(R.id.viewPagerImageSilder2);

        conn = v.findViewById(R.id.button3_conn);
        textView = v.findViewById(R.id.textView14_conn);

        /*List<SliderItem2> sliderItems = new ArrayList<>();
        sliderItems.add(new SliderItem2(R.drawable.apple));
        sliderItems.add(new SliderItem2(R.drawable.bluetoof));
        sliderItems.add(new SliderItem2(R.drawable.casque));
        sliderItems.add(new SliderItem2(R.drawable.ecout));
        sliderItems.add(new SliderItem2(R.drawable.image25));
        sliderItems.add(new SliderItem2(R.drawable.image21));
        sliderItems.add(new SliderItem2(R.drawable.nike2));
        sliderItems.add(new SliderItem2(R.drawable.image30));
        sliderItems.add(new SliderItem2(R.drawable.sony));
        sliderItems.add(new SliderItem2(R.drawable.nike3));
        sliderItems.add(new SliderItem2(R.drawable.p));
        sliderItems.add(new SliderItem2(R.drawable.image32));

        viewPager2.setAdapter(new SliderAdapter2(sliderItems, viewPager2));

        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 -Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);

            }
        });

        viewPager2.setPageTransformer(compositePageTransformer);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(silderRunnable);
                sliderHandler.postDelayed(silderRunnable,1500);
            }
        });*/

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Main12Activity.class);
                startActivity(intent);
            }
        });

        conn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), InscriptionClient.class);
                startActivity(intent);
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoginClient.class);
                startActivity(intent);
            }
        });



         c1.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(getContext(), ListeAnnonce.class);
                 startActivity(intent);
             }
         });

         c2.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 Intent intent = new Intent(getContext(), Main2Activity.class);
                 startActivity(intent);

             }
         });

        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ListePartenaire.class);
                startActivity(intent);
            }
        });

        c4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Main5Activity.class);
                startActivity(intent);
            }
        });

        ci1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Main6Activity.class);
                startActivity(intent);
            }
        });

        ci2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Main8Activity.class);
                startActivity(intent);
            }
        });

        ci3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Main9Activity.class);
                startActivity(intent);
            }
        });

        ci4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Main7Activity.class);
                startActivity(intent);
            }
        });

        ci5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Main11Activity.class);
                startActivity(intent);
            }
        });

        ci6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Main10Activity.class);
                startActivity(intent);
            }
        });




        return v;
    }

    /*private Runnable silderRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem()+1);
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(silderRunnable);

    }

    @Override
    public void onResume() {
        super.onResume();
        sliderHandler.postDelayed(silderRunnable, 1500);
    }*/
}
