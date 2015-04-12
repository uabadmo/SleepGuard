package com.mm90849491.sleepguard;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created on 2015/04/11.
 *
 * @author M.Meng
 * @version 0.1.0
 */
public class MDialog implements Runnable {
    private String feedback;
    private Context ctx;
    private TextView display;

    public MDialog(Context that) {
        this.ctx = that;
        this.feedback = null;
    }

    public MDialog(Context that, String input) {
        this.ctx = that;
        this.feedback = input;
    }

    public MDialog(Context that, String input, TextView target) {
        this.ctx = that;
        this.feedback = input;
        this.display = target;
    }

    @Override
    public synchronized void run() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.ctx);
        builder.setTitle("File Name");
        final EditText input = new EditText(this.ctx);
        input.setText(this.feedback);
        input.setInputType(InputType.TYPE_CLASS_TEXT );
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MDialog.this.feedback = input.getText().toString();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.create().show();

        if(feedback != null) {
            if(feedback.length() < 5 || !feedback.substring(feedback.length() - 5).equals(".wav")) {
                feedback = feedback.concat(".wav");
            }
            display.setText(feedback);
        }
    }

    public synchronized void getInput() {
        this.run();
    }
}
