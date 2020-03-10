package ca.mealbell;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ca.mealbell.adapters.MealAdapter;
import ca.mealbell.adapters.MealPlanAdapter;
import ca.mealbell.javabeans.Meal;


/**
 * A simple {@link Fragment} subclass.
 */
public class MealPlanDetailsFragment extends Fragment {

    RecyclerView recyclerView;
    MealAdapter adapter;

    ArrayList<Meal> meals;

    public MealPlanDetailsFragment() {
        // Required empty public constructor
    }

    public MealPlanDetailsFragment(ArrayList<Meal> meals) {
        this.meals = meals;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_meal_plan_details, container, false);


        recyclerView = view.findViewById(R.id.meals_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MealAdapter(getContext(), meals);
        recyclerView.setAdapter(adapter);

        return view;
    }

}
