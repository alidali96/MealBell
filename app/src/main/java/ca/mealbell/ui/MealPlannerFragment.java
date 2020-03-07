package ca.mealbell.ui;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

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
        final View view = inflater.inflate(R.layout.fragment_meal_planner, container, false);

        // Hide fab
        fab.hide();
        // Change Image of fab
        fab.setImageResource(R.drawable.ic_add_black_24dp);
        // Set Click Listener on fab
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_nav_meal_planner_to_createMealPlanFragment);
            }
        });
        // Show fab
        fab.show();

        return view;
    }

}
