package ca.mealbell.ui;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.VolleyError;


import ca.mealbell.Const;
import ca.mealbell.R;
import ca.mealbell.api.APIResponse;
import ca.mealbell.api.MainAPI;

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

        // Show fab
        fab.show();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Save in Database
            }
        });



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
        //TODO: populate recipe information
    }

    @Override
    public void onFailure(VolleyError error, int status, int request) {
        swipeRefreshLayout.setRefreshing(false);
    }
}
