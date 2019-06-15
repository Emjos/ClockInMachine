package sample.controllers;

public class Clocks {
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String date;
    private String clockIn;
    private String ClockOut;


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getClockIn() {
        return clockIn;
    }

    public void setClockIn(String clockIn) {
        this.clockIn = clockIn;
    }

    public String getClockOut() {
        return ClockOut;
    }

    public void setClockOut(String clockOut) {
        ClockOut = clockOut;
    }

    public Clocks(String date, String clockIn, String clockOut) {
        this.date = date;
        this.clockIn = clockIn;
        ClockOut = clockOut;
    }

    public Clocks(String email, String date, String clockIn, String clockOut) {
        this.email = email;
        this.date = date;
        this.clockIn = clockIn;
        ClockOut = clockOut;
    }
}
