package com.mm90849491.sleepguard;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;


public class MainActivity extends ActionBarActivity implements ProfileList.OnFragmentInteractionListener {
    private ArrayList<Profile> profiles = new ArrayList<Profile>();
    //private PlaceholderFragment pFragment = new PlaceholderFragment();
    private ProfileList pList;
    private Record recorder = null;
    private boolean isRecording;

    private void init() {
        Context ctx = this.getApplicationContext();
        boolean debugmode = false;

        /* clean */
        if(debugmode) {
            File[] trash = (ctx.getFilesDir()).listFiles(new FilenameFilter() {

                public boolean accept(File dir, String name) {
                    return name.toLowerCase().endsWith(".sg");// gets only .sg extension files.
                }
            });
            for (File x : trash) {
                x.delete();
            }
        }

        /* test */
        if(debugmode) {
            Profile a = new Profile(ctx);
            Profile b = new Profile(ctx);
            Profile c = new Profile(ctx);
            Profile aa = new Profile(ctx);

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

        /* normal init sequence */
        File[] files = (ctx.getFilesDir()).listFiles(new FilenameFilter() {

            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".sg");// gets only .sg extension files.
            }
        });
        sort(files);

        int i = 0;
        for(File x : files) {
            try{
                File temp = x;
                if(Profile.readSN(x.getName()) != i) {
                    if(debugmode) {
                        System.out.println("Reindex");
                    }
                    temp = new File(Profile.saveName(i));
                    x.renameTo(temp);
                }
                this.profiles.add(new Profile(ctx, temp));
                i++;
                if(debugmode) {
                    System.out.println(temp.getName());
                }
            } catch (NumberFormatException e) {
            }
        }

        /* exam output */
        if(debugmode) {
            System.out.println(">>>IMPORTING<<<");
            for (Profile x : this.profiles) {
                System.out.println(x.serialNumber());
                System.out.println(x.user.toString());
            }
            Profile d = new Profile(ctx);
            try {
                d.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (d.delete()) {
                System.out.println("D Deleted.");
            } else {
                System.out.println("Failed.");
            }
            System.out.println("Done.");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.init();
        if(this.profiles != null) {
            /* construct list view */
            /*
            this.pFragment.setProfiles(this.profiles);
            getFragmentManager().beginTransaction()
                    .add(R.id.container, this.pFragment)
                    .commit();
                    */
            this.pList = new ProfileList();
            this.pList.setProfiles(this.profiles);
            getFragmentManager().beginTransaction()
                    .add(R.id.container, this.pList)
                    .commit();

        }
        /* For the record class */
        isRecording = false; // maybe this should go somewhere else - it needs to go wherever the program is first opened; not whenever the window is opened
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
                //that.putExtra( EditProfile.NEW_PROFILE, "");
                startActivity( that );
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        }else if(id == R.id.action_profile){
            Toast.makeText(MainActivity.this, "Selected Profiles", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onFragmentInteraction(String id) {

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

