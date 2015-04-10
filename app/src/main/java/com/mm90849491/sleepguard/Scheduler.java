package com.mm90849491.sleepguard;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;


public class Scheduler extends Activity {
    public final static String NEW_SCHEDULE = "com.mm90849491.sleepguard.EDIT_SCHEDULE";
    private Schedule newSchedule;
    private String fileName;

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
