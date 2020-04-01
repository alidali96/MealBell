package ca.mealbell.javabeans;

import android.os.Parcel;
import android.os.Parcelable;

public class Ingredient implements Parcelable {

    private String name;
    private String image;
    private double amount;
    private String unit;

    public Ingredient(String name, String image, double amount, String unit) {
        this.name = name;
        this.image = image;
        this.amount = amount;
        this.unit = unit;
    }

    protected Ingredient(Parcel in) {
        name = in.readString();
        image = in.readString();
        amount = in.readDouble();
        unit = in.readString();
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public double getAmount() {
        return amount;
    }

    public String getUnit() {
        return unit;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(image);
        dest.writeDouble(amount);
        dest.writeString(unit);
    }
}
