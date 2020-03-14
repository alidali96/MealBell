package ca.mealbell.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ca.mealbell.MealPlanDetailsFragment;
import ca.mealbell.MealPlanFragment;
import ca.mealbell.R;
import ca.mealbell.holders.MealPlanHolder;
import ca.mealbell.javabeans.Meal;
import ca.mealbell.javabeans.MealPlan;

public class MealPlanAdapter extends RecyclerView.Adapter<MealPlanHolder> {


    Context context;
    List<MealPlan> mealPlans;
    FragmentManager fragmentManager;

    public MealPlanAdapter(Context context, List<MealPlan> mealPlans, FragmentManager fragmentManager) {
        this.context = context;
        this.mealPlans = mealPlans;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public MealPlanHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.meal_plan_holder, parent, false);
        return new MealPlanHolder(view, fragmentManager);
    }

    @Override
    public void onBindViewHolder(@NonNull MealPlanHolder holder, int position) {
        MealPlan mealPlan = mealPlans.get(position);
        ArrayList<Meal> meals = new ArrayList<>();
        Collections.addAll(meals, mealPlan.getMeals());


        // TODO: Create Meal Plan Fragment
        holder.setMealPlan(mealPlan);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return mealPlans.size();
    }
}
