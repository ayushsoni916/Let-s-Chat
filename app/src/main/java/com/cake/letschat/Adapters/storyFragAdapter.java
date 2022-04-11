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

public class storyFragAdapter extends RecyclerView.Adapter<storyFragAdapter.myViewHolder> {
    Context context;
    String[] name;
    int[] img;

    public storyFragAdapter(Context context, String[] name, int[] img) {
        this.context = context;
        this.name = name;
        this.img = img;
    }

    @NonNull
    @Override
    public storyFragAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=    LayoutInflater.from(context).inflate(R.layout.stroies,parent,false);
        return new myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull storyFragAdapter.myViewHolder holder, int position) {
    String n=name[position];
    if (n.length()<=10){
        holder.stText.setText(name[position]);
    }
    else
        holder.stText.setText(n.substring(0,9)+"...");

    holder.stImg.setImageResource(img[position]);
    }

    @Override
    public int getItemCount() {
        return img.length;
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        ImageView stImg;
        TextView stText;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            stImg=itemView.findViewById(R.id.imageView17);
            stText=itemView.findViewById(R.id.textView5);
        }
    }
}
