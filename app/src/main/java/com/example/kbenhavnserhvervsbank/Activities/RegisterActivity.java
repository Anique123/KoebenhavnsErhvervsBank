package com.example.kbenhavnserhvervsbank.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kbenhavnserhvervsbank.R;
import com.example.kbenhavnserhvervsbank.models.Account;
import com.example.kbenhavnserhvervsbank.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {
    private Button register_button;
    private EditText cpr, firstname, lastname, pass;
    private FirebaseDatabase db;
    private Account accountDefault, accountBudget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.register_button = findViewById(R.id.button_register);
        this.cpr = findViewById(R.id.cpr);
        this.firstname = findViewById(R.id.firstname);
        this.lastname = findViewById(R.id.lastname);
        this.pass = findViewById(R.id.password);
        this.db = FirebaseDatabase.getInstance();
        if (savedInstanceState != null) {
            User user = savedInstanceState.getParcelable("tempUser");
            cpr.setText(user.getCpr());
            firstname.setText(user.getFirstName());
            lastname.setText(user.getLastName());
            pass.setText(user.getPassword());
        }

    }


    public void registerUser(View v) {


        if (isThisValid()) {
            final DatabaseReference ref = db.getReference("Users/" + cpr.getText().toString());
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() == null) {
                        final User userMade = new User(cpr.getText().toString(), firstname.getText().toString(), lastname.getText().toString(), pass.getText().toString());

                        ref.setValue(userMade);

                        //Create and attatch new bankaccounts to user
                        final DatabaseReference accountNumberRef = db.getReference("nxtAccountNum");
                        accountNumberRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                // Fetch account number
                                 long accountNumber = Long.parseLong(dataSnapshot.getValue().toString());

                                //Create default and budget bankaccount
                                createAccounts(accountNumber, userMade.getCpr());
                                accountNumberRef.setValue("" + (accountNumber + 2));


                                // GO back to login
                                Intent backToLogin = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(backToLogin);
                                Toast.makeText(getApplicationContext(), "Welcome to København Erhvervsbank - You are now registered", Toast.LENGTH_LONG).show();
                                finish();

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    } else {
                        Toast.makeText(getApplicationContext(), "user with this cpr already exists", Toast.LENGTH_LONG).show();
                    }

                    Log.d("DEBUGS", "" + dataSnapshot.getValue());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }


    }


    public void createAccounts(Long accountNumber, String cpr) {
        //Create accounts
        DatabaseReference bankaccountsRef = db.getReference("bankaccounts/");
        long defaultAccountNumber = accountNumber;
        long budgetAccountNumber = defaultAccountNumber + 1;
        accountDefault = new Account(1000, "Default", Long.toString(defaultAccountNumber));
        accountBudget = new Account(1000, "Budget", Long.toString(budgetAccountNumber));
        bankaccountsRef.child(accountDefault.getAccountNr()).setValue(accountDefault);
        bankaccountsRef.child(accountBudget.getAccountNr()).setValue(accountBudget);

        // make accounts for user
        DatabaseReference usersBankAccountsRef = db.getReference("usersAccounts/" + cpr);
        usersBankAccountsRef.child(accountDefault.getAccountNr()).setValue(accountDefault.getAccountName());
        usersBankAccountsRef.child(accountBudget.getAccountNr()).setValue(accountBudget.getAccountName());

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        User user = new User();
        user.setCpr(cpr.getText().toString());
        user.setFirstName(firstname.getText().toString());
        user.setLastName(lastname.getText().toString());
        user.setPassword(pass.getText().toString());
        outState.putParcelable("tempUser", user);

    }

    public boolean isThisValid() {
        String cpr = this.cpr.getText().toString();
        String firstName = firstname.getText().toString();
        String lastName = lastname.getText().toString();
        String password = pass.getText().toString();

        if (cpr.length() != 10) {
            Toast.makeText(this, "enter a valid cpr!", Toast.LENGTH_LONG).show();
            return false;
        }
        if (firstName.isEmpty()) {
            Toast.makeText(this, "fill out firstname!", Toast.LENGTH_LONG).show();
            return false;
        }
        if (lastName.isEmpty()) {
            Toast.makeText(this, "fill out lastname!", Toast.LENGTH_LONG).show();

            return false;
        }
        if (password.isEmpty()) {
            Toast.makeText(this, "fill out password!", Toast.LENGTH_LONG).show();
            return false;
        }
        if (password.length() < 4) {
            Toast.makeText(this, "Password must be minimum 6 characters", Toast.LENGTH_LONG).show();
            return false;
        }

        // Default
        return true;
    }

}
