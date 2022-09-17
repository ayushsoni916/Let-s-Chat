package com.cake.letschat.Adapters;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cake.letschat.R;
import com.cake.letschat.models.chatModel;
import com.cake.letschat.models.messageModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.annotations.NotNull;

public class messageAdapter extends FirebaseRecyclerAdapter<messageModel, messageAdapter.myViewHolder> {
    Context context;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public messageAdapter(@NonNull FirebaseRecyclerOptions<messageModel> options , Context context) {
        super(options);
        this.context =context;
    }



    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull messageModel model) {
        String status = model.getYourStatus();
        if (status.equals("sender")){
            holder.lay.setGravity(Gravity.END);
        }
        else {
            holder.lay.setGravity(Gravity.START);

        }
        holder.txt.setText(model.getMessage());

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.chat_rcv_design,parent,false);
        return new messageAdapter.myViewHolder(v);
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        LinearLayout lay;
        TextView txt;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            txt = itemView.findViewById(R.id.textView10);
            lay = itemView.findViewById(R.id.lay);
        }
    }
}
