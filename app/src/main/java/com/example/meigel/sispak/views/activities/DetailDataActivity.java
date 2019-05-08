package com.example.meigel.sispak.views.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.meigel.sispak.R;
import com.example.meigel.sispak.helpers.SQLiteHelper;
import com.example.meigel.sispak.models.Penyakit;
import com.example.meigel.sispak.views.fragments.PenyakitFragment;

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
    private Button btnEditPenyakit, btnHapusPenyakit;
    Penyakit penyakit;

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
        btnEditPenyakit = findViewById(R.id.btnEditPenyakit);
        btnHapusPenyakit = findViewById(R.id.btnHapusPenyakit);
        getBundle();
        setupData();
        setupView();
    }

    private void setupView() {
        if (AdminMode)        {
            //Edit Mode
            btnEditPenyakit.setVisibility(View.VISIBLE);
            btnHapusPenyakit.setVisibility(View.VISIBLE);
        }
        else {
            btnEditPenyakit.setVisibility(View.GONE);
            btnHapusPenyakit.setVisibility(View.GONE);
            //User Mode
        }


        btnEditPenyakit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(DetailDataActivity.this);
                View prompt = li.inflate(R.layout.form_penyakit, null);

                final android.support.v7.app.AlertDialog.Builder alertDialogBuilder =
                        new android.support.v7.app.AlertDialog.Builder(DetailDataActivity.this);
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


                final android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
//                penyakit.print();

                PenyakitKode.setText(penyakit.getKode());
                PenyakitNama.setText(penyakit.getName());
                PenyakitKeterangan.setText(penyakit.getDesc());
                PenyakitPenanganan.setText(penyakit.getPenanganan());
                PenyakitPenyebab.setText(penyakit.getDesc1());
                PenyakitPengobatan.setText(penyakit.getDesc2());
                PenyakitPencegahan.setText(penyakit.getDesc3());

                btnSimpanPenyakit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        penyakit.setKode(PenyakitKode.getText().toString());
                        penyakit.setName(PenyakitNama.getText().toString());
                        penyakit.setDesc(PenyakitKeterangan.getText().toString());
                        penyakit.setDesc1(PenyakitPenyebab.getText().toString());
                        penyakit.setDesc2(PenyakitPengobatan.getText().toString());
                        penyakit.setDesc3(PenyakitPencegahan.getText().toString());
                        penyakit.setPenanganan(PenyakitPenanganan.getText().toString());



                        SQLiteHelper
                                .getInstance(DetailDataActivity.this)
                                .updatePenyakit( penyakit );


                        Log.d("Edit", "Penyakit Baru");
                        alertDialog.dismiss();
                        Intent i = new Intent(DetailDataActivity.this, MainDataActivity.class);
                        i.putExtra("tipe", "penyakit");
                        i.putExtra("admin", AdminMode);
                        startActivity(i);

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

        btnHapusPenyakit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(DetailDataActivity.this);
                builder.setTitle("Mengahapus Data Penyakit "+ penyakit.getKode());
                builder.setMessage("Apakah anda yakin untuk menghapus " + penyakit.getName()
                        + " \n ID : " + penyakit.getId());
                builder.setCancelable(true);

                builder.setPositiveButton(
                        "Iya",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                int deletePenyakit = SQLiteHelper
                                        .getInstance(DetailDataActivity.this)
                                        .deletePenyakit(penyakit);
                                dialog.cancel();
                                finish(); //Tambah Ini
                                Intent i = new Intent(DetailDataActivity.this, MainDataActivity.class);
                                i.putExtra("tipe", "penyakit");
                                i.putExtra("admin", AdminMode);
                                startActivity(i);
                            }
                        });

                builder.setNegativeButton(
                        "Tidak",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder.create();
                alert11.show();

            }
        });
    }

    private void getBundle(){
        AdminMode   = getIntent().getBooleanExtra("admin",false);
    }

    private void setupData(){
        Log.d("MainApp","Id : " + getIntent().getStringExtra("id"));
        penyakit = SQLiteHelper.getInstance(this).getPenyakit(getIntent().getStringExtra("id"));
        setSupportActionBar(toolbar);
        if (AdminMode)
            getSupportActionBar().setTitle(penyakit.getKode() + " [Admin Mode]");
        else
            getSupportActionBar().setTitle(penyakit.getName());
        txtPenanganan.setText(penyakit.getPenanganan());
        txtDesc.setText(penyakit.getDesc());
        txtDesc1.setText(penyakit.getDesc1());
        System.out.println("penyakit.getDesc1() = " + penyakit.getDesc1());
        txtDesc2.setText(penyakit.getDesc2());
        txtDesc3.setText(penyakit.getDesc3());
        imgPenyakit.setImageResource(getResources().getIdentifier(penyakit.getImg(),"drawable",getPackageName()));
    }

    //Tambah Ini Semua
    @Override
    public void onBackPressed() {        // to prevent irritating accidental logouts
        Intent i = new Intent(DetailDataActivity.this, MainDataActivity.class);
        i.putExtra("tipe", "penyakit");
        i.putExtra("admin", AdminMode);
        finish();
        startActivity(i);
    }
}
