package ca.mealbell.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ca.mealbell.R;
import ca.mealbell.holders.FavouriteRecipeHolder;
import ca.mealbell.holders.MealHolder;
import ca.mealbell.javabeans.RecipeInformation;

public class FavouriteRecipeAdapter extends RecyclerView.Adapter<FavouriteRecipeHolder> {

    private Context context;
    private ArrayList<RecipeInformation> recipes;

    public FavouriteRecipeAdapter(Context context, ArrayList<RecipeInformation> recipes) {
        this.context = context;
        this.recipes = recipes;
    }

    @NonNull
    @Override
    public FavouriteRecipeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.favourite_recipe_holder, parent, false);
        return new FavouriteRecipeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FavouriteRecipeHolder holder, int position) {
        final RecipeInformation recipe = recipes.get(position);

        Picasso.get().load(recipe.getImage()).placeholder(R.drawable.dish).into(holder.recipeImage);
        holder.recipeTitle.setText(recipe.getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putInt("RECIPE_ID", recipe.getId());
                Navigation.findNavController(holder.itemView).navigate(R.id.nav_Recipe_details, args);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }
}
