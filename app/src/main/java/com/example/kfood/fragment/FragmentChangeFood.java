package com.example.kfood.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kfood.R;
import com.example.kfood.Service.APIService;
import com.example.kfood.Service.DataService;
import com.example.kfood.adapter.EditFoodAdapter;
import com.example.kfood.model.Sanpham;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentChangeFood extends Fragment {
    RecyclerView recyclerViewAllFood;
    ArrayList<Sanpham> list;
    EditFoodAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_sua_xoa, container, false);

        recyclerViewAllFood = view.findViewById(R.id.recyclerviewallfood);
        GetData();
        return view;
    }

    private void GetData() {
        list = new ArrayList<>();
        DataService dataService = APIService.getService();
        Call<List<Sanpham>> callback = dataService.GetAllFood();
        callback.enqueue(new Callback<List<Sanpham>>() {
            @Override
            public void onResponse(Call<List<Sanpham>> call, Response<List<Sanpham>> response) {
                list = (ArrayList<Sanpham>) response.body();
                adapter = new EditFoodAdapter(getActivity(), list);
                recyclerViewAllFood.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerViewAllFood.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Sanpham>> call, Throwable t) {

            }
        });

    }
}
