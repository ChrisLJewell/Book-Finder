

# Book-Finder 📚

**Book-Finder** is an Android application built with Kotlin that allows users to search for books dynamically via the [Open Library API](https://openlibrary.org/), view book details including cover art, and save their favorite books locally using Room Database.

---

## Features ✨

* **Book Search**: Query books by title, author, or keywords using the Open Library Search API.
* **Dynamic Cover Images**: Automatically loads and caches book cover thumbnails using **Glide**.
* **Favorites / Saved Books**: Save or remove books from a local database with interactive heart toggle buttons.
* **Dedicated Favorites View**: Access a separate screen (`FavoriteBooksActivity`) to view all bookmarked books.
* **Coroutines & Architecture Components**: Implements modern Android development patterns including **ViewModel**, **LiveData**, and **Kotlin Coroutines** for smooth, asynchronous background operations.

---

## Tech Stack & Libraries 🛠️

* **Language**: Kotlin
* **Architecture**: MVVM (Model-View-ViewModel)
* **Local Database**: Android Room (SQLite persistence)
* **Networking**: Android Volley for API requests
* **Image Loading**: Glide
* **Concurrency**: Kotlin Coroutines & Lifecycle Scopes

---
## Book Finder Demo

https://github.com/user-attachments/assets/4efcf5c4-3213-4445-bc95-626834a7eca2



## Project Structure 📁

```text
com.cis2818.project4/
│
├── Book.kt                 # Data class representing a Book entity for Room & API parsing
├── BookAdapter.kt          # RecyclerView adapter for rendering book items and handling favorites
├── BookDao.kt              # Data Access Object for Room database operations
├── BookDatabase.kt         # Room database singleton configuration
├── BookRepository.kt       # Handles network requests to the Open Library API using Volley
├── BookViewModel.kt        # Manages UI-related data and coordinates between repository and local DB
├── MainActivity.kt         # Main search screen and RecyclerView host
└── FavoriteBooksActivity.kt# Dedicated activity to display locally saved favorite books

