package com.cake.letschat.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cake.letschat.Adapters.mainFragAdapter;
import com.cake.letschat.Adapters.storyFragAdapter;
import com.cake.letschat.R;


public class mainFrag extends Fragment {



    public mainFrag() {
        // Required empty public constructor
    }
    RecyclerView rcv , storyRcv;
    LinearLayoutManager manager,storyManager;
    mainFragAdapter adapter;
    storyFragAdapter StoryAdapter;
    String[] name={"Ayush Soni","Keshav Soni","Shiddarth Soni","Shubham Soni","Kushal Soni","Shivang Soni","Akshat Soni",
            "Shivam Soni","Krishna Soni", "Parv Soni","Asus","Lenovo","Raj","Aman aman aman"};


    int [] img={
            R.drawable.a,R.drawable.b,R.drawable.c,R.drawable.d,R.drawable.e,R.drawable.f,R.drawable.g,R.drawable.h,
            R.drawable.i,R.drawable.j,R.drawable.k,R.drawable.l,R.drawable.n,R.drawable.m
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view= inflater.inflate(R.layout.fragment_main, container, false);
        rcv=(RecyclerView) view.findViewById(R.id.mainRcv);
        manager=new LinearLayoutManager(getContext());
        adapter =new mainFragAdapter(getContext(),name,img);
        rcv.setLayoutManager(manager);
        rcv.setAdapter(adapter);
        storyRcv =(RecyclerView) view.findViewById(R.id.rcv);
        storyManager=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,true);
//        manager.setReverseLayout(true);
        storyManager.setStackFromEnd(true);
//        manager.setReverseLayout(true);
        storyRcv.setLayoutManager(storyManager);
        StoryAdapter =new storyFragAdapter(getContext(),name,img);
        storyRcv.setNestedScrollingEnabled(true);
        rcv.setNestedScrollingEnabled(true);
        storyRcv.setAdapter(StoryAdapter);
        return view;
    }
}