package com.example.kfood.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kfood.R;
import com.example.kfood.Service.APIService;
import com.example.kfood.Service.DataService;
import com.example.kfood.activity.AdminActivity;
import com.example.kfood.activity.EditActivity;
import com.example.kfood.model.Sanpham;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditFoodAdapter extends RecyclerView.Adapter<EditFoodAdapter.ViewHolder>{

    Context context;
    ArrayList<Sanpham> list;

    public EditFoodAdapter(Context context, ArrayList<Sanpham> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_all_food, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Sanpham sanpham = list.get(i);
        viewHolder.tvten.setText(sanpham.getTensanpham());
        viewHolder.tvgia.setText(sanpham.getGiasanpham()+"");
        Picasso.with(context).load(sanpham.getHinhanhsanpham()).into(viewHolder.imghinh);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imghinh, imgedit, imgdel;
        TextView tvten, tvgia;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imghinh = itemView.findViewById(R.id.imgallfood);
            imgedit = itemView.findViewById(R.id.imgeditall);
            imgdel = itemView.findViewById(R.id.imgdelall);
            tvten = itemView.findViewById(R.id.tvtenallfood);
            tvgia = itemView.findViewById(R.id.tvgiaallfood);

            imgedit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Sửa", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, EditActivity.class);

                    Bundle bundle = new Bundle();
                    bundle.putString("ten",list.get(getPosition()).getTensanpham());
                    bundle.putString("gia",list.get(getPosition()).getGiasanpham()+"");
                    bundle.putString("mota",list.get(getPosition()).getMotasanpham());
                    bundle.putString("anh",list.get(getPosition()).getHinhanhsanpham());
                    bundle.putString("id",list.get(getPosition()).getID()+"");

                    intent.putExtra("sp", bundle);
                    context.startActivity(intent);

                }
            });

            imgdel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    xacNhanXoa(list.get(getPosition()).getID()+"", list.get(getPosition()).getTensanpham(),list.get(getPosition()).getHinhanhsanpham());
                }
            });
        }



        void xacNhanXoa(final String id, String ten, final String hinhanh){
            AlertDialog.Builder alertdialog = new AlertDialog.Builder(context);
            alertdialog.setTitle("Thông báo!");
            alertdialog.setMessage("Bạn có chắc chắn muốn xóa món: "+ten+" không??");

            alertdialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(final DialogInterface dialog, int which) {
                    String hinh = hinhanh;
                    hinh = hinh.substring(hinh.lastIndexOf("/"));
                    DataService dataService = APIService.getService();
                    Call<String> callback = dataService.DeleteFood(id,hinh);
                    callback.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            Log.e("DELETE", response.body());
                            if (response.body().trim().equals("success")){
                                Toast.makeText(context, "Không thể xóa", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }else {
                                Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(context, AdminActivity.class);
                                context.startActivity(intent);

                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast.makeText(context, "Kiểm tra lại kết nối", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });

            alertdialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            alertdialog.show();
        }
    }
}
