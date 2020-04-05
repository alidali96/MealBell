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

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.github.florent37.androidslidr.Slidr;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import ca.mealbell.Const;
import ca.mealbell.R;
import ca.mealbell.adapters.IngredientsAdapter;
import ca.mealbell.api.APIResponse;
import ca.mealbell.api.MainAPI;
import ca.mealbell.database.DatabaseHandler;
import ca.mealbell.javabeans.MealPlan;

import static ca.mealbell.MainActivity.FOOD_API_HEADERS;
import static ca.mealbell.MainActivity.fab;


/**
 * Generate a New Meal Plan
 *
 * @author Ali Dali
 * @version 1.0
 * @since 08-03-2020
 */
public class CreateMealPlanFragment extends Fragment implements APIResponse {

    private final String TAG = getClass().getTypeName();

    RecyclerView recyclerView;
    IngredientsAdapter adapter;
    Slidr caloriesTarget;
    EditText searchView;
    Button submitButton;

    CheckBox[] dietsCheckBoxes = new CheckBox[9];

    // List of ingredients to be excluded
    final ArrayList<String> ingredients = new ArrayList<>();
    final ArrayList<String> diets = new ArrayList<>();

    private Gson gson = new Gson();

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

        // Slider
        caloriesTarget.setMin(0);
        caloriesTarget.setMax(4000);
        caloriesTarget.setCurrentValue(caloriesTarget.getMax() / 2); // start at the half value
        caloriesTarget.setTextFormatter(new Slidr.TextFormatter() {
            @Override
            public String format(float value) {
                return String.format("Calories: %d", (int) value);
            }
        });
        caloriesTarget.setRegionTextFormatter(new Slidr.RegionTextFormatter() {
            @Override
            public String format(int region, float value) {
                return "";
            }
        });

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
                MainAPI.getInstance(getContext()).setHeaders(FOOD_API_HEADERS).jsonObjectRequest(Request.Method.GET, getGenerateMealURL(), null, CreateMealPlanFragment.this);
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
            return Const.API_URL + "recipes/mealplans/generate?timeFrame=day&targetCalories=" + caloriesTarget.getCurrentValue() + "&diet=" + encodedDiets + "&exclude=" + encodedIngredients;
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, e.toString());
        }
        return null;
    }

    @Override
    public void onSuccess(Object json, int status, int request) {

        MealPlan mealPlan = gson.fromJson(json.toString(), MealPlan.class);
        if (mealPlan != null)
            DatabaseHandler.getInstance(getContext()).addMealPlan(mealPlan);

        // Back to previous page
        Navigation.findNavController(getView()).popBackStack();
    }

    @Override
    public void onFailure(VolleyError error, int status, int request) {
        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
    }
}
