package ca.mealbell.ui;

/**
 * Created by: Fadi Findakly - Feb. 23, 2020
 */


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ca.mealbell.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchByIngredientsFragment extends Fragment {


    public SearchByIngredientsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_by_ingredients, container, false);


        return view;
    }

}
