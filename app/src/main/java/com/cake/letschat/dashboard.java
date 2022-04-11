package com.cake.letschat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.cake.letschat.Adapters.storyFragAdapter;
import com.cake.letschat.Adapters.viewPagerAdapter;
import com.cake.letschat.Fragment.mainFrag;

public class dashboard extends AppCompatActivity {
    ViewPager viewPager;
    viewPagerAdapter pagerAdapter;
    mainFrag mainFrag;
//    RecyclerView storyRcv;
//    LinearLayoutManager manager;
//    storyFragAdapter adapter;
//    String[] name={"Ayush Soni","Keshav Soni","Shiddarth Soni","Shubham Soni","Kushal Soni","Shivang Soni","Akshat Soni",
//            "Shivam Soni","Krishna Soni", "Parv Soni","Asus","Lenovo","Raj","Aman aman aman"};
//
//
//    int [] img={
//            R.drawable.a,R.drawable.b,R.drawable.c,R.drawable.d,R.drawable.e,R.drawable.f,R.drawable.g,R.drawable.h,
//            R.drawable.i,R.drawable.j,R.drawable.k,R.drawable.l,R.drawable.n,R.drawable.m
//    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.statusBlue));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        viewPager =findViewById(R.id.viewPager);
        pagerAdapter =new viewPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(1);
//        storyRcv=(RecyclerView) findViewById(R.id.rcv);
//        manager=new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,true);
////        manager.getStackFromEnd();
//        storyRcv.setLayoutManager(manager);
//        adapter=new storyFragAdapter(getApplicationContext(),name,img);
//        storyRcv.setAdapter(adapter);
    }
}