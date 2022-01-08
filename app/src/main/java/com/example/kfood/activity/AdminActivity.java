package com.example.kfood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.kfood.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    public BottomNavigationView mBottomNavigationView;
    Toolbar toolbar;
    private MyViewPagerAdapter myViewPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home_admin);

        mViewPager = findViewById(R.id.view_pager);
        mBottomNavigationView = findViewById(R.id.bottom_navigation);
        toolbar = findViewById(R.id.toolbar);
        setUpViewPager();
        ActionBar();
    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_admin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case R.id.menulogout:
                intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpViewPager() {

        myViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(myViewPagerAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                switch (i) {
                    case 0:
                        mBottomNavigationView.getMenu().findItem(R.id.tab_add).setChecked(true);
                        break;

                    case 1:
                        mBottomNavigationView.getMenu().findItem(R.id.tab_delete).setChecked(true);
                        break;
                    case 2:
                        mBottomNavigationView.getMenu().findItem(R.id.tab_basket).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.tab_add:
                        mViewPager.setCurrentItem(0);
                        break;

                    case R.id.tab_delete:
                        mViewPager.setCurrentItem(1);
                        break;
                    case R.id.tab_basket:
                        mViewPager.setCurrentItem(2);
                        break;
                }
                return true;
            }
        });
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