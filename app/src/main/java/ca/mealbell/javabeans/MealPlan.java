package ca.mealbell.javabeans;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Arrays;

/**
 * <p>This class responsible to hold returned json from request (API_URL + '/recipes/mealplans')</p>
 *
 * @author Ali Dali
 * @since 22-02-2020
 * @see Meal
 * @see Nutrients
 */
public class MealPlan implements Serializable {


    private Meal[] meals;
    private Nutrients nutrients;

    public MealPlan(Meal[] meals, Nutrients nutrients) {
        this.meals = meals;
        this.nutrients = nutrients;
    }

    public Meal[] getMeals() {
        return meals;
    }

    public Nutrients getNutrients() {
        return nutrients;
    }

    /**
     * @return Meal Plan Information
     * @author Ali Dali
     * @since 08-03-2020
     */    @Override
    public String toString() {
        return String.format("Meal: %s\nNutrients: %s\n", Arrays.toString(meals), nutrients);
    }
}
