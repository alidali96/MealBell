package ca.mealbell.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import ca.mealbell.R;
import ca.mealbell.javabeans.Ingredient;
import ca.mealbell.javabeans.RecipeByIngredient;

/**
 * Adapter for recipes results (by ingredients)
 *
 * @author Ali Dali
 * @since 01-03-2020
 */
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
    public void onBindViewHolder(@NonNull final ResultsByIngredientsHolder holder, int position) {
        final RecipeByIngredient recipe = recipes.get(position);

        Picasso.get().load(recipe.getImage()).placeholder(R.drawable.dish).into(holder.image);
        holder.title.setText(recipe.getTitle());


        holder.ingredientsContainer.removeAllViewsInLayout();

        for (Ingredient usedIngredient :
                recipe.getUsedIngredients()) {
            TextView ingredient = createIngredientTextView();

            ingredient.setText(WordUtils.capitalize(usedIngredient.getName()));
            ingredient.setTextColor(ContextCompat.getColor(context, R.color.colorGreen));


            holder.ingredientsContainer.addView(ingredient);
            holder.ingredientsContainer.addView(getDivider());
        }

        for (Ingredient usedIngredient :
                recipe.getMissedIngredients()) {
            TextView ingredient = createIngredientTextView();

            ingredient.setText(WordUtils.capitalize(usedIngredient.getName()));
            ingredient.setTextColor(ContextCompat.getColor(context, R.color.colorRed));

            holder.ingredientsContainer.addView(ingredient);
            holder.ingredientsContainer.addView(getDivider());
        }

        // Remove last divider
        holder.ingredientsContainer.removeViewAt(holder.ingredientsContainer.getChildCount() - 1);

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

    /**
     * Update adapter list (recipes)
     *
     * @param newRecipes
     */
    public void updateList(ArrayList<RecipeByIngredient> newRecipes) {
        recipes = newRecipes;
        notifyDataSetChanged();
    }

    private TextView createIngredientTextView() {
        TextView textView = new TextView(context);
        textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        textView.setGravity(Gravity.CENTER);
        textView.setPadding(15, 0, 15, 0);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        return textView;
    }

    private TextView getDivider() {
        TextView textView = new TextView(context);
        textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setTextColor(ContextCompat.getColor(context, R.color.tabtextcolour));
        textView.setText("|");
        return textView;
    }

    /**
     * Results holder
     *
     * @author Ali Dali
     * @version 1.0
     * @since 01-03-2020
     */
    class ResultsByIngredientsHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView title;
        LinearLayout ingredientsContainer;

        public ResultsByIngredientsHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            ingredientsContainer = itemView.findViewById(R.id.ingredients_container);
        }
    }
}


