package com.mm90849491.sleepguard;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * @author Alex
 */
public class PlaceholderFragment extends Fragment {

    private ListView myListView;
    private String[] strListView;
    private ArrayList<Profile> profiles;

    public PlaceholderFragment() {
        this.profiles = null;
    }

    public void setProfiles(ArrayList<Profile> that) {
        this.profiles = that;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_main, container, false);

        myListView = (ListView) rootView.findViewById(R.id.myListView);

        if(this.profiles != null) {
            strListView = new String[this.profiles.size()];
            for (int i = strListView.length - 1; i >= 0; i--) {
                strListView[i] = this.profiles.get(i).user.firstName() + " " + this.profiles.get(i).user.lastName();
            }
            ArrayAdapter<String> objAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, strListView);
            myListView.setAdapter(objAdapter);
        } else {
            //strListView = getResources().getStringArray(R.array.my_data_list);
        }

        return rootView;
    }
}
