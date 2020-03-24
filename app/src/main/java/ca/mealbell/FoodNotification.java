package ca.mealbell;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;


import static android.app.PendingIntent.FLAG_ONE_SHOT;
import static ca.mealbell.MainActivity.FOOD_API_HEADERS;
import static ca.mealbell.NotificationsManager.CHANNEL_ID;
import static ca.mealbell.NotificationsManager.FOOD_JOKE;
import static ca.mealbell.NotificationsManager.FOOD_TRIVIA;
import static ca.mealbell.NotificationsManager.NOTIFICATION_TYPE;


/**
 * Create New Notification (Joke / Trivia)
 *
 * @author Ali Dali
 * @since 23-03-2020
 */
public class FoodNotification extends BroadcastReceiver implements APIResponse {

    private Context context;
    private String channelID;
    private int notificationType;
    private String type;
    private String title;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;

        channelID = intent.getStringExtra(CHANNEL_ID);
        notificationType = intent.getIntExtra(NOTIFICATION_TYPE, 0);

        switch (notificationType) {
            case FOOD_JOKE:
                title = "Food Joke";
                type = "jokes";
                break;
            case FOOD_TRIVIA:
                title = "Food Trivia";
                type = "trivia";
                break;
        }

        String url = Const.API_URL + "food/" + type + "/random";

        Log.e("RESULT", "CHANNEL: " + channelID);
        Log.e("RESULT", "TYPE: " + notificationType);
        Log.e("RESULT", "URL: " + url);

        MainAPI.getInstance(context).setHeaders(FOOD_API_HEADERS).jsonObjectRequest(Request.Method.GET, url, null, this);
    }

    @Override
    public void onSuccess(Object json, int status) {
        Log.e("RESULT", json.toString());

        JSONObject jokeJSON = (JSONObject) json;
        String joke = "Error loading notification";
        try {
            joke = jokeJSON.getString("text");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelID);

        Intent myIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                0,
                myIntent,
                FLAG_ONE_SHOT);

        builder.setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(joke)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(joke))
                .setColor(ContextCompat.getColor(context, R.color.colorGold));

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationType, builder.build());
    }

    @Override
    public void onFailure(VolleyError error, int status) {
        Log.e("RESULT", error.toString());
    }
}
