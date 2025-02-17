package com.example.cs4092_multiactivityapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class CatRecyclerAdapter extends RecyclerView.Adapter<CatRecyclerAdapter.ViewHolder> {

    private final Context context;
    private final int layout;
    private final List<Cat> cats;
    RecycleViewInterface recycleViewInterface;

    public CatRecyclerAdapter(Context context, int layout, List<Cat> cats, RecycleViewInterface recycleViewInterface) {
        this.context = context;
        this.layout = layout;
        this.cats = cats;
        this.recycleViewInterface = recycleViewInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(this.context).inflate(layout, parent, false);
        return new CatRecyclerAdapter.ViewHolder(row, recycleViewInterface);
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // get name and image from the cat
        Cat currentCat = cats.get(position);

        // get name
        String name = "Unknown Breed";

        // get the first cat's breed
        if (currentCat.getBreeds() != null && !currentCat.getBreeds().isEmpty()) {
            Cat.Breed breed = currentCat.getBreeds().get(0);
            name = breed.getName();

        }
        // print the cat name
        Log.d("CatRecyclerAdapter", "Binding cat: " + name);

        holder.label.setText(name);

        String imageUrl = currentCat.getUrl();

        holder.floatingActionButton.setOnClickListener(v ->{
            if (holder.isStarOn) {
                // Star is currently "on", switch it to "off"
                holder.floatingActionButton.setImageResource(android.R.drawable.star_off);
            } else {
                // Star is currently "off", switch it to "on"
                holder.floatingActionButton.setImageResource(android.R.drawable.star_on);
            }

            // Toggle the boolean flag to reflect the new state
            holder.isStarOn = !holder.isStarOn;
            currentCat.setStarred(holder.isStarOn);

        });
        holder.isStarOn = currentCat.isStarred();
        if (holder.isStarOn) {
            holder.floatingActionButton.setImageResource(android.R.drawable.star_on);
        } else {
            holder.floatingActionButton.setImageResource(android.R.drawable.star_off);
        }


        Glide.with(context)
                .load(imageUrl)
                .circleCrop()  // round angel
                .into(holder.icon);

        // add animation to enlarge the click item
        holder.cardView.setOnTouchListener ((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    v.animate().scaleX(1.1f).scaleY(1.1f).setDuration(200).start();
                    break;
                case MotionEvent.ACTION_UP:
                    v.animate().scaleX(1f).scaleY(1f).setDuration(200).start();
                    v.performClick(); // use perform click so that it can handle the click item
                    break;
                case MotionEvent.ACTION_CANCEL:
                    v.animate().scaleX(1f).scaleY(1f).setDuration(200).start();
                    break;
            }
            return true;
        });


    }

    @Override
    public int getItemCount() {
        return this.cats.size();
    }

    // inner static ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView label;
        ImageView icon;
        CardView cardView;
        FloatingActionButton floatingActionButton;
        boolean isStarOn;

        public ViewHolder(@NonNull View itemView, RecycleViewInterface recycleViewInterface) {
            super(itemView);
            label = itemView.findViewById(R.id.catDescription);
            icon = itemView.findViewById(R.id.imageView);
            cardView = itemView.findViewById(R.id.cardView);
            floatingActionButton = itemView.findViewById(R.id.floatingActionButton2);


            // Click the item
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    recycleViewInterface.onItemClick(position);
                }
            });


        }


    }
}

