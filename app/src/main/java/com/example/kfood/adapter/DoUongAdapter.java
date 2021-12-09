package com.example.kfood.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kfood.R;
import com.example.kfood.model.Sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class DoUongAdapter extends BaseAdapter {
    Context context;
    ArrayList<Sanpham> arrayDouong;

    public DoUongAdapter(Context context, ArrayList<Sanpham> arrayDouong) {
        this.context = context;
        this.arrayDouong = arrayDouong;
    }

    @Override
    public int getCount() {
        return arrayDouong.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayDouong.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public static class ViewHolder{
        public TextView txttendouong, txtgiadouong,txtmotadouong;
        public ImageView imageViewDouong;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder =null;
        //ViewHolder viewHolder =null;
        if (convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_douong, null);
            viewHolder.txttendouong = convertView.findViewById(R.id.textviewtendouong);
            viewHolder.txtgiadouong = convertView.findViewById(R.id.textviewgiadouong);
            viewHolder.txtmotadouong = convertView.findViewById(R.id.textviewmotadouong);
            viewHolder.imageViewDouong = convertView.findViewById(R.id.imageviewdouong);
            convertView.setTag(viewHolder);


        }else {
            viewHolder = (ViewHolder) convertView.getTag();

        }
        Sanpham sanpham = (Sanpham) getItem(position);
        viewHolder.txttendouong.setText(sanpham.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtgiadouong.setText("Giá : " + decimalFormat.format(sanpham.getGiasanpham()) + " Đ");
        viewHolder.txtmotadouong.setMaxLines(2);
        viewHolder.txtmotadouong.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtmotadouong.setText(sanpham.getMotasanpham());
        Picasso.with(context).load(sanpham.getHinhanhsanpham()).into(viewHolder.imageViewDouong);

        return convertView;
    }
}
