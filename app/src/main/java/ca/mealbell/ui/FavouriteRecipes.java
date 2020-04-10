package ca.mealbell.ui;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ca.mealbell.R;
import ca.mealbell.adapters.FavouriteRecipeAdapter;
import ca.mealbell.adapters.ResultsByNutritionAdapter;
import ca.mealbell.database.DatabaseHandler;
import ca.mealbell.javabeans.RecipeInformation;

import static ca.mealbell.MainActivity.fab;


/**
 * @author Ali Dali
 * @since 08-04-2020
 */
public class FavouriteRecipes extends Fragment {


    private RecyclerView recyclerView;
    private FavouriteRecipeAdapter adapter;
    private ArrayList<RecipeInformation> favouriteRecipes;

    public FavouriteRecipes() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favourite_recipes, container, false);

        // Hide fab
        fab.hide();

        // Attach views
        recyclerView = view.findViewById(R.id.favourites_recycler_view);


        favouriteRecipes = new ArrayList<>(DatabaseHandler.getInstance(getContext()).getAllRecipes());

        adapter = new FavouriteRecipeAdapter(getContext(), favouriteRecipes);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

}
