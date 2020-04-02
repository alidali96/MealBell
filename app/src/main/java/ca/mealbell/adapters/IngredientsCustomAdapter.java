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
import ca.mealbell.javabeans.Ingredient;

public class IngredientsCustomAdapter extends RecyclerView.Adapter<IngredientsCustomAdapter.CustomViewHolder> {

    private ArrayList<Ingredient> ingredients;
    private Context context;

    public IngredientsCustomAdapter(ArrayList<Ingredient> ingredients, Context context) {
        this.ingredients = ingredients;
        this.context = context;
    }

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

        Picasso.get().load("https://spoonacular.com/cdn/ingredients_100x100/" + ingredient.getImage()).placeholder(R.drawable.dish).into(holder.recipeImage);
        holder.recipeName.setText(ingredient.getName());
        holder.amount.setText(ingredient.getAmount() + "");
        holder.unit.setText(ingredient.getUnit());

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
