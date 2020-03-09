package ca.mealbell.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ca.mealbell.R;
import ca.mealbell.holders.MealPlanHolder;
import ca.mealbell.javabeans.MealPlan;

public class MealPlanAdapter extends RecyclerView.Adapter<MealPlanHolder> {


    List<MealPlan> mealPlans;
    Context context;

    public MealPlanAdapter(List<MealPlan> mealPlans, Context context) {
        this.mealPlans = mealPlans;
        this.context = context;
    }

    @NonNull
    @Override
    public MealPlanHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.meal_plan_card, parent, false);
        return new MealPlanHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealPlanHolder holder, int position) {
        MealPlan mealPlan = mealPlans.get(position);

        for (int i = 0; i < 3; i++) {
            Picasso.get().load(mealPlan.getMeals()[i].getImage()).placeholder(R.drawable.meal).into(holder.images[i]);
            holder.titles[i].setText(mealPlan.getMeals()[i].getTitle());
        }

        holder.calories.setText(mealPlan.getNutrients().getCalories() + "");
        holder.protein.setText(mealPlan.getNutrients().getProtein() + "");
        holder.fat.setText(mealPlan.getNutrients().getFat() + "");
        holder.carbohydrates.setText(mealPlan.getNutrients().getCarbohydrates() + "");
    }

    @Override
    public int getItemCount() {
        return mealPlans.size();
    }
}
