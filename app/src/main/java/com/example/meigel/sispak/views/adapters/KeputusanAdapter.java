package com.example.meigel.sispak.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.meigel.sispak.R;
import com.example.meigel.sispak.helpers.SQLiteHelper;
import com.example.meigel.sispak.models.Gejala;
import com.example.meigel.sispak.models.Keputusan;
import com.example.meigel.sispak.models.Penyakit;

import java.util.HashMap;
import java.util.List;

public class KeputusanAdapter extends BaseAdapter {
    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return penyakitList.size();
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
        return penyakitList.get(position);
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
        View view = LayoutInflater.from(context).inflate(R.layout.keputusan_item_layout, parent, false);
        TextView txtPenyakitKeputusan = (TextView)view.findViewById(R.id.txtPenyakitKeputusan);
        TextView txtBobotKeputusan = (TextView)view.findViewById(R.id.txtBobotKeputusan);
        Keputusan byKode = SQLiteHelper.getInstance(context)
                .getKeputusanByKode(penyakitList.get(position).getKode());

        txtPenyakitKeputusan.setText(penyakitList.get(position).getName());
        txtBobotKeputusan.setText(byKode.getProbalitas());
        return view;
    }

    public KeputusanAdapter(List<Penyakit> penyakitList, Context context) {
        this.penyakitList = penyakitList;
        this.context = context;
    }

    private List<Penyakit> penyakitList;
    private Context context;

}
