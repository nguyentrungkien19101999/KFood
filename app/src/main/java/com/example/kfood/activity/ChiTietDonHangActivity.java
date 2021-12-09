package com.example.kfood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kfood.R;
import com.example.kfood.Service.APIService;
import com.example.kfood.Service.DataService;
import com.example.kfood.adapter.ChiTietDonHangAdapter;
import com.example.kfood.model.ChiTietDonHang;
import com.example.kfood.model.DonHang;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChiTietDonHangActivity extends AppCompatActivity {
    TextView txtTenkhachhang, txtSodienthoai, txtTongtien;
    ListView lvSanphamdamua;

    ArrayList<ChiTietDonHang> arrChiTietDonHang;
    ChiTietDonHangAdapter chiTietDonHangAdapter;
    DonHang donHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_don_hang);

        Intent intent = getIntent();
        donHang = (DonHang) intent.getSerializableExtra("donhang");
        Log.e("donhang",donHang.getTenkhachhang());

        Anhxa();
        txtTenkhachhang.setText(donHang.getTenkhachhang().toString());
        txtSodienthoai.setText(donHang.getSdt().toString());
        getData();
        //Log.e("SIZE", arrChiTietDonHang.size()+" sdfghhg");
        /*long tongtien = 0;
        for (int i=0;i<arrChiTietDonHang.size();i++){
            tongtien = tongtien + (Integer.parseInt(arrChiTietDonHang.get(i).getGiasanpham()) * Integer.parseInt(arrChiTietDonHang.get(i).getSoluongsanpham()));
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtTongtien.setText(decimalFormat.format(tongtien)+" Đ");*/
    }

    private void getData() {
        DataService dataService = APIService.getService();
        Call<List<ChiTietDonHang>> layChitietdonhang = dataService.getChitietdonhang(donHang.getId());
        layChitietdonhang.enqueue(new Callback<List<ChiTietDonHang>>() {
            @Override
            public void onResponse(Call<List<ChiTietDonHang>> call, Response<List<ChiTietDonHang>> response) {
                Log.e("CHITIETDONHANG", response.body().toString());
                arrChiTietDonHang = (ArrayList<ChiTietDonHang>) response.body();
                chiTietDonHangAdapter = new ChiTietDonHangAdapter(getApplicationContext(),arrChiTietDonHang);
                lvSanphamdamua.setAdapter(chiTietDonHangAdapter);
                long tongtien = 0;
                for (int i=0;i<arrChiTietDonHang.size();i++){
                    tongtien = tongtien + (Integer.parseInt(arrChiTietDonHang.get(i).getGiasanpham()) * Integer.parseInt(arrChiTietDonHang.get(i).getSoluongsanpham()));
                }
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                txtTongtien.setText(decimalFormat.format(tongtien)+" Đ");
                Log.e("SIZE", arrChiTietDonHang.size()+"");
            }

            @Override
            public void onFailure(Call<List<ChiTietDonHang>> call, Throwable t) {
                Log.e("ERR", t.getMessage());
            }
        });
    }

    private void Anhxa() {
        txtTenkhachhang = findViewById(R.id.tvtenkhachhangabc);
        txtSodienthoai = findViewById(R.id.tvsdtkhachhang);
        txtTongtien = findViewById(R.id.tvtongtiendonhang);
        lvSanphamdamua = findViewById(R.id.lvspdamua);

    }

}
