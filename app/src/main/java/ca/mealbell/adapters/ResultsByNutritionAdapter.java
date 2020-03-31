package ca.mealbell.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ca.mealbell.R;
import ca.mealbell.javabeans.RecipeByIngredient;
import ca.mealbell.javabeans.RecipeByNutrition;


public class ResultsByNutritionAdapter extends RecyclerView.Adapter<ResultsByNutritionAdapter.ResultsByNutritionHolder> {

    private ArrayList<RecipeByNutrition> recipes;
    Context context;

    public ResultsByNutritionAdapter( Context context, ArrayList<RecipeByNutrition> recipes) {
        this.recipes = recipes;
        this.context = context;
    }

    @NonNull
    @Override
    public ResultsByNutritionAdapter.ResultsByNutritionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.results_by_nutrition_holder, parent, false);
        return new ResultsByNutritionHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultsByNutritionAdapter.ResultsByNutritionHolder holder, int position) {
        RecipeByNutrition recipe = recipes.get(position);

        Picasso.get().load(recipe.getImage()).placeholder(R.drawable.dish).into(holder.recipeImageView);
        holder.titleTextView.setText(recipe.getTitle());
        holder.caloriesTextView.setText("" + recipe.getCalories());
        holder.fatTextView.setText(recipe.getFat());
        holder.proteinTextView.setText(recipe.getProtein());
        holder.carbsTextView.setText(recipe.getCarbs());
    }

    @Override
    public int getItemCount() {

        return recipes.size();
    }

    /**
     * Update adapter list (recipes)
     * @param newRecipes
     */
    public void updateList(ArrayList<RecipeByNutrition> newRecipes) {
        recipes = newRecipes;
        notifyDataSetChanged();
    }


    class ResultsByNutritionHolder extends RecyclerView.ViewHolder {
        ImageView recipeImageView;
        TextView titleTextView;
        TextView caloriesTextView;
        TextView fatTextView;
        TextView proteinTextView;
        TextView carbsTextView;

        public ResultsByNutritionHolder(@NonNull View itemView) {

            super(itemView);
            recipeImageView = itemView.findViewById(R.id.recipe_imageview);
            titleTextView = itemView.findViewById(R.id.recipe_title_textview);
            caloriesTextView = itemView.findViewById(R.id.calories_textview);
            fatTextView = itemView.findViewById(R.id.fat_textview);
            proteinTextView = itemView.findViewById(R.id.protein_textview);
            carbsTextView = itemView.findViewById(R.id.carbs_textview);
        }
    }
}
