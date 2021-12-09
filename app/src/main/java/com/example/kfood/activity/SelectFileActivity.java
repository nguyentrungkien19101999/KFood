package com.example.kfood.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kfood.R;
import com.example.kfood.Service.OnClickedListener;
import com.example.kfood.adapter.CustomAdapterSelectFile;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SelectFileActivity extends AppCompatActivity implements OnClickedListener {

    RecyclerView rcSelectFile;
    ImageView imgBackDirectory;

    private final String TAG = SelectFileActivity.class.getSimpleName();
    public static final String NAME = "name";
    private final String root = "/storage/";
    private String path = root;
    private List<File> listFile = new ArrayList<>();
    private CustomAdapterSelectFile customAdapterSelectFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_file);

        rcSelectFile = findViewById(R.id.rc_select_file);
        imgBackDirectory = findViewById(R.id.img_back_directory);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rcSelectFile.setLayoutManager(layoutManager);

        initView();

    }

    private void initView() {
        // di chuyển đến thư mục gốc chứa emulated(bộ nhớ máy) và sdcard0(thẻ nhớ)
        File file = new File(root);
        // addAll(): add 1 ArrayList vào 1 ArrayList
        // asList(): convert Array to ArrayList
        listFile.addAll(Arrays.asList(file.listFiles()));

        setupLoadRoot();

        customAdapterSelectFile = new CustomAdapterSelectFile(this, this, listFile);
        rcSelectFile.setAdapter(customAdapterSelectFile);

        imgBackDirectory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Nếu phần tử cuối là dấu '/' thì cắt đi
                để đoạn code lấy link back về thư mục trước phía dưới
                chỉ cần dùng phương thức substring lấy từ đầu đến phần tử '/' cuối cùng
                VD: /storage/emulated/0/Download/ --> /storage/emulated/0/ */
                if (path.charAt(path.length() - 1) == '/') {
                    path = path.substring(0, path.length() - 1);
                }

                if (path.length() > root.length()) {
                    path = path.substring(0, path.lastIndexOf("/"));
                }

                /* Nếu đường dẫn là /storage/emulated thì sẽ bị crash app nên phải để là /storage/
                   chắc tại /storage/ là gốc
                   nên chỉ có /storage/emulated/0 là bộ nhớ máy và /storage/sdcard0 là thẻ nhớ */
                if (path.equalsIgnoreCase("/storage/emulated")) {
                    path = "/storage/";
                }

                 /* Kiểm tra nếu đường dẫn rỗng hoặc không có '/' ở cuối thì thêm '/' vào cuối
                  để thuật tiện cho việc nối chuỗi khi click vào một thư mục bất kỳ */
                if (path.equalsIgnoreCase("/storage") || path.isEmpty()) {
                    path = "/storage/";
                }

                File file = new File(path);
                listFile.clear();
                listFile.addAll(Arrays.asList(file.listFiles()));

                // Nếu back ra ngoài là đường dẫn gốc thì ta sẽ load thư mục gốc
                if (file.getPath().equalsIgnoreCase("/storage")) {
                    setupLoadRoot();
                }

                customAdapterSelectFile.notifyDataSetChanged();
            }
        });
    }

    private void setupLoadRoot() {
        /* Ở trong thư mục gốc /storage (máy thật) có tới 4 thư mục
          1 là của bộ nhớ trong (emulated), 2 là của thẻ nhớ, 3 là enc_emulated và 4 là self
          cái 3 và 4 là thư mục rỗng nên xóa nó đi */
        for (int i = 0; i < listFile.size(); i++) {
            if (listFile.get(i).getName().equalsIgnoreCase("enc_emulated")
                    || listFile.get(i).getName().equalsIgnoreCase("self")) {
                listFile.remove(i);
                i--;
            }
        }

        // Kiểm tra nếu đang ở thư mục root thì ta sửa lại link "emulated" thành "emulated/0"
        if (listFile.size() <= 2) {
            for (int i = 0; i < listFile.size(); i++) {
                if (listFile.get(i).getName().equalsIgnoreCase("emulated")) {
                    File file2 = new File(listFile.get(i).getPath() + "/0");
                    listFile.remove(i);
                    listFile.add(file2);
                }
            }
        }

        // Xóa file không phải là file Excel
        /*for (int i = 0; i < listFile.size(); i++) {
            if (listFile.get(i).isFile() && !listFile.get(i).getPath().endsWith(".xls")) {
                listFile.remove(i);
                i--;
            }
        }*/
    }

    @Override
    public void onFolderClicked(String folderName) {
        // Kiểm tra nếu cuối đường không có dấu '/' thì thêm
        if (path.charAt(path.length() - 1) != '/') {
            path += '/';
        }

        path += folderName + "/";

        customAdapterSelectFile.notifyDataSetChanged();
    }

    @Override
    public void onImageFileClicked(String pathimgfile) {
        Intent intent = new Intent();
        intent.putExtra(NAME, pathimgfile);
        setResult(Activity.RESULT_OK, intent);

        finish();
    }
}
