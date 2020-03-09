package ca.mealbell.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ca.mealbell.R;

public class MealHolder extends RecyclerView.ViewHolder {

    public ImageView image;
    public TextView title;
    public TextView servings;
    public TextView readyInMinutes;

    public MealHolder(@NonNull View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.image);
        title = itemView.findViewById(R.id.title);
        servings = itemView.findViewById(R.id.servings);
        readyInMinutes = itemView.findViewById(R.id.ready_in_minutes);
    }
}
