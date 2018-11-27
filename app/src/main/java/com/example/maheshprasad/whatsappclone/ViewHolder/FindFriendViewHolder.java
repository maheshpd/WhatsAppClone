package com.example.maheshprasad.whatsappclone.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.maheshprasad.whatsappclone.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class FindFriendViewHolder extends RecyclerView.ViewHolder {

   public TextView username,userstatus;
   public CircleImageView profileImage;
   public ImageView online_image;
    public FindFriendViewHolder(@NonNull View itemView) {
        super(itemView);

        username = itemView.findViewById(R.id.display_user_name);
        userstatus = itemView.findViewById(R.id.user_status);
        profileImage = itemView.findViewById(R.id.users_profile_image);
        online_image = itemView.findViewById(R.id.online_image);
    }
}
