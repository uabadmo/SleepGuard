package com.mm90849491.sleepguard.Analyser;

import java.io.BufferedInputStream;
import java.io.EOFException;
import java.util.Hashtable;

/**
 * @author Meng Meng
 *
 * 2015/02/26
 */
public class FormatChunk {
    /* ------------ begin of instance variables ------------ */
    protected byte[] _waveID;
    protected byte[] _ckID;
    protected byte[] _ckSize;
    protected byte[] _wFormatTag;
    protected byte[] _nChannels;
    protected byte[] _nSamplesPerSec;
    protected byte[] _nAvgBytesPerSec;
    protected byte[] _nBlockAlign;
    protected byte[] _wBitsPerSample;
	/*
	protected byte[] _cbSize;
	protected byte[] _wValidBitsPerSample;
	protected byte[] _dwChannelMask;
	protected byte[] _SubFormat;
	*/
	/* ------------- end of instance variables ------------- */

	/* -------------- begin of setter methods -------------- */
    /**
     * Set _ckID by reading four bytes from a BufferedInputStream.
     * @param in BufferedInputStream: binary file in BigEndian.
     * @return boolean: false if file is corrupted.
     */
    private boolean waveID(BufferedInputStream in) {
        //final byte WAVE_ID_LENGTH = 4;
        this._waveID = new byte[4];
        return MISC.read(this._waveID, in, true);
    }

    private boolean ckID(BufferedInputStream in) {
        //final byte FORMAT_CHUNCK_ID_LENGTH = 4;
        this._ckID = new byte[4];
        return MISC.read(this._ckID, in, true);
    }

    private boolean ckSize(BufferedInputStream in) {
        //final byte FORMAT_CHUNCK_SIZE_LENGTH = 4;
        this._ckSize = new byte[4];
        return MISC.read(this._ckSize, in, false);
    }

    private boolean wFormatTag(BufferedInputStream in) {
        //final byte FORMAT_TAG_LENGTH = 2;
        this._wFormatTag = new byte[2];
        return MISC.read(this._wFormatTag, in, false);
    }

    private boolean nChannels(BufferedInputStream in) {
        //final byte CHANNEL_LENGTH = 2;
        this._nChannels = new byte[2];
        return MISC.read(this._nChannels, in, false);
    }

    private boolean nSamplesPerSec(BufferedInputStream in) {
        //final byte SAMPLE_RATE_LENGTH = 4;
        this._nSamplesPerSec = new byte[4];
        return MISC.read(this._nSamplesPerSec, in, false);
    }
    private boolean nAvgBytesPerSec(BufferedInputStream in) {
        //final byte AVERAGE_BYTE_RATE_LENGTH = 4;
        this._nAvgBytesPerSec = new byte[4];
        return MISC.read(this._nAvgBytesPerSec, in, false);
    }
    private boolean nBlockAlign(BufferedInputStream in) {
        //final byte BLOCK_ALIGN_LENGTH = 2;
        this._nBlockAlign = new byte[2];
        return MISC.read(this._nBlockAlign, in, false);
    }
    private boolean wBitsPerSample(BufferedInputStream in) {
        //final byte QUANTILISER_LENGTH = 2;
        this._wBitsPerSample = new byte[2];
        return MISC.read(this._wBitsPerSample, in, false);
    }

	/*
	private boolean cbSize(BufferedInputStream in) {
		//final byte EXTENSION_SIZE_LENGTH = 2;
		this._cbSize = new byte[2];
		return MISC.read(this._cbSize, in, false);
	}
	private boolean wValidBitsPerSample(BufferedInputStream in) {
		//final byte REAL_BITS_RATE_LENGTH = 2;
		this._wValidBitsPerSample = new byte[2];
		return MISC.read(this._wValidBitsPerSample, in, false);
	}
	private boolean dwChannelMask(BufferedInputStream in) {
		//final byte SPEAKER_POSITION_MASK_LENGTH = 4;
		this._dwChannelMask = new byte[4];
		return MISC.read(this._dwChannelMask, in, false);
	}
	private boolean SubFormat(BufferedInputStream in) {
		//final byte GUID_LENGTH = 16;
		this._SubFormat = new byte[16];
		return MISC.read(this._SubFormat, in, false);
	}
	*/
	/* --------------- end of setter methods --------------- */

	/* -------------- begin of getter methods -------------- */
    /**
     * Get _waveID.
     * @return byte[].
     */
    public byte[] waveID() {
        return this._waveID;
    }

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

    /**
     * Get _wFormatTag.
     * @return unsigned integer.
     */
    public int wFormatTag() {
        return MISC.bb2uint(this._wFormatTag);
    }

    /**
     * Get _nChannels.
     * @return unsigned integer.
     */
    public int nChannels() {
        return MISC.bb2uint(this._nChannels);
    }

    /**
     * Get _nSamplesPerSec.
     * @return unsigned integer.
     */
    public int nSamplesPerSec() {
        return MISC.bbbb2uint(this._nSamplesPerSec);
    }

    /**
     * Get _nAvgBytesPerSec.
     * @return unsigned integer.
     */
    public int nAvgBytesPerSec() {
        return MISC.bbbb2uint(this._nAvgBytesPerSec);
    }

    /**
     * Get _nBlockAlign.
     * @return unsigned integer.
     */
    public int nBlockAlign() {
        return MISC.bb2uint(this._nBlockAlign);
    }

    /**
     * Get _wBitsPerSample.
     * @return unsigned integer.
     */
    public int wBitsPerSample() {
        return MISC.bb2uint(this._wBitsPerSample);
    }
	/* --------------- end of setter methods --------------- */

	/* -------------- begin of public methods -----=-=------ */
    /**
     * Print raw info to terminal.
     */
    public void print() {
        Hashtable<Integer, String> FORMATS = new Hashtable<Integer, String>();
        FORMATS.put(0x0001, "WAVE_FORMAT_PCM");
        FORMATS.put(0x0003, "WAVE_FORMAT_IEEE_FLOAT");
        FORMATS.put(0x0006, "WAVE_FORMAT_ALAW");
        FORMATS.put(0x0007, "WAVE_FORMAT_MULAW");
        FORMATS.put(0x0101, "IBM_FORMAT_MULAW");
        FORMATS.put(0x0102, "IBM_FORMAT_ALAW");
        FORMATS.put(0x0103, "IBM_FORMAT_ADPCM");
        FORMATS.put(0xFFFE, "WAVE_FORMAT_EXTENSIBLE");

        System.out.printf("+  waveID         : %c%c%c%c\n", this._waveID[0], this._waveID[1], this._waveID[2], this._waveID[3] );
        System.out.printf("+  ckID           : %c%c%c%c\n", this._ckID[0], this._ckID[1], this._ckID[2], this._ckID[3] );
        System.out.printf("+  ckSize         : %s bytes | one of 16, 18, 40\n", this.ckSize() );
        System.out.printf("+                 : 0x( %02X %02X %02X %02X )\n", this._ckSize[0], this._ckSize[1], this._ckSize[2], this._ckSize[3] );
        System.out.printf("+  Format         : %s\n", FORMATS.get(this.wFormatTag()) );
        System.out.printf("+                 : 0x( %02X %02X )\n", this._wFormatTag[0], this._wFormatTag[1] );
        System.out.printf("+  Channel        : %d\n", this.nChannels() );
        System.out.printf("+                 : 0x( %02X %02X )\n", this._nChannels[0], this._nChannels[1] );
        System.out.printf("+  Sampling Rate  : %d Hz\n", this.nSamplesPerSec() );
        System.out.printf("+                 : 0x( %02X %02X %02X %02X )\n", this._nSamplesPerSec[0], this._nSamplesPerSec[1], this._nSamplesPerSec[2], this._nSamplesPerSec[3] );
        System.out.printf("+  Avg. Bit Rate  : %d Bps =%.3f Kbps ~%.3f kbps\n", this.nAvgBytesPerSec(), this.nAvgBytesPerSec() * 8.0 / 1000, this.nAvgBytesPerSec() * 8.0 / 1024 );
        System.out.printf("+                 : 0x( %02X %02X %02X %02X )\n", this._nAvgBytesPerSec[0], this._nAvgBytesPerSec[1], this._nAvgBytesPerSec[2], this._nAvgBytesPerSec[3] );
        System.out.printf("+  Block Size     : %d\n", this.nBlockAlign() );
        System.out.printf("+                 : 0x( %02X %02X )\n", this._nBlockAlign[0], this._nBlockAlign[1] );
        System.out.printf("+  Bit per Sample : %d\n", this.wBitsPerSample() );
        System.out.printf("+                 : 0x( %02X %02X )\n", this._wBitsPerSample[0], this._wBitsPerSample[1] );

    }

    /**
     * Print audio info to terminal.
     */
    public void info(int size) {
        double biteRate = this.nChannels() * this.nSamplesPerSec() * this.wBitsPerSample();
        int duration = (int)((size - this.ckSize() - 4) / biteRate * 8);
        Hashtable<Integer, String> FORMATS = new Hashtable<Integer, String>();
        FORMATS.put(0x0001, "WAVE_FORMAT_PCM");
        FORMATS.put(0x0003, "WAVE_FORMAT_IEEE_FLOAT");
        FORMATS.put(0x0006, "WAVE_FORMAT_ALAW");
        FORMATS.put(0x0007, "WAVE_FORMAT_MULAW");
        FORMATS.put(0x0101, "IBM_FORMAT_MULAW");
        FORMATS.put(0x0102, "IBM_FORMAT_ALAW");
        FORMATS.put(0x0103, "IBM_FORMAT_ADPCM");
        FORMATS.put(0xFFFE, "WAVE_FORMAT_EXTENSIBLE");

        HorizontalLine hr = new HorizontalLine();
        hr.guessLength("+  Avg. Bit Rate  : 22050 Bps =176.400 Kbps ~172.266 kbps  ");
        hr.drawln();
        hr.println("info");
        System.out.printf("== File format\t : %s \t\t==\n", new String(this.waveID()));
        System.out.printf("== Codec format\t : %s \t==\n", FORMATS.get(this.wFormatTag()));
        System.out.printf("== File Size\t : %d bytes\t==\n", size + 4 + 4);
        System.out.printf("== Data Size\t : %d bytes\t==\n", size - this.ckSize() - 4);
        System.out.printf("== Avg. Bit Rate : %.3f Kbps\t==\n", this.nAvgBytesPerSec() * 8.0 / 1000);
        System.out.printf("== Real Bit Rate : %.3f Kbps\t==\n", biteRate / 1000.0);
        System.out.printf("== Sample Block Ratio\t : %d:%d \t==\n", this.wBitsPerSample()*this.nChannels(), this.nBlockAlign() * 8 );
        System.out.printf("== Duration\t : %dh %dm %ds \t\t==\n", duration/3600, (duration%3600)/60, duration%60 );
        hr.println("E N D");
        hr.drawln();

    }
	/* ---------------- end of public methods -------------- */

	/* --------------- begin of constructors --------------- */
    /**
     * Construct and initialise a MasterChunk.java object.
     * @param in BufferedInputStream: binary file in BigEndian.
     * @throws EOFException
     */
    public FormatChunk(BufferedInputStream in) throws EOFException {
        //if return; throw corrupted file
        if(	this.waveID(in) ) {
            throw new EOFException("Reached the end of the file while getting Wave File Identifier.");
        }
        if(	this.ckID(in) ) {
            throw new EOFException("Reached the end of the file while getting Format Chunk's ID.");
        }
        if( this.ckSize(in) ) {
            throw new EOFException("Reached the end of the file while getting Format Chunk's size.");
        }
        if( this.wFormatTag(in) ) {
            throw new EOFException("Reached the end of the file while getting Format Tag Code.");
        }
        if( this.nChannels(in) ) {
            throw new EOFException("Reached the end of the file while getting Channel Number.");
        }
        if( this.nSamplesPerSec(in) ) {
            throw new EOFException("Reached the end of the file while getting Sample Rate.");
        }
        if( this.nAvgBytesPerSec(in) ) {
            throw new EOFException("Reached the end of the file while getting Bit Rate.");
        }
        if( this.nBlockAlign(in) ) {
            throw new EOFException("Reached the end of the file while getting Block Size.");
        }
        if( this.wBitsPerSample(in)	) {
            throw new EOFException("Reached the end of the file while getting Sample Size.");
        }
		/*
		this.cbSize(in);
		if(this.cbSize[0] != 0) {
			this.wValidBitsPerSample(in);
			this.dwChannelMask(in);
			this.SubFormat(in);
		}
		*/
    }
	/* ---------------- end of constructors ---------------- */
}

