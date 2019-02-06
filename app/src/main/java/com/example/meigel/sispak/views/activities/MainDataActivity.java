package com.example.meigel.sispak.views.activities;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.meigel.sispak.R;
import com.example.meigel.sispak.views.fragments.PenyakitFragment;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class MainDataActivity extends AppCompatActivity {
    private String tipe;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT >= 9) {
            try {
                Class<?> strictModeClass = Class.forName(
                        "android.os.StrictMode", true, Thread.currentThread()
                                .getContextClassLoader());
                Class<?> threadPolicyClass = Class.forName(
                        "android.os.StrictMode$ThreadPolicy", true, Thread
                                .currentThread().getContextClassLoader());
                Field laxField = threadPolicyClass.getField("LAX");
                Method setThreadPolicyMethod = strictModeClass.getMethod(
                        "setThreadPolicy", threadPolicyClass);
                setThreadPolicyMethod.invoke(strictModeClass,
                        laxField.get(null));
            } catch (Exception ignored) {
                //do nothing
            }
        }
        setContentView(R.layout.activity_main_data);
        setupToolbar();
        getBundle();
        setupView();
    }

    private void setupToolbar(){
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Menu Utama");
    }

    private void getBundle(){
        tipe = getIntent().getStringExtra("tipe");
    }

    private void setupView(){
        FragmentManager fm = getSupportFragmentManager();
        if(tipe.equalsIgnoreCase("penyakit")){
            getSupportActionBar().setTitle("Daftar Penyakit");
            if(fm.beginTransaction().isEmpty()){
                fm.beginTransaction().add(R.id.content, new PenyakitFragment(),"content_fragment").commitAllowingStateLoss();
            } else {
                fm.beginTransaction().replace(R.id.content, new PenyakitFragment(), "content_fragment").commitAllowingStateLoss();
            }
        }
    }
}
