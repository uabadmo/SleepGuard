package com.mm90849491.sleepguard.Analyser;

import java.io.BufferedInputStream;
import java.io.EOFException;

/**
 * Container of a binary file's master chunk which has
 *  a ckID of double word, and a skSize of double word.
 * @author Meng Meng
 *
 * 2015/02/26
 */
final public class MasterChunk {
    /* ------------ begin of instance variables ------------ */
    private byte[] _ckID;
    private byte[] _ckSize;
	/* ------------- end of instance variables ------------- */

	/* -------------- begin of setter methods -------------- */
    /**
     * Set _ckID by reading four bytes from a BufferedInputStream.
     * @param in BufferedInputStream: binary file in BigEndian.
     * @return boolean: false if file is corrupted.
     */
    private boolean ckID(BufferedInputStream in) {
        //final byte FORMAT_CHUNCK_ID_LENGTH = 4;
        this._ckID = new byte[4];
        return MISC.read(this._ckID, in, true);
    }

    /**
     * Set _ckSize by reading four bytes from a BufferedInputStream.
     * @param in BufferedInputStream: binary file in BigEndian.
     * @return boolean: false if file is corrupted.
     */
    private boolean ckSize(BufferedInputStream in) {
        //final byte FORMAT_CHUNCK_SIZE_LENGTH = 4;
        this._ckSize = new byte[4];
        return MISC.read(this._ckSize, in, false);
    }
	/* --------------- end of setter methods --------------- */

	/* -------------- begin of getter methods -------------- */
    /**
     * Get _ckID.
     * @return byte[].
     */
    public byte[] ckID() {
        return this._ckID;
    }

    /**
     * Get _ckSize.
     * @return unsigned integer.
     */
    public int ckSize() {
        return MISC.bbbb2uint(this._ckSize);
    }
	/* --------------- end of setter methods --------------- */

	/* -------------- begin of public methods -----=-=------ */
    /**
     * Print info to terminal.
     */
    public void print() {
        System.out.printf("   ckID           : %c%c%c%c\n", this._ckID[0], this._ckID[1], this._ckID[2], this._ckID[3] );
        System.out.printf("   ckSize         : %d bytes | file size - 4 (ckID) - 4 (ckSize) - padding\n", this.ckSize() );
        System.out.printf("                  : 0x( %02X %02X %02X %02X )\n", this._ckSize[0], this._ckSize[1], this._ckSize[2], this._ckSize[3] );
    }
	/* ---------------- end of public methods -------------- */

	/* --------------- begin of constructors --------------- */
    /**
     * Construct and initialise a MasterChunk.java object.
     * @param in BufferedInputStream: binary file in BigEndian.
     * @throws EOFException
     */
    public MasterChunk(BufferedInputStream in) throws EOFException {
        if(	this.ckID(in) ) {
            throw new EOFException("Reached the end of the file while getting Master Chunk's ID.");
        }
        if( this.ckSize(in)	) {
            throw new EOFException("Reached the end of the file while getting Master Chunk's size.");
        }
    }
	/* ---------------- end of constructors ---------------- */

}

