package ca.mealbell.ui;


import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ca.mealbell.R;

import static ca.mealbell.MainActivity.fab;


/**
 * A simple {@link Fragment} subclass.
 */
public class BMIFragment extends Fragment {

    // UI elements
    TextView weightTextView ;
    TextView heightTextView;
    TextView bmiTextView;

    private SettingsFragment.Measurement unitSystem;
    private float weight;
    private float height;
    private float bmi;
    private String wightUnit;
    private String heightUnit;

    public BMIFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Grab the information from the SgaredPreferences
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        unitSystem = SettingsFragment.Measurement.valueOf(preferences.getString(SettingsFragment.MEASUREMENT,SettingsFragment.Measurement.US.toString()));
        weight = preferences.getFloat(SettingsFragment.WEIGHT, weight);
        height = preferences.getFloat(SettingsFragment.HEIGHT, height);


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bmi, container, false);

        // Link UI elements
        weightTextView = view.findViewById(R.id.weight_textView);
        heightTextView = view.findViewById(R.id.height_textView);
        bmiTextView = view.findViewById(R.id.bmi_textView);

        // Do the BMI calculations
        // Metric system formula: bmi = weight (kg) / [height (m)]2
        // US system formula: bmi = 703 x weight (lbs) / [height (in)]2
        if (unitSystem == SettingsFragment.Measurement.US) {
//            float tempHeight = (float) Math.floor(height);
//            float heigtInInches = ((height - tempHeight)) * 10 + (tempHeight * 12);
            float heigtInInches = height * 12;
            bmi = (703 * weight) / (heigtInInches * heigtInInches);
            wightUnit = "lbs";
            heightUnit = "ft";
        } else {
            height = height / 100;
            bmi = weight / (height * height);
            wightUnit = "kgs";
            heightUnit = "m";
        }

        // Set the bmiTextView content
        bmiTextView.setText(String.format("%.2f", bmi));
        weightTextView.setText(String.format("%.2f", weight) + wightUnit);
        heightTextView.setText(String.format("%.2f", height) + heightUnit);

        // Hide the fab button
        fab.hide();
        
        return view;
    }

}
