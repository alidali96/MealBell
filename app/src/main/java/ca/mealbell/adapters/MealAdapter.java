package ca.mealbell.adapters;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ca.mealbell.R;
import ca.mealbell.holders.MealHolder;
import ca.mealbell.javabeans.Meal;


/**
 * @author Ali Dali
 * @see MealHolder
 * @since 09-03-2020
 */
public class MealAdapter extends RecyclerView.Adapter<MealHolder> {

    Context context;
    List<Meal> meals;

    public MealAdapter(Context context, List<Meal> meals) {
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
    public void onBindViewHolder(@NonNull final MealHolder holder, int position) {
        final Meal meal = meals.get(position);

        Picasso.get().load("https://spoonacular.com/recipeImages/" + meal.getImage()).placeholder(R.drawable.meal).into(holder.image);
        holder.title.setText(meal.getTitle());
        holder.servings.setText(meal.getServings() + "");
        holder.readyInMinutes.setText(meal.getReadyInMinutes() + "");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putInt("RECIPE_ID", meal.getId());
                Navigation.findNavController(holder.itemView).navigate(R.id.nav_Recipe_details, args);
            }
        });
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }
}
