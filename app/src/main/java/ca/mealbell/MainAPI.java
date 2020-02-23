package ca.mealbell;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

/**
 * This class is responsible to create all requests
 * @author Ali Dali
 * @since 22-02-2020
 * @version 1.0
 */
public class MainAPI {

    // Requires API 26
//    private static String TAG = MainAPI.class.getTypeName();

    private static MainAPI instance;

    public static MainAPI getInstance(Context context) {
        if (instance == null)
            instance = new MainAPI(context);
        return instance;
    }

    private RequestQueue queue;
    private Context context;
    private int statusCode;

    private MainAPI(Context context) {
        this.context = context;
        queue = Volley.newRequestQueue(context);
    }


    /**
     * @param url      (API) Request URL
     * @param method   Method Type (Get, POST, PUT, DELETE)
     * @param json     JSON Object (Data)
     * @param callback Class that is responsible for the request response (onSuccess/onFailure)
     * @param <T>      Any Class that implements interface (APIResponse)
     * @author Ali Dali
     * @since 22-02-2020
     * @version 1.0
     */
    public <T extends APIResponse> void newRequest(@NonNull final int method, @NonNull final String url, final JSONObject json, @NonNull final T callback) {
        JsonObjectRequest request = new JsonObjectRequest(method, url, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                callback.onSuccess(response.toString(), statusCode);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFailure(error.toString(), statusCode);

                // MARK: Test purpose
                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                Log.wtf("MainAPI", error.getMessage() + " getMessage");
                Log.wtf("MainAPI", error.getLocalizedMessage() + " getLocalizedMessage");
                Log.wtf("MainAPI", error.toString() + " toString");
            }
        }) {
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                statusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }
        };

        queue.add(request);
    }

}
