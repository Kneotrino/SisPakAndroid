package com.example.meigel.sispak.views.adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.example.meigel.sispak.R;
import com.example.meigel.sispak.helpers.SQLiteHelper;
import com.example.meigel.sispak.models.Gejala;
import com.example.meigel.sispak.models.Keputusan;
import com.example.meigel.sispak.models.Penyakit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KeputusanGejalaAdapater extends BaseAdapter {
    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return gejalaList.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Object getItem(int position) {
        return gejalaList.get(position);
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.item_keputusan_gejala, parent, false);
        TextView txtGejalaNama = (TextView)rootView.findViewById(R.id.txtGejalaNama);
        EditText BobotGejalaKeputusan = (EditText) rootView.findViewById(R.id.BobotGejalaKeputusan);
        txtGejalaNama.setText(gejalaList.get(position).getNama());
        final Gejala gejala = gejalaList.get(position);

        BobotGejalaKeputusan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    mapData.put(gejala.getKode(),s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        try {
            Keputusan byKode = SQLiteHelper.getInstance(context)
                    .getKeputusanGejalaByKode(penyakit.getKode(),gejala.getKode());
            BobotGejalaKeputusan.setText(byKode.getProbalitas());
        }
        catch ( Exception e )
        {
            BobotGejalaKeputusan.setText("0.0");
        }
        mapData.put(gejala.getKode(),BobotGejalaKeputusan.getText().toString());
        return rootView;
    }




    private Penyakit penyakit;
    private List<Gejala> gejalaList;
    private Context context;
    HashMap<String,String> mapData = new HashMap<>();

    public HashMap<String, String> getMapData() {
        System.out.println("mapData = " + mapData);
        return mapData;
    }

    public KeputusanGejalaAdapater(Penyakit penyakit, List<Gejala> gejalaList, Context context) {
        this.penyakit = penyakit;
        this.gejalaList = gejalaList;
        this.context = context;
        for (Gejala g: gejalaList) {
            mapData.put(g.getKode(),"0.0");
        }
    }
}
