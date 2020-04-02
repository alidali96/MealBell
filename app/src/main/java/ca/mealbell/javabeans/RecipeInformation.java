package ca.mealbell.javabeans;

import android.os.Parcel;
import android.os.Parcelable;

public class RecipeInformation implements Parcelable {

    private int id;
    private String title;
    private String image;
    private String summary;
    private String instructions;
    private Ingredient[] extendedIngredients;
    private Equipement[] equipments;

    protected RecipeInformation(Parcel in) {
        id = in.readInt();
        title = in.readString();
        image = in.readString();
        summary = in.readString();
        instructions = in.readString();
        extendedIngredients = in.createTypedArray(Ingredient.CREATOR);
        equipments = in.createTypedArray(Equipement.CREATOR);
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
    }

    @Override
    public int describeContents() {
        return 0;
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

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public void setEquipments(Equipement[] equipments) {
        this.equipments = equipments;
    }
}
