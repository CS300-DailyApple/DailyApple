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
import androidx.navigation.NavAction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs300_dailyapple.Models.GlobalApplication;
import com.example.cs300_dailyapple.Models.User;
import com.example.cs300_dailyapple.R;
import com.example.cs300_dailyapple.Services.DataService;

import java.util.ArrayList;
import java.util.List;

public class AdminUserListFragment extends Fragment implements AdminUserAdapter.OnUserItemClickListener {

    private RecyclerView recyclerView;
    private AdminUserAdapter adminUserAdapter;
    private TextView noResultTextView;
    private SearchView searchView;
    private ArrayList<User> originalUserList;
    private ArrayList<User> searchedUserList;
    private GlobalApplication globalApplication;

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

        globalApplication = (GlobalApplication)this.getActivity().getApplication();
        originalUserList = globalApplication.getForAdminUserList();
        searchedUserList = new ArrayList<>(originalUserList);
        adminUserAdapter = new AdminUserAdapter(searchedUserList, this::onUserItemClick);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adminUserAdapter);

        loadUsers("");

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
        searchedUserList.clear();
        if (TextUtils.isEmpty(query)) {
            searchedUserList.addAll(originalUserList);
        } else {
            for (User user : originalUserList) {
                if (user.getUsername().toLowerCase().contains(query.toLowerCase())) {
                    searchedUserList.add(user);
                }
            }
        }
        if (searchedUserList.isEmpty()) {
            noResultTextView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            noResultTextView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
        adminUserAdapter.notifyDataSetChanged();
    }
    @Override
    public void onUserItemClick(User user) {
        // put user data to bundle
        Bundle bundle = new Bundle();
        bundle.putString("username", user.getUsername());
        bundle.putInt("creditPoints", user.getCreditPoints());
        // navigate to AdminUserItemDetail fragment
        Navigation.findNavController(getView()).navigate(R.id.action_adminUserListFragment_to_adminUserItemDetail, bundle);
    }
    @Override
    public void onResume() {
        super.onResume();
        loadUsers("");
    }
}
