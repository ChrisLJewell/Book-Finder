package com.cis2818.project4

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class BookViewModel: ViewModel() {



    private val repository = BookRepository()

    private val _bookResponse = MutableLiveData<List<Book>>()

    val bookResponse: LiveData<List<Book>> = _bookResponse

    fun loadBookData(context: Context, query: String = "") {

        repository.getBooks(context, query) { list ->

            viewModelScope.launch(Dispatchers.IO) {
                val db = BookDatabase.getDatabase(context)
                val dao = db.bookDao()

                val savedBooks = dao.getSavedBooks()

                // Mark books as saved if they match keys
                list.forEach { apiBook ->
                    apiBook.isSaved = savedBooks.any { saved -> saved.key == apiBook.key }
                }

                // Send final updated list to UI
                withContext(Dispatchers.Main) {
                    _bookResponse.value = list
                }
            }
        }
    }

    fun toggleBookSaved(context: Context, book: Book) {


        viewModelScope.launch {
            val db = BookDatabase.getDatabase(context)
            val dao = db.bookDao()


            val book = Book(
                title = book.title,
                author = book.author,
                coverI = book.coverI,
                key = book.key,

                )


            val savedBook = dao.getBookByKey(book.key)

            if (savedBook == null) {
                // not saved then insert
                dao.insertBook(book)
                book.isSaved = true
            } else {
                // saved .. delete
                dao.deleteBook(savedBook)
                book.isSaved = false
            }



        }

    }







}