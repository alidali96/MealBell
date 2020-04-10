package ca.mealbell.ui;


import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;

import belka.us.androidtoggleswitch.widgets.BaseToggleSwitch;
import belka.us.androidtoggleswitch.widgets.ToggleSwitch;
import ca.mealbell.R;

import static ca.mealbell.MainActivity.fab;


/**
 * Settings Menu
 *
 * @author Ali Dali
 * @since 31-03-2020
 */
public class SettingsFragment extends Fragment {


    public final static String MEASUREMENT = "MEASUREMENT";
    public final static String NAME = "NAME";
    public final static String WEIGHT = "WEIGHT";
    public final static String HEIGHT = "HEIGHT";

    private final String FEET = " ft";
    private final String CM = " cm";
    private final String KG = " kg";
    private final String LBS = " lbs";


    public enum Measurement {
        US,
        METRIC,
    }

    private ToggleSwitch measurmentToggleSwitch;
    private EditText nameText;
    private EditText weightText;
    private EditText heightText;

    private Measurement measurement;
    private String name;
    private float weight;
    private float height;

    private int previous;


    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPreferencesEditor;
    private DecimalFormat df = new DecimalFormat("#.##");

    private ArrayList<TextView> credits = new ArrayList<>();
    private int[] creditsIDs = {R.id.credit0, R.id.credit1, R.id.credit2, R.id.credit3, R.id.credit4, R.id.credit5, R.id.credit6, R.id.credit7, R.id.credit8, R.id.credit9, R.id.credit10};

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        sharedPreferencesEditor = sharedPreferences.edit();

        measurement = Measurement.valueOf(sharedPreferences.getString(MEASUREMENT, Measurement.US.toString()));
        name = sharedPreferences.getString(NAME, "");
        weight = sharedPreferences.getFloat(WEIGHT, 0);
        height = sharedPreferences.getFloat(HEIGHT, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        // Attach views
        measurmentToggleSwitch = view.findViewById(R.id.measurment);
        nameText = view.findViewById(R.id.name);
        weightText = view.findViewById(R.id.weight);
        heightText = view.findViewById(R.id.height);
        // Credits
        for (int creditID :
                creditsIDs) {
            TextView credit = view.findViewById(creditID);
            credits.add(credit);
        }

        // Make credits clickable
        for (TextView credit:
             credits) {
            credit.setMovementMethod(LinkMovementMethod.getInstance());
        }

        // Hide fab
        fab.hide();

        // Set Measurement Toggle Switch
        ArrayList<String> measurmentsList = new ArrayList<>();
        measurmentsList.add(String.valueOf(Measurement.US));
        measurmentsList.add(String.valueOf(Measurement.METRIC));
        // Custom Toggle Switch
        measurmentToggleSwitch.setActiveBgColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        measurmentToggleSwitch.setInactiveBgColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
        measurmentToggleSwitch.setActiveTextColor(ContextCompat.getColor(getContext(), R.color.design_default_color_background));
        measurmentToggleSwitch.setInactiveTextColor(ContextCompat.getColor(getContext(), R.color.design_default_color_background));
        measurmentToggleSwitch.setToggleWidth(300);
        measurmentToggleSwitch.setLabels(measurmentsList);

        measurmentToggleSwitch.setOnToggleSwitchChangeListener(new BaseToggleSwitch.OnToggleSwitchChangeListener() {
            @Override
            public void onToggleSwitchChangeListener(int position, boolean isChecked) {
                if (position == previous) return;
                previous = position;
                switch (position) {
                    case 0:
                        sharedPreferencesEditor.putString(MEASUREMENT, Measurement.US.toString());
                        measurement = Measurement.US;
                        break;
                    case 1:
                        sharedPreferencesEditor.putString(MEASUREMENT, Measurement.METRIC.toString());
                        measurement = Measurement.METRIC;
                        break;
                }
                sharedPreferencesEditor.commit();
                updateWeight();
                updateHeight();
            }
        });

        // Set Name
        nameText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String name = v.getText().toString();
                if (!name.isEmpty()) {
                    sharedPreferencesEditor.putString(NAME, name);
                    sharedPreferencesEditor.commit();
                }
                return false;
            }
        });

        // Set Weight
        weightText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String value = v.getText().toString();

                if (!value.isEmpty()) {
                    weight = Float.parseFloat(value);
                    sharedPreferencesEditor.putFloat(WEIGHT, weight);
                    sharedPreferencesEditor.commit();
                    setWeight();
                }
                return false;
            }
        });

        // Set Height
        heightText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String value = v.getText().toString();

                if (!value.isEmpty()) {
                    height = Float.parseFloat(value);
                    sharedPreferencesEditor.putFloat(HEIGHT, height);
                    sharedPreferencesEditor.commit();
                    setHeight();
                }
                return false;
            }
        });


        // Default Values
        setAllValues();

        return view;
    }

    private void setAllValues() {
        setMeasurement();
        setName();
        setWeight();
        setHeight();
    }

    private void setMeasurement() {
        switch (measurement) {
            case US:
                previous = 0;
                measurmentToggleSwitch.setCheckedTogglePosition(0, false);
                break;
            case METRIC:
                previous = 1;
                measurmentToggleSwitch.setCheckedTogglePosition(1, false);
                break;
        }
    }

    private void setName() {
        if (!name.isEmpty())
            nameText.setHint(name);
    }

    private void setWeight() {
        if (weight != 0) {
            weightText.setText("");
            weightText.setHint(df.format(weight) + (measurement == Measurement.US ? LBS : KG));
        }
    }

    private void setHeight() {
        if (height != 0) {
            heightText.setText("");
            heightText.setHint(df.format(height) + (measurement == Measurement.US ? FEET : CM));
        }
    }

    private void updateWeight() {
        if (weight != 0) {
            if (measurement == Measurement.US) {
                weight *= 2.20462;
            } else if (measurement == Measurement.METRIC) {
                weight *= 0.453592;
            }
            weightText.setHint(df.format(weight) + (measurement == Measurement.US ? LBS : KG));
        }
        sharedPreferencesEditor.putFloat(WEIGHT, weight);
        sharedPreferencesEditor.commit();
    }

    private void updateHeight() {
        if (height != 0) {
            if (measurement == Measurement.US) {
                height /= 30.48;
            } else if (measurement == Measurement.METRIC) {
                height *= 30.48;
            }
            heightText.setHint(df.format(height) + (measurement == Measurement.US ? FEET : CM));
        }
        sharedPreferencesEditor.putFloat(HEIGHT, height);
        sharedPreferencesEditor.commit();
    }


    // Hide menu
    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        menu.clear();
    }
}
