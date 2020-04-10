package ca.mealbell.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ca.mealbell.R;
import ca.mealbell.database.DatabaseHandler;
import ca.mealbell.holders.FavouriteRecipeHolder;
import ca.mealbell.javabeans.RecipeInformation;

/**
 * @author Ali Dali
 * @since 08-04-2020
 */

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
                args.putParcelable("RECIPE", recipe);
                Navigation.findNavController(holder.itemView).navigate(R.id.action_nav_favourite_recipes_to_favouritesViewPager, args);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setIcon(R.drawable.ic_warning_black_24dp);
                alert.setTitle("Remove Recipe");
                alert.setMessage("Are you sure you want to remove \"" + recipe.getTitle() + "\" from favourites?");
                alert.setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseHandler.getInstance(context).deleteRecipe(recipe);
                        recipes.remove(holder.getAdapterPosition());
                        notifyItemRemoved(holder.getAdapterPosition());
                        Toast.makeText(context, "Recipe removed from favourites", Toast.LENGTH_LONG).show();
                    }
                });
                alert.setNegativeButton("Cancel", null);
                alert.create();
                alert.show();
                return true;
            }
        });


        // Animate
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.down_to_top);
        holder.itemView.startAnimation(animation);

    }


    @Override
    public int getItemCount() {
        return recipes.size();
    }
}
