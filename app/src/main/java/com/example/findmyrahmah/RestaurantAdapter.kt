package com.example.findmyrahmah

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView

class RestaurantAdapter(private val restaurantList: MutableLiveData<List<Restaurant>>) : RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>() {

    inner class RestaurantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.RestaurantView)
        private val nameTextView: TextView = itemView.findViewById(R.id.RestaurantName)
        private val addressTextView: TextView = itemView.findViewById(R.id.RestaurantAddress)
        private val ratingTextView: TextView = itemView.findViewById(R.id.RestaurantRating)
        private val likeTextView: TextView = itemView.findViewById(R.id.TotalLike)
        private val button: Button = itemView.findViewById(R.id.likeButton)

        fun bind(restaurant: Restaurant) {
            imageView.setImageResource(restaurant.image)
            nameTextView.text = restaurant.name
            addressTextView.text = restaurant.address
            ratingTextView.text = restaurant.rating.toString()
            likeTextView.text = restaurant.likeCount.toString()
            button.isEnabled = restaurant.isOnline
            button.setOnClickListener {
                //increase like count
                restaurant.likeCount++

                //Update UI with new like count
                likeTextView.text = "${restaurant.likeCount}"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.restaurant, parent, false)
        return RestaurantViewHolder(view)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        holder.bind(restaurantList[position])
    }

    override fun getItemCount(): Int {
        return restaurantList.size
    }
}


