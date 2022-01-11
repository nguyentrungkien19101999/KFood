package com.example.kfood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.kfood.R;
import com.example.kfood.model.Giohang;
import com.example.kfood.model.Sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class ChiTietSanPham extends AppCompatActivity {
    Toolbar toolbarChiTietSanPham;
    ImageView imgChiTietSanPham;
    TextView txtTenSP, txtGiaSP, txtMoTaSP;
    Spinner spinner;
    Button btnDatMua;

    //Khai bao toan cuc de co the su dung lai
    int id = 0;
    String tenChiTietSP = "";
    int giaChiTietSP = 0;
    String hinhAnhChiTiet = "";
    String moTaChiTiet = "";
    int idSanPham = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);
        Anhxa();
        ActionToolbar();
        GetInformation();
        CatchEventSpinner();
        EventButton();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.mnuGioHang:
                Intent intent = new Intent(getApplicationContext(),GioHangActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    private void EventButton() {
        btnDatMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.manggiohang.size()>0){
                    int soLuong = Integer.parseInt(spinner.getSelectedItem().toString());
                    boolean exists = false;
                    for(int i=0;i<MainActivity.manggiohang.size();i++){
                        if (id==MainActivity.manggiohang.get(i).getIdSP()){
                            MainActivity.manggiohang.get(i).setSoLuong(MainActivity.manggiohang.get(i).getSoLuong()+soLuong);
                            if (MainActivity.manggiohang.get(i).getSoLuong()>10){
                                MainActivity.manggiohang.get(i).setSoLuong(10);
                            }
                            MainActivity.manggiohang.get(i).setGiaSP(MainActivity.manggiohang.get(i).getSoLuong()*giaChiTietSP);
                            exists = true;
                        }
                    }
                    if (exists==false){
                        long giaMoi = soLuong * giaChiTietSP;
                        MainActivity.manggiohang.add(new Giohang(id,tenChiTietSP,giaMoi,hinhAnhChiTiet,soLuong));

                    }

                }else {
                    int soLuong = Integer.parseInt(spinner.getSelectedItem().toString());
                    long giaMoi = soLuong * giaChiTietSP;
                    MainActivity.manggiohang.add(new Giohang(id,tenChiTietSP,giaMoi,hinhAnhChiTiet,soLuong));

                }
                Intent intent = new Intent(getApplicationContext(),GioHangActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void CatchEventSpinner() {
        Integer[] soluong = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        //ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,soluong);
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this, R.layout.support_simple_spinner_dropdown_item,soluong);
        spinner.setAdapter(arrayAdapter);
    }

    private void GetInformation() {
        Sanpham sanpham = (Sanpham) getIntent().getSerializableExtra("thongtinsanpham");
        id = sanpham.getID();
        tenChiTietSP = sanpham.getTensanpham();
        giaChiTietSP = sanpham.getGiasanpham();
        hinhAnhChiTiet = sanpham.getHinhanhsanpham();
        moTaChiTiet = sanpham.getMotasanpham();
        idSanPham = sanpham.getIDsanpham();
        txtTenSP.setText(tenChiTietSP);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtGiaSP.setText("Giá: "+decimalFormat.format(giaChiTietSP)+" Đ");
        txtMoTaSP.setText(moTaChiTiet);
        Picasso.with(getApplicationContext()).load(hinhAnhChiTiet)
//                            .placeholder(R.drawable.noimage)
//                            .error(R.drawable.error)
                           .into(imgChiTietSanPham);
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarChiTietSanPham);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarChiTietSanPham.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void Anhxa() {
        toolbarChiTietSanPham = findViewById(R.id.toolbarChiTietSanPham);
        imgChiTietSanPham = findViewById(R.id.imgSanPham);
        txtTenSP = findViewById(R.id.txtTenSanPham);
        txtGiaSP = findViewById(R.id.txtGiaSanPham);
        txtMoTaSP = findViewById(R.id.txtMoTaSanPham);
        spinner = findViewById(R.id.spinner);
        btnDatMua = findViewById(R.id.btnDatMua);
    }
}
