package com.example.kfood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.example.kfood.R;
import com.example.kfood.adapter.DoUongAdapter;
import com.example.kfood.model.Sanpham;
import com.example.kfood.until.CheckConnect;
import com.example.kfood.until.Sever;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DoUongActivity extends AppCompatActivity {
    ListView lvDouong;
    ArrayList<Sanpham> mangDouong,results;
    DoUongAdapter doUongAdapter;
    Toolbar toolbarDouong;

    int page=1;
    int idsanpham=0;

    View footerView;
    MyHandler myHandler;
    boolean isLoading =false;
    boolean limitData = false; //xac nhan da het du lieu

    boolean isfull = false;
    int size = 0,sizeafter =0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_uong);

        //Toast.makeText(this, "hâm....@@@@@@@", Toast.LENGTH_SHORT).show();

        lvDouong = findViewById(R.id.listviewdouong);
        toolbarDouong = findViewById(R.id.toolbardouong);
        addActionBar();

        //add footerView
        footerView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.progressbar,null);
        myHandler = new MyHandler();



        //lay id san pham
        Intent intent = getIntent();
        idsanpham = intent.getIntExtra("idloaisanpham",-1);

        //lam sao lay duoc gia tri dua vao mang
        mangDouong = new ArrayList<>();
        results = new ArrayList<>();
        doUongAdapter = new DoUongAdapter(DoUongActivity.this,mangDouong);
        lvDouong.setAdapter(doUongAdapter);

        GetDuLieuSanPham(page);
        LoadMoreData();



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


    public void addActionBar(){
        setSupportActionBar(toolbarDouong);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarDouong.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    public class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    lvDouong.addFooterView(footerView);
                    break;
                case 1:
                    if (mangDouong.size()>size){
                        GetDuLieuSanPham(++page);
                        isLoading = false;
                        size = size+5;
                        isfull = false;
                    }else{
                        lvDouong.removeFooterView(footerView);
                        CheckConnect.showToast(getApplicationContext(),"Đã hết dữ liệu");
                        isfull = true;
                    }
                    /*GetDuLieuSanPham(++page); //tang bien page len 1 roi moi thuc hien function
                    isLoading = false;*/
                    break;
            }
            super.handleMessage(msg);
        }
    }

    public class ThreadData extends Thread{
        @Override
        public void run() {
            myHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
           // Message message = myHandler.obtainMessage(1);
            Message message;
            if (!isfull){
                message = myHandler.obtainMessage(1);
            }else{
                message = myHandler.obtainMessage(0);
                isfull = true;
            }
            myHandler.sendMessage(message);
            super.run();
        }
    }

    private void LoadMoreData(){
        lvDouong.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(DoUongActivity.this,ChiTietSanPham.class);
                intent.putExtra("thongtinsanpham",mangDouong.get(i));
                startActivity(intent);
            }
        });
        lvDouong.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstItem, int visibleItem, int totalItem) {
                if (firstItem+visibleItem==totalItem && totalItem!=0 && isLoading==false && limitData==false){
                    isLoading=true;
                    ThreadData threadData = new ThreadData();
                    threadData.start();
                }
            }
        });

    }

    public void GetDuLieuSanPham(int Page){
        //Toast.makeText(this, ""+page,Toast.LENGTH_SHORT).show();
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongdan = Sever.Duongdandoan + String.valueOf(Page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int Id=0;
                String TenDouong = "";
                int GiaDouong = 0;
                String HinhanhDouong = "";
                String MotaDouong="";
                int Idspdouong=0;
                if (response!=null && response.length()!=2){
                    lvDouong.removeFooterView(footerView);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i =0;i<jsonArray.length();i++){
                            //JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            /*int id = jsonObject.getInt("id");
                            String tensp= jsonObject.getString("tensp");
                            Integer giasp = jsonObject.getInt("giasp");
                            String hinhanhsp = jsonObject.getString("hinhanhsp");
                            String motasp = jsonObject.getString("motasp");
                            int idsanpham = jsonObject.getInt("idsanpham");
                            mangDouong.add(new Sanpham(id,tensp,giasp,hinhanhsp,motasp,idsanpham));
                            results.add(new Sanpham(id,tensp,giasp,hinhanhsp,motasp,idsanpham));*/
                            Id = jsonObject.getInt("id");
                            TenDouong= jsonObject.getString("tensp");
                            GiaDouong = jsonObject.getInt("giasp");
                            HinhanhDouong = jsonObject.getString("hinhanhsp");
                            MotaDouong = jsonObject.getString("motasp");
                            Idspdouong = jsonObject.getInt("idsanpham");
                            mangDouong.add(new Sanpham(Id,TenDouong,GiaDouong,HinhanhDouong,MotaDouong,Idspdouong));
                        }
                        doUongAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }/*else {
                    limitData = true;
                    lvDouong.removeFooterView(footerView);
                    //  CheckConnection.ShowToast_Short(getApplicationContext(),"Đã hết dữ liệu!");

                }*/

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("idsanpham",idsanpham+"");
                //Log.e("IDSPDOUONG",idsanpham+"");
                return hashMap;
            }
        };

        requestQueue.add(stringRequest);


    }
}
