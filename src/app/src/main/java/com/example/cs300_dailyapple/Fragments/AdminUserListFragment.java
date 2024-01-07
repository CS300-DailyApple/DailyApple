package com.example.cs300_dailyapple.Fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs300_dailyapple.Models.User;
import com.example.cs300_dailyapple.R;
import com.example.cs300_dailyapple.Services.DataService;

import java.util.ArrayList;
import java.util.List;

public class AdminUserListFragment extends Fragment {

    private RecyclerView recyclerView;
    private AdminUserAdapter adminUserAdapter;
    private TextView noResultTextView;
    private SearchView searchView;
    private ArrayList<User> userList;

    public AdminUserListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_user_list, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewFood);
        noResultTextView = view.findViewById(R.id.NoResult);
        searchView = view.findViewById(R.id.searchView);

        userList = new ArrayList<>();
        adminUserAdapter = new AdminUserAdapter(userList);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adminUserAdapter);

        loadUsers(""); // Initially load all users

        searchView.setQueryHint("Search by username");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) { // When user press search button
                loadUsers(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) { // When user type in search bar
                loadUsers(newText);
                return false;
            }
        });



        return view;
    }

    private void loadUsers(String query) {
        // Query users from your data service based on the search query
        if (TextUtils.isEmpty(query)) {
            userList = DataService.getInstance().getAllUsers();
        } else {
            userList = DataService.getInstance().searchUsers(query);
        }

        if (userList.isEmpty()) {
            Log.d("AdminUserListFragment", "No result");
            noResultTextView.setVisibility(View.VISIBLE);
        } else {
            noResultTextView.setVisibility(View.GONE);
            adminUserAdapter.setUserList(userList);
        }
    }
}
