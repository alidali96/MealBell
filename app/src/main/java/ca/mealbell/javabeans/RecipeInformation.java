package ca.mealbell.javabeans;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

/**
 * <p>This class is responsible for holding a returned json object from the API</p>
 *
 * @author Fadi Findakly
 * @since 03-29-2020
 */

public class RecipeInformation implements Parcelable {

    private int id;
    private String title;
    private String image;
    private String summary;
    private String instructions;
    private Ingredient[] extendedIngredients;
    private Equipement[] equipments;
    private int readyInMinutes;
    private int servings;
    private String sourceUrl;
    private String spoonacularSourceUrl;

    public RecipeInformation(int id, String title, String image, String summary, String instructions, Ingredient[] extendedIngredients, Equipement[] equipments, int readyInMinutes, int servings, String sourceUrl, String spoonacularSourceUrl) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.summary = summary;
        this.instructions = instructions;
        this.extendedIngredients = extendedIngredients;
        this.equipments = equipments;
        this.readyInMinutes = readyInMinutes;
        this.servings = servings;
        this.sourceUrl = sourceUrl;
        this.spoonacularSourceUrl = spoonacularSourceUrl;
    }

    public RecipeInformation(int id, String title, String image, String summary, String instructions, Ingredient[] extendedIngredients, Equipement[] equipments, int readyInMinutes, int servings) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.summary = summary;
        this.instructions = instructions;
        this.extendedIngredients = extendedIngredients;
        this.equipments = equipments;
        this.readyInMinutes = readyInMinutes;
        this.servings = servings;
    }

    protected RecipeInformation(Parcel in) {
        id = in.readInt();
        title = in.readString();
        image = in.readString();
        summary = in.readString();
        instructions = in.readString();
        extendedIngredients = in.createTypedArray(Ingredient.CREATOR);
        equipments = in.createTypedArray(Equipement.CREATOR);
        readyInMinutes = in.readInt();
        servings = in.readInt();
    }

    public static final Creator<RecipeInformation> CREATOR = new Creator<RecipeInformation>() {
        @Override
        public RecipeInformation createFromParcel(Parcel in) {
            return new RecipeInformation(in);
        }

        @Override
        public RecipeInformation[] newArray(int size) {
            return new RecipeInformation[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getSummary() {
        return summary;
    }

    public String getInstructions() {
        return instructions;
    }

    public Ingredient[] getExtendedIngredients() {
        return extendedIngredients;
    }

    public Equipement[] getEquipments() {
        return equipments;
    }

    public int getReadyInMinutes() {
        return readyInMinutes;
    }

    public int getServings() {
        return servings;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public String getSpoonacularSourceUrl() {
        return spoonacularSourceUrl;
    }

    public void setExtendedIngredients(Ingredient[] extendedIngredients) {
        this.extendedIngredients = extendedIngredients;
    }

    public void setEquipments(Equipement[] equipments) {
        this.equipments = equipments;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(image);
        dest.writeString(summary);
        dest.writeString(instructions);
        dest.writeTypedArray(extendedIngredients, flags);
        dest.writeTypedArray(equipments, flags);
        dest.writeInt(readyInMinutes);
        dest.writeInt(servings);
        dest.writeString(sourceUrl);
        dest.writeString(spoonacularSourceUrl);
    }


    // Important for ViewPager
    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof RecipeInformation) {
            return ((RecipeInformation) obj).getId() == this.id;
        }
        return super.equals(obj);
    }
}
