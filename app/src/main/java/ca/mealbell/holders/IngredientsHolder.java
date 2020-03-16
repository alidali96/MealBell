package ca.mealbell.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ca.mealbell.R;

public class IngredientsHolder extends RecyclerView.ViewHolder {

    public TextView ingredient;
    public ImageView remove;

    public IngredientsHolder(@NonNull View itemView) {
        super(itemView);
        ingredient = itemView.findViewById(R.id.ingredient);
        remove = itemView.findViewById(R.id.remove_icon);
    }
}
