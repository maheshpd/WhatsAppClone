package com.example.maheshprasad.whatsappclone.Adapter;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.maheshprasad.whatsappclone.Model.Messages;
import com.example.maheshprasad.whatsappclone.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<Messages> userMessageList;
    private FirebaseAuth mAuth;
    private DatabaseReference usersRef;

    public MessageAdapter(List<Messages> userMessageList) {
        this.userMessageList = userMessageList;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.custom_messages_layout, viewGroup, false);
        mAuth = FirebaseAuth.getInstance();
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MessageViewHolder messageViewHolder, final int position) {
        final String messageSenderId = mAuth.getCurrentUser().getUid();
        Messages messages = userMessageList.get(position);

        String fromUserID = messages.getFrom();
        String fromMessageType = messages.getType();

        usersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(fromUserID);
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("image")) {
                    String receiverImage = dataSnapshot.child("image").getValue().toString();
                    Picasso.get().load(receiverImage).placeholder(R.drawable.profile_image).into(messageViewHolder.receiverProfileImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        messageViewHolder.receiverMessageText.setVisibility(View.GONE);
        messageViewHolder.receiverProfileImage.setVisibility(View.GONE);
        messageViewHolder.senderMessageText.setVisibility(View.GONE);
        messageViewHolder.messageSenderPicture.setVisibility(View.GONE);
        messageViewHolder.messageReceiverPicture.setVisibility(View.GONE);


        if (fromMessageType.equals("text")) {


            if (fromUserID.equals(messageSenderId)) {
                messageViewHolder.senderMessageText.setVisibility(View.VISIBLE);
                messageViewHolder.senderMessageText.setBackgroundResource(R.drawable.sender_messages_layout);
                messageViewHolder.senderMessageText.setTextColor(Color.BLACK);
                messageViewHolder.senderMessageText.setText(messages.getMessage() + "\n \n"+ messages.getTime() + " - " + messages.getDate());
            } else {

                messageViewHolder.receiverProfileImage.setVisibility(View.VISIBLE);
                messageViewHolder.receiverMessageText.setVisibility(View.VISIBLE);

                messageViewHolder.receiverMessageText.setBackgroundResource(R.drawable.receiver_messages_layout);
                messageViewHolder.receiverMessageText.setTextColor(Color.BLACK);
                messageViewHolder.receiverMessageText.setText(messages.getMessage() + "\n \n"+ messages.getTime() + " - " + messages.getDate());
            }
        } else if (fromMessageType.equals("image")){
            if (fromUserID.equals(messageSenderId))
            {
                messageViewHolder.messageSenderPicture.setVisibility(View.VISIBLE);
                Picasso.get().load(messages.getMessage()).into(messageViewHolder.messageSenderPicture);
            }else {
                messageViewHolder.receiverProfileImage.setVisibility(View.VISIBLE);
                messageViewHolder.messageReceiverPicture.setVisibility(View.VISIBLE);
                Picasso.get().load(messages.getMessage()).into(messageViewHolder.messageReceiverPicture);
            }
        } else {
            if (fromUserID.equals(messageSenderId))
            {
                messageViewHolder.messageSenderPicture.setVisibility(View.VISIBLE);
                messageViewHolder.messageSenderPicture.setBackgroundResource(R.drawable.file);

                messageViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(userMessageList.get(position).getMessage()));
//                        messageViewHolder.itemView.getContext().startActivity(intent);

                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.parse(userMessageList.get(position).getMessage()),"application/pdf");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        messageViewHolder.itemView.getContext().startActivity(intent);
                    }
                });

            }else {
                messageViewHolder.receiverProfileImage.setVisibility(View.VISIBLE);
                messageViewHolder.messageReceiverPicture.setVisibility(View.VISIBLE);
                messageViewHolder.messageReceiverPicture.setBackgroundResource(R.drawable.file);

                messageViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(userMessageList.get(position).getMessage()));
//                        messageViewHolder.itemView.getContext().startActivity(intent);

                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.parse(userMessageList.get(position).getMessage()),"application/pdf");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        messageViewHolder.itemView.getContext().startActivity(intent);
                    }
                });
            }
        }

    }

    @Override
    public int getItemCount() {
        return userMessageList.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {
        public TextView senderMessageText, receiverMessageText;
        public CircleImageView receiverProfileImage;
        public ImageView messageSenderPicture,messageReceiverPicture;


        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);

            senderMessageText = itemView.findViewById(R.id.sender_message_text);
            receiverMessageText = itemView.findViewById(R.id.receiver_message_text);
            receiverProfileImage = itemView.findViewById(R.id.message_profile_image);
            messageSenderPicture = itemView.findViewById(R.id.message_sender_image_view);
            messageReceiverPicture = itemView.findViewById(R.id.message_receiver_image_view);
        }
    }

}
