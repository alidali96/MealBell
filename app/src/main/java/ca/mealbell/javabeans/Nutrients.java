package ca.mealbell.javabeans;

import androidx.annotation.NonNull;

import java.io.Serializable;

/**
 * <p>This class responsible to hold <strong>nutrients</strong> returned json from request (API_URL + '/recipes/mealplans')</p>
 *
 * <h3>JSON Sample</h3>
 * <p>
 * "calories": 988.97<br>
 * "protein": 58.14<br>
 * "fat": 31.69<br>
 * "carbohydrates": 97.87<br>
 *
 * @author Ali Dali
 * @since 08-03-2020
 */
public class Nutrients implements Serializable {

    private double calories;
    private double protein;
    private double fat;
    private double carbohydrates;

    public Nutrients(double calories, double protein, double fat, double carbohydrates) {
        this.calories = calories;
        this.protein = protein;
        this.fat = fat;
        this.carbohydrates = carbohydrates;
    }

    public double getCalories() {
        return calories;
    }

    public double getProtein() {
        return protein;
    }

    public double getFat() {
        return fat;
    }

    public double getCarbohydrates() {
        return carbohydrates;
    }

    /**
     * @return Nutrients Information
     * @author Ali Dali
     * @since 08-03-2020
     */
    @Override
    public String toString() {
        return String.format("Calories: %f\nProtein: %f\nFat: %f\nCarbohydrates: %f\n", calories, protein, fat, carbohydrates);
    }
}
