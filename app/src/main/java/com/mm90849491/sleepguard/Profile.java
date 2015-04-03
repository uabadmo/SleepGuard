package com.mm90849491.sleepguard;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/** Profile class,
 *    Container of a full profile:
 *          one Client, one Clinician, multiple Records, and multiple Schedules.
 *    This class manages saving objects to file, loading objects from file.
 *    Contains NumberFormatException, error message needs to be prompted by UI.
 *    Contains IOException, error message needs to be prompted by UI.
 *  @version 0.2.0
 *  @author M.Meng
 */
public class Profile {
    private static int serialNumber = 0;
    protected static String prefix = "profile";
    protected static String suffix = "sg";
    protected static int digits = 2;

    /* ------------ begin of instance variables ------------ */
    protected Client user;
    protected Clinician doctor;
    private Record[] _record;
    private Schedule[] _schedule;
    /* ---------------- constant variables ----------------- */
    final private int _SN;
    final private Context _CTX;
    /* ------------- end of instance variables ------------- */

    public Profile(Context ctx) {
        this.user = new Client();
        this.doctor = new Clinician("mobA");
        this._CTX = ctx;
        this._SN = serialNumber;
        serialNumber++;
    }

    public Profile(Context ctx, File savedata) throws NumberFormatException {
        FileInputStream fin;
        ObjectInputStream ois;
        this._CTX = ctx;
        this._SN = readSN(savedata.getName());
        serialNumber++;
        try {
            fin = new FileInputStream(savedata);
            ois = new ObjectInputStream(fin);
            this.user = (Client) ois.readObject();
            this.doctor = (Clinician) ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
        }
    }

    protected void save() throws IOException {
        FileOutputStream fos = new FileOutputStream(
                new File(this._CTX.getFilesDir() , saveName(this.serialNumber() )));
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(this.user);
        oos.writeObject(this.doctor);
        fos.close();
    }

    protected boolean delete() {
        File file = new File(this._CTX.getFilesDir(), saveName(this.serialNumber()));
        return file.delete();
    }

    protected int serialNumber() {
        return this._SN;
    }

    static protected String saveName(int that) {
        return prefix.concat(String.format("%0" + digits + "d", that)).concat(".").concat(suffix);
    }

    static protected int readSN(String that) throws NumberFormatException {
        return Integer.valueOf(that.substring(prefix.length(), prefix.length() + digits));
    }

}
