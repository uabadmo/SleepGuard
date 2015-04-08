package com.mm90849491.sleepguard;

import java.io.File;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.media.AudioFormat;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import android.os.Environment;
import android.util.Log;

/** Record class.
 *      Stores the information about the recorded file
 *  @version 0.4.0
 *  @author M.Harrison
 *  @see com.mm90849491.sleepguard.Record
 */

public class Record {
    /* ------------ beginning of constant variables ------------ */
    static private int[] frequencyAvailable = {0, 8000, 32000, 44100, 48000};
    static private int frUsed = frequencyAvailable[2];
    /* ------------------ end of constant variables ------------ */

    /* ------------ beginning of instance variables ------------ */
    private AudioRecord re = null;
    private boolean isRecording;
    private Thread thr = null;
    private FileOutputStream out = null;
    private int bufferSize;
    private String _targetPath;
    private String _targetFilename;
    private File _targetFile;
    /* ------------------ end of instance variables ------------ */

    /* ------------ beginning of getter/setter methods --------- */
    /** Gets path to WAV file: _targetPath.
     * @return String
     */
    public String targetPath() { return _targetPath; }

    /** Sets path to WAV file: _targetPath.
     * @param input String
     */
    public void targetPath(String input) { this._targetPath = input; }

    /** Gets filename of WAV file: _targetFilename.
     * @return String
     */
    public String targetFilename() { return _targetFilename; }

    /** Sets path to WAV file: _targetFilename.
     * @param input String
     */
    public void targetFilename(String input) { this._targetFilename = input; }

    /** Gets main file: _targetFile.
     * @return java.io.File
     */
    public File targetFile() { return _targetFile; }

    /** Sets main file: _targetFile.
     * @param input java.io.File
     */
    public void targetFile(File input) { this._targetFile = input; }
    /* -------------------end of getter/setter methods --------- */

    /* ------------ beginning of constructors ------------------ */
    /** Constructs the Record instance.
     */
    public Record() {
        bufferSize = AudioRecord.getMinBufferSize(
                frUsed,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT);

        re = new AudioRecord(
                MediaRecorder.AudioSource.MIC,
                frUsed,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT,
                bufferSize);
    }
    /* ------------------ end of constructors ------------------ */

    /* ------------ beginning of public methods ---------------- */
    /** Starts the recording process.
     */
    public void startRe() {
        re.startRecording();
        isRecording = true;

        thr = new Thread(new Runnable() {
            public void run() {
                writeAudioDataToFile();
            }
        }, "SleepGuardThread");
        thr.start();
    }

    /** Stops the recording process.
     */
    public void stopRe() {
        isRecording = false;
        re.stop();
        re.release();
        re = null;
        thr = null;
    }
    /* ------------------ end of public methods ---------------- */

    /* ------------ beginning of private methods --------------- */
    /** Translates an array of Shorts to an array of Bytes.
     * Found at http://stackoverflow.com/questions/8499042/android-audiorecord-example; Unchanged.
     * @param sData Array of Shorts.
     * @return Array of Bytes.
     */
    private byte[] short2byte(short[] sData) {
        int shortArraySize = sData.length;
        byte[] bytes = new byte[shortArraySize * 2];
        for (int i = 0; i < shortArraySize; i++) {
            bytes[i * 2] = (byte) (sData[i] & 0x00FF);
            bytes[(i * 2) + 1] = (byte) (sData[i] >> 8);
            sData[i] = 0;
        }
        return bytes;
    }

    /** Writes the audio information to a WAV file (including the header).
     * Found at (same URL as above); changed extensively.
     */
    private void writeAudioDataToFile() {
        targetPath(Environment.getExternalStorageDirectory().getPath());
        targetFilename("/sleep.wav");
        short sData[] = new short[bufferSize];

        try {
            targetFile(new File(targetPath(), targetFilename()));
            out = new FileOutputStream(targetFile());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            byte[] header = new byte[44];
            int byteRate = frUsed * 16 * 1 / 8;

            header[0] = 'R';
            header[1] = 'I';
            header[2] = 'F';
            header[3] = 'F';
            header[4] = 0; // still needs to be calculated after
            header[5] = 0;
            header[6] = 0;
            header[7] = 0;
            header[8] = 'W';
            header[9] = 'A';
            header[10] = 'V';
            header[11] = 'E';
            header[12] = 'f';
            header[13] = 'm';
            header[14] = 't';
            header[15] = ' ';
            header[16] = 16;
            header[17] = 0;
            header[18] = 0;
            header[19] = 0;
            header[20] = 1;
            header[21] = 0;
            header[22] = (byte) AudioFormat.CHANNEL_IN_MONO;
            header[23] = 0;
            header[24] = (byte) (frUsed & 0xff);
            header[25] = (byte) ((frUsed >> 8) & 0xff);
            header[26] = (byte) ((frUsed >> 16) & 0xff);
            header[27] = (byte) ((frUsed >> 24) & 0xff);
            header[28] = (byte) (byteRate & 0xff);
            header[29] = (byte) ((byteRate >> 8) & 0xff);
            header[30] = (byte) ((byteRate >> 16) & 0xff);
            header[31] = (byte) ((byteRate >> 24) & 0xff);
            header[32] = (byte) (AudioFormat.CHANNEL_IN_MONO * AudioFormat.ENCODING_PCM_16BIT / 8);  // block align
            header[33] = 0;
            header[34] = 2;
            header[35] = 0;
            header[36] = 'd';
            header[37] = 'a';
            header[38] = 't';
            header[39] = 'a';
            header[40] = 0; // still needs to be calculated
            header[41] = 0;
            header[42] = 0;
            header[43] = 0;
            out.write(header, 0, 44);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (isRecording) {
            re.read(sData, 0, bufferSize);
            try {
                byte bData[] = short2byte(sData);
                out.write(bData, 0, bufferSize);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /* ------------------ end of private methods --------------- */


    /*
     * Meng, these are the variables you wrote -- I won't delete them until later when we sure
     * that we won't need them or whatever.
     */
    /*
    private File _targetDirectory;
    private String _targetName;
    private String _targetExtension;
    /* char == unsigned int /
    private char _bitRate = 32; // ??
    /* Default(device recording setting) 32000 44100 48000 /
    private byte _frequencyLevel = 0;
    private Result _result;

    /**
     *
     * @return File _targetDirectory
     /
    public File targetDirectory() {
        return _targetDirectory;
    }

    /**
     *
     * @param :File _targetDirectory
     /
    public void targetDirectory(File _targetDirectory) {
        this._targetDirectory = _targetDirectory;
    }

    public String targetName() {
        return _targetName;
    }

    public void targetName(String _targetName) {
        this._targetName = _targetName;
    }

    public String targetExtension() {
        return _targetExtension;
    }

    public void targetExtension(String _targetExtension) {
        this._targetExtension = _targetExtension;
    }

    public char bitRate() {
        return _bitRate;
    }
    public void bitRate(char _bitRate) {
        this._bitRate = _bitRate;
    }

    public byte frequencyLevel() {
        return _frequencyLevel;
    }
    public void frequencyLevel(byte _frequencyLevel) {
        this._frequencyLevel = _frequencyLevel;
    }

    /**
     *
     * @return int sampling frequency
     /
    public int frequency() {
        return Record.frequencyAvailable[this.frequencyLevel()];
    }
    */
}
