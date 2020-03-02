package ca.mealbell.ui;



import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import ca.mealbell.Const;
import ca.mealbell.R;

import static ca.mealbell.MainActivity.fab;


/**
 * Form to search for recipes by <strong>ingredients</strong>.
 *
 * @author Ali Dali
 * @version 1.0
 * @since 25-02-2020
 */
public class SearchByIngredientsFragment extends Fragment {

    private final String TAG = getClass().getTypeName();

    RecyclerView recyclerView;
    IngredientsAdapter adapter;
    //    SearchView searchView;
    EditText searchView;
    Button submitButton;

    // List of ingredients to be searched
    final ArrayList<String> ingredients = new ArrayList<>();

    public SearchByIngredientsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View view = inflater.inflate(R.layout.fragment_search_by_ingredients, container, false);

        // Hide fab
        fab.hide();

        // Attach with layout
        recyclerView = view.findViewById(R.id.ingredients_list);
        searchView = view.findViewById(R.id.search);
        submitButton = view.findViewById(R.id.submit);

        // Set RecyclerView adapter and LayoutManager
        LinearLayoutManager linearLayout = new LinearLayoutManager(getContext());
        linearLayout.setOrientation(RecyclerView.HORIZONTAL);
        adapter = new IngredientsAdapter(getContext(), ingredients);
        recyclerView.setLayoutManager(linearLayout);
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
                // Send Search URL in bundle to Results Layout
                Bundle args = new Bundle();
                args.putString(Const.SEARCH_URL, getSearchURL());
                args.putInt(Const.SEARCH_TYPE, Const.INGREDIENT);
                Navigation.findNavController(view).navigate(R.id.action_nav_serach_recipes_to_recipesResultsFragment, args);
                Log.e(TAG, getSearchURL());
            }
        });


        return view;
    }


    /**
     * Add all ingredients to the query
     * @return the api request URL
     */
    private String getSearchURL() {
        try {
            // Join ingredients with ","
            String csvIngredients = String.join(",", ingredients);
            // encode ingredients (spaces, symbols or ",")
            String encodedIngredients = URLEncoder.encode(csvIngredients, "UTF-8");
            // return search URL
            return Const.API_URL + "recipes/findByIngredients?ingredients=" + encodedIngredients;
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, e.toString());
        }
        return null;
    }

}

class IngredientsAdapter extends RecyclerView.Adapter<IngredientsHolder> {

    List<String> ingredients;
    Context context;

    IngredientsAdapter(Context context, ArrayList<String> ingredients) {
        this.context = context;
        this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public IngredientsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ingredient_holder, parent, false);
        return new IngredientsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final IngredientsHolder holder, int position) {
        holder.ingredient.setText(ingredients.get(position));
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ingredients.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ingredients.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }
}

class IngredientsHolder extends RecyclerView.ViewHolder {

    TextView ingredient;
    ImageView remove;

    public IngredientsHolder(@NonNull View itemView) {
        super(itemView);
        ingredient = itemView.findViewById(R.id.ingredient);
        remove = itemView.findViewById(R.id.remove_icon);
    }
}
