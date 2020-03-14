package ca.mealbell.javabeans;

import java.io.Serializable;

/**
 * <p>This class responsible to hold returned json from request (API_URL + '/recipes/findByIngredients')</p>
 *
 * <h3>JSON Sample</h3>
 *
 * "id":641803<br>
 * "title":"Easy & Delish! ~ Apple Crumble"<br>
 * "imageC":"https://spoonacular.com/recipeImages/Easy---Delish--Apple-Crumble-641803.jpg"<br>
 * "usedIngredientCount":3<br>
 * "missedIngredientCount":4<br>
 * "likes":1<br>
 *
 * @author Ali Dali
 * @since 22-02-2020
 */
public class RecipeByIngredient implements Serializable {

    /**
     * "id":641803
     * "title":"Easy & Delish! ~ Apple Crumble"
     * "imageC":"https://spoonacular.com/recipeImages/Easy---Delish--Apple-Crumble-641803.jpg"
     * "usedIngredientCount":3
     * "missedIngredientCount":4
     * "likes":1
     */

    private int id;
    private String title;
    private String image;
    private int usedIngredientCount;
    private int missedIngredientCount;
    private int likes;

    public RecipeByIngredient(int id, String title, String image, int usedIngredientCount, int missedIngredientCount, int likes) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.usedIngredientCount = usedIngredientCount;
        this.missedIngredientCount = missedIngredientCount;
        this.likes = likes;
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

    public int getUsedIngredientCount() {
        return usedIngredientCount;
    }

    public int getMissedIngredientCount() {
        return missedIngredientCount;
    }

    public int getLikes() {
        return likes;
    }


    /**
     * This will print all information about a recipe by ingredients (search result)
     *
     * @return Recipe Information
     * @author Ali Dali
     * @since 22-02-2020
     */
    @Override
    public String toString() {
        return String.format("ID: %d\nTitle: %s\nImage: %s\nUsed Ingredients: %d\nMissed Ingredients: %d\nLikes: %d\n", id, title, image, usedIngredientCount, missedIngredientCount, likes);
    }
}
