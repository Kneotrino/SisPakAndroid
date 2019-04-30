package com.example.meigel.sispak.views.activities;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.meigel.sispak.R;
import com.example.meigel.sispak.helpers.SQLiteHelper;
import com.example.meigel.sispak.models.Penyakit;
import com.example.meigel.sispak.views.fragments.PenyakitFragment;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class MainDataActivity extends AppCompatActivity {
    private String tipe;
    private Toolbar toolbar;
    private static boolean AdminMode = false;
    private Button btnAddPenyakit;


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
        tipe        = getIntent().getStringExtra("tipe");
        AdminMode   = getIntent().getBooleanExtra("admin",false);
    }

    private void setupView(){
        btnAddPenyakit = (Button) findViewById(R.id.btnAddPenyakit);

        final Bundle bundle = new Bundle();
        bundle.putBoolean("admin", AdminMode);


        final FragmentManager fm = getSupportFragmentManager();

        System.out.println("tipe = " + tipe);
        PenyakitFragment penyakitFragment = new PenyakitFragment();
        penyakitFragment.setArguments(bundle);

        fm.beginTransaction()
                .add(R.id.content,penyakitFragment,"content_fragment")
                .commitAllowingStateLoss();

        if (AdminMode)
        {
            btnAddPenyakit.setVisibility(View.VISIBLE);
            toolbar.setTitle("Daftar Penyakit Admin Mode");
        }
        else {
            btnAddPenyakit.setVisibility(View.INVISIBLE);
            toolbar.setTitle("Daftar Penyakit");
        }

        btnAddPenyakit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteHelper
                        .getInstance(MainDataActivity.this)
                        .addPenyakit(
                                new Penyakit(
                                        "H9",
                                        "Penyakit H10",
                                        "Vaksinasi dengan serum anti cholera babi atau rovac hog cholera.Sesudah babi umur 6" +
                                                "minggu, diulangi setahun sekali.Babi-babi dara atau induk sebaiknya 3 minggu sebelum" +
                                                "dikawinkan, sedang pejantan bisa sewaktu-waktu",
                                        "Hog cholera (HC) merupakan penyakit viral menular terpenting pada babi," +
                                                "berlangsung subakut, akut atau kronik, dengan proses penyakit yang tidak" +
                                                "menciri atau bahkan kadang tidak tampak sama sekali.",
                                        "pig",
                                        "Penyebab hog cholera adalah virus single stranded Ribonucleic Acid (ss-" +
                                                "RNA) dari genus Pestivirus termasuk famili Flaviviridae. Virus HC berada dalam" +
                                                "genus yang sama dengan virus bovine viral diarrhea (BVD). Virus berbentuk bulat" +
                                                "helikal atau tidak teratur dan berukuran antara 40-50 nm dengan nukleokapsid" +
                                                "berukuran 29 nm.",
                                        "Belum ada obat yang efektif untuk mencegah hog cholera.",
                                        "Tindakan yang paling efektif untuk mencegah atau mengendalikan" +
                                                "penyakit adalah melakukan vaksinasi dengan menggunakan vaksin"+
                                                "aktif yang sudah diatenuasi. Keberhasilan program vaksinasi sangat" +
                                                "tergantung dari strain, dosis dan aplikasi vaksin serta status kesehatan" +
                                                "hewan yang divaksinasi. Pengendalian dapat dilakukan dengan melalui" +
                                                "tindakan karantina."
                                ));

                PenyakitFragment penyakitFragment = new PenyakitFragment();
                penyakitFragment.setArguments(bundle);
                fm.beginTransaction()
                        .replace(R.id.content,penyakitFragment,"content_fragment")
                        .commitAllowingStateLoss();
            }
        });
    }
}
