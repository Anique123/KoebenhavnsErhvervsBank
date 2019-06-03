package com.example.kbenhavnserhvervsbank.Activities;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kbenhavnserhvervsbank.R;
import com.example.kbenhavnserhvervsbank.models.Account;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TransferActivity extends AppCompatActivity {

    FirebaseDatabase db;
    DatabaseReference ref;
    private EditText toAccount, transferText, ammount;
    Account accountToParse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
        this.toAccount = findViewById(R.id.toAccount);
        this.transferText = findViewById(R.id.transferDetail);
        this.ammount = findViewById(R.id.amountToTransfer);
        this.db = FirebaseDatabase.getInstance();

        //Get parceableobject
        Intent intent = getIntent();
        Account account = intent.getParcelableExtra("account");
        int line1 = account.getBalance();
        String line2 = account.getAccountName();
        String line3 = account.getAccountNr();
        accountToParse = new Account(line1,line2,line3);
    }

    public void transfer(View view) {

        // Check if amount is entered
        if (ammount.getText().toString().length() < 1) {
            Toast.makeText(getApplicationContext(), "Enter ammount!", Toast.LENGTH_LONG).show();
            return;
        }

        if (toAccount.getText().toString().length() < 1) {
            Toast.makeText(getApplicationContext(), "Enter account number!", Toast.LENGTH_LONG).show();
            return;
        }

        if (transferText.getText().toString().length() < 1) {
            Toast.makeText(getApplicationContext(), "Enter text for transaction! ", Toast.LENGTH_LONG).show();
            return;
        }

        transferMoney(Integer.parseInt(toAccount.getText().toString()), accountToParse,  Integer.parseInt(ammount.getText().toString()));
    }

    public void transferMoney(final int accountnumber, final Account account, final int ammount){

        final int ac = accountnumber;
        final Account from = account;
        final DatabaseReference accountNumbers = db.getReference("bankaccounts");
        accountNumbers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean exists = false;
                for(DataSnapshot snap: dataSnapshot.getChildren()){
                    final String accountNumber = snap.getKey();
                    if(ac == Integer.parseInt((accountNumber))){
                        exists = true;
                        int balance = account.getBalance();

                        // Get account balance
                        DatabaseReference accountReceiver = accountNumbers.child("/"+accountNumber).child("/balance");
                        accountReceiver.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                int balance = dataSnapshot.getValue(Integer.class);
                                // Substract from sender

                                accountNumbers.child(from.getAccountNr()).child("/balance").setValue(account.getBalance()-ammount);

                                // Add to receiver

                                accountNumbers.child(accountNumber).child("/balance").setValue(balance+ammount);

                                // Account for intent
                                account.setBalance(account.getBalance()-ammount);

                                Intent intent = new Intent(TransferActivity.this, AccountHandle.class);

                                intent.putExtra("accountInfo", account);
                                finish();
                                startActivity(intent);

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }


                        });



                        if(!exists){
                            Toast.makeText(getApplicationContext(), "Account does not exist", Toast.LENGTH_LONG).show();
                            return;
                        }

                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
