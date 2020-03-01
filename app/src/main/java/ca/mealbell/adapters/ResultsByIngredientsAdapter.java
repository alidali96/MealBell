package ca.mealbell.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ca.mealbell.R;
import ca.mealbell.javabeans.RecipeByIngredient;

public class ResultsByIngredientsAdapter extends RecyclerView.Adapter<ResultsByIngredientsAdapter.ResultsByIngredientsHolder> {

    List<RecipeByIngredient> recipes;
    Context context;

    public ResultsByIngredientsAdapter(Context context, List<RecipeByIngredient> recipes) {
        this.context = context;
        this.recipes = recipes;
    }

    @NonNull
    @Override
    public ResultsByIngredientsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.results_by_ingredients_holder, parent, false);
        return new ResultsByIngredientsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultsByIngredientsHolder holder, int position) {
        RecipeByIngredient recipe = recipes.get(position);

        holder.title.setText(recipe.getTitle());
        holder.usedIngredients.setText(recipe.getUsedIngredientCount());
        holder.missedIngredients.setText(recipe.getMissedIngredientCount());
        holder.likes.setText(recipe.getLikes());
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }





    class ResultsByIngredientsHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView title;
        TextView usedIngredients;
        TextView missedIngredients;
        TextView likes;

        public ResultsByIngredientsHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            usedIngredients = itemView.findViewById(R.id.used);
            missedIngredients = itemView.findViewById(R.id.missed);
            likes = itemView.findViewById(R.id.likes);
        }
    }
}


