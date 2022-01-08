package com.example.kfood.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.kfood.R;
import com.example.kfood.Service.APIService;
import com.example.kfood.Service.DataService;
import com.example.kfood.activity.ChiTietDonHangActivity;
import com.example.kfood.activity.LoginActivity;
import com.example.kfood.activity.ThemMonAnActivity;
import com.example.kfood.activity.XemDonHangActivivty;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentAddFood extends Fragment {

    Button btnadd;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_btn_them, container, false);

        btnadd = view.findViewById(R.id.btnthemmonan);

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ThemMonAnActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
