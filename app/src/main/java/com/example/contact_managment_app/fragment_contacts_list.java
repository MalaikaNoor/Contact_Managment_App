package com.example.contact_managment_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class fragment_contacts_list extends Fragment {

    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_contacts_list, container, false);

        // Initialize RecyclerView
        recyclerView = rootView.findViewById(R.id.recycler_view_contacts);
        // Set up an adapter and data source for the RecyclerView here

        return rootView;
    }
}
