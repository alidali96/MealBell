package ca.mealbell.ui;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ca.mealbell.R;

import static ca.mealbell.MainActivity.fab;


/**
 * Generate Meal Plans
 *
 * @author Ali Dali
 * @version 1.0
 * @since 07-03-2020
 */
public class MealPlannerFragment extends Fragment {


    public MealPlannerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_meal_planner, container, false);

        // Hide fab
        fab.hide();

        return view;
    }

}
