package ca.mealbell.javabeans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * <p>This class is responsible for holding a returned json object from the API</p>
 * @author Fadi Findakly
 * @since 04-01-2020
 */

public class Measurements implements Parcelable {

    private double amount;
    private String unitShort;

    public Measurements(double amount, String unitShort) {
        this.amount = amount;
        this.unitShort = unitShort;
    }

    protected Measurements(Parcel in) {
        amount = in.readDouble();
        unitShort = in.readString();
    }

    public static final Creator<Measurements> CREATOR = new Creator<Measurements>() {
        @Override
        public Measurements createFromParcel(Parcel in) {
            return new Measurements(in);
        }

        @Override
        public Measurements[] newArray(int size) {
            return new Measurements[size];
        }
    };

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getUnitShort() {
        return unitShort;
    }

    public void setUnitShort(String unitShort) {
        this.unitShort = unitShort;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(amount);
        dest.writeString(unitShort);
    }
}
