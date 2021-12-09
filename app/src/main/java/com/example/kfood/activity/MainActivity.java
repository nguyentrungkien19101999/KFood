package com.example.kfood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.kfood.R;
import com.example.kfood.adapter.LoaispAdapter;
import com.example.kfood.adapter.SanPhamAdapter;
import com.example.kfood.model.Giohang;
import com.example.kfood.model.Loaisp;
import com.example.kfood.model.Sanpham;
import com.example.kfood.until.CheckConnect;
import com.example.kfood.until.Sever;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerView_trangchu;
    NavigationView navigationView;
    ListView listView_trangchu;
    DrawerLayout drawerLayout;


    ArrayList<Loaisp> mangloaisp;
    LoaispAdapter loaispAdapter;
    int id=0;
    String tenloaisp="";
    String hinhaanhloaisp="";

    ArrayList<Sanpham> mangsanpham;
    SanPhamAdapter sanPhamAdapter;

   /* ArrayList<Sanpham> mangsanpham;
    SanPhamAdapter sanPhamAdapter;
    */
   public static ArrayList<Giohang> manggiohang;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();
        if (CheckConnect.haveNetworkConnection(getApplicationContext())){
            ActionBar();
            ActionViewflipper();
            LayDLloaisp();
           // GetDLSPmoinhat();
            getDLSPmoinhat();
            CatchOnItemListView();
            

        }else {
            CheckConnect.showToast(getApplicationContext(),"error");
            finish();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case R.id.mnuGioHang:
                intent = new Intent(getApplicationContext(),GioHangActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void CatchOnItemListView() {
        listView_trangchu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch(i){
                    case 0:
                        if(CheckConnect.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            CheckConnect.showToast(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        if(CheckConnect.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, DoAnActivity.class);
                            intent.putExtra("idloaisanpham",mangloaisp.get(i).getId());
                            //putExtra la pt de truyen du lieu sang man hinh khac
                            Log.e("IDSP",mangloaisp.get(i).getId()+"");
                            startActivity(intent);
                        }else{
                            CheckConnect.showToast(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        if(CheckConnect.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, DoUongActivity.class);
                            intent.putExtra("idloaisanpham",mangloaisp.get(i).getId());
                            Log.e("IDSP",mangloaisp.get(i).getId()+"");
                            startActivity(intent);
                        }else{
                            CheckConnect.showToast(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        if(CheckConnect.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, LienHeActivity.class);
                            startActivity(intent);
                        }else{
                            CheckConnect.showToast(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 4:
                        if(CheckConnect.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, ThongTinActivity.class);
                            startActivity(intent);
                        }else{
                            CheckConnect.showToast(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                }
            }
        });
    }

    private void getDLSPmoinhat() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Sever.Duongdanspmoinhat, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response !=null){
                    int ID = 0;
                    String Tensanpham="";
                    Integer Giasanpham=0;
                    String Hinhanhsanpham ="";
                    String Motasanpham = "";
                    int IDsanpham=0;
                    for (int i= 0;i<response.length();i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            ID=jsonObject.getInt("id");
                            Tensanpham = jsonObject.getString("tensp");
                            Giasanpham = jsonObject.getInt("giasp");
                            Hinhanhsanpham = jsonObject.getString("hinhanhsp");
                            Motasanpham = jsonObject.getString("motasp");
                            IDsanpham = jsonObject.getInt("idsanpham");
                            mangsanpham.add(new Sanpham(ID,Tensanpham,Giasanpham,Hinhanhsanpham,Motasanpham,IDsanpham));
                            sanPhamAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            System.out.print("--->" +e);
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }



    private void LayDLloaisp() {
         RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
         JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Sever.duongdanloaisp, new Response.Listener<JSONArray>() {
             @Override
             public void onResponse(JSONArray response) {
                if (response !=null){
                    for (int i=0;i<response.length();i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            tenloaisp = jsonObject.getString("tenloaisp");
                            hinhaanhloaisp = jsonObject.getString("hinhanhloaisp");
                            mangloaisp.add(new Loaisp(id,tenloaisp,hinhaanhloaisp));
                            loaispAdapter.notifyDataSetChanged();//update ban ve
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    mangloaisp.add(3,new Loaisp(0,"Liên Hệ","http://192.168.0.104/server/image/lienhe.png"));
                    mangloaisp.add(4,new Loaisp(0,"Thông Tin","http://192.168.0.104/server/image/thongtin.png"));
                }
             }
         }, new Response.ErrorListener() {
             @Override
             public void onErrorResponse(VolleyError error) {
                 CheckConnect.showToast(getApplicationContext(), error.toString());
                 Log.e("AAB",error.toString());
             }
         });
         requestQueue.add(jsonArrayRequest); //jsonArrayRequest du lieu muon gui len server
    }


    private void ActionViewflipper() {
        int[] quangcao = {R.drawable.com_ga, R.drawable.banh_chuoi,
                R.drawable.matcha , R.drawable.pizza, R.drawable.tra_sua};//mang chua hinh anh
        //view fliper chi nhan Imageview
        for(int i = 0 ; i<quangcao.length ; i++){
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(quangcao[i]);//lay hinh anh trg drawable va hien thi
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);//them view de chay trong ViewFlipper
        }
        viewFlipper.setFlipInterval(5000);//khoang tg cho de chuyen sang view tiep theo (minis)
        viewFlipper.setAutoStart(true);//su dung de bat dau lat cac view
        //view dong
        Animation animation_in = AnimationUtils.loadAnimation(this, R.anim.silde_in);
        Animation animation_out = AnimationUtils.loadAnimation(this, R.anim.slide_out);
        viewFlipper.setInAnimation(animation_in);
        viewFlipper.setOutAnimation(animation_out);
    }

    private void ActionBar() {//xu ly menu
        setSupportActionBar(toolbar);//ho tro toolbar as Actionbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//Thiet lap nut home
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);// icon
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {//mo ra thanh menu
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void Anhxa() {

        toolbar = findViewById(R.id.toolbartrangchinh);
        viewFlipper = findViewById(R.id.viewfipper);
        recyclerView_trangchu = findViewById(R.id.recyclerView);
        navigationView = findViewById(R.id.navigation);
        listView_trangchu = findViewById(R.id.listview_trangchinh);
        drawerLayout = findViewById(R.id.drawerlayout);
        mangloaisp = new ArrayList<>();
        mangloaisp.add(0,new Loaisp(0,"Trang Chủ", "http://www.residenceplus.fr/wp-content/uploads/2014/03/bouton-maison-300x297.png"));
        loaispAdapter = new LoaispAdapter(mangloaisp,getApplicationContext());
        listView_trangchu.setAdapter(loaispAdapter);

        mangsanpham = new ArrayList<>();
        sanPhamAdapter = new SanPhamAdapter(getApplicationContext(),mangsanpham);
        recyclerView_trangchu.setHasFixedSize(true);
        recyclerView_trangchu.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerView_trangchu.setAdapter(sanPhamAdapter);


        if (manggiohang !=null){

        }else {
            manggiohang = new ArrayList<>();
        }

    }

    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Ấn QUAY LẠI một lần nữa để thoát", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
