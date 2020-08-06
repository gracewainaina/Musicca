package com.example.musicca.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.musicca.R;
import com.example.musicca.adapters.PeopleAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class PeopleFragment extends Fragment {

    public static final String TAG = "PeopleFragment";

    private RecyclerView rvPeople;
    protected PeopleAdapter peopleAdapter;
    protected List<ParseUser> allUsers;
    private SwipeRefreshLayout swipeContainer;

    public PeopleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_people, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // using recycler view in a fragment, create a view for on row in the list
        rvPeople = view.findViewById(R.id.rvPeople);
        allUsers = new ArrayList<>();

        queryUsers();

        // Lookup the swipe container view
        swipeContainer = view.findViewById(R.id.swipeContainer);
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Remember to CLEAR OUT old items before appending in the new ones
                peopleAdapter.clear();
                queryUsers();
                peopleAdapter.notifyDataSetChanged();
            }
        });
    }

    protected void queryUsers() {
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> parseUsers, ParseException e) {
                if (e == null) {
                    // The query was successful.
                    // removing current user from the list
                    for (int i = 0; i < parseUsers.size(); i++) {
                        if (parseUsers.get(i).getUsername() != ParseUser.getCurrentUser().getUsername()){
                            allUsers.add(parseUsers.get(i));
                        }
                    }
                    // create adapter and create data source
                    peopleAdapter = new PeopleAdapter(getContext(), allUsers);
                    // set the adapter on the recycler view
                    rvPeople.setAdapter(peopleAdapter);
                    // set the layout manager on the recycler view
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                    rvPeople.setLayoutManager(linearLayoutManager);

                    peopleAdapter.notifyDataSetChanged();

                } else {
                    Log.e(TAG, "Issue retrieving profiles!", e);
                    Toast.makeText(getContext(), "Issue retrieving profiles!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}