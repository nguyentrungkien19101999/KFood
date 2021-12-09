package com.example.kfood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kfood.R;
import com.example.kfood.Service.APIService;
import com.example.kfood.Service.DataService;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditActivity extends AppCompatActivity {

    ImageView imganh;
    EditText edten, edgia, edmota;
    Button btnEdit;
    String id = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent intent = getIntent();
        Bundle sp = intent.getBundleExtra("sp");

        Log.e("INTENT",intent.toString());

        String ten = sp.getString("ten");
        String gia = sp.getString("gia");
        String mota = sp.getString("mota");
        String anh = sp.getString("anh");
        id = sp.getString("id");


        anhXa();
        Picasso.with(getApplicationContext()).load(anh).into(imganh);
        edten.setText(ten);
        edgia.setText(gia);
        edmota.setText(mota);

       btnEdit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Update();
           }
       });
    }

    private void Update() {
        DataService dataService = APIService.getService();
        Call<String> callback = dataService.UpdateData(edten.getText().toString(), edgia.getText().toString(), edmota.getText().toString(), id);
        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body() != null){
                    Toast.makeText(EditActivity.this, "Sửa thành công!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditActivity.this, AdminActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void anhXa() {
        imganh = findViewById(R.id.imgedit);
        edten = findViewById(R.id.ededitten);
        edgia = findViewById(R.id.ededitgia);
        edmota = findViewById(R.id.ededitmota);
        btnEdit = findViewById(R.id.btnedit);
    }
}
