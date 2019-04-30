package com.example.meigel.sispak.views.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.meigel.sispak.R;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MenuActivity extends AppCompatActivity {
    private LinearLayout btnDiagnosa, btnPenyakit, btnArtikel, btnBantuan, btnKeluar;
    private Toolbar toolbar;
    private Button btnBack;


    private static int versionCode;
    private static String versionName;
    private static String pathDownload;
    private static String url = "https://kliniksip.firebaseio.com/Version/Lastest/apkInfo.json";


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

        setUpDate();
    }


    private void setUpDate() {
        String rest = getIntent().getStringExtra("Rest");
        cekUpdate(rest);

    }


    public void cekUpdate(String json) {
        versionCode = 4;
        System.out.println("versionCode = " + versionCode);
        try {
            JSONObject reader = new JSONObject(json);
            String versionCodeString = reader.getString("versionCode");
            int newVersionCode = Integer.parseInt(versionCodeString);
            System.out.println("newVersionCode = " + newVersionCode);
            pathDownload = reader.getString("path");

            if (newVersionCode > versionCode)
            {
                Log.d("Update","Perlu update");
                System.out.println("pathDownload = " + pathDownload);
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
                builder.setTitle("Update terbaru tersedia");
                builder.setMessage("Apakah anda ingin memperbarui aplikasi?");
                builder.setPositiveButton("Iya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(pathDownload));
                        startActivity(browserIntent);
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                android.app.AlertDialog alert = builder.create();
                alert.show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;
        inputStream.close();
        return result;
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

class Handler {
    OkHttpClient client;

    public Handler() {
        this.client = new OkHttpClient();
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    String result;

    void doGetRequest(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        Log.d("Fail", e.getMessage());
                        result = "{" +
                                "\"path\":\"https://drive.google.com/drive/folders/1vcUtYW8YQ1uus_ZF9fsA_md6tAd8VTkA\"," +
                                "\"versionCode\":1," +
                                "\"versionName\":\"Dasar\"}";
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        setResult(response.body().string());
                    }
                });
    }

}