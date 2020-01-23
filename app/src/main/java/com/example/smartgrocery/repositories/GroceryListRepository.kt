package com.example.smartgrocery.repositories

import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException
import java.util.ArrayList

object GroceryListItems{
    
    val ITEMS: MutableList<GroceryListDataItem> = ArrayList()

    /**
     * Fetch List information of user from REST API and create object from it
     *
     */
    fun fetchGroceryListData(userID: String?) {           // Parameter User name und password
        println("Attempting to Fetch JSON")

        // Rest API URL
        // Make String in dependence of user name and password
        val url = "http://192.168.178.20:8080/smart-grocery-0.0.1-SNAPSHOT/einkaufslisten/$userID"

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
                groceryData = gson.fromJson(body, Array<GroceryListDataItem>::class.java).toList()
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
            println(item.id_einkaufsliste)
            println(item.name_einkaufsliste)
        }

    }

    data class GroceryListDataItem(val id_einkaufsliste : Int, val name_einkaufsliste: String)

}

