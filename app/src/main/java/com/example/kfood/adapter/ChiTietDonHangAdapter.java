package com.example.kfood.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kfood.R;
import com.example.kfood.model.ChiTietDonHang;

import java.util.ArrayList;

public class ChiTietDonHangAdapter extends BaseAdapter {
    Context context;
    ArrayList<ChiTietDonHang> arrchitietdonhang;

    public ChiTietDonHangAdapter(Context context, ArrayList<ChiTietDonHang> arrchitietdonhang) {
        this.context = context;
        this.arrchitietdonhang = arrchitietdonhang;
    }

    @Override
    public int getCount() {
        return arrchitietdonhang.size();
    }

    @Override
    public Object getItem(int position) {
        return arrchitietdonhang.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    class ViewHolder{
        TextView txtTendonhang, txtSoluongdonhang, txtGiadonhang;
        ImageView imgChitietdonhang;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_chi_tiet_don_hang, parent, false);
            viewHolder.imgChitietdonhang = convertView.findViewById(R.id.imgsanphamdamua);
            viewHolder.txtTendonhang = convertView.findViewById(R.id.tvtendonhangdamua);
            viewHolder.txtSoluongdonhang = convertView.findViewById(R.id.tvtsoluongdonhangdamua);
            viewHolder.txtGiadonhang = convertView.findViewById(R.id.tvtendongiadamua);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ChiTietDonHang chitietdonhang = arrchitietdonhang.get(position);
        viewHolder.txtTendonhang.setText(chitietdonhang.getTensanpham().toString());
        viewHolder.txtSoluongdonhang.setText(chitietdonhang.getSoluongsanpham().toString());
        viewHolder.txtGiadonhang.setText(chitietdonhang.getGiasanpham().toString());

        return convertView;
    }
}
