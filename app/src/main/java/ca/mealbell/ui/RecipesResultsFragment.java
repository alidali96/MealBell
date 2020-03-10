package ca.mealbell.ui;


import android.graphics.Color;
import android.os.Bundle;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ca.mealbell.APIResponse;
import ca.mealbell.Const;
import ca.mealbell.MainAPI;
import ca.mealbell.R;
import ca.mealbell.adapters.ResultsByIngredientsAdapter;
import ca.mealbell.adapters.ResultsByNutritionAdapter;
import ca.mealbell.javabeans.RecipeByIngredient;
import ca.mealbell.javabeans.RecipeByNutrition;

import static ca.mealbell.MainActivity.fab;

enum SearchType {
    Ingredient,
    Nutrition
}

/**
 * This Fragment will show list of recipes (search results)
 *
 * @author Ali Dali
 * @updated 26-02-2020
 * @updated 01-03-2020
 * @see SwipeRefreshLayout
 * @since 22-02-2020
 */
public class RecipesResultsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, APIResponse {

    private final String TAG = getClass().getTypeName();
    private final Map<String, String> FOOD_API_HEADERS = new HashMap<>();

    private ArrayList<RecipeByIngredient> recipeByIngredients = new ArrayList<>();
    private ArrayList<RecipeByNutrition> recipeByNutrition = new ArrayList<>();

    private SearchType searchType;

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;

    ResultsByIngredientsAdapter ingredientsAdapter;
    ResultsByNutritionAdapter nutritionAdapter;

    Gson gson;
    String requestURL;

    public RecipesResultsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            requestURL = getArguments().getString(Const.SEARCH_URL);
            searchType = SearchType.values()[getArguments().getInt(Const.SEARCH_TYPE, 0)];
        }
        // Initialize Gson
        gson = new Gson();

        // Add API headers
        FOOD_API_HEADERS.put("x-rapidapi-host", "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com");
        FOOD_API_HEADERS.put("x-rapidapi-key", "fd162a08c4mshdc6272c968e7ee8p1367dcjsne4431e0cbaea");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipes_results, container, false);

        // Hide FAB
        fab.hide();

        // Set SwipeRefreshLayout
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(Color.GREEN, Color.RED);    // Pick Awesome Colors !)


        // Set RecyclerView
        recyclerView = view.findViewById(R.id.recycler_view);

        // Set Adapters
        if (searchType == SearchType.Ingredient) {
            ingredientsAdapter = new ResultsByIngredientsAdapter(getContext(), recipeByIngredients);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(ingredientsAdapter);
        } else {
            nutritionAdapter = new ResultsByNutritionAdapter(getContext(), recipeByNutrition);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(nutritionAdapter);
        }


//        // TODO: Set adapter and layout manager
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerView.setAdapter(ingredientsAdapter);

        search();

        return view;
    }


    /**
     * Make API Request
     */
    private void search() {
        MainAPI.getInstance(getContext()).setHeaders(FOOD_API_HEADERS).newRequest(Request.Method.GET, requestURL, null, this);
    }

    /**
     * Response to Refresh Event
     */
    @Override
    public void onRefresh() {

        search();
    }

    @Override
    public void onSuccess(String json, int status) {
        // Stop refreshing
        swipeRefreshLayout.setRefreshing(false);
        Log.e(TAG, json);

        if (searchType == SearchType.Ingredient) {
            Type ingredientsType = new TypeToken<ArrayList<RecipeByIngredient>>() {
            }.getType();
            recipeByIngredients = gson.fromJson(json, ingredientsType);

            ingredientsAdapter.updateList(recipeByIngredients);
        } else {
            // TODO: Nutrition
            Type nutritionType = new TypeToken<ArrayList<RecipeByNutrition>>() {
            }.getType();
            recipeByNutrition = gson.fromJson(json, nutritionType);

            nutritionAdapter.updateList(recipeByNutrition);
        }
    }

    @Override
    public void onFailure(String error, int status) {
        // Stop refreshing
        swipeRefreshLayout.setRefreshing(false);
        Log.e(TAG, error);
    }
}
