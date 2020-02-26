package ca.mealbell.ui;


import android.graphics.Color;
import android.os.Bundle;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;

import java.util.HashMap;
import java.util.Map;

import ca.mealbell.APIResponse;
import ca.mealbell.Const;
import ca.mealbell.MainAPI;
import ca.mealbell.R;

import static ca.mealbell.MainActivity.fab;


/**
 * This Fragment will show list of recipes (search results)
 *
 * @author Ali Dali
 * @updated 26-02-2020
 * @see SwipeRefreshLayout
 * @since 22-02-2020
 */
public class RecipesResultsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, APIResponse {

    private final String TAG = getClass().getTypeName();
    private final Map<String, String> FOOD_API_HEADERS = new HashMap<>();

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;

    String requestURL;

    public RecipesResultsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            requestURL = getArguments().getString(Const.SEARCH_KEY);
        }
        // Add API headers
        FOOD_API_HEADERS.put("x-rapidapi-host", "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com");
        FOOD_API_HEADERS.put("x-rapidapi-key", "ADD_YOUR_API_KEY_HERE");
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
        // TODO: Set adapter and layout manager
        recyclerView.setLayoutManager(null);
        recyclerView.setAdapter(null);

        search();

        return view;
    }


    private void search() {
        MainAPI.getInstance(getContext()).setHeaders(FOOD_API_HEADERS).newRequest(Request.Method.GET, requestURL, null, this);
    }

    /**
     * Response to Refresh Event
     */
    @Override
    public void onRefresh() {
        search();

        // stop refreshing after 2.5 seconds :D
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                swipeRefreshLayout.setRefreshing(false);
//            }
//        }, 2500);
    }

    @Override
    public void onSuccess(String json, int status) {
        // Stop refreshing
        swipeRefreshLayout.setRefreshing(false);
        Log.e(TAG, json);
    }

    @Override
    public void onFailure(String error, int status) {
        // Stop refreshing
        swipeRefreshLayout.setRefreshing(false);
        Log.e(TAG, error);
    }
}
