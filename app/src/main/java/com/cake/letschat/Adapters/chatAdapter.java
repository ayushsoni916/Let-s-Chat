package com.cake.letschat.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.cake.letschat.R;
import com.cake.letschat.message;
import com.cake.letschat.models.chatModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class chatAdapter extends FirebaseRecyclerAdapter<chatModel, chatAdapter.myViewHolder> {
    Context context;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public chatAdapter(@NonNull FirebaseRecyclerOptions<chatModel> options,Context context) {
        super(options);
        this.context =context;
    }


    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.chat_view,parent,false);
        return new chatAdapter.myViewHolder(v);
    }


    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull chatModel model) {
        holder.mainText.setText(model.getFirstName());
        Glide.with(holder.mainImg.getContext()).load(model.getProfilePic()).thumbnail(0.01f)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.mainImg);
        holder.cns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("name",model.getFirstName());
                bundle.putString("img",model.getProfilePic());
                bundle.putString("phoneNumber",model.getPhoneNumber());
                Intent i =new Intent(context, message.class);
//                i.putExtra("name",model.getFirstName());
//                i.putExtra("img",model.getProfilePic());
                if(bundle !=  null)
                {
                    i.putExtras(bundle);
                }
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(i);

            }
        });

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
