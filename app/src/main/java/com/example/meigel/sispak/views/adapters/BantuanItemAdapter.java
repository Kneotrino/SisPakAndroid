package com.example.meigel.sispak.views.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.meigel.sispak.R;
import com.example.meigel.sispak.views.fragments.BantuanFragment;

public class BantuanItemAdapter extends FragmentStatePagerAdapter {
    public BantuanItemAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return BantuanFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return 5;
    }
}
