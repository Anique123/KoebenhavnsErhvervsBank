package com.example.kbenhavnserhvervsbank.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Account implements Parcelable {
    private int balance;
    private String accountName;
    private String accountNr;

    public Account(){

    }

    public Account(String accountNr){
        this.accountNr = accountNr;
    }

    public Account(int balance, String accountName, String accountNr) {
        this.balance = balance;
        this.accountName = accountName;
        this.accountNr = accountNr;
    }

    protected Account(Parcel in) {
        balance = in.readInt();
        accountName = in.readString();
        accountNr = in.readString();
    }

    public static final Creator<Account> CREATOR = new Creator<Account>() {
        @Override
        public Account createFromParcel(Parcel in) {
            return new Account(in);
        }

        @Override
        public Account[] newArray(int size) {
            return new Account[size];
        }
    };

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountNr() {
        return accountNr;
    }

    public void setAccountNr(String accountNr) {
        this.accountNr = accountNr;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(balance);
        dest.writeString(accountName);
        dest.writeString(accountNr);
    }
}

