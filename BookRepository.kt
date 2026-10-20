package com.cis2818.project4

import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room

import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.net.URLEncoder




class BookRepository {



// getBooks Call the API ( OpenLibrary) and return a list of books and parse it
    fun getBooks(context: Context, input: String, callback: (List<Book>) -> Unit) {
        val query = URLEncoder.encode(input, "UTF-8")
        val url = "https://openlibrary.org/search.json?q=$query"

        val queue = Volley.newRequestQueue(context)


        val request = StringRequest(
            Request.Method.GET,
            url,
            { response ->
                try {
                    val json = JSONObject(response)
                    val docs = json.getJSONArray("docs")

                    val books = mutableListOf<Book>()

                    for (i in 0 until docs.length()) {
                        val item = docs.getJSONObject(i)

                        val key = item.optString("key")




                        val title = item.optString("title")
                        val author = if (item.has("author_name")) {
                            item.getJSONArray("author_name").optString(0)
                        } else {
                            "Unknown"
                        }

                        val coverId = if (item.has("cover_i")) item.optInt("cover_i") else null
                        val editionList = if (item.has("edition_key")){
                            val array = item.getJSONArray("edition_key")
                            List(array.length()){ array.optString(it)}
                        }else null

                        books.add(
                            Book(
                                title = title,
                                author = author,
                                coverI = coverId,
                                //editionKey = editionList,
                                key = key,
                                isSaved = false







                                )
                        )




                    }

                    callback(books) // ca;ll the callback with the list of books

                } catch (e: Exception) {
                    e.printStackTrace()
                    callback(emptyList())
                }
            },
            { error ->
                error.printStackTrace()
                callback(emptyList())
            }
        )

        queue.add(request)
    }

}