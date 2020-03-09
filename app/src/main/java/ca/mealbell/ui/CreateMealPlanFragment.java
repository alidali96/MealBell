package ca.mealbell.ui;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import ca.mealbell.Const;
import ca.mealbell.R;
import ca.mealbell.adapters.IngredientsAdapter;

import static ca.mealbell.MainActivity.fab;


/**
 * Generate a New Meal Plan
 *
 * @author Ali Dali
 * @version 1.0
 * @since 08-03-2020
 */
public class CreateMealPlanFragment extends Fragment {

    private final String TAG = getClass().getTypeName();

    RecyclerView recyclerView;
    IngredientsAdapter adapter;
    SeekBar caloriesTarget;
    EditText searchView;
    Button submitButton;

    CheckBox[] dietsCheckBoxes = new CheckBox[9];

    // List of ingredients to be excluded
    final ArrayList<String> ingredients = new ArrayList<>();
    final ArrayList<String> diets = new ArrayList<>();

    public CreateMealPlanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_create_meal_plan, container, false);

        // Hide fab
        fab.hide();

        // Attach with layout
        recyclerView = view.findViewById(R.id.exclude_list);
        caloriesTarget = view.findViewById(R.id.calories_target);
        searchView = view.findViewById(R.id.exclude);
        submitButton = view.findViewById(R.id.submit);

        // Diets
        dietsCheckBoxes[0] = view.findViewById(R.id.diet_gluten_free);
        dietsCheckBoxes[1] = view.findViewById(R.id.diet_vegan);
        dietsCheckBoxes[2] = view.findViewById(R.id.diet_vegetarian);
        dietsCheckBoxes[3] = view.findViewById(R.id.diet_lacto_vegetarian);
        dietsCheckBoxes[4] = view.findViewById(R.id.diet_ovo_vegetarian);
        dietsCheckBoxes[5] = view.findViewById(R.id.diet_pescetarian);
        dietsCheckBoxes[6] = view.findViewById(R.id.diet_ketogenic);
        dietsCheckBoxes[7] = view.findViewById(R.id.diet_paleo);
        dietsCheckBoxes[8] = view.findViewById(R.id.diet_primal);

        for (CheckBox dietCheckBox :
                dietsCheckBoxes) {
            dietCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        diets.add(buttonView.getText().toString());
                    } else {
                        diets.remove(buttonView.getText().toString());
                    }
                    // TEST
                    Log.e(TAG, diets.toString());
                }
            });
        }


        // Set RecyclerView LayoutManager
        FlexboxLayoutManager flexboxLayout = new FlexboxLayoutManager(getContext());
        flexboxLayout.setFlexWrap(FlexWrap.WRAP);

        // Set RecyclerView Adapter
        adapter = new IngredientsAdapter(getContext(), ingredients);
        recyclerView.setLayoutManager(flexboxLayout);
        recyclerView.setAdapter(adapter);

        // Search Click Listener
        searchView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // (Android Keyboard || External Keyboard && Text !Empty)
                if ((actionId == EditorInfo.IME_ACTION_DONE || event.getKeyCode() == KeyEvent.KEYCODE_ENTER) && !v.getText().toString().isEmpty()) {
                    ingredients.add(v.getText().toString());
                    adapter.notifyItemInserted(ingredients.size() - 1);
                    v.setText(null);
                    return true;
                }
                return false;
            }
        });

        // Submit Click Listener
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Generate Meal Plan

                // Back to previous page
                Navigation.findNavController(view).popBackStack();
                Log.e(TAG, getGenerateMealURL());
            }
        });

        return view;
    }


    /**
     * Add all ingredients to the query
     *
     * @return the api request URL
     */
    private String getGenerateMealURL() {
        try {
            // Join ingredients with ","
            String csvIngredients = String.join(",", ingredients);
            // Join diets with ","
            String csvDiets = String.join(",", diets);
            // encode ingredients (spaces, symbols or ",")
            String encodedIngredients = URLEncoder.encode(csvIngredients, "UTF-8");
            // encode ingredients (spaces, symbols or ",")
            String encodedDiets = URLEncoder.encode(csvDiets, "UTF-8");
            // return generate meal URL
            return Const.API_URL + "recipes/mealplans/generate?timeFrame=day&targetCalories=" + caloriesTarget.getProgress() + "&diet=" + encodedDiets + "&exclude=" + encodedIngredients;
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, e.toString());
        }
        return null;
    }

}
