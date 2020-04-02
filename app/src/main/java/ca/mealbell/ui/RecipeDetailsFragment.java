package ca.mealbell.ui;


import android.os.Bundle;

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
    Gson gson = new Gson();

    private final int RECIPE = 0;
    private final int EQUIPMENT =1;


    private ArrayList<Ingredient> ingredients = new ArrayList<>();
    private ArrayList<Equipement> equipements = new ArrayList<>();
    private Equipement[] equipementsSet;
    private RecipeInformation recipe;


    // UI elements
    TextView titleTextView;
    TextView summaryTextView;
    TextView instructionsTextView;
    ImageView recipeImageView;
    RecyclerView ingredientsRecyclerView;
    RecyclerView equipmentsRecyclerView;




    public RecipeDetailsFragment() {
        // Required empty public constructor
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

        // Set the ingredients RecyclerView
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.VERTICAL);

        ingredientsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        IngredientsCustomAdapter adapter = new IngredientsCustomAdapter(ingredients, getContext());
        ingredientsRecyclerView.setAdapter(adapter);

        equipmentsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        EquipmentsCustomAdapter equipmentsAdapter = new EquipmentsCustomAdapter(equipements, getContext());
        equipmentsRecyclerView.setAdapter(equipmentsAdapter);


        // API requests
        int id = getArguments().getInt("RECIPE_ID", 0);


        getRecipeDetails(id);
        getRecipeEquipments(id);

        return view;
    }


    public void getRecipeDetails(int id) {
        if(id != 0) {
            // Retrieve a recipe with the given id
            String recipeURL = Const.API_URL + "recipes/" + id + "/information";
            MainAPI.getInstance(getContext()).setHeaders(FOOD_API_HEADERS).stringRequest(Request.Method.GET, recipeURL, null, this, RECIPE);
        }
    }

    public void getRecipeEquipments(int id) {
        if(id != 0) {
            // Retrieve a set of equipments for the given id
            String equipmentURL = Const.API_URL + "recipes/" + id + "/equipmentWidget.json";
            MainAPI.getInstance(getContext()).setHeaders(FOOD_API_HEADERS).stringRequest(Request.Method.GET, equipmentURL, null, this, EQUIPMENT);
        }
    }


    public void populateRecipe(RecipeInformation recipe) {
        if(recipe == null) return;

        titleTextView.setText(recipe.getTitle());
        Picasso.get().load(recipe.getImage()).placeholder(R.drawable.dish).into(recipeImageView);
        summaryTextView.setText(Html.fromHtml("<style>a { color: green;} b { color: red; font-size: 2em;}</style>"+recipe.getSummary(), Html.FROM_HTML_MODE_LEGACY));
        summaryTextView.setMovementMethod(LinkMovementMethod.getInstance());
        instructionsTextView.setText(recipe.getInstructions());

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
    }

    @Override
    public void onSuccess(Object json, int status, int request) {
        // TODO: retrieve recipe information
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
