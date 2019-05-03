package com.example.meigel.sispak.views.activities;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
            btnAddPenyakit.setVisibility(View.GONE);
            toolbar.setTitle("Daftar Penyakit");
        }

        btnAddPenyakit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(MainDataActivity.this);
                View prompt = li.inflate(R.layout.form_penyakit, null);

                final AlertDialog.Builder alertDialogBuilder =
                        new AlertDialog.Builder(MainDataActivity.this);
                alertDialogBuilder.setTitle("Admin Mode");
                alertDialogBuilder.setMessage("Masukan Data Penyakit Baru");
                alertDialogBuilder.setView(prompt);

                Button btnSimpanPenyakit = (Button) prompt.findViewById(R.id.btnSimpanPenyakit);
                Button btnBatal = (Button) prompt.findViewById(R.id.btnBatal);

                final EditText PenyakitKode = (EditText) prompt.findViewById(R.id.PenyakitKode);
                final EditText PenyakitNama = (EditText) prompt.findViewById(R.id.PenyakitNama);
                final EditText PenyakitPengobatan = (EditText) prompt.findViewById(R.id.PenyakitPengobatan);
                final EditText PenyakitPenanganan = (EditText) prompt.findViewById(R.id.PenyakitPenanganan);
                final EditText PenyakitPencegahan = (EditText) prompt.findViewById(R.id.PenyakitPencegahan);
                final EditText PenyakitKeterangan = (EditText) prompt.findViewById(R.id.PenyakitKeterangan);
                final EditText PenyakitPenyebab = (EditText) prompt.findViewById(R.id.PenyakitPenyebab);


                final AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();



                btnSimpanPenyakit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SQLiteHelper
                            .getInstance(MainDataActivity.this)
                            .addPenyakit(
                                    new Penyakit(
                                                PenyakitKode.getText().toString(),
                                                PenyakitNama.getText().toString(),
                                                PenyakitPenanganan.getText().toString(),
                                                PenyakitKeterangan.getText().toString(),
                                            "imgicon",
                                                PenyakitPenyebab.getText().toString(),
                                                PenyakitPengobatan.getText().toString(),
                                                PenyakitPencegahan.getText().toString()
                                    ));
                        Log.d("Create", "Penyakit Baru");
                        UpdateView();
                        alertDialog.dismiss();

                    }

                    private void UpdateView() {
                        PenyakitFragment penyakitFragment = new PenyakitFragment();
                        penyakitFragment.setArguments(bundle);
                        fm.beginTransaction()
                                .replace(R.id.content,penyakitFragment,"content_fragment")
                                .commitAllowingStateLoss();
                    }
                });
                btnBatal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

            }
        });
    }
}
