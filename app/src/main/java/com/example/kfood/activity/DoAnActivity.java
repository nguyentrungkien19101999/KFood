package com.example.kfood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kfood.adapter.DoanAdapter;
import com.example.kfood.R;
import com.example.kfood.adapter.DoanAdapter;
import com.example.kfood.model.Sanpham;
import com.example.kfood.until.CheckConnect;
import com.example.kfood.until.Sever;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DoAnActivity extends AppCompatActivity {
    Toolbar toolbardoan;
    ListView lvdoan;
    DoanAdapter doanAdapter;
    ArrayList<Sanpham> mangdoan;

    int iddoan = 0;
    int page = 1;

    View footerView;
    boolean isLoading = false;

    mHander mHander;
    boolean isfull = false;

    boolean limitdata = false;
    int size = 0, sizeafter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_an);
        Anhxa();
        if (CheckConnect.haveNetworkConnection(getApplicationContext())){
            GetIDloaisp();
            ActionToolbar();
            //Log.e("KTTTTTTT", "lỗi ở đây?");
            GetData(page);
            LoadMoreData();

        }else {
            CheckConnect.showToast(getApplicationContext(), "Kiểm tra internet");
        }

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
    private void LoadMoreData() {
        lvdoan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ChiTietSanPham.class);
                intent.putExtra("thongtinsanpham",mangdoan.get(position));
                startActivity(intent);
            }
        });
        lvdoan.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {}

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount != 0 && isLoading == false && limitdata == false){
                    isLoading = true;
                    ThreadData threadData = new ThreadData();
                    threadData.start();
                }
            }
        });
    }

    private void GetData(int Page) {

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongdan = Sever.Duongdandoan+String.valueOf(Page);
        Log.e("PATH", duongdan);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id=0;
                String TenDoan = "";
                int GiaDoan = 0;
                String HinhanhDoan = "";
                String MotaDoan="";
                int Idspdoan=0;

                if (response != null && response.length()!=2){
                    lvdoan.removeFooterView(footerView);
                    try {
                        JSONArray jsonArray = new JSONArray(response);

                        for (int i = 0;i<jsonArray.length();i++){
                            Log.e("KTTTTTTT", "lỗi ở đây?");
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                           // Toast.makeText(DoAnActivity.this, ""+jsonObject.getInt("id"), Toast.LENGTH_SHORT).show();
                            //Toast.makeText(DoAnActivity.this, ""+jsonObject.getString("hinhanhsp"), Toast.LENGTH_SHORT).show();
                            id = jsonObject.getInt("id");
                            TenDoan = jsonObject.getString("tensp");
                            GiaDoan = jsonObject.getInt("giasp");
                            HinhanhDoan = jsonObject.getString("hinhanhsp");
                            MotaDoan = jsonObject.getString("motasp");
                            Idspdoan = jsonObject.getInt("idsanpham");
                            mangdoan.add(new Sanpham(id,TenDoan,GiaDoan,HinhanhDoan,MotaDoan, Idspdoan));
                        }
                        doanAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<String, String>();
                param.put("idsanpham",String.valueOf(iddoan));

                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbardoan);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbardoan.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void GetIDloaisp() {
        iddoan = getIntent().getIntExtra("idloaisanpham", -1);
        Log.d("giatriloaisanpham",iddoan+"");
    }

    private void Anhxa() {
        toolbardoan = findViewById(R.id.toolbardoan);
        lvdoan = findViewById(R.id.listviewdoan);
        mangdoan = new ArrayList<>();
        doanAdapter = new DoanAdapter(getApplicationContext(),mangdoan);
        lvdoan.setAdapter(doanAdapter);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerView = inflater.inflate(R.layout.progressbar, null);
        mHander= new mHander();
    }

    public class mHander extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    lvdoan.addFooterView(footerView);
                    break;
                case 1:
                    if (mangdoan.size() > size){
                        GetData(++page);
                        isLoading = false;
                        size = size+5;
                        isfull = false;
                    }else {
                        lvdoan.removeFooterView(footerView);
                        CheckConnect.showToast(getApplicationContext(),"Đã hết dữ liệu");
                        isfull = true;
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    }
    public class ThreadData extends Thread{
        @Override
        public void run() {
            mHander.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message;

            if (!isfull){
                 message = mHander.obtainMessage(1);

            }else {
                message = mHander.obtainMessage(0);
                isfull = true;
            }
            mHander.sendMessage(message);
            super.run();
        }
    }
}
