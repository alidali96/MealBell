package ca.mealbell.ui;


import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ca.mealbell.R;

import static ca.mealbell.MainActivity.fab;


/**
 * This Fragment will show list of recipes (search results)
 *
 * @author Ali Dali
 * @since 22-02-2020
 * @see SwipeRefreshLayout
 */
public class RecipesResultsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;


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


        return view;
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
}
