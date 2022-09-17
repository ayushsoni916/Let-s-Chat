package com.cake.letschat.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.cake.letschat.R;
import com.cake.letschat.models.songSheetModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class songSheetAdapter extends FirebaseRecyclerAdapter<songSheetModel , songSheetAdapter.myViewHolde> {
    Context context;
    private OnSongClickListener onSongClickListener;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public songSheetAdapter(@NonNull FirebaseRecyclerOptions<songSheetModel> options , Context context,OnSongClickListener onSongClickListener) {
        super(options);
        this.context = context;
        this.onSongClickListener = onSongClickListener;
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolde holder, int position, @NonNull songSheetModel model) {
        holder.name.setText(model.getName());
        Glide.with(holder.pic.getContext()).load(model.getPic()).thumbnail(0.01f)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.pic);

        holder.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] data = new String[3];
                data[0] = model.getPic();
                data[1] = model.getName();
                data[2] = model.getAudio();

                onSongClickListener.onSongClick(data);
            }
        });


    }

    @NonNull
    @Override
    public myViewHolde onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.songs_rcv_layout,parent,false);
        return new songSheetAdapter.myViewHolde(v);
    }

    public class myViewHolde extends RecyclerView.ViewHolder {
        ImageView pic,play;
        TextView name;
        public myViewHolde(@NonNull View itemView) {
            super(itemView);
            pic = itemView.findViewById(R.id.imageView30);
            name = itemView.findViewById(R.id.textView9);
            play = itemView.findViewById(R.id.play);
        }
    }
    public interface OnSongClickListener {
        void onSongClick(String[] data);
    }
}

