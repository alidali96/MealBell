package ca.mealbell.javabeans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * <p>This class is responsible for holding a returned json object from the API</p>
 * @author Fadi Findakly
 * @since 04-01-2020
 */

public class MeasurementsSet implements Parcelable {

    private Measurements us;
    private Measurements metric;

    public MeasurementsSet(Measurements us, Measurements metric) {
        this.us = us;
        this.metric = metric;
    }

    protected MeasurementsSet(Parcel in) {
        us = in.readParcelable(Measurements.class.getClassLoader());
        metric = in.readParcelable(Measurements.class.getClassLoader());
    }

    public static final Creator<MeasurementsSet> CREATOR = new Creator<MeasurementsSet>() {
        @Override
        public MeasurementsSet createFromParcel(Parcel in) {
            return new MeasurementsSet(in);
        }

        @Override
        public MeasurementsSet[] newArray(int size) {
            return new MeasurementsSet[size];
        }
    };

    public Measurements getUs() {
        return us;
    }

    public void setUs(Measurements us) {
        this.us = us;
    }

    public Measurements getMetric() {
        return metric;
    }

    public void setMetric(Measurements metric) {
        this.metric = metric;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(us, flags);
        dest.writeParcelable(metric, flags);
    }
}
