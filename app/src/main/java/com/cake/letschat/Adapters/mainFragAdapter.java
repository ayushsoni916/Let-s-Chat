package com.cake.letschat.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cake.letschat.R;

public class mainFragAdapter extends RecyclerView.Adapter<mainFragAdapter.myViewHolder> {
    Context context;
    String[] name;
    int[] img;

    public mainFragAdapter(Context context, String[] name, int[] img) {
        this.context = context;
        this.name = name;
        this.img = img;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(context).inflate(R.layout.main_frag_layout,parent,false);
        return new myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
    holder.mainText.setText(name[position]);
    holder.mainImg.setImageResource(img[position]);
    }

    @Override
    public int getItemCount() {
        return img.length;
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        ImageView mainImg;
        TextView mainText;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            mainImg=itemView.findViewById(R.id.imageView24);
            mainText=itemView.findViewById(R.id.textView7);
        }
    }
}
