package com.cis2818.project4

import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteBooksActivity : AppCompatActivity() {

    private lateinit var adapter: BookAdapter
    private val books = mutableListOf<Book>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_books)

        requestedOrientation = SCREEN_ORIENTATION_PORTRAIT // KEEPS SCREEN IN PORTRAIT MODE

        // Toolbar setup
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val recyclerView: RecyclerView = findViewById(R.id.favRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = BookAdapter(books) { book ->
            toggleBookSaved(book)
        }
        recyclerView.adapter = adapter

        fetchBooksWithSavedStatus()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun fetchBooksWithSavedStatus() {
        val db = BookDatabase.getDatabase(applicationContext)
        val bookDao = db.bookDao()

        lifecycleScope.launch(Dispatchers.IO) {
            val allBooks = bookDao.getSavedBooks()
            val savedBookIds = allBooks.map { it.id }.toSet()
            allBooks.forEach { it.isSaved = savedBookIds.contains(it.id) }

            withContext(Dispatchers.Main) {
                books.clear()
                books.addAll(allBooks)
                adapter.notifyDataSetChanged()

            }
        }
    }

    private fun toggleBookSaved(book: Book) {
        val db = BookDatabase.getDatabase(applicationContext)
        val bookDao = db.bookDao()

        lifecycleScope.launch(Dispatchers.IO) {
            if (book.isSaved) {
                bookDao.insertBook(book)
            } else {
                bookDao.deleteBook(book)
            }
        }
    }
}
