package ca.mealbell.ui;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ca.mealbell.R;
import ca.mealbell.adapters.FavouritesViewPagerAdapter;
import ca.mealbell.database.DatabaseHandler;
import ca.mealbell.javabeans.RecipeInformation;
import me.kungfucat.viewpagertransformers.AccordionTransformer;
import me.kungfucat.viewpagertransformers.RandomTransformer;
import me.kungfucat.viewpagertransformers.RotateAboutBottomTransformer;
import me.kungfucat.viewpagertransformers.RotateAboutTopTransformer;
import me.kungfucat.viewpagertransformers.ScaleTransformer;
import me.kungfucat.viewpagertransformers.WindmillTransformer;
import me.kungfucat.viewpagertransformers.ZoomOutPageTransformer;

import static ca.mealbell.MainActivity.fab;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavouritesViewPager extends Fragment {

    private ViewPager viewPager;
    private FavouritesViewPagerAdapter adapter;
    private ArrayList<RecipeInformation> recipes;
    private RecipeInformation recipe;

    public FavouritesViewPager() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            recipe = getArguments().getParcelable("RECIPE");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favourites_view_pager, container, false);

        // Hide fab
        fab.hide();

        // Attach views
        viewPager = view.findViewById(R.id.favourites_view_pager);

        recipes = new ArrayList<>(DatabaseHandler.getInstance(getContext()).getAllRecipes());

        adapter = new FavouritesViewPagerAdapter(getChildFragmentManager(), recipes);

        viewPager.setAdapter(adapter);
        viewPager.setPageTransformer(true, new RotateAboutTopTransformer());

        adapter.getItem(adapter.getItemPosition(recipe));
        return view;
    }

}
