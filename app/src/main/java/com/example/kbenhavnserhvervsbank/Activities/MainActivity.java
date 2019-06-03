package com.example.kbenhavnserhvervsbank.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;
import com.example.kbenhavnserhvervsbank.R;
import com.example.kbenhavnserhvervsbank.RecyclerAct.RecyclerViewActivity;
import com.example.kbenhavnserhvervsbank.models.Account;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    GridLayout mainGrid;

    //Firebase
    private FirebaseDatabase userInfoDatabase;
    private DatabaseReference referece;

    Account mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Write a message to the database
        userInfoDatabase = FirebaseDatabase.getInstance();
        referece = userInfoDatabase.getReference("account");
        setContentView(R.layout.activity_main);
        mainGrid = findViewById(R.id.mainGrid);

        //setgrid
        setSingleEvent(mainGrid);
    }

    public void setSingleEvent(GridLayout mainGrid){

        //Onclicklistener for budget to navigate
        CardView budgetView = (CardView) findViewById(R.id.budgetView);
        budgetView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), RecyclerViewActivity.class);
                intent.putExtra("user", mUser);
                v.getContext().startActivity(intent);
                Toast.makeText(MainActivity.this, "Budget clicked", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
