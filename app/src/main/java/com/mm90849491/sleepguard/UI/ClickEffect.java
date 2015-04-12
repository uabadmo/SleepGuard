package com.mm90849491.sleepguard.UI;

import android.view.View;

/**
 * Created by M.Meng on 2015/04/03.
 */
public class ClickEffect implements Runnable {
    private View v;
    private int c;

    public ClickEffect(View that, int colour) {
        this.v = that;
        this.c = colour;
    }

    @Override
    public void run() {
        this.v.setBackgroundResource(this.c);
    }

}
