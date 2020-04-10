package ca.mealbell.ui;


import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.gson.Gson;


import org.json.JSONException;
import org.json.JSONObject;

import ca.mealbell.Const;
import ca.mealbell.R;
import ca.mealbell.api.APIResponse;
import ca.mealbell.api.MainAPI;
import ca.mealbell.database.DatabaseHandler;
import ca.mealbell.javabeans.RecipeInformation;

import static ca.mealbell.MainActivity.FOOD_API_HEADERS;
import static ca.mealbell.MainActivity.fab;


/**
 * Show a Random Recipe
 *
 * @author Ali Dali
 * @since 31-03-2020
 */
public class RandomRecipeFragment extends Fragment implements APIResponse, SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecipeDetailsFragment recipeDetailsFragment;

    private Gson gson = new Gson();

    public RandomRecipeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_random_recipe, container, false);

        // Swipe Refresh Layout
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.colorAccent));

        // Hide fab
        fab.hide();

        recipeDetailsFragment = (RecipeDetailsFragment) getChildFragmentManager().findFragmentById(R.id.random_recipe);

//        if (recipeDetailsFragment != null)
//            generateRandomRecipe();

        return view;
    }

    private void generateRandomRecipe() {
        String url = Const.API_URL + "/recipes/random";
        MainAPI.getInstance(getContext()).setHeaders(FOOD_API_HEADERS).jsonObjectRequest(Request.Method.GET, url, null, this);
    }


    @Override
    public void onRefresh() {
        generateRandomRecipe();
    }

    @Override
    public void onSuccess(Object json, int status, int request) {
        swipeRefreshLayout.setRefreshing(false);

        try {
            JSONObject jsonObject = (JSONObject) json;
            String jsonRecipe = jsonObject.getJSONArray("recipes").get(0).toString();
            RecipeInformation recipeInformation = gson.fromJson(jsonRecipe, RecipeInformation.class);

            // Set Recipe in Details Fragment
            recipeDetailsFragment.setRecipe(recipeInformation);
            recipeDetailsFragment.getRecipeEquipments(recipeInformation.getId());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(VolleyError error, int status, int request) {
        swipeRefreshLayout.setRefreshing(false);
    }
}
