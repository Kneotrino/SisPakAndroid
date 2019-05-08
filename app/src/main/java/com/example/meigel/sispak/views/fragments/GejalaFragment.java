package com.example.meigel.sispak.views.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.example.meigel.sispak.models.Penyakit;
import com.example.meigel.sispak.views.activities.DetailDataActivity;
import com.example.meigel.sispak.views.activities.MainDataActivity;
import com.example.meigel.sispak.views.adapters.GejalaItemAdapter;
import com.example.meigel.sispak.views.adapters.PenyakitItemAdapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GejalaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GejalaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GejalaFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "user";

    // TODO: Rename and change types of parameters
    private String mParam1;

    private ListView listItemGejala;
    private GejalaItemAdapter gejalaItemAdapteria;
    private static boolean AdminMode = false;


    private OnFragmentInteractionListener mListener;

    public GejalaFragment() {
        // Required empty public constructor
    }

    public static GejalaFragment newInstance(String param1) {
        GejalaFragment fragment = new GejalaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gejala, container, false);
        listItemGejala = (ListView)view.findViewById(R.id.listDataGejala);
        setupList();
        AdminMode = this.getArguments().getBoolean("admin",false);
        return view;
        
//        return inflater.inflate(R.layout.fragment_gejala, container, false);
    }

    private void setupList() {
        final List<Gejala> gejalaList = SQLiteHelper.getInstance(getActivity()).getGejalas();
        gejalaItemAdapteria= new GejalaItemAdapter(gejalaList, getActivity());
        listItemGejala.setAdapter(gejalaItemAdapteria);

        listItemGejala.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                LayoutInflater li = LayoutInflater.from(getContext());
                View prompt = li.inflate(R.layout.form_gejala, null);

                final AlertDialog.Builder alertDialogBuilder =
                        new AlertDialog.Builder(getContext());
                alertDialogBuilder.setTitle("Admin Mode");
                alertDialogBuilder.setMessage("Masukkan Data Gejala");
                alertDialogBuilder.setView(prompt);
                final AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

                final Gejala gejala = gejalaList.get(position);

                Button btnSimpanGejala = (Button) prompt.findViewById(R.id.btnSimpanGejala);
                Button btnGejalaBatal = (Button) prompt.findViewById(R.id.btnGejalaBatal);

                final EditText gejalaKode = (EditText) prompt.findViewById(R.id.gejalaKode);
                final EditText gejalaNama = (EditText) prompt.findViewById(R.id.gejalaNama);

                btnGejalaBatal.setText("HAPUS");
                gejalaKode.setText(gejala.getKode());
                gejalaNama.setText(gejala.getNama());


                btnGejalaBatal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int deletePenyakit = SQLiteHelper
                                .getInstance(getContext())
                                .deleteGejala(gejala);
                        gejalaList.remove(gejala);
                        gejalaItemAdapteria.notifyDataSetChanged();
                        alertDialog.dismiss();
                    }
                });

                btnSimpanGejala.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gejala.setKode(gejalaKode.getText().toString());
                        gejala.setNama(gejalaNama.getText().toString());
                        gejala.print();
                        SQLiteHelper
                                .getInstance(getContext())
                                .updateGejala(gejala);
                        gejalaItemAdapteria.notifyDataSetChanged();
                        alertDialog.dismiss();
                    }
                });

            }
        });


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
