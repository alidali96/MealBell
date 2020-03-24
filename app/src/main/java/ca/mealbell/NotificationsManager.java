package ca.mealbell;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import java.util.Calendar;

/**
 * Notification Manager
 *
 * @author Ali Dali
 * @since 23-03-2020
 */
public class NotificationsManager {

    enum Repeat {
        INTERVAL_MINUTE(60 * 1000),
        INTERVAL_FIVE_MINUTES(5 * 60 * 1000),
        INTERVAL_FIFTEEN_MINUTES(15 * 60 * 1000),
        INTERVAL_HALF_HOUR(30 * 60 * 1000),
        INTERVAL_HOUR(60 * 60 * 1000),
        INTERVAL_THREE_HOURS(3 * 60 * 60 * 1000),
        INTERVAL_SIX_HOURS(6 * 60 * 60 * 1000),
        INTERVAL_HALF_DAY(12 * 60 * 60 * 1000),
        INTERVAL_DAY(24 * 60 * 60 * 1000);

        private final long value;

        private Repeat(long value) {
            this.value = value;
        }
    }


    // Bundle Keys
    public static final String CHANNEL_ID = "CHANNEL";
    public static final String NOTIFICATION_TYPE = "NOTIFICATION_TYPE";

    // Notification Types
    public static final int FOOD_JOKE = 1;
    public static final int FOOD_TRIVIA = 2;

    // Variables
    private final Context context;
    private final String channelID;
    private final String channelName;
    private final String channelDescription;
    private final int notificationType;
    private final Repeat repeat;
    private final NotificationTime notificationTime;

    private static int requestCode = 0;

    private NotificationsManager(Builder builder) {
        this.context = builder.context;
        this.channelID = builder.channelID;
        this.channelName = builder.channelName;
        this.channelDescription = builder.channelDescription;
        this.notificationType = builder.notificationType;
        this.notificationTime = builder.notificationTime;
        this.repeat = builder.repeat;
    }

    public static class Builder {

        private final Context context;
        private String channelID;
        private String channelName;
        private String channelDescription;
        private int notificationType;
        private Repeat repeat;
        private NotificationTime notificationTime;

        public Builder(Context context) {
            this.context = context;
        }

        public NotificationsManager build() {
            if (repeat.value == 0)
                repeat = Repeat.INTERVAL_DAY;

            return new NotificationsManager(this);
        }

        public Builder setChannelID(String channelID) {
            this.channelID = channelID;
            return this;
        }

        public Builder setChannelName(String channelName) {
            this.channelName = channelName;
            return this;
        }

        public Builder setChannelDescription(String channelDescription) {
            this.channelDescription = channelDescription;
            return this;
        }

        public Builder setNotificationType(int notificationType) {
            this.notificationType = notificationType;
            return this;
        }

        public Builder setRepeat(Repeat repeat) {
            this.repeat = repeat;
            return this;
        }

        public Builder setNotificationTime(NotificationTime notificationTime) {
            this.notificationTime = notificationTime;
            return this;
        }
    }

    public void launchNotification() {

        // REQUIRED
        // Create Channel if SDK >= 26 (O)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            android.app.NotificationManager notificationManager = (android.app.NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH); // High to popup
            channel.setDescription(channelDescription);
            channel.enableLights(true);
            channel.enableVibration(true);
            channel.setLightColor(Color.parseColor("#ffc842"));


            notificationManager.createNotificationChannel(channel);
        }

        // Get Alarm Manager
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        // Schedule The Notification
        Calendar calendar = Calendar.getInstance();
        // Check If Time Is Set
        if (notificationTime != null) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            calendar.set(Calendar.HOUR_OF_DAY, notificationTime.getHour());
            calendar.set(Calendar.MINUTE, notificationTime.getMinute());
            calendar.set(Calendar.SECOND, notificationTime.getSecond());
            Log.e("RESULT", "HOUR: " + notificationTime.getHour());
            Log.e("RESULT", "MINUTE: " + notificationTime.getMinute());
            Log.e("RESULT", "SECOND: " + notificationTime.getSecond());
        }
        // Food Notification
        Intent foodNotification = new Intent(context.getApplicationContext(), FoodNotification.class);
        foodNotification.putExtra(CHANNEL_ID, channelID);               // Send Channel ID
        foodNotification.putExtra(NOTIFICATION_TYPE, notificationType); // Send Notification Type

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), requestCode++, foodNotification, 0);  // Unique Request Code

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), repeat.value, pendingIntent);
    }
}

class NotificationTime {
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
