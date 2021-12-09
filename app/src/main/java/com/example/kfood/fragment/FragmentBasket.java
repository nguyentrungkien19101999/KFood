package com.example.kfood.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.kfood.R;
import com.example.kfood.Service.APIService;
import com.example.kfood.Service.DataService;
import com.example.kfood.activity.ChiTietDonHangActivity;
import com.example.kfood.adapter.DonHangAdapter;
import com.example.kfood.model.DonHang;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentBasket extends Fragment {
    ListView lvdonhang;
    ArrayList<DonHang> list;
    DonHangAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_xem_don_hang_activivty, container, false);
        lvdonhang = view.findViewById(R.id.lvdonhang);

        getData();

        lvdonhang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ChiTietDonHangActivity.class);
                intent.putExtra("donhang", list.get(position));
                startActivity(intent);
            }
        });

        return view;
    }
    private void getData() {
        list = new ArrayList<>();

        DataService dataService = APIService.getService();
        Call<List<DonHang>> callback = dataService.getListCart();
        callback.enqueue(new Callback<List<DonHang>>() {
            @Override
            public void onResponse(Call<List<DonHang>> call, Response<List<DonHang>> response) {
                list = (ArrayList<DonHang>) response.body();
                adapter = new DonHangAdapter(getActivity(), list);
                lvdonhang.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<DonHang>> call, Throwable t) {
                Toast.makeText(getActivity(), "Kiểm tra kết nối mạng", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
