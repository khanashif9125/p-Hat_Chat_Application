package com.workforashif.basicchatapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.workforashif.basicchatapp.Model.Messages;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<Messages> messagesList;

    public MessageAdapter(List<Messages> messagesList) {
        this.messagesList = messagesList;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.message_single_layout,parent,false);
        return new MessageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int i) {
        Messages c=messagesList.get(i);
        holder.messageText.setText(c.getMessage());

    }

    @Override
    public int getItemCount() {
        return messagesList.size();
    }

    class MessageViewHolder extends RecyclerView.ViewHolder{
        TextView messageText;
        ImageView imageView;
        public MessageViewHolder(View view){
            super(view);
            messageText=view.findViewById(R.id.messageText);
            imageView=view.findViewById(R.id.chat_image);
        }

    }
}
