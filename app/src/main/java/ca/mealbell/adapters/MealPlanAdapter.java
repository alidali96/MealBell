package ca.mealbell.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import ca.mealbell.MealPlanDetailsFragment;
import ca.mealbell.MealPlanFragment;
import ca.mealbell.R;
import ca.mealbell.holders.MealPlanHolder;
import ca.mealbell.javabeans.Meal;
import ca.mealbell.javabeans.MealPlan;
import ca.mealbell.ui.MealPlannerFragment;

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
        return new MealPlanHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealPlanHolder holder, int position) {
        MealPlan mealPlan = mealPlans.get(position);
        ArrayList<Meal> meals = new ArrayList<>();
        Collections.addAll(meals, mealPlan.getMeals());
//        for (int i = 0; i < 3; i++) {
//            Picasso.get().load(mealPlan.getMeals()[i].getImage()).placeholder(R.drawable.meal).into(holder.images[i]);
//            holder.titles[i].setText(mealPlan.getMeals()[i].getTitle());
//        }
//
//        holder.calories.setText(mealPlan.getNutrients().getCalories() + "");
//        holder.protein.setText(mealPlan.getNutrients().getProtein() + "");
//        holder.fat.setText(mealPlan.getNutrients().getFat() + "");
//        holder.carbohydrates.setText(mealPlan.getNutrients().getCarbohydrates() + "");

        // TODO: Create Meal Plan Fragment
        fragmentManager.beginTransaction().replace(holder.mealPlanCardHolder.getId(), new MealPlanFragment(mealPlan)).commit();
        fragmentManager.beginTransaction().replace(holder.mealPlanDetailsCardHolder.getId(), new MealPlanDetailsFragment(meals)).commit();

    }

    @Override
    public int getItemCount() {
        return mealPlans.size();
    }
}
