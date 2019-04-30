package com.example.meigel.sispak.views.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.meigel.sispak.R;
import com.example.meigel.sispak.helpers.SQLiteHelper;
import com.example.meigel.sispak.models.Penyakit;

public class DetailDataActivity extends AppCompatActivity {
    private TextView txtPenanganan
            ,txtDesc
            ,txtDesc1
            ,txtDesc2
            ,txtDesc3
            ;
    private ImageView imgPenyakit;
    private Toolbar toolbar;
    private static boolean AdminMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_data);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        txtPenanganan = (TextView)findViewById(R.id.txtPenanganan);
        txtDesc = (TextView)findViewById(R.id.txtDesc);
        txtDesc1 = (TextView)findViewById(R.id.txtDesc1);
        txtDesc2 = (TextView)findViewById(R.id.txtDesc2);
        txtDesc3 = (TextView)findViewById(R.id.txtDesc3);
        imgPenyakit = (ImageView)findViewById(R.id.imgPenyakit);
        getBundle();
        setupData();
        setupView();
    }

    private void setupView() {
        if (AdminMode)        {
            //Edit Mode

        }
        else {
            //User Mode
        }
    }

    private void getBundle(){
        AdminMode   = getIntent().getBooleanExtra("admin",false);
    }

    private void setupData(){
        Log.d("MainApp","Id : " + getIntent().getStringExtra("id"));
        Penyakit penyakit = SQLiteHelper.getInstance(this).getPenyakit(getIntent().getStringExtra("id"));
        setSupportActionBar(toolbar);
        if (AdminMode)
            getSupportActionBar().setTitle(penyakit.getKode() + " Admin Mode");
        else
            getSupportActionBar().setTitle(penyakit.getKode());
        txtPenanganan.setText(penyakit.getPenanganan());
        txtDesc.setText(penyakit.getDesc());
        txtDesc1.setText(penyakit.getDesc1());
        System.out.println("penyakit.getDesc1() = " + penyakit.getDesc1());
        txtDesc2.setText(penyakit.getDesc2());
        txtDesc3.setText(penyakit.getDesc3());
        imgPenyakit.setImageResource(getResources().getIdentifier(penyakit.getImg(),"drawable",getPackageName()));
    }

}
