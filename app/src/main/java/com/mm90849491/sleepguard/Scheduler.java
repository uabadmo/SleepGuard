package com.mm90849491.sleepguard;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Scheduler extends Activity {
    public final static String NEW_SCHEDULE = "com.mm90849491.sleepguard.EDIT_SCHEDULE";
    private Schedule newSchedule;
    private String fileName;

    SchedulerAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    public void onClickCancel(View v) {
        v.setBackgroundColor(getResources().getColor(R.color.primaryLight));
        super.onBackPressed();
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Top 250");
        listDataHeader.add("Now Showing");
        listDataHeader.add("Coming Soon..");

        // Adding child data
        List<String> top250 = new ArrayList<String>();
        top250.add("The Shawshank Redemption");
        top250.add("The Godfather");
        top250.add("The Godfather: Part II");
        top250.add("Pulp Fiction");
        top250.add("The Good, the Bad and the Ugly");
        top250.add("The Dark Knight");
        top250.add("12 Angry Men");

        List<String> nowShowing = new ArrayList<String>();
        nowShowing.add("The Conjuring");
        nowShowing.add("Despicable Me 2");
        nowShowing.add("Turbo");
        nowShowing.add("Grown Ups 2");
        nowShowing.add("Red 2");
        nowShowing.add("The Wolverine");

        List<String> comingSoon = new ArrayList<String>();
        comingSoon.add("2 Guns");
        comingSoon.add("The Smurfs 2");
        comingSoon.add("The Spectacular Now");
        comingSoon.add("The Canyons");
        comingSoon.add("Europa Report");

        listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
        listDataChild.put(listDataHeader.get(1), nowShowing);
        listDataChild.put(listDataHeader.get(2), comingSoon);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduler);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                this.fileName = Profile.saveName(99);
            } else {
                this.fileName = extras.getString(NEW_SCHEDULE);
            }
        } else {
            this.fileName = (String) savedInstanceState.getSerializable(NEW_SCHEDULE);
        }

        this.expListView = (ExpandableListView) findViewById(R.id.elvStatus);
        this.prepareListData();

        this.listAdapter = new SchedulerAdapter(this, this.listDataHeader, this.listDataChild);

        // setting list adapter
        this.expListView.setAdapter(this.listAdapter);
    }

    @Override
    protected void  onStart() {
        super.onStart();

        setTitle(this.fileName);
        Context ctx = this.getApplicationContext();

        File temp = new File(ctx.getFilesDir(), this.fileName);
        if(temp.exists()) {
            this.newSchedule = new Schedule(ctx, temp);
        } else {
            this.newSchedule = new Schedule(ctx);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scheduler, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
