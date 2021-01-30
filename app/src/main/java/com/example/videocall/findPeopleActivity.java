package com.example.videocall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

public class findPeopleActivity<options> extends AppCompatActivity {

    private RecyclerView findfreindsList;
    private EditText searchET;
    private String str="";
    private DatabaseReference usersRef;
    private FirebaseRecyclerAdapter<Contacts, FindFriendsViewHolder> firebaseRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_people);

        searchET=findViewById(R.id.search_user_text);
        findfreindsList=findViewById(R.id.find_friends_list);
        findfreindsList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        searchET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if(searchET.getText().toString().equals(""))
                {
                    Toast.makeText(findPeopleActivity.this,"Please write name to search",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    str=s.toString();
                    onStart();
                }

            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Contacts> options = null;
        if (str.equals(""))
        {
            options = new FirebaseRecyclerOptions.Builder<Contacts>()
                    .setQuery(usersRef, Contacts.class)
                    .build();
        }
        else
            {
            options = new FirebaseRecyclerOptions.Builder<Contacts>()
                    .setQuery(usersRef.orderByChild("name").startAt(str).endAt(str + "\uf8ff"), Contacts.class)
                    .build();
        }
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Contacts, FindFriendsViewHolder>(options)
        {

            @Override
            protected void onBindViewHolder(@NonNull FindFriendsViewHolder Holder, final int position, @NonNull  final Contacts model)
            {
                Holder.userNameTxt.setText(model.getName());
                Picasso.get().load(model.getImage()).into(Holder.profileImageView);
                Holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        String visit_user_id=getRef(position).getKey();
                        Intent intent=new Intent(findPeopleActivity.this,ProfileActivity.class);
                        intent.putExtra("visit_user_id",visit_user_id);
                        intent.putExtra("profile_image",model.getImage());
                        intent.putExtra("profile_name",model.getName());
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public FindFriendsViewHolder onCreateViewHolder(@NonNull ViewGroup p, int viewType) {
                View view = LayoutInflater.from(p.getContext()).inflate(R.layout.contact_design, p, false);
                return new FindFriendsViewHolder(view);
            }
        };
        findfreindsList.setAdapter(firebaseRecyclerAdapter);
        //firebaseRecyclerAdapter.startListening();
    }

    public static class FindFriendsViewHolder extends RecyclerView.ViewHolder
    {
        TextView userNameTxt;
        Button videoCallBtn;
        ImageView profileImageView;
        RelativeLayout cardView;
        public FindFriendsViewHolder(@NonNull View item)
        {
            super(item);
            userNameTxt=item.findViewById(R.id.name_contact);
            videoCallBtn=item.findViewById(R.id.call_btn);
            profileImageView=item.findViewById(R.id.image_contact);
            cardView=item.findViewById(R.id.card_view1);
            videoCallBtn.setVisibility(View.GONE);


        }
    }
}
