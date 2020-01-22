package com.example.smartgrocery.repositories

import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException
import java.util.ArrayList

object GroceryListItems{
    
    val ITEMS: MutableList<GroceryListDataItem> = ArrayList()

    /**
     * Fetch JSON from REST API and create object from it
     *
     */
    fun fetchJson() {           // Parameter User name und password
        println("Attempting to Fetch JSON")

        // Rest API URL
        // Make String in dependence of user name and password
        val url = "https://jsonplaceholder.typicode.com/todos/1"

        val request = Request.Builder().url(url).build()

        val  client = OkHttpClient()
        // Make request on REST API
        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string()

                val gson = GsonBuilder().create()

                // Creates objects out of the body string

                val groceryData = gson.fromJson(body, Array<GroceryListDataItem>::class.java).toList()

                for (item in groceryData){
                    makeList(
                        item.id_benutzer,
                        item.name_benutzer,
                        item.passwort,
                        item.email)
                }


            }

            override fun onFailure(call: Call, e: IOException) {
                println("Failed to execute request")
            }
        })
    }

    fun makeList(id_benutzer: Int, name_benutzer: String, passwort: String, email: String) {
        addListItem(GroceryListDataItem(id_benutzer, name_benutzer, passwort, email))
    }

    fun addListItem(listItem: GroceryListDataItem) {
        ITEMS.add(listItem)
    }

    data class GroceryListDataItem(val id_benutzer : Int,
                                   val name_benutzer: String, val passwort: String, val email: String)

}

