package com.example.kbenhavnserhvervsbank.Helper;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Toast;
import com.example.kbenhavnserhvervsbank.Activities.MainActivity;
import com.example.kbenhavnserhvervsbank.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Authentication {
    private static User user;
    private FirebaseDatabase database;

    public Authentication() {
        database = FirebaseDatabase.getInstance();
    }

    public void login(String cpr, final String pass, final Context context) {
        if (cpr.length() == 10 ) {
            if (pass.length() >= 1) {

                DatabaseReference users = database.getReference("Users/" + cpr);
                users.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() != null) {
                            if (dataSnapshot.child("password").getValue().equals(pass) && dataSnapshot.hasChild("password")) {
                                Intent intent = new Intent(context, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);

                                // logged in user
                                user = dataSnapshot.getValue(User.class);
                            } else {
                                Toast.makeText(context, "Cpr or password was wrong", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // User does not exist
                            Toast.makeText(context, "User already exists", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        } else {
            Toast.makeText(context, "Info is invalid", Toast.LENGTH_SHORT).show();
        }
    }

    public User currentUser() {
        return user;
    }
}
