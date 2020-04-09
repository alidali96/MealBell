package ca.mealbell.ui;


import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

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

//    public final static String US = "US";
//    public final static String METRIC = "METRIC";

    public enum Measurement {
        US,
        METRIC,
    }

    private ToggleSwitch measurmentToggleSwitch;
    private EditText nameText;
    private EditText weightText;

    private Measurement measurement;
    private String name;
    private int weight;

    private int previous;


    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPreferencesEditor;

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
        weight = sharedPreferences.getInt(WEIGHT, 0);
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

        // Hide fab
        fab.hide();

        // Set Measurement Toggle Switch
        ArrayList<String> measurmentsList = new ArrayList<>();
        measurmentsList.add("US");
        measurmentsList.add("METRIC");
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
                    weight = Integer.parseInt(value);
                    sharedPreferencesEditor.putInt(WEIGHT, weight);
                    sharedPreferencesEditor.commit();
                    setWeight();
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
            weightText.setHint(weight + (measurement == Measurement.US ? " lb" : " kg"));
        }
    }

    private void updateWeight() {
        if (weight != 0) {
            if (measurement == Measurement.US) {
                weight *= 2.20462;
            } else if (measurement == Measurement.METRIC) {
                weight *= 0.453592;
                weight++; // round up
            }
            weightText.setHint(weight + (measurement == Measurement.US ? " lb" : " kg"));
        }
        sharedPreferencesEditor.putInt(WEIGHT, weight);
        sharedPreferencesEditor.commit();
    }


    // Hide menu
    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        menu.clear();
    }
}
