package com.mm90849491.sleepguard;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 */
public class Schedule {
    /* UTC: [-1200: 30: +1400] */
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
     */
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
     */
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
}
