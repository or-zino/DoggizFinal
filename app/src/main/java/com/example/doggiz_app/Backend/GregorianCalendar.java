package com.example.doggiz_app.Backend;

import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class GregorianCalendar extends Calendar {
    public static final int AD = 1;
    public static final int BC = 0;

    public GregorianCalendar() {
        throw new RuntimeException("Stub!");
    }

    public GregorianCalendar(TimeZone zone) {
        throw new RuntimeException("Stub!");
    }

    public GregorianCalendar(Locale aLocale) {
        throw new RuntimeException("Stub!");
    }

    public GregorianCalendar(TimeZone zone, Locale aLocale) {
        throw new RuntimeException("Stub!");
    }

    public GregorianCalendar(int year, int month, int dayOfMonth) {
        throw new RuntimeException("Stub!");
    }

    public GregorianCalendar(int year, int month, int dayOfMonth, int hourOfDay, int minute) {
        throw new RuntimeException("Stub!");
    }

    public GregorianCalendar(int year, int month, int dayOfMonth, int hourOfDay, int minute, int second) {
        throw new RuntimeException("Stub!");
    }

    public void setGregorianChange(Date date) {
        throw new RuntimeException("Stub!");
    }

    public final Date getGregorianChange() {
        throw new RuntimeException("Stub!");
    }

    public boolean isLeapYear(int year) {
        throw new RuntimeException("Stub!");
    }

    public String getCalendarType() {
        throw new RuntimeException("Stub!");
    }

    public boolean equals(Object obj) {
        throw new RuntimeException("Stub!");
    }

    public int hashCode() {
        throw new RuntimeException("Stub!");
    }

    public void add(int field, int amount) {
        throw new RuntimeException("Stub!");
    }

    public void roll(int field, boolean up) {
        throw new RuntimeException("Stub!");
    }

    public void roll(int field, int amount) {
        throw new RuntimeException("Stub!");
    }

    public int getMinimum(int field) {
        throw new RuntimeException("Stub!");
    }

    public int getMaximum(int field) {
        throw new RuntimeException("Stub!");
    }

    public int getGreatestMinimum(int field) {
        throw new RuntimeException("Stub!");
    }

    public int getLeastMaximum(int field) {
        throw new RuntimeException("Stub!");
    }

    public int getActualMinimum(int field) {
        throw new RuntimeException("Stub!");
    }

    public int getActualMaximum(int field) {
        throw new RuntimeException("Stub!");
    }

    public Object clone() {
        throw new RuntimeException("Stub!");
    }

    public TimeZone getTimeZone() {
        throw new RuntimeException("Stub!");
    }

    public void setTimeZone(TimeZone zone) {
        throw new RuntimeException("Stub!");
    }

    public final boolean isWeekDateSupported() {
        throw new RuntimeException("Stub!");
    }

    public int getWeekYear() {
        throw new RuntimeException("Stub!");
    }

    public void setWeekDate(int weekYear, int weekOfYear, int dayOfWeek) {
        throw new RuntimeException("Stub!");
    }

    public int getWeeksInWeekYear() {
        throw new RuntimeException("Stub!");
    }

    protected void computeFields() {
        throw new RuntimeException("Stub!");
    }

    protected void computeTime() {
        throw new RuntimeException("Stub!");
    }

    public ZonedDateTime toZonedDateTime() {
        throw new RuntimeException("Stub!");
    }

    public static GregorianCalendar from(ZonedDateTime zdt) {
        throw new RuntimeException("Stub!");
    }
}