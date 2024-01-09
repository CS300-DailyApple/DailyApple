package com.example.cs300_dailyapple.Fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs300_dailyapple.Models.User;
import com.example.cs300_dailyapple.R;

import java.util.List;

public class AdminUserAdapter extends RecyclerView.Adapter<AdminUserAdapter.AdminUserViewHolder> {
    public interface OnUserItemClickListener {
        void onUserItemClick(User user);
    }

    private List<User> userList;
    private OnUserItemClickListener listener;

    public AdminUserAdapter(List<User> userList, OnUserItemClickListener listener) {
        this.userList = userList;
        this.listener = listener;
    }

    public void setUserList(List<User> userList, OnUserItemClickListener listener) {
        this.userList = userList;
        this.listener = listener;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdminUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_user_list_item, parent, false);
        return new AdminUserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminUserViewHolder holder, int position) {
        User user = userList.get(position);

        holder.textViewName.setText("Tên người dùng: " + user.getUsername());
        holder.textViewAttributes.setText("Uy tín: " + user.getCreditPoints());
        if (user.isBanned()) {
            holder.banView.setVisibility(View.VISIBLE);
        } else {
            holder.banView.setVisibility(View.INVISIBLE);
        }
        // Handle item click
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onUserItemClick(user);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    static class AdminUserViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        TextView textViewAttributes;
        ImageView banView;

        AdminUserViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewAttributes = itemView.findViewById(R.id.textViewAttributes);
            banView = itemView.findViewById(R.id.imageViewBan);
        }
    }
}
