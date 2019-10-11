package com.example.milano.dapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.milano.R;
import com.example.milano.model.Impressora;

import java.util.List;

public class ImpressoraAdater extends BaseAdapter {

    Context context;
    List<Impressora> list;

    public ImpressoraAdater(Context context, List<Impressora> lista) {
        this.context = context;
        this.list = lista;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View myView = LayoutInflater.from(context).inflate(R.layout.layout_impressora, viewGroup, false);
        TextView nome_ompressora = myView.findViewById(R.id.text_name_impressora);
        TextView address_impressora = myView.findViewById(R.id.text_address_impressora);
        nome_ompressora.setText(list.get(position).getNome());
        address_impressora.setText(list.get(position).getAddress());
        return myView;
    }
}
