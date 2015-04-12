package com.mm90849491.sleepguard.Objects;

/* To Alex:
 * The schedule class is close, but it's still buggy.
 * I wanted to get this up for you so you can get the GUI
 * working with it.  The Schedule(int, int, int, int) constructor
 * needs to be called with the information where the four ints are
 * as follows:
 *      int hourIn              - the hour of the time to schedule the recording
 *      int minuteIn            - the minute of the time to schedule the recording
 *      int durationHoursIn     - the hour of how long the recording will take
 *      int durationMinutesIn   - the minute of how long the recording will take
 * If you prefer different inputs/outputs, just let me know and I'll
 * change it to however you'd like.
 *
 * Cheers,
 * Matt
 */

import android.content.Context;
import android.os.Handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Calendar;

import android.util.Log;

/** Schedule class.
 *      Schedules the instance of the Record class.
 *  @version 0.4.0
 *  @author M.Harrison
 *  @see Schedule
 */
public class Schedule implements Serializable {
    private static final long serialVersionUID = 1L;

    /* ------------ beginning of constant variables ------------ */
    static private int HOURS_PER_DAY = 24;
    static private int MINUTES_PER_HOUR = 60;
    static private int SECONDS_PER_MINUTE = 60;
    static private int MILLIS_PER_SECOND = 1000;
    static private int DECI_S2H = 10 * 60 * 60;
    static private String SERIAL = "(UTC%c%02d:%02d)%04d-%02d-%02d %02d:%02d@%02d.%03d";
    /* ------------------ end of constant variables ------------ */

    /* ------------ beginning of instance variables ------------ */
    private final Handler handler = new Handler();
    private Timer timer = null;
    private TimerTask timerTask = null;
    private Record recorder = null;
    private boolean isRecording;
    private int _hour;
    private int _minute;
    private int _currentHour;
    private int _currentMinute;
    private int _durationHours;
    private int _durationMinutes;
    private int _timeUntilStart;
    private int _recordingDuration;
    /* ------------------ end of instance variables ------------ */

    /* ------------ beginning of getter/setter methods --------- */
    /** Gets hour to start recording in 24-hour-clock: _hour.
     * @return int
     */
    public int hour() { return _hour; }

    /** Sets hour to start recording in 24-hour-clock: _hour.
     * @param input int
     */
    public void hour(int input) { this._hour = input; }

    /** Gets minute to start recording: _minute.
     * @return int
     */
    public int minute() { return _minute; }

    /** Sets minute to start recording: _minute.
     * @param input int
     */
    public void minute(int input) { this._minute = input; }

    /** Gets current time's hour: _currentHour.
     * @return int
     */
    public int currentHour() { return _currentHour; }

    /** Sets current time's hour: _currentHour.
     * @param input int
     */
    public void currentHour(int input) { this._currentHour = input; }

    /** Gets current time's minute: _currentMinute.
     * @return int
     */
    public int currentMinute() { return _currentMinute; }

    /** Sets current time's minute: _currentMinute.
     * @param input int
     */
    public void currentMinute(int input) { this._currentMinute = input; }

    /** Gets amount of hours of the recording: _durationHours.
     * @return int
     */
    public int durationHours() { return _durationHours; }

    /** Sets amount of hours of the recording: _durationHours.
     * @param input int
     */
    public void durationHours(int input) { this._durationHours = input; }

    /** Gets remainder of minutes of the recording: _durationMinutes.
     * @return int
     */
    public int durationMinutes() { return _durationMinutes; }

    /** Sets remainder of minutes of the recording: _durationMinutes.
     * @param input int
     */
    public void durationMinutes(int input) { this._durationMinutes = input; }

    /** Gets time until the recording starts in milliseconds: _timeUntilStart.
     * @return int
     */
    public int timeUntilStart() { return _timeUntilStart; }

    /** Sets time until the recording starts in milliseconds: _timeUntilStart.
     * @param input int
     */
    public void timeUntilStart(int input) { this._timeUntilStart = input; }

    /** Gets the duration of the recording in milliseconds: _recordingDuration.
     * @return int
     */
    public int recordingDuration() { return _recordingDuration; }

    /** Sets the duration of the recording in milliseconds: _recordingDuration.
     * @param input int
     */
    public void recordingDuration(int input) { this._recordingDuration = input; }
    /* -------------------end of getter/setter methods --------- */

    /* ------------ beginning of public methods ---------------- */

    /** Checks that the input hour and minute are in the 24-hour clock.
     * @return true if both the hour and minute are valid.
     */
    public boolean areValidTimes() {
        return true;
    }

    /** The main public method that starts the schedule (and by extension the recording).
     */
    public void startTi() {
        Log.e("tagtag", "2");
        timer = new Timer();

        if (!isRecording) {
            initializeTimerTaskStart();

            timer.schedule(timerTask, timeUntilStart(), recordingDuration());
        }
    }

    /** The main public method that stops the scheduled task and performs requisite resets.
     */
    public void stopTi() {
        timer = null;
        recorder.stopRe();
        recorder = null;
        isRecording = false;
    }
    /* ------------------ end of public methods ---------------- */

    /* ------------ beginning of private methods --------------- */
    /** Handler that starts or stops the scheduled recording as needed.
     */
    private void initializeTimerTaskStart() {
        timerTask = new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        if (!isRecording) {
                            isRecording = true;
                            Log.e("tagtag", "Started at a time.");
                            recorder.startRe();
                        } else {
                            recorder.stopRe();
                            recorder = null;
                            isRecording = false;
                            timer.cancel();
                            timer.purge();
                            Log.e("tagtag", "Stopped a minute later.");
                        }
                    }
                });
            }
        };
    }
    /** Given the integer inputs, calculates in milliseconds the amount of time before the
     * recording should start and in milliseconds the intended duration fo the recording.
     */
    private void calculateTimes() {
        int addMillisForDuration = 0, addMillisForStart = 0;

        if (currentHour() <= hour()) {
            addMillisForStart += (hour() - currentHour()) * MINUTES_PER_HOUR * SECONDS_PER_MINUTE * MILLIS_PER_SECOND;
        } else {
            addMillisForStart += (HOURS_PER_DAY - currentHour() + hour()) * MINUTES_PER_HOUR * SECONDS_PER_MINUTE * MILLIS_PER_SECOND;
        }

        if (currentMinute() <= minute()) {
            addMillisForStart += (minute() - currentMinute()) * SECONDS_PER_MINUTE * MILLIS_PER_SECOND;
        } else {
            addMillisForStart += (MINUTES_PER_HOUR - currentMinute() + minute()) * SECONDS_PER_MINUTE * MILLIS_PER_SECOND;
        }
        timeUntilStart(addMillisForStart);

        addMillisForDuration += durationHours() * MINUTES_PER_HOUR * SECONDS_PER_MINUTE * MILLIS_PER_SECOND;
        addMillisForDuration += durationMinutes() * SECONDS_PER_MINUTE * MILLIS_PER_SECOND;
        recordingDuration(addMillisForDuration);
    }


    /*
    * Like in Record - I'll leave these available in case they're needed.
     */
    /* UTC: [-1200: 30: +1400] */
    private int timeZone = -800;
    private Date upTime;
    private Date startTime;
    private Date endTime;
    private boolean dayLightSaving = true;
    private boolean active = true;
    private final Context _CTX;


    /* ------------ beginning of constructors ------------------ */
    public Schedule(Context ctx) {
        String[] ids = TimeZone.getAvailableIDs(this.timeZone * DECI_S2H);
        SimpleTimeZone pdt = new SimpleTimeZone(this.timeZone * DECI_S2H, ids[0]);
        this.upTime =  new Date();
        this._CTX = ctx;
    }

    public Schedule(Context ctx, File savedata) {
        FileInputStream fin;
        ObjectInputStream ois;
        String that = savedata.getName().toString();
        this._CTX = ctx;
        this.upTime = new Date ( (new Timestamp( Long.valueOf(that) )).getTime() );
        this.timeZone = getTimeZone(that);
        try {
            fin = new FileInputStream(savedata);
            ois = new ObjectInputStream(fin);
            ois.close();
        } catch (IOException e) {
        }
    }
    /* ------------------ end of constructors ------------------ */

    public String getID() {
        return Schedule.toString(this.upTime, this.timeZone, this.dayLightSaving);
    }

    public int getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(int timeZone) {
        this.timeZone = timeZone;
    }


    public void advance(int duration) {
        GregorianCalendar date = new GregorianCalendar();
        date.setTime(this.upTime);
        date.add(Calendar.MINUTE, -(duration % 100));
        date.add(Calendar.HOUR, -(duration / 100));
        this.upTime = date.getTime();
    }

    public void delay(int duration) {
        GregorianCalendar date = new GregorianCalendar();
        date.setTime(this.upTime);
        date.add(Calendar.MINUTE, (duration % 100));
        date.add(Calendar.HOUR, (duration / 100));
        this.upTime = date.getTime();
    }


    /**
     *
     * @param startTime
     * @param duration HH * 100 + MM
     */
    public void setStartTime(GregorianCalendar startTime, int duration) {
        //this.setStartTime(startTime);
        //this.endTime.add(Calendar.MINUTE, -(duration % 100));
        //this.endTime.add(Calendar.HOUR, -(int)(duration / 100));
    }

    /**
     *
     * @param endTime
     * @param duration HH * 100 + MM
     */
    public void setEndTime(GregorianCalendar endTime, int duration) {
        //this.setEndTime(endTime);
        //this.endTime.add(Calendar.MINUTE, duration % 100);
        //this.endTime.add(Calendar.HOUR, (int)(duration / 100));
    }

    public boolean isDayLightSaving() {
        return dayLightSaving;
    }

    public void setDayLightSaving(boolean dayLightSaving) {
        this.dayLightSaving = dayLightSaving;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public static String toString(Date time, int timeZone, boolean dayLightSaving) {
        String[] ids = TimeZone.getAvailableIDs(timeZone * DECI_S2H);
        SimpleTimeZone pdt = new SimpleTimeZone(timeZone * DECI_S2H, ids[0]);
        GregorianCalendar that = new GregorianCalendar(pdt);
        that.setTime(time);
        if(dayLightSaving) {
            that.add(Calendar.HOUR, 1);
        }
        boolean neg = (timeZone < 0);
        if(neg) timeZone = -timeZone;
        return String.format(SERIAL,
                                neg ? '-' : '+',
                                timeZone / 100,
                                timeZone % 100,
                                that.get(Calendar.YEAR),
                                that.get(Calendar.MONTH) + 1,
                                that.get(Calendar.DATE),
                                that.get(Calendar.HOUR_OF_DAY),
                                that.get(Calendar.MINUTE),
                                that.get(Calendar.SECOND),
                                that.get(Calendar.MILLISECOND)
                             );
    }

    public static GregorianCalendar toTime(String that) {
        int timeZone = getTimeZone(that);
        String[] ids = TimeZone.getAvailableIDs(timeZone * DECI_S2H);
        SimpleTimeZone pdt = new SimpleTimeZone(timeZone * DECI_S2H, ids[0]);
        GregorianCalendar time = new GregorianCalendar(pdt);
        time.set(Integer.valueOf(that.substring(11, 15)),
                Integer.valueOf(that.substring(16, 18)) - 1,
                Integer.valueOf(that.substring(19, 21)),
                Integer.valueOf(that.substring(22, 24)),
                Integer.valueOf(that.substring(25, 27)),
                Integer.valueOf(that.substring(28, 30)));
        time.set(Calendar.MILLISECOND, Integer.valueOf(that.substring(31)));
        //01234 %02d 7 %02d 10 %04d 15 %02d 18 %02d 21 %02d 24 %02d 27 %02d 30 %03d;
        return time;
    }


    public static int getTimeZone(String that) {
        int timeZone = Integer.valueOf(that.substring(5,7)) * 100 + Integer.valueOf(that.substring(8,10));
        if(that.charAt(4) == '-') timeZone = -timeZone;
        return timeZone;
    }

}
