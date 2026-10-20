package com.cis2818.project4

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
@Dao
interface BookDao {
// Data access object for the database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: Book)

    @Query("SELECT * FROM bookDatabase")
    suspend fun getSavedBooks(): List<Book>

    @Query("SELECT * FROM bookDatabase WHERE `key` = :key LIMIT 1")
    suspend fun getBookByKey(key: String): Book?


    @Query("DELETE  FROM bookDatabase")
    suspend fun deleteAllBooks ()



    @Delete
    suspend fun deleteBook(book: Book)
}
