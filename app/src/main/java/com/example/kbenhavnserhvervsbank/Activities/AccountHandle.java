package com.example.kbenhavnserhvervsbank.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.kbenhavnserhvervsbank.R;
import com.example.kbenhavnserhvervsbank.models.Account;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AccountHandle extends AppCompatActivity {

    //Account to parce
    private Account accountToParce;

    //firebase
    private FirebaseDatabase userInfoDatabase;
    private DatabaseReference referece;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_account_handle);

        //Get parceableobject
        Intent intent = getIntent();
        Account account = intent.getParcelableExtra("accountInfo");

        int line1 = account.getBalance();
        String line2 = account.getAccountName();
        String line3 = account.getAccountNr();

        TextView accountName = findViewById(R.id.accountNamePage);
        accountName.setText(line2);

        TextView accountBalance = findViewById(R.id.balanceViewPage);
        accountBalance.setText(Integer.toString(line1));
        accountToParce = new Account(line1, line2, line3);


        //Send parceableobject to next view
        Button transferMoney = (Button) findViewById(R.id.TransferMoneyButton);
        transferMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(AccountHandle.this, TransferActivity.class);
                intent1.putExtra("account", accountToParce);
                finish();
                startActivity(intent1);

            }
        });

        Button billButton = (Button) findViewById(R.id.BillButton);
        billButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(AccountHandle.this, TransferActivity.class);
                intent2.putExtra("account", accountToParce);
                finish();
                startActivity(intent2);
            }
        });


    }




}
