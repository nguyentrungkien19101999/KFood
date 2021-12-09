package com.example.kfood.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kfood.R;
import com.example.kfood.activity.ChiTietSanPham;
import com.example.kfood.model.Sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ItemHolder> {
    Context context;
    ArrayList<Sanpham> arraysanpham;

    public SanPhamAdapter(Context context, ArrayList<Sanpham> arraysanpham) {
        this.context = context;
        this.arraysanpham = arraysanpham;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dong_sanphammoinhat,null);
        // gan thuoc tinh vao view
        ItemHolder itemHolder = new ItemHolder(v);

        return itemHolder;
    }
        //get, set thuoc tinh gan len layout
    @Override
    public void onBindViewHolder( ItemHolder holder, int i) {
        Sanpham sanpham = arraysanpham.get(i);
        holder.txttensp.setText(sanpham.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtgiasanpham.setText("Giá : " + decimalFormat.format(sanpham.getGiasanpham()) + " Đ");
       /* Picasso.with(context).load(sanpham.getHinhanhsanpham())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(holder.imghinhsanpham); //??*/
       Picasso.with(context).load(sanpham.getHinhanhsanpham()).into(holder.imghinhsanpham);
    }

    @Override
    public int getItemCount() {
        return arraysanpham.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {

        public ImageView imghinhsanpham;
        public TextView txttensp,txtgiasanpham;

        public ItemHolder( View itemView) {//anh xa
            super(itemView);
            imghinhsanpham = itemView.findViewById(R.id.imageviewsanphammoi);
            txtgiasanpham = itemView.findViewById(R.id.textviewgiasanpham);
            txttensp = itemView.findViewById(R.id.textviewtensanpham);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ChiTietSanPham.class);
                    intent.putExtra("thongtinsanpham",arraysanpham.get(getLayoutPosition()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    // CheckConnection.ShowToast_Short(context,sanphamArrayList.get(getLayoutPosition()).getTensp());
                    context.startActivity(intent);
                }
            });
        }
    }
}
