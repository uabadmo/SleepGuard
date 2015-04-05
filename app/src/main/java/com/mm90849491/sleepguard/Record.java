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
    private String _targetPath;
    private String _targetFilename;
    private File _targetFile;

    public String targetPath() { return _targetPath; }
    public void targetPath(String input) { this._targetPath = input; }
    public String targetFilename() { return _targetFilename; }
    public void targetFilename(String input) { this._targetFilename = input; }
    public File targetFile() { return _targetFile; }
    public void targetFile(File input) { this._targetFile = input; }

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
        targetPath(Environment.getExternalStorageDirectory().getPath());
        targetFilename("/sleep.wav"); // I used PCM because it's a pre-stripped Wav file, I could make it a wav file too
        short sData[] = new short[bufferSize];

        try {
            targetFile(new File(targetPath(), targetFilename()));
            out = new FileOutputStream(targetFile());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // header
        try {
            byte[] header = new byte[44];
            int byteRate = frUsed * 16 * 1 / 8;

            int duration = 0; // should be calculated from duration of the scheduling
            int totalAudioLength = frUsed * duration / 1000;

            header[0] = 'R';  // RIFF/WAVE header
            header[1] = 'I';
            header[2] = 'F';
            header[3] = 'F';
            header[4] = (byte) ((totalAudioLength+36) & 0xff);
            header[5] = (byte) (((totalAudioLength+36) >> 8) & 0xff);
            header[6] = (byte) (((totalAudioLength+36) >> 16) & 0xff);
            header[7] = (byte) (((totalAudioLength+36) >> 24)& 0xff);
            header[8] = 'W';
            header[9] = 'A';
            header[10] = 'V';
            header[11] = 'E';
            header[12] = 'f';  // 'fmt ' chunk
            header[13] = 'm';
            header[14] = 't';
            header[15] = ' ';
            header[16] = 16;  // 4 bytes: size of 'fmt ' chunk
            header[17] = 0;
            header[18] = 0;
            header[19] = 0;
            header[20] = 1;  // format = 1
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
            header[34] = 2;  // bits per sample (channels * bitspersample / 8)
            header[35] = 0;
            header[36] = 'd';
            header[37] = 'a';
            header[38] = 't';
            header[39] = 'a';
            header[40] = (byte) (totalAudioLength & 0xff);
            header[41] = (byte) ((totalAudioLength >> 8) & 0xff);
            header[42] = (byte) ((totalAudioLength >> 16) & 0xff);
            header[43] = (byte) ((totalAudioLength >> 24) & 0xff);

            out.write(header, 0, 44);
        } catch (IOException e) {
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
