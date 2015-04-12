package com.mm90849491.sleepguard;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.text.InputType;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created on 2015/04/11.
 *
 * @author M.Meng
 * @version 0.1.0
 */
public class MDialog {
    private String feedback;
    private Context ctx;
    private TextView display;
    private Boolean done;
    private String title;

    public MDialog(Context that, String title) {
        this.ctx = that;
        this.feedback = null;
        this.title = title;
    }

    public MDialog(Context that, String input, String title) {
        this.ctx = that;
        this.feedback = input;
        this.title = title;
    }

    public MDialog(Context that, String input, TextView target, String title) {
        this.ctx = that;
        this.feedback = input;
        this.display = target;
        this.title = title;
    }

    public void getInput() {
        this.done = false;
        AlertDialog.Builder builder = new AlertDialog.Builder(this.ctx);
        builder.setTitle(title);
        final EditText input = new EditText(this.ctx);
        input.setText(this.feedback);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MDialog.this.feedback = input.getText().toString();
                MDialog.this.done = true;

                if (MDialog.this.done && feedback != null) {
                    if (feedback.length() < 5 || !feedback.substring(feedback.length() - 4).equals(".wav")) {
                        feedback = feedback.concat(".wav");
                    }
                    display.setText(feedback);
                }

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                done = true;
            }
        });
        builder.create().show();

    }
}
