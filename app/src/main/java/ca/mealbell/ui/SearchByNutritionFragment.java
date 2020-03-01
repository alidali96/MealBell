package ca.mealbell.ui;

/**
 * @author: Fadi Findakly
 * @date: Mar. 01, 2020
 */


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

import ca.mealbell.Const;
import ca.mealbell.R;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchByNutritionFragment extends Fragment {

    private SeekBar caloriesSeekBar;
    private SeekBar fatSeekBar;
    private SeekBar proteinSeekBar;
    private SeekBar carbsSeekBar;
    private TextView caloriesTextView;
    private TextView fatTextView;
    private TextView proteinTextView;
    private TextView carbsTextView;
    private Button searchButton;

    // Variables for the nutrition elements values
    private int maxCalories;
    private int maxFat;
    private int maxProtein;
    private int maxCarbs;



    public SearchByNutritionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_search_by_nutrition, container, false);

        //link xml elements
        caloriesSeekBar = view.findViewById(R.id.calories_seekbar);
        fatSeekBar = view.findViewById(R.id.fat_seekbar);
        proteinSeekBar = view.findViewById(R.id.protein_seekbar);
        carbsSeekBar = view.findViewById(R.id.carbs_seekbar);
        caloriesTextView = view.findViewById(R.id.calories_textview);
        fatTextView = view.findViewById(R.id.fat_textview);
        proteinTextView = view.findViewById(R.id.protein_textview);
        carbsTextView = view.findViewById(R.id.carbs_textview);
        searchButton = view.findViewById(R.id.search_button);


        /**
         * Add event listeners to form elements
         * Retrieve data from the form to generate the Api url string
         */
        caloriesSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                caloriesTextView.setText("" + progress + " cals");
                maxCalories = progress;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        fatSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                fatTextView.setText("" + progress + " gms");
                maxFat = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        proteinSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                proteinTextView.setText("" + progress + " gms");
                maxProtein = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        carbsSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                carbsTextView.setText("" + progress + " gms");
                maxCarbs = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a bundle to pass information to the recipe results fragment
                Bundle args = new Bundle();
                args.putString(Const.SEARCH_KEY, getSearchURL());
                Navigation.findNavController(view).navigate(R.id.action_nav_serach_recipes_to_recipesResultsFragment, args); // why???
            }
        });

        return view;
    }

    /**
     * Add all optional arguments to the query
     * (maxCalories, maxFat, maxProtein, maxCarbs)
     * @return API url string
     */
    private String getSearchURL() {
        return Const.API_URL + "recipes/findByNutrients?"
                             + "maxCalories=" + maxCalories
                             + "&maxFat=" + maxFat
                             + "&maxProtein" + maxProtein
                             + "&maxCarbs" + maxCarbs;
    }
}
