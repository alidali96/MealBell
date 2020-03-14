package ca.mealbell;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ca.mealbell.javabeans.MealPlan;


/**
 * A simple {@link Fragment} subclass.
 */
public class MealPlanFragment extends Fragment {

    public ImageView image;
    public ImageView image2;
    public ImageView image3;
    public ImageView[] images = new ImageView[3];

    public TextView title;
    public TextView title2;
    public TextView title3;
    public TextView[] titles = new TextView[3];

    public TextView calories;
    public TextView protein;
    public TextView fat;
    public TextView carbohydrates;


    public MealPlan mealPlan;
    int id;

    public MealPlanFragment() {
        // Required empty public constructor
    }

    public MealPlanFragment(MealPlan mealPlan, int id) {
        this.mealPlan = mealPlan;
        this.id = id;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_meal_plan, container, false);

        view.setId(id);
        Log.e(this.getClass().getTypeName(), view.getId() + "");



        // ATTACH
        image = view.findViewById(R.id.image);
        image2 = view.findViewById(R.id.image2);
        image3 = view.findViewById(R.id.image3);

        title = view.findViewById(R.id.title);
        title2 = view.findViewById(R.id.title2);
        title3 = view.findViewById(R.id.title3);

        calories = view.findViewById(R.id.calories);
        protein = view.findViewById(R.id.protein);
        fat = view.findViewById(R.id.fat);
        carbohydrates = view.findViewById(R.id.carbohydrates);

        // JOIN
        images[0] = image;
        images[1] = image2;
        images[2] = image3;

        titles[0] = title;
        titles[1] = title2;
        titles[2] = title3;


        if(mealPlan == null)
            return view;

        // POPULATE
        for (int i = 0; i < 3; i++) {
            Picasso.get().load(mealPlan.getMeals()[i].getImage()).placeholder(R.drawable.meal).into(images[i]);
            titles[i].setText(mealPlan.getMeals()[i].getTitle());
        }

        calories.setText(mealPlan.getNutrients().getCalories() + "");
        protein.setText(mealPlan.getNutrients().getProtein() + "");
        fat.setText(mealPlan.getNutrients().getFat() + "");
        carbohydrates.setText(mealPlan.getNutrients().getCarbohydrates() + "");


        return view;
    }

}
