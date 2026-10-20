package com.cis2818.project4

import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem

import androidx.appcompat.app.AppCompatActivity

import androidx.lifecycle.ViewModelProvider

import androidx.recyclerview.widget.LinearLayoutManager


import com.cis2818.project4.databinding.ActivityMainBinding
import com.google.android.material.appbar.MaterialToolbar



open class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: BookViewModel
    private var bookData: List<Book> = emptyList()

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.fav_books -> {
                val intent = Intent(this, FavoriteBooksActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }




    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestedOrientation = SCREEN_ORIENTATION_PORTRAIT // KEEPS SCREEN IN PORTRAIT MODE


        viewModel = ViewModelProvider(this)[BookViewModel::class.java]

        setupRecyclerView()
        observeViewModel()
        setupSearch()

        viewModel.loadBookData(this) // initial load

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)



        binding.button?.setOnClickListener {
            val query = binding.searchInput.text.toString().trim()
            if (query.isNotEmpty()) {
                viewModel.loadBookData(this, query)
            }
        }




    }

    private fun setupRecyclerView() {
        binding.RecyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun observeViewModel() {
        viewModel.bookResponse.observe(this) { result ->
            bookData = result

            val adapter = BookAdapter(result) { book ->
                // Save the book when heart is clicked
                viewModel.toggleBookSaved(this,book)
            }

            binding.RecyclerView.adapter = adapter
        }
    }

    private fun setupSearch() {

        binding.searchInput.setOnClickListener {
            val query = binding.searchInput.text.toString().trim()
            if (query.isNotEmpty()) {
                viewModel.loadBookData(this, query)
            }
        }
    }


}
