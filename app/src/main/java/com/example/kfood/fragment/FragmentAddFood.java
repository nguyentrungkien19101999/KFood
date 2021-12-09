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
import com.example.kfood.activity.XemDonHangActivivty;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentAddFood extends Fragment {
    EditText edten, edgia, edmota;
    RadioButton rddoan, rddouong;
    ImageView imganh;
    Toolbar toolbar;
    Button btnadd;
    public static final int REQUEST_CODE = 123;
    String realpath = "";
    String tenmon;
    String gia;
    String mota;
    String idmasp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_them_mon_an, container, false);

        edten = view.findViewById(R.id.edtenmon);
        edgia = view.findViewById(R.id.edgiamonan);
        edmota = view.findViewById(R.id.edmota);
        rddoan = view.findViewById(R.id.rddoan);
        rddouong = view.findViewById(R.id.rddouong);
        imganh = view.findViewById(R.id.imganh);
        btnadd = view.findViewById(R.id.btnthemmonan);
        toolbar = view.findViewById(R.id.toolbarthemdoan);

        onClick();

        return view;

    }

//    private void ActionBar() {
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_admin, menu);
//
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case R.id.menuadmincart:
                intent = new Intent(getActivity(), XemDonHangActivivty.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void onClick() {
        imganh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,REQUEST_CODE);

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
                                                Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                                            }else {
                                                Toast.makeText(getActivity(), "Không thể thêm.", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<String> call, Throwable t) {

                                        }
                                    });
                                }else {
                                    Toast.makeText(getActivity(), "Hãy nhập đày đủ thông tin", Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(getActivity(), "Hãy xem lại hình ảnh", Toast.LENGTH_SHORT).show();
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

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//
//        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null){
//            Uri uri = data.getData();
//            realpath = getRealPathFromURI(uri);
//            try {
//                InputStream inputStream = getContentResolver().openInputStream(uri);
//                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//                imganh.setImageBitmap(bitmap);
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }
//    //phiên bản android dưới SDK 19 thì dùng hàm này để lấy đường dẫn ảnh.
//
//    public String getRealPathFromURI (Uri contentUri) {
//        String path = null;
//        String[] proj = { MediaStore.MediaColumns.DATA };
//        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
//        if (cursor.moveToFirst()) {
//            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
//            path = cursor.getString(column_index);
//        }
//        cursor.close();
//        return path;
//    }
}
