package com.mm90849491.sleepguard.Analyser;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author Meng Meng
 *
 * 2015/02/28
 */
public class Analyser {
    /* error = DOWN_SAMPLE_SCALE / SAMPLE_FREQUENCY second */
    public static final int DEF_DOWN_SAMPLE_SCALE = 100;
    public static final int DEF_DURATION = 3600;
    public static final int DEF_START = 300;
    public static final boolean LENGTH_CHECK = false;

    public static final String[] HAZARD_CLASS = {"File cannot be Open",
                                                      "Not Supported File Format",
                                                      "Bad Recording",
                                                      "No Apnoea is detected",
                                                      "Apnoea detected"};

    public static int generate(File audioFile) {
        int hazardLevel = 0;
        int peak = (1 << 14);
        if(audioFile.exists() && audioFile.isFile() && audioFile.canRead()) {

            FileInputStream in = null;
            BufferedInputStream buf = null;
            try {
                in = new FileInputStream(audioFile);
                buf = new BufferedInputStream(in);

                MasterChunk mc = new MasterChunk(buf);
                FormatChunk fc = new FormatChunk(buf);
                if( isWave(mc, fc) ) {
                    double biteRate = fc.nChannels() * fc.nSamplesPerSec() * fc.wBitsPerSample();
                    int duration = (int)((mc.ckSize() - fc.ckSize() - 4) / biteRate * 8);

                    if(!LENGTH_CHECK || duration > DEF_START) {
                        DataChunk dc = new DataChunk(buf);
                        peak = peak(buf, duration, fc);
                        buf.close();
                        in.close();

                        in = new FileInputStream(audioFile);
                        buf = new BufferedInputStream(in);
                        hazardLevel = diagnose(buf, duration, peak);
                    } else {
                        hazardLevel = 2;
                    }
                } else {
                    hazardLevel = 1;
                }
                buf.close();
                in.close();
            } catch (IOException e) {
                hazardLevel = 1;
            }
        }
        return hazardLevel;
    }

    private static boolean isWave(MasterChunk mc, FormatChunk fc) throws IOException {
        if(!( new String( mc.ckID() ) ).equals("RIFF")) {
            return false;
        }
        if(! ( new String( fc.waveID() ) ).equals("WAVE") || fc.wFormatTag() != 1 ) {
            return false;
        }
        return true;
    }

    private static int peak(BufferedInputStream buf, int duration, FormatChunk fc) {
        int max = 0;
        int min = 0;
        int cur = 0;
        int sec, sam, cha;
        int sampRate = fc.nSamplesPerSec();
        int channels = fc.nChannels();
        if(duration > DEF_DURATION + DEF_START) {
            duration = DEF_DURATION;
            for(sec = 0; sec < DEF_START; sec++) {
                for(sam = 0; sam < sampRate; sam++) {
                    for (cha = 0; cha < channels; cha++) {
                        MISC.readWord(buf);
                    }
                }
            }
        }
        for(sec = 0; sec < duration; sec++) {
            for(sam = 0; sam < sampRate; sam++) {
                for (cha = 0; cha < channels; cha++) {
                    cur = MISC.readWord(buf);
                    if(cur > max) {
                        max = cur;
                    } else {
                        if(cur < min) {
                            min = cur;
                        }
                    }
                }
            }
        }
        return (max + min < 0) ? -min : max;
    }

    private static int diagnose(BufferedInputStream buf, int duration, int peak) throws IOException {
        MasterChunk mc = new MasterChunk(buf);
        FormatChunk fc = new FormatChunk(buf);
        DataChunk dc = new DataChunk(buf);

        int threshold1 = peak/100;
        int threshold2 = peak/20;
        int threshold3 = peak/4;

        if(threshold3 - threshold2 < 100) {
            return 3;
        }
        if(threshold3 - threshold1 < 1000 && duration < DEF_START) {
            return 2;
        }

        int cur = 0;
        int sec, sam, cha;
        int sampRate = fc.nSamplesPerSec();
        int channels = fc.nChannels();
        if(duration > DEF_DURATION + DEF_START) {
            duration = DEF_DURATION;
            for(sec = 0; sec < DEF_START; sec++) {
                for(sam = 0; sam < sampRate; sam++) {
                    for (cha = 0; cha < channels; cha++) {
                        MISC.readWord(buf);
                    }
                }
            }
        }
        sampRate = sampRate / DEF_DOWN_SAMPLE_SCALE;
        int cluster = DEF_DOWN_SAMPLE_SCALE * channels;
        int trueValue;
        boolean hill = false;
        boolean snoring = false;
        int pause = 0;
        int breath = 0;
        int snore = 0;
        int pauseCount = 0;
        int snoreCount = 0;
        int pauseDuration = 0;

        int hc = 0;
        int record = 0;
        int sum = 0;

        for(sec = 0; sec < duration; sec++) {
            pause = 0;
            snore = 0;
            breath = 0;
            for(sam = 0; sam < sampRate; sam++) {
                trueValue = 0;
                for (cha = 0; cha < cluster; cha++) {
                    trueValue += MISC.readWord(buf) / cluster;
                }
                sum += trueValue;
                trueValue = Math.abs(trueValue);
                if(trueValue < threshold1) {
                    pause++;
                } else if(trueValue > threshold2) {
                    snore++;
                } else  {
                    breath++;
                }
            }
            /* fuzzy algorithm */
            if(hill) {
                if(pause > breath + snore) {
                    hc++;
                    hill = false;
                    snoring = false;
                } else if(!snoring && snore > breath) {
                    snoring = true;
                    snoreCount++;
                }
            } else {
                if(pause > breath) {
                    pauseDuration++;
                    if(pauseDuration > record) record = pauseDuration;
                } else {
                    if(pauseDuration > 10) pauseCount++;
                    hill = true;
                    pauseDuration = 0;
                }
            }
        }

        if(Math.abs(sum)/2 > threshold2) {
            if(pauseCount >= 20 || record >= 10) {
                return 4;
            }
        }
        return 3;
    }

}
