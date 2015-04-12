package com.mm90849491.sleepguard.Objects;

import android.content.Context;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Created by M.Meng on 2015/04/07.
 */
public class Setting {
    static private final String SAVEDATA = "sg_setting.ini";
    private Context ctx;
    private File ini;
    private int primary;
    private boolean lastOpen;
    private boolean lockMode;
    private boolean _showFirstName;
    private boolean _showLastName;
    private boolean changed;

    public Setting(Context that) {
        this.ctx = that;
        this.ini = new File(that.getFilesDir(), SAVEDATA);
    }

    public boolean showFirstName() {
        return  this._showFirstName;
    }

    public boolean showLastName() {
        return this._showLastName;
    }

    public void showFirstName(boolean flag) {
        this._showFirstName = flag;
    }

    public void showLastName(boolean flag) {
        this._showLastName = flag;
    }

    public void lastOpen(boolean flag) {
        this.lastOpen = flag;
    }

    public void lockMode(boolean flag) {
        this.lockMode = flag;
    }

    public void primary(int that) {
        this.primary = that;
    }

    public void delete() {
        if( this.ini.exists() ) {
            this.ini.delete();
        }
    }

    public boolean load() {
        boolean exist = this.ini.exists();
        if(!exist) {
            this.changed = true;
            this.lastOpen = true;
            this.lockMode =false;
            this.showFirstName(true);
            this.showLastName(true);
        } else {
            FileInputStream in = null;
            BufferedInputStream buf = null;
            try {
                byte[] temp = new byte[1 + Integer.SIZE];
                byte mask = 0b1000000;
                in = new FileInputStream(this.ini);
                buf = new BufferedInputStream(in);
                buf.read(temp);
                this.lastOpen = ( (mask & temp[0]) == mask);
                mask = 0b100000;
                this.lockMode = ( (mask & temp[0]) == mask);
                mask = 0b10000;
                this.showFirstName ( (mask & temp[0]) == mask);
                mask = 0b1000;
                this.showLastName ( (mask & temp[0]) == mask);

                this.primary = 0;
                for(int i = 0; i < Integer.SIZE; i++ ) {
                    this.primary ^= (temp[Integer.SIZE - i] & 0xFF) << (i*8);
                }
            } catch (IOException e) {
                exist = false;
                this.changed = true;
                this.lastOpen = true;
                this.lockMode =false;
                this.showFirstName(true);
                this.showLastName(true);
            } finally {
                if (buf != null) {
                    try {
                        buf.close();
                    } catch (IOException e) {
                    }
                }
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                    }
                }
            }
        }
        return exist;
    }

    public void save() throws IOException {
        FileOutputStream out = null;
        BufferedOutputStream buf = null;
        byte mask = 0b1000000;
        byte flag = 0b0;
        if(this.lastOpen) {
            flag ^= mask;
        }
        mask = 0b100000;
        if(this.lockMode) {
            flag ^= mask;
        }
        mask = 0b10000;
        if(this.showFirstName()) {
            flag ^= mask;
        }
        mask = 0b1000;
        if(this.showLastName()) {
            flag ^= mask;
        }

        try {
            out = new FileOutputStream(this.ini);
            buf = new BufferedOutputStream(out);
            byte[] temp = new byte[1 + Integer.SIZE];
            byte[] hold = ByteBuffer.allocate(Integer.SIZE).putInt(this.primary).array();
            temp[0] = flag;
            for(int i = 0; i < Integer.SIZE; i ++ ) {
                temp[i + 1] = hold[i];
            }
            buf.write(temp);
        } catch (IOException e) {
            throw new IOException("Setting file cannot be saved.");
        } finally {
            if (buf != null) {
                try {
                    buf.close();
                } catch (IOException e) {
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                }
            }
        }
    }

}
