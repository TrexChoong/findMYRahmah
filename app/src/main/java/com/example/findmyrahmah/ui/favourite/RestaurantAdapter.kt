package com.example.findmyrahmah.ui.favourite

import android.content.Context
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.findmyrahmah.R
import com.example.findmyrahmah.Record
import com.example.findmyrahmah.RestaurantList
import org.w3c.dom.Text

class RestaurantAdapter(private val list: ArrayList<Record>) : RecyclerView.Adapter<RestaurantAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val restaurantID: TextView = itemView.findViewById(R.id.RestaurantID)

        val restaurantName : TextView = itemView.findViewById(R.id.RestaurantName)

        val restaurantLike : TextView = itemView.findViewById(R.id.TotalLike)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.restaurant, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val restaurant = list[position]

        holder.restaurantID.text = restaurant.id
        holder.restaurantName.text = restaurant.name
        holder.restaurantLike.text = restaurant.likes
    }

    override fun getItemCount(): Int {
        return list.size
    }
}