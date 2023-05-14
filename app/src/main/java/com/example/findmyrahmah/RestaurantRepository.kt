package com.example.findmyrahmah

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class RestaurantRepository(private val restaurantDao: RestaurantDao) {

    val allRestaurant: LiveData<List<Restaurant>> = restaurantDao.getAllRestaurant()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(restaurant: Restaurant){
        restaurantDao.update(restaurant)
    }

    @WorkerThread
    suspend fun add(restaurant: Restaurant){
        restaurantDao.insert(restaurant)
    }
}