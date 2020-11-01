package com.ict.vmarket;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PageAdapter extends FragmentPagerAdapter {


    private int numoftab;

    public PageAdapter(@NonNull FragmentManager fm, int numoftab) {
        super(fm);

        this.numoftab = numoftab;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position ){
            case 0:
                return new Tab1();

            case 1:
                return new Tab2();

           // case 2:
               // return new Tab3();

            default:
                return null;
        }


    }

    @Override
    public int getCount() {
        return numoftab;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}

