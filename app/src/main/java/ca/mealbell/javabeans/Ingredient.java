package ca.mealbell.javabeans;

import android.content.SharedPreferences;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.PreferenceManager;

/**
 * <p>This class is responsible for holding a returned json object from the API</p>
 * @author Fadi Findakly
 * @since 03-30-2020
 */

public class Ingredient implements Parcelable {

    private String name;
    private String image;
    private double amount;
    private String unit;
    private MeasurementsSet measures;

    protected Ingredient(Parcel in) {
        name = in.readString();
        image = in.readString();
        amount = in.readDouble();
        unit = in.readString();
        measures = in.readParcelable(MeasurementsSet.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(image);
        dest.writeDouble(amount);
        dest.writeString(unit);
        dest.writeParcelable(measures, flags);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public MeasurementsSet getMeasures() {
        return measures;
    }
}
