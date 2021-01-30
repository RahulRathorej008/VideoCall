package com.example.videocall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class NotificationActivity extends AppCompatActivity
{
    private RecyclerView notifications_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        notifications_list=findViewById(R.id.notifications_list);
        notifications_list.setLayoutManager(new LinearLayoutManager(getApplicationContext()
        ));
    }
    public static class NotificationViewHolder extends RecyclerView.ViewHolder
    {
        TextView userNameTxt;
        Button acceptBtn,cancelBtn;
        ImageView profileImageView;
        RelativeLayout cardView;
        public NotificationViewHolder(@NonNull View item)
        {
            super(item);
            userNameTxt=item.findViewById(R.id.name_notification);
            acceptBtn=item.findViewById(R.id.request_accept_btn);
            cancelBtn=item.findViewById(R.id.request_decline_btn);
            profileImageView=item.findViewById(R.id.image_notification);
            cardView=item.findViewById(R.id.card_view);

        }
    }
}
