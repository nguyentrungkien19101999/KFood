package com.example.kfood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kfood.R;
import com.example.kfood.until.CheckConnect;
import com.example.kfood.until.Sever;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ThongTinKhachHangActivity extends AppCompatActivity {

    EditText edtTenKhachHang, edtEmailKhachHang, edtSdtKhachHang;
    Button btnXacNhan, btnTroVe;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_khach_hang);
        Anhxa();
        btnTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (CheckConnect.haveNetworkConnection(getApplicationContext())) {
            EventButton();
        } else {
            CheckConnect.showToast(getApplicationContext(), "Kiểm tra lại kết nối");
        }
    }

    private void EventButton() {
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Toast.makeText(ThongTinKhachHangActivity.this, "enter code here", Toast.LENGTH_SHORT).show();*/
                final String ten = edtTenKhachHang.getText().toString().trim();
                final String sdt = edtSdtKhachHang.getText().toString().trim();
                final String email = edtEmailKhachHang.getText().toString().trim();
                if (ten.isEmpty() || sdt.isEmpty() || email.isEmpty()) {
                    CheckConnect.showToast(getApplicationContext(), "Vui lòng nhập đầy đủ thông tin ");
                } else {
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Sever.Duongdandonhang, new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String madonhang) {

                            Log.d("madonhang", madonhang);
                            Log.e("MADONHANG", madonhang);
                            //Toast.makeText(ThongTinKhachHangActivity.this, "check your API", Toast.LENGTH_SHORT).show();
                            if (Integer.parseInt(madonhang.trim()) > 0) {
                                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                StringRequest request = new StringRequest(Request.Method.POST, Sever.Duongdanchitietdonhang, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if (response != null) {
                                            MainActivity.manggiohang.clear();
                                            CheckConnect.showToast(getApplicationContext(), "bạn đã thêm dữ liệu giỏ hàng thành công");
                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(intent);
                                            CheckConnect.showToast(getApplicationContext(), "Mời bạn tiếp tục mua hàng");
                                            finish();
                                        } else {
                                            CheckConnect.showToast(getApplicationContext(), "Dữ liệu giỏ hàng của bạn bị lỗi");
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }) {
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        JSONArray jsonArray = new JSONArray();
                                        for (int i = 0; i < MainActivity.manggiohang.size(); i++) {
                                            JSONObject jsonObject = new JSONObject();
                                            try {
                                                jsonObject.put("madonhang", madonhang.trim());
                                                jsonObject.put("masanpham", MainActivity.manggiohang.get(i).getIdSP());
                                                jsonObject.put("tensanpham", MainActivity.manggiohang.get(i).getTenSP());
                                                jsonObject.put("giasanpham", MainActivity.manggiohang.get(i).getGiaSP());
                                                jsonObject.put("soluongsanpham", MainActivity.manggiohang.get(i).getSoLuong());

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            jsonArray.put(jsonObject);
                                        }
                                        HashMap<String, String> hashMap = new HashMap<String, String>();
                                        hashMap.put("json", jsonArray.toString());
                                        return hashMap;
                                    }
                                };
                                queue.add(request);
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(ThongTinKhachHangActivity.this, "Cannot connect to server", Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String, String> hashMap = new HashMap<String, String>();
                            hashMap.put("tenkhachhang", ten);
                            Log.e("TENKHACHHANG", ten);
                            hashMap.put("sodienthoai", sdt);
                            Log.e("SDTKH", sdt);
                            hashMap.put("email", email);
                            Log.e("EMAILKH", email);
                            return hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);
                }
            }
        });
    }

    private void Anhxa() {
        edtTenKhachHang = findViewById(R.id.edtTenKhachHang);
        edtEmailKhachHang = findViewById(R.id.edtEmailKhachHang);
        edtSdtKhachHang = findViewById(R.id.edtDienThoaiKhachHang);
        btnXacNhan = findViewById(R.id.btnXacNhanThongTinKhachHang);
        btnTroVe = findViewById(R.id.btnTroVe);
        toolbar = findViewById(R.id.toolbarThongTinKhachHang);
    }
}
