package ca.mealbell.adapters;

import android.content.Context;
import android.media.audiofx.DynamicsProcessing;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ca.mealbell.R;
import ca.mealbell.javabeans.Equipement;
import ca.mealbell.javabeans.Ingredient;

public class EquipmentsCustomAdapter extends RecyclerView.Adapter<EquipmentsCustomAdapter.CustomViewHolder> {

    private ArrayList<Equipement> equipments;
    private Context context;

    public EquipmentsCustomAdapter(ArrayList<Equipement> equipements, Context context) {
        this.equipments = equipements;
        this.context = context;
    }

    @NonNull
    @Override
    public EquipmentsCustomAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingredient_equipment_row, parent, false);
        return new EquipmentsCustomAdapter.CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EquipmentsCustomAdapter.CustomViewHolder holder, int position) {

        final Equipement equipment = equipments.get(position);

        Picasso.get().load("https://spoonacular.com/cdn/equipment_100x100/" + equipment.getImage()).placeholder(R.drawable.dish).into(holder.equipmentImage);
        holder.equipmentName.setText(equipment.getName());

    }

    @Override
    public int getItemCount() {
        return equipments.size();
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected ImageView equipmentImage;
        protected TextView equipmentName;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            this.equipmentImage = itemView.findViewById(R.id.ingredient_imageView);
            this.equipmentName = itemView.findViewById(R.id.ingredientName_textView);
        }
    }
}
