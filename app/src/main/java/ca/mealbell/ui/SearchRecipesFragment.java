package ca.mealbell.ui;
/*
Created by: Ali Dali
Date: Feb. 22, 2020
Updated by: Fadi Findakly - Feb. 23, 2020
 */


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

import ca.mealbell.R;
import ca.mealbell.adapters.ViewPagerAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchRecipesFragment extends Fragment {

    // xml elements
    private AppBarLayout appBarLayout;
    private TabLayout tabLayout;
    private ViewPager tabLayoutViewPager;


    public SearchRecipesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_recipes, container, false);

        // link xml elements
        appBarLayout = view.findViewById(R.id.tab_layout_bar);
        tabLayout = view.findViewById(R.id.tab_layout);
        tabLayoutViewPager = view.findViewById(R.id.tab_layout_viewpager);

        // create an adapter for the viewPager
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());

        // add fragments to the viewPager
        adapter.addFragment(new SearchByIngredientsFragment(), "Serach By Ingredients");
        adapter.addFragment(new SearchByNutritionFragment(), "Serach By Nutritions");

        // viewPager setup
        tabLayoutViewPager.setAdapter(adapter);

        // tablayout setup
        tabLayout.setupWithViewPager(tabLayoutViewPager);


        return view;
    }

}
