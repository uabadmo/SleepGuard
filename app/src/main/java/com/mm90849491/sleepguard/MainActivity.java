package com.mm90849491.sleepguard;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;


public class MainActivity extends ActionBarActivity {
    private ArrayList<Profile> profiles;
    private ProfileList pList;
    private Setting config;
    private Record recorder = null;
    private boolean isRecording;
    private Context ctx;

    private AndroidExplorer fileSelector;

    static final int PICK_CONTACT_REQUEST = 1;
    public void selectFile(View dummy) {
        Intent that = new Intent(getApplicationContext(), AndroidExplorer.class );
        startActivityForResult(that, PICK_CONTACT_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_CONTACT_REQUEST) {
            if (resultCode == RESULT_OK) {
                String filePath = data.getStringExtra(AndroidExplorer.FILE_PATH);
                TextView txtView = (TextView)findViewById(R.id.textView);
                txtView.setText(filePath);
            }
        }
    }

    private void clearProfile() {
        File[] trash = (this.ctx.getFilesDir()).listFiles(new FilenameFilter() {

            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".sg");// gets only .sg extension files.
            }
        });
        for (File x : trash) {
            x.delete();
        }
    }

    private void clearSetting() {
        if(this.config != null) {
            this.config.delete();
        }
    }

    private void dummyProfile() {
        Profile a = new Profile(this.ctx);
        Profile b = new Profile(this.ctx);
        Profile c = new Profile(this.ctx);
        Profile aa = new Profile(this.ctx);

        try {
            c.user.lastName("cccccccc");
            c.save();
            a.save();
            a.user.firstName("Meng");
            b.user.firstName("Matt");
            c.user.firstName("Alex");
            c.save();
            a.save();
            b.save();
            aa.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void init() {
        this.ctx = this.getApplicationContext();
        //clearProfile();
        this.config = new Setting(this.ctx);
        if( !this.config.load() ) {
            try {
                this.config.save();
            } catch (IOException e) {
                Toast.makeText(MainActivity.this, "Failed to initialise this app", Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(MainActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
            Intent that = new Intent(getApplicationContext(), HelpDesk.class );
            startActivity(that);
        }

        this.pList = new ProfileList();
        this.getFragmentManager().beginTransaction()
                .add(R.id.container, this.pList)
                .commit();
    }

    private void clearFragment() {
        Fragment that = this.getFragmentManager().findFragmentById(R.id.container);
        if(that != null) {
            this.getFragmentManager().beginTransaction()
                    .remove(that)
                    .commit();
        }
    }

    private void refresh() {
        File[] files = (this.ctx.getFilesDir()).listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".sg");
            }
        });
        sort(files);

        if(files.length > 0) {
            this.profiles = new ArrayList<Profile>();
            int i = 0;
            for (File x : files) {
                try {
                    File temp = x;
                    if (Profile.readSN(x.getName()) != i) {
                        temp = new File(this.ctx.getFilesDir(), Profile.saveName(i));
                        x.renameTo(temp);
                    }
                    this.profiles.add(new Profile(this.ctx, temp));
                    i++;
                } catch (NumberFormatException e) {
                }
            }

            this.pList = new ProfileList();
            this.pList.setProfiles(this.profiles, this.config.showFirstName(), this.config.showLastName() );
            this.getFragmentManager().beginTransaction()
                    .replace(R.id.container, this.pList)
                    .commit();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            this.init();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
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

    }

    @Override
    public void onResume() {
        super.onResume();
        this.clearFragment();
        this.refresh();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent that = new Intent(getApplicationContext(), SettingsActivity.class );
                startActivity(that);
                return true;
            case R.id.action_profile:
                this.dummyProfile();
                this.refresh();
                return true;
            case R.id.action_clear_setting:
                this.clearSetting();
                this.refresh();
                Toast.makeText(MainActivity.this, "Setting Cleared", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_clear_profile:
                this.clearProfile();
                this.clearFragment();
                this.profiles = null;
                Toast.makeText(MainActivity.this, "Profiles Cleared", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    static public void sort(File[] trash) {
        Arrays.sort(trash, new Comparator<File>() {
            @Override
            public int compare(File trash1, File trash2) {
                int n1 = extractNumber(trash1.getName());
                int n2 = extractNumber(trash2.getName());
                return n1 - n2;
            }

            private int extractNumber(String that) {
                return Profile.readSN(that);
            }
        });
    }

}

