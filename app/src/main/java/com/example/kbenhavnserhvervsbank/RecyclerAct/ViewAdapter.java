package com.example.kbenhavnserhvervsbank.RecyclerAct;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.example.kbenhavnserhvervsbank.R;
import com.example.kbenhavnserhvervsbank.models.Account;

import java.util.ArrayList;

public class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.MenuViewHolder> {
    //lists for recyclerobjects to menu
    ArrayList<Account> accountsList;

    private OnItemClickListener mListener;

    public ViewAdapter(ArrayList<Account> accountList){
        accountsList = accountList;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    public static class MenuViewHolder extends RecyclerView.ViewHolder {
        public TextView accountName, accountNr, balance;

        public MenuViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            accountName = itemView.findViewById(R.id.accountTitle);
            accountNr = itemView.findViewById(R.id.accountNr);
            balance = itemView.findViewById(R.id.balance);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });

        }
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_budget, parent, false);
        MenuViewHolder evh = new MenuViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(MenuViewHolder holder, int i) {
         Account accountItem = accountsList.get(i);

        holder.accountName.setText(accountItem.getAccountName());
        holder.accountNr.setText(accountItem.getAccountNr());
        holder.balance.setText(Integer.toString((accountItem.getBalance())));
    }

    @Override
    public int getItemCount() {
        return accountsList.size();
    }


}
