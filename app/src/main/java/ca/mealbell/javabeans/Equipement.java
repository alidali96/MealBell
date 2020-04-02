package ca.mealbell.javabeans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * <p>This class is responsible for holding a returned json object from the API</p>
 * @author Fadi Findakly
 * @since 04-01-2020
 */

public class Equipement implements Parcelable {

    private String name;
    private String image;

    public Equipement(String name, String image) {
        this.name = name;
        this.image = image;
    }

    protected Equipement(Parcel in) {
        name = in.readString();
        image = in.readString();
    }

    public static final Creator<Equipement> CREATOR = new Creator<Equipement>() {
        @Override
        public Equipement createFromParcel(Parcel in) {
            return new Equipement(in);
        }

        @Override
        public Equipement[] newArray(int size) {
            return new Equipement[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(image);
    }
}
