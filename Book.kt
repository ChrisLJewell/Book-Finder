package com.cis2818.project4


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "bookDatabase")
data class Book(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var isSaved: Boolean = false,
    val title: String,
    val author: String,
    val coverI: Int?,    // CoverI  is used for the images
    @ColumnInfo(name = "key") val key: String
) {

// This the cover URL for the book
    val coverUrl: String?
        get() = when {

            coverI != null -> "https://covers.openlibrary.org/b/id/${coverI}-M.jpg"

            else -> null
        }

}