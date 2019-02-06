package com.example.meigel.sispak.views.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.meigel.sispak.R;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.example.meigel.sispak.views.adapters.BantuanItemAdapter;
import me.relex.circleindicator.CircleIndicator;

public class BantuanActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private CircleIndicator indicator;
    private BantuanItemAdapter bia;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bantuan);
        //toolbar = (Toolbar)findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
       //getSupportActionBar().setTitle("Menu Bantuan");

        viewPager = (ViewPager)findViewById(R.id.viewPager);
        indicator = (CircleIndicator)findViewById(R.id.indicator);
        bia = new BantuanItemAdapter(getSupportFragmentManager());
        viewPager.setAdapter(bia);
        indicator.setViewPager(viewPager);
    }
}
