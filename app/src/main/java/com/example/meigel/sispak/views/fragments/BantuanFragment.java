package com.example.meigel.sispak.views.fragments;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.meigel.sispak.R;

public class BantuanFragment extends Fragment {
    private String[] imgBantuans = {"ic_konsultasi_white","ic_penyakit_white","ic_berita_white","ic_info_white","ic_feedback_white"};
    private String[] textBantuan = {"Menu Diagnosa ", "Menu Penyakit","Menu Bantuan","Menu Tentang","Menu Keluar"};
    private TextView txtBantuan;
    private ImageView imgBantuan;

    public static BantuanFragment newInstance(int pos){
        BantuanFragment fragment = new BantuanFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("pos",pos);
        fragment.setArguments(bundle);

        return fragment;
    }

    public BantuanFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bantuan, container, false);
        txtBantuan = (TextView)view.findViewById(R.id.txtBantuanText);
        imgBantuan = (ImageView)view.findViewById(R.id.txtBantuanImage);
        txtBantuan.setText(textBantuan[getArguments().getInt("pos")]);
        imgBantuan.setImageResource(getContext().getResources().getIdentifier(imgBantuans[getArguments().getInt("pos")], "drawable", getContext().getPackageName()));
        return view;
    }
}
