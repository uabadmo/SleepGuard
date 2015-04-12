package com.mm90849491.sleepguard.Objects;

import android.content.Context;

import com.mm90849491.sleepguard.UI.Clinician;

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
    public static String prefix = "profile";
    public static String suffix = "sg";
    public static int digits = 2;

    /* ------------ begin of instance variables ------------ */
    public Client user;
    public Clinician doctor;
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
            this.user = new Client();
            this.doctor = new Clinician("mobA");
        }
    }

    @Override
    public boolean equals(Object obj) {
        Profile that = (Profile) obj;
        return (
                    this.user.toString().equals( that.user.toString() ) &&
                    this.doctor.toString().equals( that.doctor.toString() )
                );
    }

    public void save() throws IOException {
        FileOutputStream fos = new FileOutputStream(
                new File(this._CTX.getFilesDir() , saveName(this.serialNumber() )));
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(this.user);
        oos.writeObject(this.doctor);
        fos.close();
    }

    public boolean delete() {
        File file = new File(this._CTX.getFilesDir(), saveName(this.serialNumber()));
        return file.delete();
    }

    public int serialNumber() {
        return this._SN;
    }

    static public String saveName(int that) {
        return prefix.concat(String.format("%0" + digits + "d", that)).concat(".").concat(suffix);
    }

    static public int readSN(String that) throws NumberFormatException {
        return Integer.valueOf(that.substring(prefix.length(), prefix.length() + digits));
    }

}
