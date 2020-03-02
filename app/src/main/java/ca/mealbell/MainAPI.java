package ca.mealbell;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

/**
 * This class is responsible to create all requests
 *
 * @author Ali Dali
 * @version 1.0
 * @since 22-02-2020
 */
public class MainAPI {

    private final String TAG = MainAPI.class.getTypeName();

    private static MainAPI instance;

    public static MainAPI getInstance(Context context) {
        if (instance == null)
            instance = new MainAPI(context);
        return instance;
    }

    private RequestQueue queue;
    private Context context;
    private int statusCode;
    private Map<String, String> headers;

    private MainAPI(Context context) {
        this.context = context;
        queue = Volley.newRequestQueue(context);
    }

    public MainAPI setHeaders(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    /**
     * @param url      (API) Request URL
     * @param method   Method Type (Get, POST, PUT, DELETE)
     * @param json     JSON Object (Data)
     * @param callback Class that is responsible for the request response (onSuccess/onFailure)
     * @param <T>      Any Class that implements interface (APIResponse)
     * @author Ali Dali
     * @since 22-02-2020
     */

    public <T extends APIResponse> void jsonObjectRequest(@NonNull final int method, @NonNull final String url, final JSONObject json, @NonNull final T callback) {
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
                Log.e(TAG, error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (headers == null) return super.getHeaders();
                return headers;
            }

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                statusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }
        };

        queue.add(request);
    }

    /**
     * @param url      (API) Request URL
     * @param method   Method Type (Get, POST, PUT, DELETE)
     * @param json     JSON Object (Data)
     * @param callback Class that is responsible for the request response (onSuccess/onFailure)
     * @param <T>      Any Class that implements interface (APIResponse)
     * @author Ali Dali
     * @since 01-03-2020
     */

    public <T extends APIResponse> void newRequest(@NonNull final int method, @NonNull final String url, @Nullable final String json, @NonNull final T callback) {
        StringRequest request = new StringRequest(method, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onSuccess(response, statusCode);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFailure(error.toString(), statusCode);

                // MARK: Test purpose
                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                Log.e(TAG, error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (headers == null) return super.getHeaders();
                return headers;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                if (json == null) return super.getBody();
                return json.getBytes();
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                statusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }
        };

        queue.add(request);
    }
}


