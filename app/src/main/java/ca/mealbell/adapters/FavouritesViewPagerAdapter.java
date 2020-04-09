package ca.mealbell.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import ca.mealbell.database.DatabaseHandler;
import ca.mealbell.javabeans.RecipeInformation;
import ca.mealbell.ui.RecipeDetailsFragment;

public class FavouritesViewPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<RecipeInformation> recipes;

    public FavouritesViewPagerAdapter(@NonNull FragmentManager fm, ArrayList<RecipeInformation> recipes) {
        super(fm);
        this.recipes = recipes;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return RecipeDetailsFragment.newInstance(recipes.get(position));
    }

    @Override
    public int getItemPosition(@NonNull Object recipe) {
        if (recipes.contains(recipe))
            return recipes.indexOf(recipe);
        return super.getItemPosition(recipe);
    }

    @Override
    public int getCount() {
        return recipes.size();
    }
}
