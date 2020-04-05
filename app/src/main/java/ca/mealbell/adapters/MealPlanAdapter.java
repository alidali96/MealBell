package ca.mealbell.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ca.mealbell.R;
import ca.mealbell.database.DatabaseHandler;
import ca.mealbell.holders.MealPlanHolder;
import ca.mealbell.javabeans.Meal;
import ca.mealbell.javabeans.MealPlan;

/**
 * @author Ali Dali
 * @since 09-03-2020
 */
public class MealPlanAdapter extends RecyclerView.Adapter<MealPlanHolder> {


    Context context;
    List<MealPlan> mealPlans;

    public MealPlanAdapter(Context context, List<MealPlan> mealPlans) {
        this.context = context;
        this.mealPlans = mealPlans;
    }

    @NonNull
    @Override
    public MealPlanHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.meal_plan_holder, parent, false);
        return new MealPlanHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MealPlanHolder holder, final int position) {
        final MealPlan mealPlan = mealPlans.get(position);

        // Set Meal Plan
        holder.setMealPlan(mealPlan);

        holder.mealPlanContainer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setIcon(R.drawable.ic_warning_black_24dp);
                alert.setTitle("Delete Meal Plan");
                alert.setMessage("Are you sure you want to delete this meal plan?");
                alert.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseHandler.getInstance(context).deleteMealPlan(mealPlan);
                        mealPlans.remove(holder.getAdapterPosition());
                        notifyItemRemoved(holder.getAdapterPosition());
                        Toast.makeText(context, "Meal Plan Deleted", Toast.LENGTH_LONG).show();
                    }
                });
                alert.setNegativeButton("Cancel", null);
                alert.create();
                alert.show();
                return true;
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return mealPlans.size();
    }
}
