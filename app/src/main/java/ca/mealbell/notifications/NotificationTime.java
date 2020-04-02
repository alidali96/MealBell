package ca.mealbell.notifications;

public class NotificationTime {
    private int hour;
    private int minute;
    private int second;

    public NotificationTime() {
        hour = minute = second = 0;
    }

    public NotificationTime(int hour) {
        this();
        this.hour = hour;
    }

    public NotificationTime(int hour, int minute) {
        this();
        this.hour = hour;
        this.minute = minute;
    }

    public NotificationTime(int hour, int minute, int second) {
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getSecond() {
        return second;
    }
}
