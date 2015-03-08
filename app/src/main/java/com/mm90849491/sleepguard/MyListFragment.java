package com.mm90849491.sleepguard;

import android.app.ListFragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;


/**
 * Created by alex on 07/03/15.
 */
public class MyListFragment extends ListFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_main, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //brings the data from xml into java code
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(), R.array.names, android.R.layout.simple_list_item_1);
        setListAdapter(adapter);

    }




}
