package ca.mealbell.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ramotion.foldingcell.FoldingCell;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;

import ca.mealbell.MealPlanFragment;
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
    public ImageView imageC;
    public ImageView imageC2;
    public ImageView imageC3;
    public ImageView[] images = new ImageView[3];

    public TextView titleC;
    public TextView titleC2;
    public TextView titleC3;
    public TextView[] titles = new TextView[3];
    FragmentManager fragmentManager;

    // MEAL PLAN DETAILS
    public TextView calories;
    public TextView protein;
    public TextView fat;
    public TextView carbohydrates;

    RecyclerView recyclerView;
    MealAdapter adapter;

    ArrayList<Meal> meals;
    MealPlan mealPlan;

    public MealPlanHolder(@NonNull View itemView, FragmentManager fragmentManager) {
        super(itemView);
        this.fragmentManager = fragmentManager;

        foldingCell = itemView.findViewById(R.id.folding_cell_meal);

//        // Toggle FoldingCell
//        mealPlanCardHolder.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                foldingCell.toggle(false);
//            }
//        });
//
//        mealPlanDetailsCardHolder.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                foldingCell.toggle(false);
//            }
//        });

        imageC = itemView.findViewById(R.id.image);
        imageC2 = itemView.findViewById(R.id.image2);
        imageC3 = itemView.findViewById(R.id.image3);
        titleC = itemView.findViewById(R.id.title);
        titleC2 = itemView.findViewById(R.id.title2);
        titleC3 = itemView.findViewById(R.id.title3);

        images[0] = imageC;
        images[1] = imageC2;
        images[2] = imageC3;
        titles[0] = titleC;
        titles[1] = titleC2;
        titles[2] = titleC3;


        calories = itemView.findViewById(R.id.calories);
        protein = itemView.findViewById(R.id.protein);
        fat = itemView.findViewById(R.id.fat);
        carbohydrates = itemView.findViewById(R.id.carbohydrates);

        recyclerView = itemView.findViewById(R.id.meals_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));

        itemView.setOnClickListener(this);

    }

    public void setMealPlan(MealPlan mealPlan) {
        // POPULATE
        for (int i = 0; i < 3; i++) {
            Picasso.get().load(mealPlan.getMeals()[i].getImage()).placeholder(R.drawable.meal).into(images[i]);
            titles[i].setText(mealPlan.getMeals()[i].getTitle());
        }

        calories.setText(mealPlan.getNutrients().getCalories() + "");
        protein.setText(mealPlan.getNutrients().getProtein() + "");
        fat.setText(mealPlan.getNutrients().getFat() + "");
        carbohydrates.setText(mealPlan.getNutrients().getCarbohydrates() + "");

        meals = new ArrayList<>();
        Collections.addAll(meals, mealPlan.getMeals());

        adapter = new MealAdapter(itemView.getContext(), this, meals);
        recyclerView.setAdapter(adapter);
    }

    public void addFragment(MealPlan mealPlan, int position) {
        // Tried to add Fragment dynamically to the layout
        fragmentManager.beginTransaction().add(foldingCell.getId(), new MealPlanFragment(mealPlan, position)).commit();
    }

    @Override
    public void onClick(View v) {
        foldingCell.toggle(false);
    }
}
