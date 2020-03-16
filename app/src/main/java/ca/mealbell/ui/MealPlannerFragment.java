package ca.mealbell.ui;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ca.mealbell.R;
import ca.mealbell.adapters.MealPlanAdapter;
import ca.mealbell.javabeans.Meal;
import ca.mealbell.javabeans.MealPlan;
import ca.mealbell.javabeans.Nutrients;

import static ca.mealbell.MainActivity.fab;


/**
 * Generated Meal Plans
 *
 * @author Ali Dali
 * @version 1.0
 * @since 07-03-2020
 */
public class MealPlannerFragment extends Fragment {

    RecyclerView recyclerView;
    MealPlanAdapter adapter;

    ArrayList<MealPlan> mealPlans = new ArrayList<>();

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
        fab.setImageResource(R.drawable.ic_add_white_24dp);
        // Set Click Listener on fab
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_nav_meal_planner_to_createMealPlanFragment);
            }
        });
        // Show fab
        fab.show();

        // TEST
        String image = "https://hips.hearstapps.com/ghk.h-cdn.co/assets/cm/15/11/768x516/54ff93e612be1-healthy-unhealthy-food-pizza-xln.jpg";
        Meal meal = new Meal(1,"Meal", 10,2,image, new String[]{});
        Meal meal2 = new Meal(2,"Meal 2", 15,5,image, new String[]{});
        Meal meal3 = new Meal(3,"Meal 3", 20,12,image, new String[]{});
        MealPlan mealPlan = new MealPlan(new Meal[]{meal, meal2, meal3}, new Nutrients(500,100,22.4,123.45));

        String image2 = "https://upload.wikimedia.org/wikipedia/commons/thumb/6/6d/Good_Food_Display_-_NCI_Visuals_Online.jpg/1200px-Good_Food_Display_-_NCI_Visuals_Online.jpg";
        Meal meal4 = new Meal(1,"Meal 4", 44,5,image2, new String[]{});
        Meal meal5 = new Meal(2,"Meal 5", 12,2,image2, new String[]{});
        Meal meal6 = new Meal(3,"Meal 6", 33,4,image2, new String[]{});
        MealPlan mealPlan2 = new MealPlan(new Meal[]{meal4, meal5, meal6}, new Nutrients(1250,80,122.4,450));

        mealPlans.add(mealPlan2);
        mealPlans.add(mealPlan);
        mealPlans.add(mealPlan);
        mealPlans.add(mealPlan2);
        mealPlans.add(mealPlan2);
        // END TEST



        adapter = new MealPlanAdapter(getContext(), mealPlans);
        recyclerView = view.findViewById(R.id.meal_plans_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }

}
