package ca.mealbell.javabeans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * <p>This class is responsible for holding a returned json object from the API</p>
 * @author Fadi Findakly
 * @since 04-01-2020
 */

public class EquipmentsObject implements Parcelable {

    private Equipement[] equipment;

    public EquipmentsObject(Equipement[] equipment) {
        this.equipment = equipment;
    }

    protected EquipmentsObject(Parcel in) {
        equipment = in.createTypedArray(Equipement.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedArray(equipment, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<EquipmentsObject> CREATOR = new Creator<EquipmentsObject>() {
        @Override
        public EquipmentsObject createFromParcel(Parcel in) {
            return new EquipmentsObject(in);
        }

        @Override
        public EquipmentsObject[] newArray(int size) {
            return new EquipmentsObject[size];
        }
    };

    public Equipement[] getEquipment() {
        return equipment;
    }
}
