package ca.mealbell.ui;

/**
 * This fragment is responsible to display details of a recipe that is retrieved from different fragments in the app
 * It requests recipes from the API based on the their id
 * @author: Fadi Findakly
 * @date: 03-29-2020
 */

import android.opengl.Visibility;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import ca.mealbell.Const;
import ca.mealbell.R;
import ca.mealbell.adapters.EquipmentsCustomAdapter;
import ca.mealbell.adapters.IngredientsCustomAdapter;
import ca.mealbell.api.APIResponse;
import ca.mealbell.api.MainAPI;
import ca.mealbell.javabeans.Equipement;
import ca.mealbell.javabeans.EquipmentsObject;
import ca.mealbell.javabeans.Ingredient;
import ca.mealbell.javabeans.RecipeInformation;
import static ca.mealbell.MainActivity.FOOD_API_HEADERS;
import static ca.mealbell.MainActivity.fab;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeDetailsFragment extends Fragment  implements APIResponse {

    // Properties
    Gson gson = new Gson();
    private final int RECIPE = 0;
    private final int EQUIPMENT =1;
    private ArrayList<Ingredient> ingredients = new ArrayList<>();
    private ArrayList<Equipement> equipements = new ArrayList<>();
    private Equipement[] equipementsSet;
    private RecipeInformation recipe;

    // Fragment-UI elements
    TextView titleTextView;
    TextView summaryTextView;
    TextView instructionsTextView;
    ImageView recipeImageView;
    RecyclerView ingredientsRecyclerView;
    RecyclerView equipmentsRecyclerView;
    TextView readyInMinutesTextView;
    TextView servingsTextView;
    TextView instructionsLabel;
    TextView summaryLabel;
    TextView equipmentsLabel;

    int id;

    public RecipeDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            id = getArguments().getInt("RECIPE_ID", 0);
        }
    }

    // Getters and setters
    public RecipeInformation getRecipe() {
        return recipe;
    }
    public void setRecipe(RecipeInformation recipe) {
        this.recipe = recipe;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_details, container, false);

        // Hide fab
        fab.hide();

        // Link GUI elements
        titleTextView = view.findViewById(R.id.recipeTitle_textView);
        recipeImageView = view.findViewById(R.id.recipeImage_imageView);
        summaryTextView = view.findViewById(R.id.summary_TextView);
        instructionsTextView = view.findViewById(R.id.instructions_TextView);
        ingredientsRecyclerView = view.findViewById(R.id.ingredients_recyclerView);
        equipmentsRecyclerView = view.findViewById(R.id.equipments_recyclerView);
        readyInMinutesTextView = view.findViewById(R.id.minutes_textView);
        servingsTextView = view.findViewById(R.id.servings_textView);
        instructionsLabel = view.findViewById(R.id.instructions_label);
        summaryLabel = view.findViewById(R.id.summary_label);
        equipmentsLabel = view.findViewById(R.id.equipments_label);

        // Set the ingredients and equipments RecyclerViews
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.VERTICAL);

        ingredientsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        IngredientsCustomAdapter adapter = new IngredientsCustomAdapter(ingredients, getContext());
//        ingredientsRecyclerView.setNestedScrollingEnabled(false);
        ingredientsRecyclerView.setHasFixedSize(true);
        ingredientsRecyclerView.setAdapter(adapter);

        equipmentsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        EquipmentsCustomAdapter equipmentsAdapter = new EquipmentsCustomAdapter(equipements, getContext());
//        equipmentsRecyclerView.setNestedScrollingEnabled(false);
        equipmentsRecyclerView.setHasFixedSize(true);
        equipmentsRecyclerView.setAdapter(equipmentsAdapter);


        // Retrieve the recipe's id
//        int id = getArguments().getInt("RECIPE_ID", 0);

        getRecipeDetails(id);
        getRecipeEquipments(id);

        return view;
    }


    /**
     * This function retrieved a json object that represents a recipe based on its id
     * @param id
     */
    public void getRecipeDetails(int id) {
        if(id != 0) {
            // Retrieve a recipe with the given id
            String recipeURL = Const.API_URL + "recipes/" + id + "/information";
            MainAPI.getInstance(getContext()).setHeaders(FOOD_API_HEADERS).stringRequest(Request.Method.GET, recipeURL, null, this, RECIPE);
        }
    }

    /**
     * This function retrieved a json object that represents a recipe's set of needed equipments based on its id
     * @param id
     */
    public void getRecipeEquipments(int id) {
        if(id != 0) {
            // Retrieve a set of equipments for the given id
            String equipmentURL = Const.API_URL + "recipes/" + id + "/equipmentWidget.json";
            MainAPI.getInstance(getContext()).setHeaders(FOOD_API_HEADERS).stringRequest(Request.Method.GET, equipmentURL, null, this, EQUIPMENT);
        }
    }


    /**
     * This function is to populate the fragment with a passed recipe
     * @param recipe
     */
    public void populateRecipe(RecipeInformation recipe) {
        if(recipe == null) return;

        titleTextView.setText(recipe.getTitle());

        Picasso.get().load(recipe.getImage()).placeholder(R.drawable.dish).into(recipeImageView);

        if (recipe.getSummary() != null) {
            summaryTextView.setText(Html.fromHtml(recipe.getSummary(), Html.FROM_HTML_MODE_LEGACY));
            summaryTextView.setMovementMethod(LinkMovementMethod.getInstance());
        }

        if (recipe.getInstructions() != null) {
            instructionsTextView.setText(Html.fromHtml(recipe.getInstructions(), Html.FROM_HTML_MODE_LEGACY));
            instructionsTextView.setMovementMethod(LinkMovementMethod.getInstance());
        }

        readyInMinutesTextView.setText(recipe.getReadyInMinutes() + "");
        servingsTextView.setText(recipe.getServings() + "");

        Ingredient[] ingredientsArray = recipe.getExtendedIngredients();
        // Populate ingredients ArrayList from the ingredients array
        for (int i = 0; i < ingredientsArray.length; i++) {
            ingredients.add(ingredientsArray[i]);
        }

        recipe.setEquipments(equipementsSet);
        // Populate equipments ArrayList from the equipmentsSet array
        for (int i = 0; i < equipementsSet.length; i++) {
            equipements.add(equipementsSet[i]);
        }

        // Check if some layout parts content exist in the returned json object
        if (recipe.getEquipments() == null) {
            equipmentsLabel.setVisibility(View.GONE);
        }
        if (recipe.getInstructions() == null) {
            instructionsLabel.setVisibility(View.GONE);
        }
        if (recipe.getSummary() == null) {
            summaryLabel.setVisibility(View.GONE);
        }


        getView().setVisibility(View.VISIBLE);
    }

    @Override
    public void onSuccess(Object json, int status, int request) {

        // Check API requests responses
        if(request == RECIPE) {
             recipe = gson.fromJson(json.toString(), RecipeInformation.class);
        }
        if(request == EQUIPMENT) {
            EquipmentsObject equipementsObject = gson.fromJson(json.toString(), EquipmentsObject.class);
            equipementsSet = equipementsObject.getEquipment();
        }

        if(recipe != null && equipementsSet != null) {
            populateRecipe(recipe);
        }
    }

    @Override
    public void onFailure(VolleyError error, int status, int request) {

    }
}
