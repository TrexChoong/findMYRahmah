package com.example.findmyrahmah

import android.media.Image
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Restaurant")

data class Restaurant(
    val image: Int,
    val name: String,
    val address: String,
    val rating: Float,
    var likeCount: Int,
    val isOnline: Boolean){

}
