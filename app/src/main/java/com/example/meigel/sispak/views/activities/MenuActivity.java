package com.example.meigel.sispak.views.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meigel.sispak.R;
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
import java.util.ArrayList;

import io.github.skyhacker2.sqliteonweb.SQLiteOnWeb;

public class MenuActivity extends AppCompatActivity {
    private LinearLayout btnDiagnosa;
    private LinearLayout btnPenyakit;
    private LinearLayout btnArtikel;
    private LinearLayout btnBantuan;
    private LinearLayout btnKeluar;
    private LinearLayout btnDataGejala;
    private LinearLayout btnDataKeputusan;
    private Toolbar toolbar;
    private Button btnBack;


    private static boolean AdminMode = false;
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
        btnDataGejala = (LinearLayout) findViewById(R.id.btnDataGejala);
        btnDataKeputusan = (LinearLayout) findViewById(R.id.btnDataKeputusan);
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

        SQLiteOnWeb.init(this).start();


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
                i.putExtra("admin", AdminMode);
                startActivity(i);
            }
        });

        btnPenyakit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuActivity.this, MainDataActivity.class);
                i.putExtra("tipe", "penyakit");
                i.putExtra("admin", AdminMode);
                startActivity(i);
            }
        });
        btnDataGejala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuActivity.this, MainDataActivity.class);
                i.putExtra("tipe", "gejala");
                i.putExtra("admin", AdminMode);
                startActivity(i);
            }
        });

        btnDataKeputusan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuActivity.this, MainDataActivity.class);
                i.putExtra("tipe", "keputusan");
                i.putExtra("admin", AdminMode);
                startActivity(i);
            }
        });

        btnArtikel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                LayoutInflater li = LayoutInflater.from(MenuActivity.this);
                View prompt = li.inflate(R.layout.frament_login, null);

                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MenuActivity.this);
                alertDialogBuilder.setTitle("Admin Mode");
                alertDialogBuilder.setMessage("Masukan Username dan Password");
                alertDialogBuilder.setView(prompt);

                Button btnLogin = (Button) prompt.findViewById(R.id.btnLogin);
                Button btnCancel = (Button) prompt.findViewById(R.id.btnCancel);

                final EditText txtPassword = (EditText) prompt.findViewById(R.id.txtPassword);
                final EditText txtUsername = (EditText) prompt.findViewById(R.id.txtUsername);

                final AlertDialog alertDialog = alertDialogBuilder.create();
//                alertDialog.show();

                if (AdminMode)  {
                    final AlertDialog.Builder logoutBuilder = new AlertDialog.Builder(MenuActivity.this);
                    logoutBuilder.setTitle("Admin Mode");
                    logoutBuilder.setMessage("Apakah anda ingin logout?");
                    logoutBuilder.setPositiveButton("Iya", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            // Do nothing but close the dialog
                            AdminMode = false;
                            btnDataGejala.setVisibility(View.GONE);
                            btnDataKeputusan.setVisibility(View.GONE);
                            TextView textView = findViewById(R.id.txtAdminMode);
                            textView.setText("Admin Mode");

                            dialog.dismiss();
                        }
                    });

                    logoutBuilder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            // Do nothing
                            dialog.dismiss();
                        }
                    });

                    logoutBuilder.create().show();
                }
                else {
                    alertDialog.show();
                }


                btnLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String pass = txtPassword.getText().toString();
                        String user = txtUsername.getText().toString();
                        boolean login = Login(user, pass);
                        System.out.println("login = " + login);

                        if (login)
                        {
                            Toast.makeText(MenuActivity.this,"Login Berhasil", Toast.LENGTH_LONG).show();
                            AdminMode = true;
                            alertDialog.dismiss();
                            toolbar.setTitle("SiPaBi Admin Mode");
                            btnDataGejala.setVisibility(View.VISIBLE);
                            btnDataKeputusan.setVisibility(View.VISIBLE);

                            TextView textView = findViewById(R.id.txtAdminMode);
                            textView.setText("User Mode");
//                            AdminMode
                        }
                        else
                        {
                            Toast.makeText(MenuActivity.this,"Login Gagal", Toast.LENGTH_LONG).show();

                        }

                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });


//                Intent i = new Intent(MenuActivity.this, BantuanActivity.class);
//                startActivity(i);
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
                                System.exit(0); //Tambah Ini
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

    private boolean Login(String user, String pass) {
        ArrayList<String> dataUser = new ArrayList<String>();
        dataUser.add("admin:admin");
        String key = user + ":" + pass;
        System.out.println("key = " + key);
        for(String s : dataUser)
            if(s.equals(key))
                return true;
        return false;
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