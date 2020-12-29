package com.example.squadmaker.model.localdatasouce.roomdatabase.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Data class representing the stored information about the comics that a specific character is involved.
 */
@Entity(tableName = "comics_table")
data class ComicsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val resourceUri: String,
    val name: String
)