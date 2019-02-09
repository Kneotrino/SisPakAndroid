package com.example.meigel.sispak.views.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.meigel.sispak.R;
import com.example.meigel.sispak.helpers.SQLiteHelper;
import com.example.meigel.sispak.models.Keputusan;
import com.example.meigel.sispak.models.Penyakit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class ResultDiagnoseActivity extends AppCompatActivity {
    private String[] results;
    private ListView listView;
    private HashMap<String, ArrayList<String>> chains = new HashMap<>();
    private Toolbar toolbar;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_diagnose);
        setupToolbar();
        getBundle();
        setupView();
        chainProcess();
        setupList();
    }

    private void setupToolbar(){
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Hasil diagnosa");
    }

    private void setupView(){
        listView = (ListView)findViewById(R.id.listPenyakit);
        btnBack = (Button)findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void getBundle(){
        Bundle b = getIntent().getExtras();
        results = b.getStringArray("selectedItems");
    }

    private void chainProcess(){
        for(String code : results){
            for(Keputusan keputusan : SQLiteHelper.getInstance(this).getKeputusans()){
                if(keputusan.getGejala().contains(code + ",")){
                    if(chains.containsKey(keputusan.getPenyakit())){
                        chains.get(keputusan.getPenyakit()).add(code);
                    } else {
                        ArrayList<String> str = new ArrayList<>();
                        str.add(keputusan.getGejala().split(",").length + "");
                        str.add(code);
                        chains.put(keputusan.getPenyakit(), str);
                    }
                }
            }
        }
    }

    private void setupList(){

        HashMap<String,Double> mapProbability = new HashMap<>();
        // tabel bobot penyakit
        mapProbability.put("H1",0.5d);
        mapProbability.put("H2",0.6d);
        mapProbability.put("H3",0.6d);
        mapProbability.put("H4",0.7d);
        mapProbability.put("H5",0.5d);
        mapProbability.put("H6",0.4d);
        mapProbability.put("H7",0.45d);
        mapProbability.put("H8",0.3d);
        //tabel penyakit H1
        mapProbability.put("H1-E1", .31d);
        mapProbability.put("H1-E2", .52d);
        mapProbability.put("H1-E3", .59d);
        mapProbability.put("H1-E4", .61d);
        mapProbability.put("H1-E5", .66d);
        mapProbability.put("H1-E6", .78d);
        mapProbability.put("H1-E7", .9d);
        mapProbability.put("H1-E8", 1d);
        //tabel penyakit H2
        mapProbability.put("H2-E1", .2d);
        mapProbability.put("H2-E6", .8d);
        mapProbability.put("H2-E9", .39d);
        mapProbability.put("H2-E10", .89d);
        mapProbability.put("H2-E11", 1d);
        //tabel penyakit H3
        mapProbability.put("H3-E1", .4d);
        mapProbability.put("H3-E2", .48d);
        mapProbability.put("H3-E12", .6d);
        mapProbability.put("H3-E13", .88d);
        //tabel penyakit H4
        mapProbability.put("H4-E1", .33d);
        mapProbability.put("H4-E2", .4d);
        mapProbability.put("H4-E3", .6d);
        mapProbability.put("H4-E14", .74d);
        mapProbability.put("H4-E15", .91d);
        mapProbability.put("H4-E16", .98d);
        //tabel penyakit H5
        mapProbability.put("H5-E1", .38d);
        mapProbability.put("H5-E3", .92d);
        mapProbability.put("H5-E6", .43d);
        mapProbability.put("H5-E9", .41d);
        mapProbability.put("H5-E10", .64d);
        mapProbability.put("H5-E14", 1d);
        //tabel penyakit H6
        mapProbability.put("H6-E1", .27d);
        mapProbability.put("H6-E3", .33d);
        mapProbability.put("H6-E17", .88d);
        mapProbability.put("H6-E18", .7d);
        mapProbability.put("H6-E19", 1d);
        //tabel penyakit H7
        mapProbability.put("H7-E5", .99d);
        mapProbability.put("H7-E13", .52d);
        mapProbability.put("H7-E17", .82d);
        //tabel penyakit H8
        mapProbability.put("H8-E1", .26d);
        mapProbability.put("H8-E2", .44d);
        mapProbability.put("H8-E3", .81d);
        mapProbability.put("H8-E5", 1d);
        mapProbability.put("H8-E20", .5d);

        List<String> test = new LinkedList<>();
        test.add("E1");
        test.add("E3");
        test.add("E6");

        double TotalProbabilitas = 0d;
        List<Penyakit> penyakits = SQLiteHelper.getInstance(this).getPenyakits();
        for (Penyakit penyakit : penyakits) {
            double probababilitasExH = 1d;
            String keyH = penyakit.getKode();
            for (String s : test) {
                String key = keyH+"-"+s;
                Double vE = 0d;
                if  ( mapProbability.get(key) == null)
                    vE = 0d;
                else
                    vE = mapProbability.get(key);
                probababilitasExH = probababilitasExH * vE;
            }
            Double vH = 0d;
            if  ( mapProbability.get(penyakit.getKode()) == null)
                vH = 0d;
            else
                vH = mapProbability.get(penyakit.getKode());

            probababilitasExH = probababilitasExH * vH;
            Log.d("P(" +keyH +"|"+ test.toString()+") ", "= "+probababilitasExH );
            TotalProbabilitas = TotalProbabilitas + probababilitasExH;
        }

        ArrayList<String> resultnya = new ArrayList<>();

        for (Penyakit penyakit : penyakits) {
            double probababilitasExH = 1d;
            String keyH = penyakit.getKode();
            for (String s : test) {
                String key = keyH+"-"+s;
                Double vE = 0d;
                if  ( mapProbability.get(key) == null)
                    vE = 0d;
                else
                    vE = mapProbability.get(key);
                probababilitasExH = probababilitasExH * vE;
            }
            Double vH = 0d;
            if  ( mapProbability.get(penyakit.getKode()) == null)
                vH = 0d;
            else
                vH = mapProbability.get(penyakit.getKode());
            probababilitasExH = probababilitasExH * vH;
            double value = probababilitasExH / TotalProbabilitas;
//            System.out.println("P(" +keyH +"|"+ test.toString()+") / TotalProbabilitas= "+ value);
            Log.d("P(" +keyH +"|"+ test.toString()+") ", "= "+value );


            int persen = (int) Math.ceil(((probababilitasExH/TotalProbabilitas)) * 100);
            resultnya.add(persen + " % " + penyakit.getName());

        }



        final Set<String> keySet = chains.keySet();
//        for(String key : keySet){
//            float ms = Float.parseFloat(chains.get(key).get(0));
//            Log.d("MainApp","ms : " + ms);
//            float ma = chains.get(key).size() - 1;
//            Log.d("MainApp","ma : " + ma);
//            int pa = (int) Math.ceil((1 - (ma/ms)) * 100);
////            float pa = ma / ms * 100;
//            Log.d("MainApp","Pa : " + pa);
//            String namaPenyakit = SQLiteHelper.getInstance(this).getPenyakit(key).getKode();
//            Log.d("MainApp","Penyakit : " + namaPenyakit + " Code : " + key);
//            Log.d("MainApp","=======================");
//            resultnya.add((int)pa + " % " + namaPenyakit);
//        }


//        resultnya.add("48% Hog Cholere");
//        resultnya.add("51% Erysipelas");

        System.out.println("resultnya = " + resultnya);
        System.out.println("keySet = " + keySet);





        ArrayAdapter<String> diagnoseAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, resultnya);
        listView.setAdapter(diagnoseAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(ResultDiagnoseActivity.this, DetailDataActivity.class);
                i.putExtra("id",(String)keySet.toArray()[position]);
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
