package com.mm90849491.sleepguard.UI;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mm90849491.sleepguard.Analyser.Analyser;
import com.mm90849491.sleepguard.Objects.Schedule;

import java.io.File;

/**
 * Created on 2015/04/11.
 *
 * @author M.Meng
 * @version 0.1.0
 */
public class WaitingDialog {
    private Context ctx;
    private Boolean done;
    private String title;
    private File audioPath;
    private Integer diagResult;
    private SchedulerAdapter statuesAdapter;
    private AlertDialog task;

    public WaitingDialog(Context that, File file,Integer feedback, SchedulerAdapter pool, String title) {
        this.ctx = that;
        this.title = title;
        this.audioPath = file;
        this.statuesAdapter = pool;
        this.diagResult = feedback;
    }

    public int go() {
        this.diagResult = -1;
        final AlertDialog.Builder builder = new AlertDialog.Builder(this.ctx);
        builder.setTitle(title);
        final ProgressBar spinner = new ProgressBar(this.ctx);
        builder.setView(spinner);
        builder.setCancelable(false);
        builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                done = true;
            }
        });
        task = builder.create();
        task.show();

        diagResult = Analyser.generate(this.audioPath);
        final Thread t = new Thread(){

            @Override
            public void run(){
                while(diagResult == -1){
                    try {
                        sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if(diagResult > 3) {
                    statuesAdapter.setText(0, Scheduler.STATES[4]);
                } else {
                    statuesAdapter.setText(0, Scheduler.STATES[2]);
                }
                task.dismiss();
            }
        };
        t.start();
        return this.diagResult;

    }
}
