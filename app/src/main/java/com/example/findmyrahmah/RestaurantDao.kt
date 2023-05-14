package com.example.findmyrahmah

import android.content.ClipData.Item
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface RestaurantDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(restaurant: Restaurant)

    @Query("Select * FROM restaurant ORDER BY likeCount ASC")
    fun getAllRestaurant(): LiveData<List<Restaurant>>

    @Update
    suspend fun update(restaurant: Restaurant)
}