package com.example.kfood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kfood.R;
import com.example.kfood.Service.APIService;
import com.example.kfood.Service.DataService;
import com.example.kfood.adapter.DonHangAdapter;
import com.example.kfood.model.DonHang;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class XemDonHangActivivty extends AppCompatActivity {

    ListView lvdonhang;
    ArrayList<DonHang> list;
    DonHangAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xem_don_hang_activivty);

        lvdonhang = findViewById(R.id.lvdonhang);

        getData();

        lvdonhang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(XemDonHangActivivty.this, ChiTietDonHangActivity.class);
                intent.putExtra("donhang", list.get(position));
                startActivity(intent);
            }
        });
    }


    private void getData() {
        list = new ArrayList<>();

        DataService dataService = APIService.getService();
        Call<List<DonHang>> callback = dataService.getListCart();
        callback.enqueue(new Callback<List<DonHang>>() {
            @Override
            public void onResponse(Call<List<DonHang>> call, Response<List<DonHang>> response) {
                list = (ArrayList<DonHang>) response.body();
                adapter = new DonHangAdapter(getApplicationContext(), list);
                lvdonhang.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<DonHang>> call, Throwable t) {
                Toast.makeText(XemDonHangActivivty.this, "Kiểm tra kết nối mạng", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
