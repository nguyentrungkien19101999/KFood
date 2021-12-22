package com.example.kfood.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.kfood.R;
import com.example.kfood.Service.APIService;
import com.example.kfood.Service.DataService;
import com.example.kfood.model.RealPathUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThemMonAnActivity extends AppCompatActivity {

    public static final String TAG = ThemMonAnActivity.class.getName();

    private static final int MY_REQUEST_CODE = 10;
    EditText edten, edgia, edmota;
    RadioButton rddoan, rddouong;
    ImageView imgFromGallery;
    Toolbar toolbar;
    Button btnadd;
    String realpath = "";
    String tenmon;
    String gia;
    String mota;
    String idmasp;

    Uri mUri;

    ProgressDialog mProgressDialog;

    private ActivityResultLauncher<Intent> mActivityResultLauncher =registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Log.e(TAG, "onActivityResult");
                    if (result.getResultCode() == Activity.RESULT_OK){
                        //There are no request codes
                        Intent data = result.getData();
                        if (data ==null){
                            return;
                        }

                        Uri uri = data.getData();

                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            imgFromGallery.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_mon_an);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Please wait . . .");

        initUi();
        onClick();
    }

    private void initUi() {
        edten = findViewById(R.id.edtenmon);
        edgia = findViewById(R.id.edgiamonan);
        edmota = findViewById(R.id.edmota);
        rddoan = findViewById(R.id.rddoan);
        rddouong = findViewById(R.id.rddouong);
        imgFromGallery = findViewById(R.id.imganh);
        btnadd = findViewById(R.id.btnthemmonan);
        toolbar = findViewById(R.id.toolbarthemdoan);
    }

    private void onClickRequestPermission(){
        //check permission
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            openGallery();
            return;
        }

        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            openGallery();
        } else{
            String [] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permission, MY_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_REQUEST_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                openGallery();
            }
        }
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent, "Select Picture"));
    }

    private void onClick() {

        imgFromGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRequestPermission();
            }
        });

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tenmon = edten.getText().toString();
                gia = edgia.getText().toString();
                mota = edmota.getText().toString();
                if (rddoan.isChecked()){
                    idmasp = "1";
                }else {
                    idmasp = "2";
                }
                
                File file = new File(realpath);
                String file_path = file.getAbsolutePath();

                String[] mangtenfile = file_path.split("\\.");

                file_path = mangtenfile[0] + System.currentTimeMillis() + "." + mangtenfile[1];
                Log.e("FILE",file_path);
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);

                MultipartBody.Part body = MultipartBody.Part.createFormData("uploaded_file",file_path,requestBody);

                final DataService dataService = APIService.getService();
                final Call<String> callback = dataService.ThemAnhMonAn(body);
                callback.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.body() != null){
                            String mes = response.body().toString();//bien luu ten file
                            Log.e("UPLOAD",mes+"");
                            if (mes.length() > 0){
                                if (!tenmon.isEmpty() && !gia.isEmpty() && !mota.isEmpty() && !idmasp.isEmpty()){
                                    DataService dataService1 = APIService.getService();
                                    Call<String> callback1 = dataService1.InsertData(tenmon,gia,APIService.baseURL + "image/" + mes,mota,idmasp);
                                    callback1.enqueue(new Callback<String>() {
                                        @Override
                                        public void onResponse(Call<String> call, Response<String> response) {
                                            if (response.body().equals("success")){
                                                Toast.makeText(ThemMonAnActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                                                finish();
                                            }else {
                                                Toast.makeText(ThemMonAnActivity.this, "Không thể thêm.", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<String> call, Throwable t) {

                                        }
                                    });
                                }else {
                                    Toast.makeText(ThemMonAnActivity.this, "Hãy nhập đày đủ thông tin", Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(ThemMonAnActivity.this, "Hãy xem lại hình ảnh", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.e("ERR", t.getMessage());
                    }
                });
            }
        });
    }

    private void callApiAddFood(){
        mProgressDialog.show();

        String strNameFood = edten.getText().toString().trim();
        String strPriceFood = edgia.getText().toString().trim();
        String strDescriptionFood = edmota.getText().toString().trim();

        RequestBody requestBodyName = RequestBody.create(MediaType.parse("multipart/form-data"), strNameFood);
        RequestBody requestBodyPrice = RequestBody.create(MediaType.parse("multipart/form-data"), strPriceFood);
        RequestBody requestBodyDescription = RequestBody.create(MediaType.parse("multipart/form-data"), strDescriptionFood);

        String strRealPath = RealPathUtil.getRealPath(this, mUri);
        Log.e("File image: ", strRealPath);

        File file = new File(strRealPath);
        RequestBody requestBodyAvatar = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        MultipartBody.Part multiPathBodyAvatar = MultipartBody.Part.createFormData("uploaded_file",file.getName(),requestBodyAvatar);



    }
}
