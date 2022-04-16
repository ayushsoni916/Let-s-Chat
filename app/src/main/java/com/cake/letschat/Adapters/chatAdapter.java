package com.cake.letschat.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.cake.letschat.R;
import com.cake.letschat.message;

public class chatAdapter extends RecyclerView.Adapter<chatAdapter.myViewHolder> {
    Context context;
    String[] name;
    int[] img;

    public chatAdapter(Context context, String[] name, int[] img) {
        this.context = context;
        this.name = name;
        this.img = img;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.chat_view,parent,false);
        return new chatAdapter.myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
    holder.mainImg.setImageResource(img[position]);
    holder.mainText.setText(name[position]);
    holder.cns.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i =new Intent(context, message.class);
            i.putExtra("name",name[holder.getAdapterPosition()]);
            i.putExtra("img",img[holder.getAdapterPosition()]);
            context.startActivity(i);

        }
    });
    }

    @Override
    public int getItemCount() {
        return img.length;
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        ImageView mainImg;
        TextView mainText;
        ConstraintLayout cns;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            mainImg=itemView.findViewById(R.id.pic);
            mainText=itemView.findViewById(R.id.name);
            cns=itemView.findViewById(R.id.cns);
        }
    }
}
