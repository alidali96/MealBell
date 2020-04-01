package ca.mealbell.ui;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import ca.mealbell.APIResponse;
import ca.mealbell.Const;
import ca.mealbell.MainAPI;
import ca.mealbell.R;
import ca.mealbell.javabeans.RecipeInformation;

import static ca.mealbell.MainActivity.FOOD_API_HEADERS;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeDetailsFragment extends Fragment  implements APIResponse {
    TextView titleTextView;
    Gson gson = new Gson();



    public RecipeDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_details, container, false);

        // hide fab

        titleTextView = view.findViewById(R.id.recipeTitle_textView);




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
        //titleTextView.setText(recipe.getID());
    }

    @Override
    public void onSuccess(Object json, int status) {
        // TODO: retrieve recipe information

//        RecipeInformation recipe = new RecipeInformation();

        RecipeInformation recipe = gson.fromJson(json.toString(), RecipeInformation.class);

        //Log.d("name test", recipe.getOriginalName());
        Log.d("name test", "working");
        Log.d("name test", recipe.getId() + "");
        Log.d("name test", recipe.getTitle() + "");

    }

    @Override
    public void onFailure(VolleyError error, int status) {

    }
}
