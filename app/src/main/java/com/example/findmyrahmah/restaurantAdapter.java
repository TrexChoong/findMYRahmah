package com.example.findmyrahmah;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class restaurantAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE = 1;
    private Context context;
    private List<Object> listRecyclerItem;


    public void RecyclerAdapter(Context context, List<Object> listRecyclerItem){
        this.context = context;
        this.listRecyclerItem = listRecyclerItem;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView restaurantName;
        private TextView restaurantAddress;
        private TextView restaurantRating;

        public ItemViewHolder (@NonNull View itemView){
            super(itemView);
            restaurantName = (TextView) itemView.findViewById(R.id.RestaurantName);
            restaurantAddress = (TextView) itemView.findViewById(R.id.RestaurantAddress);
            restaurantRating = (TextView) itemView.findViewById(R.id.RestaurantRating);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        switch (i){
            case TYPE:
            default:
                View layoutView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.restaurant, viewGroup, false);

                return new ItemViewHolder((layoutView));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        int viewType = getItemViewType(i);

        switch (viewType){
            case TYPE:
            default:
                ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;

                Restaurant restaurant = (Restaurant) listRecyclerItem.get(i);

                itemViewHolder.restaurantName.setText(restaurant.getRestaurantName());
                itemViewHolder.restaurantAddress.setText(restaurant.getRestaurantAddress());
                itemViewHolder.restaurantRating.setText(restaurant.getRestaurantRating().toString());
        }
    }

    @Override
    public int getItemCount() {
        return listRecyclerItem.size();
    }
}
