package com.mm90849491.sleepguard;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;


public class DiagnosisManager extends ActionBarActivity {
    public final static String DISPLAY_NAME = "com.mm90849491.sleepguard.DISPLAY_NAME";
    private ArrayList<Schedule> schedules;
    private DiagnosisList dList;
    private Context ctx;
    private String displayName;
    private String profileName;

    TextView txtCurrent;


    private void init() {
        this.ctx = this.getApplicationContext();
        Schedule nowee = new Schedule();
        Schedule nowee1 = new Schedule();
        Schedule nowee2 = new Schedule();
        Schedule nowee3 = new Schedule();
        this.schedules = new ArrayList<Schedule>();
        this.schedules.add(nowee);
        nowee1.advance(45);
        this.schedules.add(nowee1);
        nowee2.advance(45);
        nowee2.delay(45);
        this.schedules.add(nowee2);
        nowee3.delay(45);
        this.schedules.add(nowee3);

        this.dList = new DiagnosisList();
        this.dList.setSchedules(this.schedules );
        this.getFragmentManager().beginTransaction()
                .add(R.id.diagnosisLayout, this.dList)
                .commit();
    }

    private void clearFragment() {
        android.app.Fragment that = this.getFragmentManager().findFragmentById(R.id.container);
        if(that != null) {
            this.getFragmentManager().beginTransaction()
                    .remove(that)
                    .commit();
        }
    }

    private void refresh() {
        /*
        File[] files = (this.ctx.getFilesDir()).listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".sg");
            }
        });
        MainActivity.sort(files);

        if(files.length > 0) {
            this.schedules = new ArrayList<Schedule>();
            int i = 0;
            for (File x : files) {
                try {
                    File temp = x;
                    if (Profile.readSN(x.getName()) != i) {
                        temp = new File(this.ctx.getFilesDir(), Profile.saveName(i));
                        x.renameTo(temp);
                    }
                    this.schedules.add(new Schedule(this.ctx, temp));
                    i++;
                } catch (NumberFormatException e) {
                }
            }

            this.dList = new DiagnosisList();
            this.dList.setProfiles(this.schedules );
            this.getFragmentManager().beginTransaction()
                    .replace(R.id.container, this.dList)
                    .commit();
        }
        */
        this.dList = new DiagnosisList();
        this.dList.setSchedules(this.schedules );
        this.getFragmentManager().beginTransaction()
                .replace(R.id.diagnosisLayout, this.dList)
                .commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosis_manager);
        this.init();
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            this.displayName = "John Smith";
        } else {
            this.displayName = extras.getString(DISPLAY_NAME);
            this.profileName = extras.getString(EditProfile.NEW_PROFILE);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        setTitle(this.displayName);
        this.txtCurrent = (TextView) findViewById(R.id.txtCurrentUser);

        /*
        ImageButton btnNewProfile = (ImageButton)findViewById(R.id.btnNewProfile);
        btnNewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "New Profile", Toast.LENGTH_SHORT).show();
                Intent that = new Intent(getApplicationContext(), EditProfile.class);
                startActivity( that );
            }
        });

        ImageButton btnManual = (ImageButton)findViewById(R.id.btnManual);
        btnManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Manual", Toast.LENGTH_SHORT).show();
                Intent that = new Intent(getApplicationContext(), HelpDesk.class );
                startActivity( that );
            }
        });
        */

    }

    @Override
    public void onResume() {
        super.onResume();
        this.clearFragment();
        this.refresh();
        this.txtCurrent.setText(this.displayName);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_diagnosis_manager, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        Context ctx = this.getApplicationContext();
        if (id == R.id.action_edit_profile) {
            Toast.makeText(ctx, "Edit Profile", Toast.LENGTH_SHORT).show();
            Intent that = new Intent(ctx, EditProfile.class);
            that.putExtra(EditProfile.NEW_PROFILE, this.profileName);
            startActivity(that);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
