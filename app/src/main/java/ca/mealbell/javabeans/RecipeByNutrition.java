package ca.mealbell.javabeans;

import java.io.Serializable;

/**
 * <p>This class is responsible for holding a returned json from request (API_URL + '/recipes/findByNutrients')</p>
 *
 * <h3>JSON Sample</h3>
 *
 *"id":551315<br>
 *"title":"More Power Gingerbread Smoothie"<br>
 *"image":"https://spoonacular.com/recipeImages/More-Power-Gingerbread-Smoothie-551315.jpg"<br>
 *"imageType":"jpg"<br>
 *"calories":645<br>
 *"protein":"37g"<br>
 *"fat":"14g"<br>
 *"carbs":"99g"<br>
 *
 * @author Fadi Findakly
 * @since 29-02-2020
 */

public class RecipeByNutrition implements Serializable {

    private int id;
    private String title;
    private String image;
    private String imageType;
    private int calories;
    private String protein;
    private String fat;
    private String carbs;


    /**
     *
     * @param id
     * @param title
     * @param image
     * @param imageType
     * @param calories
     * @param protein
     * @param fat
     * @param carbs
     */
    public RecipeByNutrition(int id, String title, String image, String imageType, int calories, String protein, String fat, String carbs) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.imageType = imageType;
        this.calories = calories;
        this.protein = protein;
        this.fat = fat;
        this.carbs = carbs;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getImageType() {
        return imageType;
    }

    public int getCalories() {
        return calories;
    }

    public String getProtein() {
        return protein;
    }

    public String getFat() {
        return fat;
    }

    public String getCarbs() {
        return carbs;
    }


    /**
     * This will output all data fields about a recipe by nutrition (search result)
     *
     * @return Recipe Information
     * @author Fadi Findakly
     * @since 29-02-2020
     */
    public String toString() {
        return String.format("ID: %d\nTitle: %s\nImage: %s\nImage type: %s\nCalories: %d\nProtein: %d\nFat: %d\nCarbs: %d\n", id, title, image, imageType, calories, protein, fat, carbs);
    }
}
