package ca.mealbell.javabeans;

import java.io.Serializable;

/**
 * This class responsible to hold returned json from request (API_URL + '/recipes/findByIngredients')
 * @author Ali Dali
 * @date 22-02-2020
 */
public class RecipeByIngredient implements Serializable {

    /**
     * This is a sample of json
     *
     * "id":641803
     * "title":"Easy & Delish! ~ Apple Crumble"
     * "image":"https://spoonacular.com/recipeImages/Easy---Delish--Apple-Crumble-641803.jpg"
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
     * @return Recipe Information
     * @author Ali Dali
     * @date 22-02-2020
     */
    @Override
    public String toString() {
        return String.format("ID: %d\nTitle: %s\nImage: %s\nUsed Ingredients: %d\nMissed Ingredients: %d\nLikes: %d\n", id, title, image, usedIngredientCount, missedIngredientCount, likes);
    }
}
