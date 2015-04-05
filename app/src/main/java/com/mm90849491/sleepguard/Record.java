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

/**
 * store the information about the recorded file
 * shared by UserInterface, InputOutput, and DataAnalysing
 */
public class Record {

    private AudioRecord re = null;
    private boolean isRecording; // in my example program I used 2 rather than 1
    private Thread thr = null;
    private FileOutputStream out = null;
    private int bufferSize;

    // added 8000 - in my readings supposedly that's what the computer uses
    static private int[] frequencyAvailable = {0, 8000, 32000, 44100, 48000};
    static private int frUsed = frequencyAvailable[2];

    private File _targetDirectory;
    private String _targetName;
    private String _targetExtension;
    /* char == unsigned int */
    private char _bitRate = 32; // ??
    /* Default(device recording setting) 32000 44100 48000 */
    private byte _frequencyLevel = 0;
    private Result _result;

    public Record(/*File DIRECTORY, String NAME*/) {
        //this.targetDirectory(DIRECTORY);
        //this.targetName(NAME);
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

    public void stopRe() {
        isRecording = false;
        re.stop();
        re.release();
        re = null;
        thr = null;
    }

    // found at http://stackoverflow.com/questions/8499042/android-audiorecord-example
    // unchanged
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

    // found at http://stackoverflow.com/questions/8499042/android-audiorecord-example (same as above)
    // changed somewhat
    private void writeAudioDataToFile() {
        String filepath = Environment.getExternalStorageDirectory().getPath();
        String filename = "/sleep.pcm"; // I used PCM because it's a pre-stripped Wav file, I could make it a wav file too
        short sData[] = new short[bufferSize];

        try {
            File file = new File(filepath, filename);
            out = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while (isRecording) {
            // gets the voice output from microphone to byte format

            re.read(sData, 0, bufferSize);
            try {
                // // writes the data to file from buffer
                // // stores the voice buffer
                byte bData[] = short2byte(sData);
                out.write(bData, 0, bufferSize); // rather than writing to a file here, maybe it could be analyzed here on the fly (if that's more difficult, that's fine
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

    /**
     *
     * @return File _targetDirectory
     */
    public File targetDirectory() {
        return _targetDirectory;
    }

    /**
     *
     * @param :File _targetDirectory
     */
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
     */
    public int frequency() {
        return Record.frequencyAvailable[this.frequencyLevel()];
    }
}
