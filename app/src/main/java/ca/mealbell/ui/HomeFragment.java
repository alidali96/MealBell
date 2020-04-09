package ca.mealbell.ui;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
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
public class HomeFragment extends Fragment implements APIResponse, SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecipeDetailsFragment recipeDetailsFragment;

    private Gson gson = new Gson();

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Swipe Refresh Layout
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

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

    private void handleFAB(final RecipeInformation recipe) {
        final RecipeInformation favourite = DatabaseHandler.getInstance(getContext()).getRecipe(recipe.getId());
        fab.hide();
        fab.setImageResource(favourite == null ? R.drawable.ic_favorite_border_black_24dp : R.drawable.ic_favorite_black_24dp);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (favourite == null) {
                    DatabaseHandler.getInstance(getContext()).addRecipe(recipe);
                    Toast.makeText(getContext(), "Recipe added to favourites", Toast.LENGTH_SHORT).show();
                } else {
                    DatabaseHandler.getInstance(getContext()).deleteRecipe(recipe);
                    Toast.makeText(getContext(), "Recipe removed from favourites", Toast.LENGTH_SHORT).show();
                }
                handleFAB(recipe);
            }
        });
        fab.show();
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


            handleFAB(recipeInformation);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(VolleyError error, int status, int request) {
        swipeRefreshLayout.setRefreshing(false);
    }
}
