package com.mm90849491.sleepguard;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

        this.myListView = (ListView) rootView.findViewById(R.id.myListView);

        if(this.profiles != null) {
            this.strListView = new String[this.profiles.size()];
            for (int i = this.strListView.length - 1; i >= 0; i--) {
                this.strListView[i] = this.profiles.get(i).user.firstName() + " " + this.profiles.get(i).user.lastName();
            }
            ArrayAdapter<String> objAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, strListView);
            this.myListView.setAdapter(objAdapter);
            this.myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    view.setBackgroundResource(R.color.listBackgroundOnclick);
                    (new Handler()).postDelayed(new ClickEffect(view, R.color.listBackground), 100);
                    Toast.makeText(view.getContext(), "Edit Profile", Toast.LENGTH_SHORT).show();
                    Intent that = new Intent(view.getContext(), EditProfile.class);
                    that.putExtra( EditProfile.NEW_PROFILE, Profile.saveName(position));
                    startActivity( that );
                }
            });
        } else {
            //strListView = getResources().getStringArray(R.array.my_data_list);
        }



        return rootView;
    }
}
