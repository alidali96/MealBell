package ca.mealbell;

import com.android.volley.VolleyError;

/**
 *  All Classes that uses MainAPI <strong>must</strong> implements this interface
 * @author Ali Dali
 * @since 22-02-2020
 * @version 1.0
 */
public interface APIResponse {

    void onSuccess(Object json, int status);
    void onFailure(VolleyError error, int status);
}
