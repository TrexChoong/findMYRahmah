package com.example.findmyrahmah.ui.favourite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.findmyrahmah.R
import com.example.findmyrahmah.Restaurant
import com.example.findmyrahmah.RestaurantDatabase
import com.example.findmyrahmah.RestaurantRepository
import kotlinx.coroutines.launch

class RestaurantListViewModel(application: Application): AndroidViewModel(application) {

    lateinit var restaurantList: LiveData<List<Restaurant>>
    private var repository: RestaurantRepository
    var selectedIndex : Int = -1

    init {
        val restaurantDao = RestaurantDatabase.getDatabase(application).RestaurantDao()
        repository = RestaurantRepository(restaurantDao)
        restaurantList = repository.allRestaurant
    }

    fun addCount(restaurant: Restaurant) = viewModelScope.launch { repository.add(restaurant) }

    val restaurants = MutableLiveData<List<Restaurant>>()

    init {
        // Initialize with some dummy data
        val restaurantList = listOf(
            Restaurant(R.drawable.mamak, "Restaurant 1", "Address 1", 4.5f, 10, true),
            Restaurant(R.drawable.malayRestaurant, "Restaurant 2", "Address 2", 4.0f, 5, false),
        )

        restaurants.value = restaurantList
    }
}