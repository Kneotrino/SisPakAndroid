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
import android.widget.TextView;

import com.example.meigel.sispak.R;
import com.example.meigel.sispak.helpers.SQLiteHelper;
import com.example.meigel.sispak.models.Gejala;
import com.example.meigel.sispak.models.Keputusan;
import com.example.meigel.sispak.models.Penyakit;
import com.example.meigel.sispak.views.adapters.GejalaItemAdapter;
import com.example.meigel.sispak.views.adapters.KeputusanAdapter;
import com.example.meigel.sispak.views.adapters.KeputusanGejalaAdapater;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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
        final List<Penyakit> keputusanList = SQLiteHelper.getInstance(getActivity()).getPenyakits();

        adapter = new KeputusanAdapter(keputusanList, getActivity());
        listItemKeputusan.setAdapter(adapter);

        listItemKeputusan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                LayoutInflater li = LayoutInflater.from(getContext());
                View prompt = li.inflate(R.layout.form_keputusan, null);

                final AlertDialog.Builder alertDialogBuilder =
                        new AlertDialog.Builder(getContext());
                alertDialogBuilder.setTitle("Admin Mode");
                alertDialogBuilder.setMessage("Edit Data Keputusan");
                alertDialogBuilder.setView(prompt);
                final AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

                final Keputusan byKode = SQLiteHelper.getInstance(getActivity())
                        .getKeputusanByKode(keputusanList.get(position).getKode());

//                System.out.println("byKode = " + byKode);

                Button btnSimpanKeputusan = (Button) prompt.findViewById(R.id.btnSimpanKeputusan);
                Button btnHapusKeputusan = (Button) prompt.findViewById(R.id.btnHapusKeputusan);
                TextView txtNamaPenyakit = (TextView) prompt.findViewById(R.id.txtNamaPenyakit);
                final EditText BobotPenyakit = (EditText) prompt.findViewById(R.id.BobotPenyakit);

                BobotPenyakit.setText(byKode.getProbalitas());
                txtNamaPenyakit.setText(keputusanList.get(position).getName());

                final ListView listGejalaKeputusan = (ListView) prompt.findViewById(R.id.listGejalaKeputusan);
                final List<Gejala> gejalaList = SQLiteHelper.getInstance(getActivity()).getGejalas();

                final KeputusanGejalaAdapater keputusanGejalaAdapater =
                        new KeputusanGejalaAdapater(
                                keputusanList.get(position),
                                gejalaList,
                                getContext());
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

                        List<Keputusan> dataSimpan = new LinkedList<>();
                        byKode.setProbalitas(BobotPenyakit.getText().toString());
                        dataSimpan.add(byKode);

                        HashMap<String, String> data = keputusanGejalaAdapater.getMapData();
                        Set<String> stringSet = data.keySet();
                        for (String key: stringSet) {

                            Keputusan lama;
                            try {
                                lama = SQLiteHelper
                                        .getInstance(getContext())
                                        .getKeputusanGejalaByKode(
                                                byKode.getPenyakit(),
                                                key
                                                );
                                System.out.println("lama = " + lama);
                                lama.setProbalitas(data.get(key));
                                System.out.println("lama = " + lama);
                                dataSimpan.add(lama);
                            }
                            catch (Exception e)
                            {
                                Keputusan baru = new Keputusan(
                                        byKode.getPenyakit(),
                                        key,
                                        data.get(key)
                                );
                                System.out.println("baru = " + baru);
                                SQLiteHelper
                                        .getInstance(getContext())
                                        .addKeputusan(baru);
                            }
                        }


                        for (Keputusan keputusan: dataSimpan) {
                            SQLiteHelper
                                    .getInstance(getContext())
                                    .updateKeputusan(keputusan);
                        }
                        adapter.notifyDataSetChanged();
                        alertDialog.dismiss();
                    }
                });

            }
        });


    }

}
