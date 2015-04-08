package com.mm90849491.sleepguard;

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
 * Created by Kenelf on 2015/04/07.
 */
public class Setting {
    static private final String SAVEDATA = "sg_setting.ini";
    private Context ctx;
    private File ini;
    private int primary;
    private boolean lastOpen;
    private boolean lockMode;
    private boolean changed;

    public Setting(Context that) {
        this.ctx = that;
        this.ini = new File(that.getFilesDir(), SAVEDATA);
    }

    protected boolean load() {
        boolean exist = this.ini.exists();
        if(!exist) {
            this.changed = true;
            this.lastOpen = true;
            this.lockMode =false;
        } else {
            FileInputStream in = null;
            BufferedInputStream buf = null;
            try {
                byte[] temp = new byte[1 + Integer.SIZE];
                byte mask = 0b1000000;
                in = new FileInputStream(this.ini);
                buf = new BufferedInputStream(in);
                buf.read();
                this.lastOpen = ( (mask & temp[0]) == mask);
                mask = 0b10000;
                this.lockMode = ( (mask & temp[0]) == mask);

                this.primary = 0;
                for(int i = 0; i < Integer.SIZE; i++ ) {
                    this.primary ^= (temp[i + 1] & 0xFF) << (i*8);
                }
            } catch (IOException e) {
                exist = false;
                this.changed = true;
                this.lastOpen = true;
                this.lockMode =false;
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

    protected void save() throws IOException {
        FileOutputStream out = null;
        BufferedOutputStream buf = null;
        byte mask = 0b1000000;
        byte flag = 0b0;
        if(this.changed) {
            flag ^= mask;
        }
        mask = 0b100000;
        if(this.lockMode) {
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
