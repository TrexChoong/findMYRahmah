package com.example.findmyrahmah

import android.app.Application

class RestaurantApplication: Application() {
    //create database when needed
    val database: RestaurantDatabase by lazy { RestaurantDatabase.getDatabase(this) }
}