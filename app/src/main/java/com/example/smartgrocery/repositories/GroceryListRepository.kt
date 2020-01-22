package com.example.smartgrocery.repositories

import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.File
import java.io.IOException
import java.util.ArrayList

object GroceryListItems{
    
    val ITEMS: MutableList<GroceryListDataItem> = ArrayList()

    /**
     * Fetch List information of user from REST API and create object from it
     *
     */
    fun fetchGroceryListData(userName: String, password: String) {           // Parameter User name und password
        println("Attempting to Fetch JSON")

        // Rest API URL
        // Make String in dependence of user name and password
        //val url = "https://192.168.178.20:8080/user$userName/password/$password/lists"     oder so
        val url = "https://jsonplaceholder.typicode.com/todos/1"

        val jsonString = """[{"id": 1,"name": "Einkauf"},{"id": 2,"name": "Einkauf2"},{"id": 3,"name": "Einkauf3"}]"""


        val request = Request.Builder().url(url).build()

        val  client = OkHttpClient()
        var groceryData: List<GroceryListDataItem>? = null
        // Make request on REST API
        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string()

                val gson = GsonBuilder().create()

                // Creates objects out of the body string
                // TODO: jsonString mit body ersetzen
                groceryData = gson.fromJson(jsonString, Array<GroceryListDataItem>::class.java).toList()
                makeList(groceryData)

            }

            override fun onFailure(call: Call, e: IOException) {
                println("Failed to execute request")
            }
        })

    }

    fun makeList(groceryData: List<GroceryListDataItem>?) {

        for (item in groceryData!!) {
            ITEMS.add(item)
        }

    }

    data class GroceryListDataItem(val id : String, val name: String)

}

