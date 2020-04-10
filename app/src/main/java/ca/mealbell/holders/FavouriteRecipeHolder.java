package ca.mealbell.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ca.mealbell.R;

public class FavouriteRecipeHolder extends RecyclerView.ViewHolder {

    public ImageView recipeImage;
    public TextView recipeTitle;

    public FavouriteRecipeHolder(@NonNull View itemView) {
        super(itemView);

        recipeImage = itemView.findViewById(R.id.recipe_imageview);
        recipeTitle = itemView.findViewById(R.id.recipe_title_textview);
    }
}
