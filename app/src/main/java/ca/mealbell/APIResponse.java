package ca.mealbell;

/**
 *  All Classes that uses MainAPI <strong>must</strong> implements this interface
 * @author Ali Dali
 * @since 22-02-2020
 * @version 1.0
 */
public interface APIResponse {

    void onSuccess(String json, int status);
    void onFailure(String error, int status);
}
