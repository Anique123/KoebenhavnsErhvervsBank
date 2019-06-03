package com.example.kbenhavnserhvervsbank.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class User implements Parcelable {

    //User and profile data
    private String cpr;
    private String firstName;
    private String lastName;
    private String password;

    // Account specific data
    private ArrayList<Account> accounts = new ArrayList<>();


    public User() {
    }

    public User(String cpr, String firstName, String lastName, String password) {
        this.cpr = cpr;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

    // Getters and setters
    protected User(Parcel in) {
        cpr = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        password = in.readString();
        accounts = in.createTypedArrayList(Account.CREATOR);
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getCpr() {
        return cpr;
    }

    public void setCpr(String cpr) {
        this.cpr = cpr;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cpr);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(password);
        dest.writeTypedList(accounts);
    }
}
