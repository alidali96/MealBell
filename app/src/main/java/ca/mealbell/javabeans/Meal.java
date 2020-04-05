package ca.mealbell.javabeans;

import java.io.Serializable;
import java.util.Arrays;

/**
 * <p>This class responsible to hold <strong>meal</strong> returned json from request (API_URL + '/recipes/mealplans')</p>
 *
 * <h3>JSON Sample</h3>
 * <p>
 * "id": 1100990<br>
 * "title": "Blueberry, Chocolate & Cocao Superfood Pancakes - Gluten-Free/Paleo/Vegan"<br>
 * "readyInMinutes": 30<br>
 * "servings": 2<br>
 * "image": "blueberry-chocolate-cocao-superfood-pancakes-gluten-free-paleo-vegan-1100990.jpg"<br>
 * "imageUrls": ["blueberry-chocolate-cocao-superfood-pancakes-gluten-free-paleo-vegan-1100990.jpg"]<br>
 *
 * @author Ali Dali
 * @since 08-03-2020
 */
public class Meal implements Serializable {

    private int id;
    private String title;
    private int readyInMinutes;
    private int servings;
    private String image;
    private String[] imageUrls;

    public Meal(int id, String title, int readyInMinutes, int servings, String image, String[] imageUrls) {
        this.id = id;
        this.title = title;
        this.readyInMinutes = readyInMinutes;
        this.servings = servings;
        this.image = image;
        this.imageUrls = imageUrls;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getReadyInMinutes() {
        return readyInMinutes;
    }

    public int getServings() {
        return servings;
    }

    public String getImage() {
        return "https://spoonacular.com/recipeImages/" + image;
    }

    public String[] getImageUrls() {
        return imageUrls;
    }

    /**
     * @return Meal Information
     * @author Ali Dali
     * @since 08-03-2020
     */
    @Override
    public String toString() {
        return String.format("ID: %d\nTitle: %s\nReady In Minutes: %d\nServings: %d\nImage: %s\nImage Urls: %s\n", id, title, readyInMinutes, servings, image, Arrays.toString(imageUrls));
    }
}
