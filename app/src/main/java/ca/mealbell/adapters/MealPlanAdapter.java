package ca.mealbell.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ca.mealbell.MealPlanDetailsFragment;
import ca.mealbell.MealPlanFragment;
import ca.mealbell.R;
import ca.mealbell.holders.MealPlanHolder;
import ca.mealbell.javabeans.Meal;
import ca.mealbell.javabeans.MealPlan;

public class MealPlanAdapter extends RecyclerView.Adapter<MealPlanHolder> {


    Context context;
    List<MealPlan> mealPlans;
    FragmentManager fragmentManager;

    public MealPlanAdapter(Context context, List<MealPlan> mealPlans, FragmentManager fragmentManager) {
        this.context = context;
        this.mealPlans = mealPlans;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public MealPlanHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.meal_plan_holder, parent, false);
        return new MealPlanHolder(view, fragmentManager);
    }

    @Override
    public void onBindViewHolder(@NonNull MealPlanHolder holder, int position) {
        MealPlan mealPlan = mealPlans.get(position);
        ArrayList<Meal> meals = new ArrayList<>();
        Collections.addAll(meals, mealPlan.getMeals());


        // TODO: Create Meal Plan Fragment
//        int mealPlanCardID = holder.mealPlanCardHolder.getId();
//        int mealPlanDetailsID = holder.mealPlanDetailsCardHolder.getId();
//
//        Fragment oldFragment = fragmentManager.findFragmentById(mealPlanCardID);
//        if(oldFragment != null) {
//            fragmentManager.beginTransaction().remove(oldFragment);
//        }
//
//        Fragment oldFragment2 = fragmentManager.findFragmentById(mealPlanDetailsID);
//        if(oldFragment2 != null) {
//            fragmentManager.beginTransaction().remove(oldFragment2);
//        }


//        FrameLayout frameLayout = new FrameLayout(context);
//        frameLayout.setId(position + 1);
//        frameLayout.setId(View.generateViewId());

//        holder.linearLayout.addView(frameLayout);

//        Fragment oldFragment = fragmentManager.findFragmentById(frameLayout.getId());
//        if(oldFragment != null) {
//            fragmentManager.beginTransaction().remove(oldFragment);
//        }
//
//

//        holder.mealPlanCardHolder.setId(position + 1);
//        fragmentManager.beginTransaction().replace(holder.mealPlanCardHolder.getId(), new MealPlanFragment(mealPlan, position + 1)).commit();


//        int firstID = holder.mealPlanCardHolder.getId();

        holder.mealPlanCardHolder.setId(position + 1);
//        holder.mealPlanDetailsCardHolder.setId(position * mealPlans.size() + 1);


//        if (holder.mealPlanCardHolder != null) {
        fragmentManager.beginTransaction().replace(holder.mealPlanCardHolder.getId(), new MealPlanFragment(mealPlan, position + 1), position + 1 + "").commit();
////        fragmentManager.beginTransaction().replace(holder.mealPlanDetailsCardHolder.getId(), new MealPlanDetailsFragment(meals)).commit();
        Log.e(this.getClass().getTypeName(), holder.mealPlanCardHolder.getId() + "");
//        } else {
//            Log.e(this.getClass().getTypeName(), holder.mealPlanCardHolder.getId() + " NULL");
//        }


//        fragmentManager.beginTransaction().add(new MealPlanFragment(mealPlan), "MEAL_PLAN").commit();

//        holder.replaceFragment(fragmentManager, new MealPlanFragment(mealPlan));


//        MealPlanFragment mealPlanFragment = (MealPlanFragment) holder.fragment;
//        if (mealPlanFragment != null) {
//            mealPlanFragment.getView().setId(position + 1);
//            mealPlanFragment.mealPlan = mealPlan;
//            Log.e("RESULT", "WORKED");
//        } else {
//            Log.e("RESULT", "FRAGMENT NULL");
//        }


//        holder.mealPlan = mealPlan;
//        holder.position = position;
//        holder.addFragment(mealPlan, position + 1);
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
