package ca.mealbell.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ca.mealbell.R;
import ca.mealbell.holders.MealPlanHolder;
import ca.mealbell.javabeans.Meal;
import ca.mealbell.javabeans.MealPlan;

/**
 * @author Ali Dali
 * @since 09-03-2020
 */
public class MealPlanAdapter extends RecyclerView.Adapter<MealPlanHolder> {


    Context context;
    List<MealPlan> mealPlans;

    public MealPlanAdapter(Context context, List<MealPlan> mealPlans) {
        this.context = context;
        this.mealPlans = mealPlans;
    }

    @NonNull
    @Override
    public MealPlanHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.meal_plan_holder, parent, false);
        return new MealPlanHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealPlanHolder holder, int position) {
        MealPlan mealPlan = mealPlans.get(position);

        // Set Meal Plan
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
