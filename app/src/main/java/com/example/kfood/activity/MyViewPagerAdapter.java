package com.example.kfood.activity;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.kfood.fragment.FragmentAddFood;
import com.example.kfood.fragment.FragmentBasket;
import com.example.kfood.fragment.FragmentChangeFood;

public class MyViewPagerAdapter extends FragmentStatePagerAdapter {


    public MyViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                return new FragmentAddFood();

            case 1:
                return new FragmentChangeFood();

            case 2:
                return new FragmentBasket();

            default:
                return new FragmentAddFood();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
