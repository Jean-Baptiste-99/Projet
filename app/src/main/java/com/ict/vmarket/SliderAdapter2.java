package com.ict.vmarket;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class SliderAdapter2 extends RecyclerView.Adapter<SliderAdapter2.SliderViewHolder>{

    private List<SliderItem2> sliderItems;
    private ViewPager2 viewPager2;

    SliderAdapter2(List<SliderItem2> sliderItems, ViewPager2 viewPager2) {
        this.sliderItems = sliderItems;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public SliderAdapter2.SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderAdapter2.SliderViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.slideitem2,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull SliderAdapter2.SliderViewHolder holder, int position) {

        holder.setImage(sliderItems.get(position));
        if (position == sliderItems.size() -2){
            viewPager2.post(runnable);
        }

    }

    @Override
    public int getItemCount() {
        return sliderItems.size();
    }

    class SliderViewHolder extends RecyclerView.ViewHolder{

        private RoundedImageView imageView;

        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgSlider2);
        }

        void setImage(SliderItem2 sliderItem){
            imageView.setImageResource(sliderItem.getImage());
        }
    }

    private Runnable runnable =new Runnable() {
        @Override
        public void run() {
            sliderItems.addAll(sliderItems);
            notifyDataSetChanged();
        }
    };
}
