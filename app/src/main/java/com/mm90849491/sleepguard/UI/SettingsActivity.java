package com.mm90849491.sleepguard.UI;

import android.content.Context;
import android.content.DialogInterface;
import android.location.GpsStatus;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;

import com.mm90849491.sleepguard.Objects.Setting;
import com.mm90849491.sleepguard.R;

import java.io.IOException;

public class SettingsActivity extends ActionBarActivity {
    private Setting config;
    private CheckBox rbtnFN;
    private CheckBox rbtnLN;

    public void onClickSave(View that) {
        that.setBackgroundResource(R.color.primaryLight);
        try {
            this.config.showFirstName(this.rbtnFN.isChecked());
            this.config.showLastName(this.rbtnLN.isChecked());
            this.config.save();
        } catch (IOException e) {

        }
        (new Handler()).postDelayed(new ClickEffect(that, R.color.primary), 100);
        System.exit(0);

    }

    public void onClickCancel(View v) {
        v.setBackgroundColor(getResources().getColor(R.color.primaryLight));
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        this.config = new Setting(getApplicationContext());
        this.config.load();
        this.rbtnFN = (CheckBox) findViewById(R.id.rbtnFirstName);
        this.rbtnLN = (CheckBox) findViewById(R.id.rbtnLastName);
        this.rbtnFN.setChecked(this.config.showFirstName());
        this.rbtnLN.setChecked(this.config.showLastName());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_diagnosis_manager, menu);
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
