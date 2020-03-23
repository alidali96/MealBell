package ca.mealbell;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.android.volley.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.app.PendingIntent.FLAG_ONE_SHOT;

public class FoodNotification extends BroadcastReceiver implements APIResponse {

    private final Map<String, String> FOOD_API_HEADERS = new HashMap<>();
    Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        FOOD_API_HEADERS.put("x-rapidapi-host", "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com");
        FOOD_API_HEADERS.put("x-rapidapi-key", context.getString(R.string.api_key));

        String url = Const.API_URL + "food/jokes/random";

        MainAPI.getInstance(context).setHeaders(FOOD_API_HEADERS).newRequest(Request.Method.GET, url, null, this );

//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
//
//        Intent myIntent = new Intent(context, MainActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(
//                context,
//                0,
//                myIntent,
//                FLAG_ONE_SHOT);
//
//        builder.setAutoCancel(true)
//                .setDefaults(Notification.DEFAULT_ALL)
//                .setWhen(System.currentTimeMillis())
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setContentTitle("Food")
//                .setContentIntent(pendingIntent)
//                .setContentText("This is you notification\nMealBell")
//                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND)
//                .setContentInfo("Info")
//                .setChannelId("food");
//
//        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(1, builder.build());
    }

    @Override
    public void onSuccess(String json, int status) {
        Log.e("RESULT", json);
        String joke = "";
        try {
            JSONObject jokeJSON = new JSONObject(json);
            joke = jokeJSON.getString("text");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        Intent myIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                0,
                myIntent,
                FLAG_ONE_SHOT);

        builder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Food Joke")
                .setContentIntent(pendingIntent)
//                .setContentText(joke)
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND)
                .setContentInfo("Info")
                .setStyle(new NotificationCompat.BigTextStyle().bigText(joke))
                .setChannelId("food");

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());

    }

    @Override
    public void onFailure(String error, int status) {
        Log.e("RESULT", error);
    }
}
