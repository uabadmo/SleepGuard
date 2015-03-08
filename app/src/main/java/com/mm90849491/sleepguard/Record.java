package com.mm90849491.sleepguard;

import java.io.File;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.media.AudioFormat;

/**
 * store the information about the recorded file
 * shared by UserInterface, InputOutput, and DataAnalysing
 */
public class Record {

    static private int[] frequencyAvailable = {0, 32000, 44100, 48000};

    private File _targetDirectory;
    private String _targetName;
    private String _targetExtension;
    /* char == unsigned int */
    private char _bitRate = 32;
    /* Default(device recording setting) 32000 44100 48000 */
    private byte _frequencyLevel = 0;
    private Result _result;

    private void recordNew() {
        int bufferSize = AudioRecord.getMinBufferSize(frequencyAvailable[2], AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);
        AudioRecord recorder = new AudioRecord(MediaRecorder.AudioSource.MIC, frequencyAvailable[2], AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT, bufferSize);
        recorder.startRecording();
        // read loop for analysis
            // analyze -> return result
        recorder.stop();
        recorder.release();

        // exception handling to be written
    }

    public Record(File DIRECTORY, String NAME) {
        this.targetDirectory(DIRECTORY);
        this.targetName(NAME);
        this.recordNew();
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
