package ca.mealbell.ui;


import android.graphics.Color;
import android.os.Bundle;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;

import ca.mealbell.APIResponse;
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

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;

    String requestURL;

    public RecipesResultsFragment() {
        // Required empty public constructor
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
        MainAPI.getInstance(getContext()).jsonObjectRequest(Request.Method.GET, requestURL, null, this);
    }

    /**
     * Response to Refresh Event
     */
    @Override
    public void onRefresh() {
        // TODO: add function to response on refresh
        // stop refreshing after 2.5 seconds :D
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 2500);
    }

    @Override
    public void onSuccess(String json, int status) {
        Log.d(TAG, json);
    }

    @Override
    public void onFailure(String error, int status) {
        Log.e(TAG, error);
    }
}
