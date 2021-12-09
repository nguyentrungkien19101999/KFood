package com.example.kfood.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kfood.R;
import com.example.kfood.model.DonHang;

import java.util.ArrayList;

public class DonHangAdapter extends BaseAdapter {

    Context context;
    ArrayList<DonHang> list;


    public DonHangAdapter(Context context, ArrayList<DonHang> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder{
        TextView tvtendonhang, tvtennguoi;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder =null;
        //ViewHolder viewHolder =null;
        if (convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_don_hang, null);
            viewHolder.tvtendonhang = convertView.findViewById(R.id.tvtendonhang);
            viewHolder.tvtennguoi = convertView.findViewById(R.id.tvtennguoidat);

            convertView.setTag(viewHolder);


        }else {
            viewHolder = (ViewHolder) convertView.getTag();

        }
        DonHang donhang = list.get(position);
        viewHolder.tvtendonhang.setText("Đơn hàng " + (position + 1));
        viewHolder.tvtennguoi.setText(donhang.getTenkhachhang());

        return convertView;
    }
}
