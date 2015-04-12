package com.mm90849491.sleepguard.Analyser;

import java.io.BufferedInputStream;

/**
 * @author Meng Meng
 *
 * 2015/02/28
 */
public class DataChunk {
    protected byte[] ckID;
    protected int ckSize;

    private boolean ckID(BufferedInputStream in) {
        //final byte FORMAT_CHUNCK_ID_LENGTH = 4;
        this.ckID = new byte[4];
        return MISC.read(this.ckID, in, true);
    }

    private int ckSize(BufferedInputStream in) {
        //final byte FORMAT_CHUNCK_SIZE_LENGTH = 4;
        this.ckSize = MISC.readDWord(in);
        return this.ckSize;
    }

    public void print() {
        System.out.printf("++ ckID           : %c%c%c%c\n", this.ckID[0], this.ckID[1], this.ckID[2], this.ckID[3] );
        System.out.printf("++ ckSize         : %s bytes | header: 36 bytes\n", this.ckSize );
        //System.out.printf("++ ckSize         : %s bytes | header: 36 bytes\n", MISC.bbbb2uint(this.ckSize) );
        //System.out.printf("++                : 0x( %02X %02X %02X %02X )\n", this.ckSize[0], this.ckSize[1], this.ckSize[2], this.ckSize[3] );
    }

    public DataChunk(BufferedInputStream in) {
        this.ckID(in);
        this.ckSize(in);
    }

}
