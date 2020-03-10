package ca.mealbell.holders;

import android.media.Image;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ramotion.foldingcell.FoldingCell;

import ca.mealbell.R;

/**
 * @author Ali Dali
 * @since 09-03-2020
 */
public class MealPlanHolder extends RecyclerView.ViewHolder {

//    public ImageView image;
//    public ImageView image2;
//    public ImageView image3;
//    public ImageView[] images = new ImageView[3];
//
//    public TextView title;
//    public TextView title2;
//    public TextView title3;
//    public TextView[] titles = new TextView[3];
//
//    public TextView calories;
//    public TextView protein;
//    public TextView fat;
//    public TextView carbohydrates;


    public FrameLayout mealPlanCardHolder;
    public FrameLayout mealPlanDetailsCardHolder;
    public FoldingCell foldingCell;

    public MealPlanHolder(@NonNull View itemView) {
        super(itemView);

//        image = itemView.findViewById(R.id.image);
//        image2 = itemView.findViewById(R.id.image2);
//        image3 = itemView.findViewById(R.id.image3);
//
//        title = itemView.findViewById(R.id.title);
//        title2 = itemView.findViewById(R.id.title2);
//        title3 = itemView.findViewById(R.id.title3);
//
//        calories = itemView.findViewById(R.id.calories);
//        protein = itemView.findViewById(R.id.protein);
//        fat = itemView.findViewById(R.id.fat);
//        carbohydrates = itemView.findViewById(R.id.carbohydrates);
//
//        images[0] = image;
//        images[1] = image2;
//        images[2] = image3;
//
//        titles[0] = title;
//        titles[1] = title2;
//        titles[2] = title3;

        foldingCell = itemView.findViewById(R.id.folding_cell_meal);

        mealPlanCardHolder = itemView.findViewById(R.id.meal_plan_card_holder);
        mealPlanDetailsCardHolder = itemView.findViewById(R.id.meal_plan_details_card_holder);


        mealPlanCardHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foldingCell.toggle(false);
            }
        });

        mealPlanDetailsCardHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foldingCell.toggle(false);
            }
        });

    }
}
