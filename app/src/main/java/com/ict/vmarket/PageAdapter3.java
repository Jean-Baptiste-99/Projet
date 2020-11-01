package com.ict.vmarket;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PageAdapter3 extends FragmentPagerAdapter {

    private int numoftab;

    public PageAdapter3(@NonNull FragmentManager fm, int numoftab) {
        super(fm);

        this.numoftab = numoftab;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position ){
            case 0:
                return new Tabs1();

            case 1:
                return new Tabs2();




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
