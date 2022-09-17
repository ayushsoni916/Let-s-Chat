package com.cake.letschat.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cake.letschat.Adapters.chatAdapter;
import com.cake.letschat.Adapters.mainFragAdapter;
import com.cake.letschat.Adapters.storyFragAdapter;
import com.cake.letschat.R;
import com.cake.letschat.models.chatModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class chatFrag extends Fragment {

    public chatFrag() {
        // Required empty public constructor
    }

    RecyclerView rcv ;
    LinearLayoutManager manager;
    chatAdapter adapter;
    FirebaseDatabase mDatabase;
    DatabaseReference mref;
    FirebaseDatabase mStorage;

    String[] name={"Ayush Soni","Keshav Soni","Shiddarth Soni","Shubham Soni","Kushal Soni","Shivang Soni","Akshat Soni",
            "Shivam Soni","Krishna Soni", "Parv Soni","Asus","Lenovo","Raj","Aman aman aman"};

    int [] img={
            R.drawable.a,R.drawable.b,R.drawable.c,R.drawable.d,R.drawable.e,R.drawable.f,R.drawable.g,R.drawable.h,
            R.drawable.i,R.drawable.j,R.drawable.k,R.drawable.l,R.drawable.n,R.drawable.m
    };

    @Override
    public void onStart() {
        adapter.startListening();
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mDatabase =FirebaseDatabase.getInstance();
        View view= inflater.inflate(R.layout.fragment_chat, container, false);
        rcv = (RecyclerView) view.findViewById(R.id.chatRcv);
        manager =new LinearLayoutManager(getContext());

        rcv.setLayoutManager(manager);
        FirebaseRecyclerOptions<chatModel> options =
                new FirebaseRecyclerOptions.Builder<chatModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("users"), chatModel.class)
                        .build();
        adapter = new chatAdapter(options , getContext());
        rcv.setAdapter(adapter);
        return view;
    }
}