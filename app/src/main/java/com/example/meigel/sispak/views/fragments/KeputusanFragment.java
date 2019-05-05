package com.example.meigel.sispak.views.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.meigel.sispak.R;
import com.example.meigel.sispak.helpers.SQLiteHelper;
import com.example.meigel.sispak.models.Gejala;
import com.example.meigel.sispak.models.Keputusan;
import com.example.meigel.sispak.views.adapters.GejalaItemAdapter;
import com.example.meigel.sispak.views.adapters.KeputusanAdapter;
import com.example.meigel.sispak.views.adapters.KeputusanGejalaAdapater;

import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class KeputusanFragment extends Fragment {

    private ListView listItemKeputusan;
    private KeputusanAdapter adapter;
    private static boolean AdminMode = false;
    HashMap<String,Double> mapNama = new HashMap<>();

    public KeputusanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_keputusan, container, false);
        listItemKeputusan = (ListView)view.findViewById(R.id.listKeputusan);
        setupList();
        AdminMode = this.getArguments().getBoolean("admin",false);
        return view;
    }

    private void setupList() {
        final List<Keputusan> keputusanList = SQLiteHelper.getInstance(getActivity()).getKeputusans();
        adapter = new KeputusanAdapter(keputusanList, getActivity());
        listItemKeputusan.setAdapter(adapter);

        listItemKeputusan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                LayoutInflater li = LayoutInflater.from(getContext());
                View prompt = li.inflate(R.layout.form_keputusan, null);

                final AlertDialog.Builder alertDialogBuilder =
                        new AlertDialog.Builder(getContext());
                alertDialogBuilder.setTitle("Admin Mode");
                alertDialogBuilder.setMessage("Edit Data Keputusan");
                alertDialogBuilder.setView(prompt);
                final AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();


                Button btnSimpanKeputusan = (Button) prompt.findViewById(R.id.btnSimpanKeputusan);
                Button btnHapusKeputusan = (Button) prompt.findViewById(R.id.btnHapusKeputusan);
                final EditText KeputusanKode = (EditText) prompt.findViewById(R.id.KeputusanKode);

                KeputusanKode.setText(keputusanList.get(position).getPenyakit());


                ListView listGejalaKeputusan = (ListView) prompt.findViewById(R.id.listGejalaKeputusan);
                List<Gejala> gejalaList = SQLiteHelper.getInstance(getActivity()).getGejalas();

                KeputusanGejalaAdapater keputusanGejalaAdapater =
                        new KeputusanGejalaAdapater(gejalaList,getContext());
                listGejalaKeputusan.setAdapter(keputusanGejalaAdapater);


                btnHapusKeputusan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                btnSimpanKeputusan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        
                        alertDialog.dismiss();
                    }
                });

            }
        });


    }

}