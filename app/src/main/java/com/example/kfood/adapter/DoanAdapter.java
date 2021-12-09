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

public class DoanAdapter extends BaseAdapter  {
    Context context;
    ArrayList<Sanpham> arrayDoan;

    public DoanAdapter(Context context, ArrayList<Sanpham> arrayDoan) {
        this.context = context;
        this.arrayDoan = arrayDoan;
    }

    @Override
    public int getCount() {
        return arrayDoan.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayDoan.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder{
        public TextView txttendoan, txtgiadoan,txtmotadoan;
        public ImageView imageViewDoan;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder =null;
        if (convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_doan, null);
            viewHolder.txttendoan = convertView.findViewById(R.id.textviewtendoan);
            viewHolder.txtgiadoan = convertView.findViewById(R.id.textviewgiadoan);
            viewHolder.txtmotadoan = convertView.findViewById(R.id.textviewmotadoan);
            viewHolder.imageViewDoan = convertView.findViewById(R.id.imageviewdoan);
            convertView.setTag(viewHolder);


        }else {
            viewHolder = (ViewHolder) convertView.getTag();

        }
        Sanpham sanpham = (Sanpham) getItem(position);
        viewHolder.txttendoan.setText(sanpham.getTensanpham().toString());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtgiadoan.setText("Giá : " + decimalFormat.format(sanpham.getGiasanpham()) + " Đ");
        viewHolder.txtmotadoan.setMaxLines(2);
        viewHolder.txtmotadoan.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtmotadoan.setText(sanpham.getMotasanpham());
        Picasso.with(context).load(sanpham.getHinhanhsanpham()).into(viewHolder.imageViewDoan);

        return convertView;
    }
}
