package com.mm90849491.sleepguard;

/**
 * Created by alex on 07/04/15.
 *
 * Implements file explorer to add audio file.
 */

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class AndroidExplorer extends ListActivity {
    public final static String FILE_PATH = "com.mm90849491.sleepguard.FILE_PATH";
    private static final String root = "/";
    private List<String> item;
    private List<String> path;
    private TextView myPath;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_file);
        this.myPath = (TextView) findViewById(R.id.path);
        if(Environment.getExternalStorageState () == Environment.MEDIA_MOUNTED) {
            this.getDir( Environment.getExternalStorageDirectory() );
        } else {
            this.getDir( new File(root) );
        }
    }

    private void getDir(File dirPath) {
        this.myPath.setText("Location: " + dirPath.getAbsolutePath() );
        this.item = new ArrayList<String>();
        this.path = new ArrayList<String>();
        File[] files = dirPath.listFiles();

        if (!dirPath.getAbsolutePath().equals(root)) {
            item.add("../");
            path.add(dirPath.getParent());
        }

        for(File file: files) {
            if (file.isDirectory()) {
                if(file.canRead()) {
                    path.add(file.getPath());
                    item.add(file.getName() + "/");
                }
            } else {
                //if(file.getName().toLowerCase().endsWith(".wav")) {
                    path.add(file.getPath());
                    item.add(file.getName());
                //}
            }
        }

        ArrayAdapter<String> fileList =
                new ArrayAdapter<String>(this, R.layout.row, item);
        setListAdapter(fileList);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        File file = new File(path.get(position));
        if (file.isDirectory()) {
            this.getDir(file);
        } else {
            Intent resultData = new Intent();
            resultData.putExtra( FILE_PATH, file.getAbsolutePath() );
            setResult(Activity.RESULT_OK, resultData);
            finish();
        }
    }
}
