package com.example.kbenhavnserhvervsbank.RecyclerAct;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.example.kbenhavnserhvervsbank.Activities.AccountHandle;
import com.example.kbenhavnserhvervsbank.R;
import com.example.kbenhavnserhvervsbank.Helper.Authentication;
import com.example.kbenhavnserhvervsbank.models.Account;
import com.example.kbenhavnserhvervsbank.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RecyclerViewActivity extends AppCompatActivity {


    private RecyclerView mRecyclerView;
    private ViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    //firebase
    private FirebaseDatabase userInfoDatabase;
    private DatabaseReference referece;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        AccountObjects();
    }

    public void AccountObjects() {
        final ArrayList<Account> accounts = new ArrayList<>();
        Authentication authService = new Authentication();
        User user = authService.currentUser();
        userInfoDatabase = FirebaseDatabase.getInstance();
        referece = userInfoDatabase.getReference("usersAccounts").child("/" + user.getCpr());

        referece.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot accountSnapshot: dataSnapshot.getChildren()){
                    DatabaseReference bankAccounts = userInfoDatabase.getReference("bankaccounts").child(accountSnapshot.getKey());
                    bankAccounts.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Account account = dataSnapshot.getValue(Account.class);
                            accounts.add(account);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

                // Must be done to wait for data is loaded
                userInfoDatabase.getReference().addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        buildRecyclerView(accounts);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void buildRecyclerView(ArrayList<Account> list){
        final ArrayList<Account> accounts = list;
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ViewAdapter(accounts);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new ViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(RecyclerViewActivity.this, AccountHandle.class);
                intent.putExtra("accountInfo", accounts.get(position));
                finish();
                startActivity(intent);
            }
        });
    }
}