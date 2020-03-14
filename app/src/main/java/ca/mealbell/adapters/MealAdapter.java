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
import ca.mealbell.holders.MealHolder;
import ca.mealbell.javabeans.Meal;


/**
 *
 * @author Ali Dali
 * @since 09-03-2020
 * @see MealHolder
 */
public class MealAdapter extends RecyclerView.Adapter<MealHolder> {

    Context context;
    List<Meal> meals;

    public MealAdapter(Context context,  List<Meal> meals) {
        this.context = context;
        this.meals = meals;
    }

    @NonNull
    @Override
    public MealHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.meal_holder, parent, false);
        return new MealHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealHolder holder, int position) {
        Meal meal = meals.get(position);

        Picasso.get().load(meal.getImage()).placeholder(R.drawable.meal).into(holder.image);
        holder.title.setText(meal.getTitle());
        holder.servings.setText(meal.getServings() + "");
        holder.readyInMinutes.setText(meal.getReadyInMinutes() + "");

        // TODO: Set on click listener on each Meal
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }
}
