package com.mm90849491.sleepguard;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.app.ListFragment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 */
public class DiagnosisList extends Fragment implements ListView.OnItemClickListener {
    /**
     * The fragment's ListView/GridView.
     */
    private ListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListAdapter mAdapter;
    private ArrayList<Schedule> schedules;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public DiagnosisList() {
        this.schedules = null;
    }

    public void setSchedules(ArrayList<Schedule> that) {
        this.schedules = that;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_diagnosis_manager, container, false);

        this.mListView = (ListView) view.findViewById(R.id.lstSchedule);
        this.mListView.setOnItemClickListener(this);

        if (this.schedules != null) {
            this.mAdapter = new DiagnosisAdapter(this.getActivity(), R.layout.schedule_item, this.schedules);
            this.mListView.setAdapter(this.mAdapter);
        }

        return view;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        view.setBackgroundResource(R.color.listBackgroundOnclick);
        (new Handler()).postDelayed(new ClickEffect(view, R.color.listBackground), 100);
        Toast.makeText(view.getContext(), "Edit Schedule", Toast.LENGTH_SHORT).show();
        Intent that = new Intent(parent.getContext(), Scheduler.class);
        that.putExtra(Scheduler.NEW_SCHEDULE, this.mAdapter.getItem(position).toString() );
        startActivity(that);
    }
}

