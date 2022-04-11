package com.cake.letschat.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cake.letschat.Adapters.storyFragAdapter;
import com.cake.letschat.R;


public class storyFrag extends Fragment {



    public storyFrag() {
        // Required empty public constructor
    }



    RecyclerView storyRcv;
    LinearLayoutManager manager;
    storyFragAdapter adapter;


    String[] name={"Ayush Soni","Keshav Soni","Shiddarth Soni","Shubham Soni","Kushal Soni","Shivang Soni","Akshat Soni",
            "Shivam Soni","Krishna Soni", "Parv Soni","Asus","Lenovo","Raj","Aman aman aman"};


    int [] img={
            R.drawable.a,R.drawable.b,R.drawable.c,R.drawable.d,R.drawable.e,R.drawable.f,R.drawable.g,R.drawable.h,
            R.drawable.i,R.drawable.j,R.drawable.k,R.drawable.l,R.drawable.n,R.drawable.m
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_story, container, false);
        storyRcv =(RecyclerView) v.findViewById(R.id.rcv);

        manager=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,true);
//        manager.setReverseLayout(true);
        manager.setStackFromEnd(true);
//        manager.setReverseLayout(true);
        storyRcv.setLayoutManager(manager);
        adapter =new storyFragAdapter(getContext(),name,img);
//        storyRcv.setAdapter(adapter);
//        storyRcv.smoothScrollToPosition(13);


        return v;
    }
}