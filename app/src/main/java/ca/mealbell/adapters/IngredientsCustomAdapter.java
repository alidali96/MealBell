package ca.mealbell.adapters;

/**
 *  @author: Fadi Findakly
 *  @date: 03-29-2020
 */

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.prefs.Preferences;

import ca.mealbell.R;
import ca.mealbell.javabeans.Ingredient;
import ca.mealbell.ui.SettingsFragment;

public class IngredientsCustomAdapter extends RecyclerView.Adapter<IngredientsCustomAdapter.CustomViewHolder> {

    private ArrayList<Ingredient> ingredients;
    private Context context;
    private SettingsFragment.Measurement unitSystem;


    public IngredientsCustomAdapter(ArrayList<Ingredient> ingredients, Context context) {
        this.ingredients = ingredients;
        this.context = context;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        unitSystem = SettingsFragment.Measurement.valueOf(preferences.getString(SettingsFragment.MEASUREMENT,SettingsFragment.Measurement.US.toString()));
    }

    //SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences


    @NonNull
    @Override
    public IngredientsCustomAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingredient_equipment_row, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsCustomAdapter.CustomViewHolder holder, int position) {

        final Ingredient ingredient = ingredients.get(position);

        Picasso.get().load("https://spoonacular.com/cdn/ingredients_100x100/" + ingredient.getImage()).placeholder(R.drawable.ingredient).into(holder.recipeImage);
        holder.recipeName.setText(ingredient.getName());

        if (unitSystem == SettingsFragment.Measurement.US) {
            holder.amount.setText(String.format("%.2f", ingredient.getMeasures().getUs().getAmount()));
            holder.unit.setText(ingredient.getMeasures().getUs().getUnitShort() + "");
        } else {
            holder.amount.setText(String.format("%.2f", ingredient.getMeasures().getMetric().getAmount()));
            holder.unit.setText(ingredient.getMeasures().getMetric().getUnitShort() + "");
        }


    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected ImageView recipeImage;
        protected TextView recipeName;
        protected TextView amount;
        protected TextView unit;


        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            this.recipeImage = itemView.findViewById(R.id.ingredient_imageView);
            this.recipeName = itemView.findViewById(R.id.ingredientName_textView);
            this.amount = itemView.findViewById(R.id.amount_textView);
            this.unit = itemView.findViewById(R.id.unit_textView);
        }
    }
}
