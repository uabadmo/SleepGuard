package com.mm90849491.sleepguard.UI;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.mm90849491.sleepguard.Analyser.Analyser;
import com.mm90849491.sleepguard.Objects.Profile;
import com.mm90849491.sleepguard.R;
import com.mm90849491.sleepguard.Objects.Schedule;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class Scheduler extends Activity {
    public final static String NEW_SCHEDULE = "com.mm90849491.sleepguard.EDIT_SCHEDULE";
    public final static String[] STATES = {
            "Waiting for Configuration",
            "Audio File Not Found",
            "Bad Recording",
            "Ready to Generate Result",
            "Result Available",
            "Successfully Finished" };
    private Schedule newSchedule;
    private String fileName;
    private int diagResult;
    private String audioPath;

    SchedulerAdapter statuesAdapter;
    ExpandableListView statuesGroup;
    List<String> statuesItems;

    SchedulerAdapter sourceAdapter;
    ExpandableListView sourceGroup;
    List<String> sourceItems;

    SchedulerAdapter scheduleAdapter;
    ExpandableListView scheduleGroup;
    List<String> scheduleItems;

    MDialog mm;

    public void onClickCancel(View v) {
        v.setBackgroundColor(getResources().getColor(R.color.primaryLight));
        super.onBackPressed();
    }

    static final int PICK_CONTACT_REQUEST = 1;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_CONTACT_REQUEST) {
            if (resultCode == RESULT_OK) {
                String filePath = data.getStringExtra(AndroidExplorer.FILE_PATH);
                this.audioPath = filePath;
                int end = filePath.lastIndexOf(File.separator);
                this.sourceAdapter.setText(0, filePath.substring(0, end + 1) );
                if(end < filePath.length()) {
                    this.sourceAdapter.setText(1, filePath.substring(end + 1));
                }
            }
        }
    }

    private void prepareListData() {
        this.statuesItems = new ArrayList<String>();
        this.statuesItems.add(STATES[0]);
        this.statuesItems.add("Generate Result");
        this.statuesItems.add("Read Result");

        int[] icons = {0, 3, 2};
        this.statuesAdapter = new SchedulerAdapter(this, getResources().getString(R.string.text_diagnosis_status) , this.statuesItems, icons);
        this.statuesGroup.setAdapter(this.statuesAdapter);
        this.statuesGroup.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                switch (childPosition) {
                    case 1:
                        diagResult = Analyser.generate(new File(audioPath));
                        statuesAdapter.setText(0, STATES[4]);
                        return true;
                    case 2:
                        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(parent.getContext());
                        dlgAlert.setMessage(Analyser.HAZARD_CLASS[diagResult]);
                        dlgAlert.setTitle("Diagnosis Result");
                        dlgAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        dlgAlert.setCancelable(false);
                        dlgAlert.create().show();
                        return true;
                    default:
                }
                return true;
            }
        });
        this.statuesGroup.expandGroup(0);


        this.sourceItems = new ArrayList<String>();
        this.sourceItems.add("/");
        this.sourceItems.add("");

        int[] icons2 = {4, 1};
        this.sourceAdapter = new SchedulerAdapter(this, "Audio File Path" , this.sourceItems, icons2);
        this.sourceGroup.setAdapter(this.sourceAdapter);
        this.sourceGroup.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {


            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Context ctx = parent.getContext();
                TextView me = (TextView) v.findViewById(R.id.txtItem0);
                switch (childPosition) {
                    case 0:
                        Intent that = new Intent(ctx, AndroidExplorer.class );
                        that.putExtra(AndroidExplorer.CWD, me.getText().toString());
                        startActivityForResult(that, PICK_CONTACT_REQUEST);
                        return true;
                    case 1:
                        audioPath = me.getText().toString();
                        mm = new MDialog(ctx, audioPath, me, "File Name");
                        mm.getInput();
                        return true;
                    default:
                }
                return true;
            }
        });


        this.scheduleItems = new ArrayList<String>();
        this.scheduleItems.add("Record Now");
        this.scheduleItems.add("Starting Time");
        this.scheduleItems.add("2015-05-05 12:21");
        this.scheduleItems.add("Duration HH:MM");
        this.scheduleItems.add("12:21");
        this.scheduleItems.add("Ending Time");
        this.scheduleItems.add("2015-05-05 12:22");

        int[] icons3 = {6, 0, 5, 0, 5, 0, 5};
        this.scheduleAdapter = new SchedulerAdapter(this, "Audio File Path" , this.scheduleItems, icons3);
        this.scheduleGroup.setAdapter(this.scheduleAdapter);
        this.scheduleGroup.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {


            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Context ctx = parent.getContext();
                TextView me = (TextView) v.findViewById(R.id.txtItem0);
                switch (childPosition) {
                    case 0:
                        Intent that = new Intent(ctx, AndroidExplorer.class );
                        that.putExtra(AndroidExplorer.CWD, me.getText().toString());
                        startActivityForResult(that, PICK_CONTACT_REQUEST);
                        return true;
                    case 2:
                        mm = new MDialog(ctx, me.getText().toString(), me, "Starting Time");
                        mm.getInput();
                        return true;
                    case 4:
                        mm = new MDialog(ctx, me.getText().toString(), me, "Duration HH:MM");
                        mm.getInput();
                        return true;
                    case 6:
                        mm = new MDialog(ctx, me.getText().toString(), me, "Ending Time");
                        mm.getInput();
                        return true;
                    default:
                }
                return true;
            }
        });
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

        this.audioPath = "";
        this.statuesGroup = (ExpandableListView) findViewById(R.id.elvStatus);
        this.sourceGroup = (ExpandableListView) findViewById(R.id.elvSource);
        this.scheduleGroup = (ExpandableListView) findViewById(R.id.elvSchedule);
        this.prepareListData();
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
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_scheduler, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
