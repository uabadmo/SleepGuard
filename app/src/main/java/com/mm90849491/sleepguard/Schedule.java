package com.mm90849491.sleepguard; // for update

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

import android.os.Handler;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Calendar;
import android.util.Log;

/** Schedule class.
 *      Schedules the instance of the Record class.
 *  @version 0.4.0
 *  @author M.Harrison
 *  @see com.mm90849491.sleepguard.Schedule
 */
public class Schedule {

    /* ------------ beginning of constant variables ------------ */
    static private int HOURS_PER_DAY = 24;
    static private int MINUTES_PER_HOUR = 60;
    static private int SECONDS_PER_MINUTE = 60;
    static private int MILLIS_PER_SECOND = 1000;
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

    /* ------------ beginning of constructors ------------------ */
    /** Constructs the Schedule instance.
     * @param hourIn (int) The hour that the recording should start in 24-hour time.
     * @param minuteIn (int) The minute that the recording should start in 24-hour time.
     * @param durationHoursIn (int) The number of hours of the intended duration of the recording.
     * @param durationMinutesIn (int) The remainder of minutes of the intended duration of the recording.
     */
    public Schedule(int hourIn, int minuteIn, int durationHoursIn, int durationMinutesIn) {
        if (areValidTimes()) {
            recorder = new Record();
            isRecording = false;
            hour(hourIn);
            minute(minuteIn);
            currentHour(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
            currentMinute(Calendar.getInstance().get(Calendar.MINUTE));
            durationHours(durationHoursIn);
            durationMinutes(durationMinutesIn);
            calculateTimes();
            startTi();
            Log.e("tagtag", "1");
        }
    }
    /* ------------------ end of constructors ------------------ */

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
    /* UTC: [-1200: 30: +1400] /
    private int timeZone = -800;
    private GregorianCalendar startTime;
    private GregorianCalendar endTime;
    private boolean dayLightSaving = false;
    private boolean active = true;

    public Schedule(int timeZone, GregorianCalendar startTime, GregorianCalendar endTime, boolean dayLightSaving) {
        this.timeZone = timeZone;
        this.startTime = startTime;
        this.endTime = endTime;
        this.dayLightSaving = dayLightSaving;
    }

    public int getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(int timeZone) {
        this.timeZone = timeZone;
    }

    public GregorianCalendar getStartTime() {
        return startTime;
    }

    public void setStartTime(GregorianCalendar startTime) {
        this.startTime = startTime;
    }

    /**
     *
     * @param startTime
     * @param duration HH * 100 + MM
     /
    public void setStartTime(GregorianCalendar startTime, int duration) {
        this.setStartTime(startTime);
        this.endTime.add(Calendar.MINUTE, -(duration % 100));
        this.endTime.add(Calendar.HOUR, -(int)(duration / 100));
    }

    public GregorianCalendar getEndTime() {
        return endTime;
    }

    public void setEndTime(GregorianCalendar endTime) {
        this.endTime = endTime;
    }

    /**
     *
     * @param endTime
     * @param duration HH * 100 + MM
     /
    public void setEndTime(GregorianCalendar endTime, int duration) {
        this.setEndTime(endTime);
        this.endTime.add(Calendar.MINUTE, duration % 100);
        this.endTime.add(Calendar.HOUR, (int)(duration / 100));
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
    */
}
