package com.example.mesdeputes.fragments;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.example.mesdeputes.R;
import com.example.mesdeputes.models.Deputy;
import com.example.mesdeputes.models.DeputyAdapter;
import com.example.mesdeputes.services.ApiServices;
import com.example.mesdeputes.services.SearchObserver;
import java.util.ArrayList;

public class SearchFragment extends Fragment implements SearchView.OnQueryTextListener, SearchObserver, AdapterView.OnItemClickListener {
    private SearchView searchView;
    private ListView listView;
    private DeputyAdapter adapter;
    private ArrayList<Deputy> deputies;
    private SearchObserver listener;

    public void setListener(SearchObserver listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        @SuppressLint("InflateParams") View v = inflater.inflate(R.layout.fragment_search, null);

        searchView = v.findViewById(R.id.searchViewMain);
        searchView.setOnQueryTextListener(this);
        listView = v.findViewById(R.id.listViewMain);
        deputies = new ArrayList<>();
        adapter = new DeputyAdapter(deputies, getContext());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        return v;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        deputies = new ArrayList<>();
        ApiServices.searchRequest(getContext(), query, this);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void onReceiveDeputyInfo(Deputy deputy) {
        if(!deputies.contains(deputy)){
            deputies.add(deputy);
            adapter.setDeputies(deputies);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        listener.onReceiveDeputyInfo(deputies.get(position));
    }
}