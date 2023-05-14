package com.example.findmyrahmah.ui.Suggestion

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "suggest")
data class Suggestion (val name: String, val address: String, val cuisine: String, val description: String ) {
    override fun toString(): String {
        return "$name : $address: $cuisine: $description"
    }
}



