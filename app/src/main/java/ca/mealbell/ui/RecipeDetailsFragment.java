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
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ca.mealbell.APIResponse;
import ca.mealbell.Const;
import ca.mealbell.MainAPI;
import ca.mealbell.R;
import ca.mealbell.adapters.IngredientsEquipmentsAdapter;
import ca.mealbell.javabeans.Ingredient;
import ca.mealbell.javabeans.RecipeInformation;

import static ca.mealbell.MainActivity.FOOD_API_HEADERS;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeDetailsFragment extends Fragment  implements APIResponse {
    Gson gson = new Gson();

    ArrayList<Ingredient> ingredients = new ArrayList<>();

    // UI elements
    TextView titleTextView;
    TextView summaryTextView;
    TextView instructionsTextView;
    ImageView recipeImageView;
    RecyclerView ingredientsRecyclerView;




    public RecipeDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_details, container, false);

        // hide fab

        // Link GUI elements
        titleTextView = view.findViewById(R.id.recipeTitle_textView);
        recipeImageView = view.findViewById(R.id.recipeImage_imageView);
        summaryTextView = view.findViewById(R.id.summary_TextView);
        instructionsTextView = view.findViewById(R.id.instructions_TextView);
        ingredientsRecyclerView = view.findViewById(R.id.ingredients_recyclerView);

        // Set the ingredients RecyclerView
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        ingredientsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        IngredientsEquipmentsAdapter adapter = new IngredientsEquipmentsAdapter(ingredients, getContext());
        ingredientsRecyclerView.setAdapter(adapter);


        // API request
        int id = getArguments().getInt("RECIPE_ID", 0);
        titleTextView.setText(id + "");
        if(id != 0) {
            String url = Const.API_URL + "recipes/" + id + "/information";
            MainAPI.getInstance(getContext()).setHeaders(FOOD_API_HEADERS).stringRequest(Request.Method.GET, url, null, this);
        }

        return view;
    }


    public void populateRecipe(RecipeInformation recipe) {
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
    }

    @Override
    public void onSuccess(Object json, int status) {
        // TODO: retrieve recipe information

//        RecipeInformation recipe = new RecipeInformation();

        RecipeInformation recipe = gson.fromJson(json.toString(), RecipeInformation.class);


        populateRecipe(recipe);
        //Log.d("name test", recipe.getOriginalName());
        Log.d("name test", "working");
        Log.d("name test", recipe.getId() + "");
        Log.d("name test", recipe.getTitle() + "");
        Log.d("ingredients array test", recipe.getExtendedIngredients().length + "");

    }

    @Override
    public void onFailure(VolleyError error, int status) {

    }
}
