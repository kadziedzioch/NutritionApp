package com.example.nutritionapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nutritionapp.R;
import com.example.nutritionapp.listener.RecyclerItemListener;
import com.example.nutritionapp.models.Food;

import java.util.ArrayList;
import java.util.Locale;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    ArrayList<Food> foodArrayList;
    RecyclerItemListener recyclerItemListener;

    public RecyclerViewAdapter(ArrayList<Food> foodArrayList, RecyclerItemListener recyclerItemListener) {
        this.foodArrayList = foodArrayList;
        this.recyclerItemListener = recyclerItemListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        String foodName = foodArrayList.get(position).getItem_name() +
                " (" + foodArrayList.get(position).getBrand_name()+")";

        String quantity = String.format(Locale.getDefault(),"%.1f",foodArrayList.get(position).getNf_serving_size_qty())+
                " x "+foodArrayList.get(position).getNf_serving_size_unit();

        if(foodArrayList.get(position).getNf_serving_weight_grams()>0){
            quantity = foodArrayList.get(position).getNf_serving_size_qty()+
                    " x "+foodArrayList.get(position).getNf_serving_size_unit()+
                    " ("+ foodArrayList.get(position).getNf_serving_weight_grams()+"g)";
        }
        String kcal = String.format(Locale.getDefault(),"%.0f",foodArrayList.get(position).getNf_calories()) +" kcal";

        holder.foodName.setText(foodName);
        holder.quantity.setText(quantity);
        holder.calories.setText(kcal);

    }

    @Override
    public int getItemCount() {
        return foodArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        RecyclerItemListener itemListener;
        public TextView foodName,calories,quantity;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            foodName = itemView.findViewById(R.id.rowFoodNameTextView);
            calories = itemView.findViewById(R.id.rowKcalFoodTextView);
            quantity = itemView.findViewById(R.id.rowFoodQuantityTextView);
            itemListener = recyclerItemListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Food food = foodArrayList.get(getAdapterPosition());
            itemListener.onRecyclerItemClicked(food);
        }
    }
}
