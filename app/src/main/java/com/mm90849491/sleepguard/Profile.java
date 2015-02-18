package com.mm90849491.sleepguard;

import android.content.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Created by Kenelf on 2015/02/17.
 */
public class Profile {
    protected Client user;
    protected Clinician doctor;
    private Record[] _record;
    private Schedule[] _schedule;
    //private File _targetDirectory;
    private String _targetName;

    public Profile() {
        this.user = new Client();
        this.doctor = new Clinician("mobA");
        this._targetName = "profile.sg";
    }

    protected void save(Context ctx) throws IOException {
        FileOutputStream fos = ctx.openFileOutput(this._targetName, Context.MODE_PRIVATE);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(this.user);
        fos.close();
    }

}
