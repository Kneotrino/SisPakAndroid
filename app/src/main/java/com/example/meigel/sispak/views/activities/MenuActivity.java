package com.example.meigel.sispak.views.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.example.meigel.sispak.R;
import com.example.meigel.sispak.views.fragments.BantuanFragment;

public class MenuActivity extends AppCompatActivity {
    private LinearLayout btnDiagnosa, btnPenyakit, btnArtikel, btnBantuan, btnKeluar;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        btnDiagnosa = (LinearLayout) findViewById(R.id.btnDiagnose);
        btnPenyakit = (LinearLayout) findViewById(R.id.btnPenyakit);
        btnArtikel = (LinearLayout) findViewById(R.id.btnArtikel);
        btnBantuan = (LinearLayout) findViewById(R.id.btnBantuan);
        btnKeluar = (LinearLayout) findViewById(R.id.btnKeluar);
        setupToolbar();
        setupView();
    }

    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("SiPaBi");
    }

    private void setupView() {
        btnDiagnosa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuActivity.this, DiagnoseActivity.class);
                startActivity(i);
            }
        });

        btnPenyakit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuActivity.this, MainDataActivity.class);
                i.putExtra("tipe", "penyakit");
                startActivity(i);
            }
        });

        btnArtikel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuActivity.this, BantuanActivity.class);
                startActivity(i);
            }
        });

        btnBantuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuActivity.this, TentangActivity.class);
                startActivity(i);
            }
        });

        btnKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this);
                builder.setTitle("Konfirmasi");
                builder.setMessage("Anda yakin ingin keluar?")
                        //.setCancelable(false)
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                            }
                        })
                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

}