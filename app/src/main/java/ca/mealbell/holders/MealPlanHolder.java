package ca.mealbell.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ramotion.foldingcell.FoldingCell;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;

import ca.mealbell.R;
import ca.mealbell.adapters.MealAdapter;
import ca.mealbell.javabeans.Meal;
import ca.mealbell.javabeans.MealPlan;

/**
 * @author Ali Dali
 * @since 09-03-2020
 */
public class MealPlanHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public FoldingCell foldingCell;

    // MEAL PLAN CARD
    public ImageView image;
    public ImageView image2;
    public ImageView image3;
    public ImageView[] images = new ImageView[3];

    public TextView title;
    public TextView title2;
    public TextView title3;
    public TextView[] titles = new TextView[3];

    // MEAL PLAN DETAILS
    public TextView calories;
    public TextView protein;
    public TextView fat;
    public TextView carbohydrates;

    public View mealPlanContainer;
    public LinearLayout open;
    public LinearLayout close;

    RecyclerView recyclerView;
    MealAdapter adapter;


    public MealPlanHolder(@NonNull View itemView) {
        super(itemView);

        attachViews(itemView);
    }

    public void attachViews(View view) {
        // Folding Cell
        foldingCell = view.findViewById(R.id.folding_cell_meal);

        // Images
        image = view.findViewById(R.id.image);
        image2 = view.findViewById(R.id.image2);
        image3 = view.findViewById(R.id.image3);
        images[0] = image;
        images[1] = image2;
        images[2] = image3;

        // Titles
        title = view.findViewById(R.id.title);
        title2 = view.findViewById(R.id.title2);
        title3 = view.findViewById(R.id.title3);
        titles[0] = title;
        titles[1] = title2;
        titles[2] = title3;


        // Nutrition
        calories = view.findViewById(R.id.calories);
        protein = view.findViewById(R.id.protein);
        fat = view.findViewById(R.id.fat);
        carbohydrates = view.findViewById(R.id.carbohydrates);


        // Click Listener (open / close)
        mealPlanContainer = view.findViewById(R.id.meal_plan_container);
        mealPlanContainer.setOnClickListener(this);

//        open = view.findViewById(R.id.open);
//        open.setOnClickListener(this);

        close = view.findViewById(R.id.close);
        close.setOnClickListener(this);

        // Recycler View (details)
        recyclerView = view.findViewById(R.id.meals_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

    }

    public void setMealPlan(MealPlan mealPlan) {
        // Populate Meal Card
        for (int i = 0; i < mealPlan.getMeals().length; i++) {
            Picasso.get().load(mealPlan.getMeals()[i].getImage()).placeholder(R.drawable.meal).into(images[i]);
            titles[i].setText(mealPlan.getMeals()[i].getTitle());
        }

        // Populate Meal Details
        calories.setText(mealPlan.getNutrients().getCalories() + "");
        protein.setText(mealPlan.getNutrients().getProtein() + "");
        fat.setText(mealPlan.getNutrients().getFat() + "");
        carbohydrates.setText(mealPlan.getNutrients().getCarbohydrates() + "");

        // Get Meals from Meal Plan
        ArrayList<Meal> meals = new ArrayList<>();
        Collections.addAll(meals, mealPlan.getMeals());
        // Set Adapter for Meal Plan Details
        adapter = new MealAdapter(itemView.getContext(),  meals);
        recyclerView.setAdapter(adapter);
//        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
    }

    @Override
    public void onClick(View v) {
        foldingCell.toggle(false);
    }
}
