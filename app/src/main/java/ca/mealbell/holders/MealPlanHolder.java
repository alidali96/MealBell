package ca.mealbell.holders;

import android.media.Image;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ramotion.foldingcell.FoldingCell;

import ca.mealbell.MealPlanFragment;
import ca.mealbell.R;
import ca.mealbell.javabeans.MealPlan;

/**
 * @author Ali Dali
 * @since 09-03-2020
 */
public class MealPlanHolder extends RecyclerView.ViewHolder {

    public FrameLayout mealPlanCardHolder;
    public FrameLayout mealPlanDetailsCardHolder;
    public FoldingCell foldingCell;


    /*
    public Fragment fragment;
    public Fragment fragment2;

    public LinearLayout linearLayout;
    public MealPlan mealPlan;
    public int position;
     */
    FragmentManager fragmentManager;

    public MealPlanHolder(@NonNull View itemView, FragmentManager fragmentManager) {
        super(itemView);
        this.fragmentManager = fragmentManager;

        foldingCell = itemView.findViewById(R.id.folding_cell_meal);

        mealPlanCardHolder = itemView.findViewById(R.id.meal_plan_card_holder);
        mealPlanDetailsCardHolder = itemView.findViewById(R.id.meal_plan_details_card_holder);

        // Toggle FoldingCell
        mealPlanCardHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foldingCell.toggle(false);
            }
        });

        mealPlanDetailsCardHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foldingCell.toggle(false);
            }
        });


        // Tried to add LinearLayout and add children to it, instead of replacing FrameLayout
        /*
        linearLayout = itemView.findViewById(R.id.test_holder);

         */

        // Tried to have fragments and replace them in adapter (by id)
        /*
        fragment = (MealPlanFragment)fragmentManager.findFragmentById(R.id.fragment2);
        fragment2 = fragmentManager.findFragmentById(R.id.fragment3);
        */

        // Tried to Add Fragments here and passed position and meal plan -.- (still pointless)
        /*
        fragmentManager.beginTransaction().add(foldingCell.getId(), new MealPlanFragment(mealPlan, position)).commit();
        */
    }


    public void addFragment(MealPlan mealPlan, int position) {
        // Tried to add Fragment dynamically to the layout
        fragmentManager.beginTransaction().add(foldingCell.getId(), new MealPlanFragment(mealPlan, position)).commit();
    }

}
