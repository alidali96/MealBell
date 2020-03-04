package ca.mealbell.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ca.mealbell.R;
import ca.mealbell.javabeans.RecipeByNutrition;


public class ResultsByNutritionAdapter extends RecyclerView.Adapter<ResultsByNutritionAdapter.ResultsByNutritionHolder> {

    private ArrayList<RecipeByNutrition> recipes;
    Context context;

    public ResultsByNutritionAdapter(ArrayList<RecipeByNutrition> recipes, Context context) {
        this.recipes = recipes;
        this.context = context;
    }

    @NonNull
    @Override
    public ResultsByNutritionAdapter.ResultsByNutritionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.results_by_nutrition_holder, parent, false);
        return new ResultsByNutritionAdapter.ResultsByNutritionHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultsByNutritionAdapter.ResultsByNutritionHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }


    class ResultsByNutritionHolder extends RecyclerView.ViewHolder {

        public ResultsByNutritionHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
