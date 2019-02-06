package com.example.meigel.sispak.views.fragments;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.meigel.sispak.R;
import com.example.meigel.sispak.helpers.SQLiteHelper;
import com.example.meigel.sispak.models.Penyakit;
import com.example.meigel.sispak.views.activities.DetailDataActivity;
import com.example.meigel.sispak.views.adapters.PenyakitItemAdapter;

import java.util.List;

public class PenyakitFragment extends Fragment {
    private ListView listPenyakit;
    private PenyakitItemAdapter pia;

    public PenyakitFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_penyakit, container, false);
        listPenyakit = (ListView)view.findViewById(R.id.listPenyakit);
        setupList();
        return view;
    }

    private void setupList(){
        final List<Penyakit> penyakits = SQLiteHelper.getInstance(getActivity()).getPenyakits();
        pia = new PenyakitItemAdapter(penyakits, getActivity());
        listPenyakit.setAdapter(pia);
        listPenyakit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(), DetailDataActivity.class);
                i.putExtra("id",penyakits.get(position).getKode());
                startActivity(i);
            }
        });
    }

}
