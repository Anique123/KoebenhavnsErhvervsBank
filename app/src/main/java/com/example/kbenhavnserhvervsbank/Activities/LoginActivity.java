package com.example.kbenhavnserhvervsbank.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.kbenhavnserhvervsbank.R;
import com.example.kbenhavnserhvervsbank.Helper.Authentication;

public class LoginActivity extends AppCompatActivity {
    private EditText mUsername, mPassword;
    private Button login_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        this.mUsername = findViewById(R.id.username);
        this.mPassword = findViewById(R.id.loginpassword);
        this.login_button = findViewById(R.id.login_button);

        if (savedInstanceState != null) {
            mUsername.setText(savedInstanceState.getString("cpr"));
            mPassword.setText(savedInstanceState.getString("password"));
        }
    }


    public void login(View view) {
        Authentication authentication = new Authentication();
        String cpr = mUsername.getText().toString();
        String pass = mPassword.getText().toString();
        authentication.login(cpr, pass, getApplicationContext());
    }

    public void register(View view) {
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String cpr = mUsername.getText().toString();
        String password = mPassword.getText().toString();
        outState.putString("cpr", cpr);
        outState.putString("password", password);
    }


}
