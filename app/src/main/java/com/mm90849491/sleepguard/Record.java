package com.mm90849491.sleepguard;

import java.io.File;

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
    private Result _recordResult;

    public Record(File DIRECTORY, String NAME) {
        this.targetDirectory(DIRECTORY);
        this.targetName(NAME);
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

    private void generateResult() {
        _recordResult.run();
    }

}
