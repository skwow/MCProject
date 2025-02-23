package com.example.skwow.mcproject;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter{

    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;

    private Context mContext;
    private List<Message> messagesList = new ArrayList<>();

    public ChatAdapter(Context context) {
        mContext = context;
    }

    public void setMessages(List<Message> messages) {
        this.messagesList = messages;
    }

    @Override
    public int getItemCount() {
        return messagesList.size();
    }

    // Determines the appropriate ViewType according to the sender of the message.
    @Override
    public int getItemViewType(int position) {

        Message message = messagesList.get(position);

        if (message.getUserId().equals(User.currentUser.getUID())) {
            // If the current user is the sender of the message
            Log.d("Checking", "getItemViewType: " + "selfMessage");
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            // If some other user sent the message
            Log.d("Checking", "getItemViewType: " + "notSelfMessage");
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    // Inflates the appropriate layout according to the ViewType.
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            Log.d("Checking", "onCreateViewHolder: "  + viewType);
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_sent, parent, false);
            return new SentMessageHolder(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            Log.d("Checking", "onCreateViewHolder: " +viewType);
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_received, parent, false);
            return new ReceivedMessageHolder(view);
        }

        return null;
    }

    // Passes the message object to a ViewHolder so that the contents can be bound to UI.
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Message message = messagesList.get(position);
        Log.d("checking", "onBindViewHolder: " + "entered");
        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                Log.d("Checking", "onBindViewHolder: " + "messageSent");
                ((SentMessageHolder) holder).bind(message);

                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                Log.d("Checking", "onBindViewHolder: " + "messageReceived");
                ((ReceivedMessageHolder) holder).bind(message);
        }
    }

    private class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText;

        SentMessageHolder(View itemView) {
            super(itemView);
            Log.d("Checking", "SentMessageHolder: ");

            messageText = itemView.findViewById(R.id.tv_message_body_sent);
        }

        void bind(Message message) {
            Log.d("Checking", "bindSent: ");
            messageText.setText(message.getMessage());
        }
    }

    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        TextView nameText;

        ReceivedMessageHolder(View itemView) {
            super(itemView);

            Log.d("Checking", "ReceivedMessageHolder: ");
            messageText = itemView.findViewById(R.id.tv_message_body_received);
            nameText = itemView.findViewById(R.id.tv_message_name);
        }

        void bind(Message message) {
            Log.d("Checking", "bindReceive: ");
            messageText.setText(message.getMessage());

            nameText.setText(message.getUserName());
        }
    }
}
