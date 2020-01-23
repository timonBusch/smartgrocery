package com.example.smartgrocery.repositories

import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException


object ProductRepository{

    val ITEMS: MutableList<ProductDataItem> = ArrayList()

    /**
     * Fetch List information of user from REST API and create object from it
     *
     */
    fun fetchProductListData(userID: String?, listID: Int) {           // Parameter User name und password
        println("Attempting to Fetch JSON")

        // Rest API URL
        // Make String in dependence of user name and password
        val url = "http://192.168.178.20:8080/smart-grocery-0.0.1-SNAPSHOT/produkte/$userID/$listID"

        val request = Request.Builder().url(url).build()

        val  client = OkHttpClient()
        var productData: List<ProductDataItem>? = null
        // Make request on REST API
        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string()

                val gson = GsonBuilder().create()

                // Creates objects out of the body string
                // TODO: jsonString mit body ersetzen
                productData = gson.fromJson(body, Array<ProductDataItem>::class.java).toList()
                makeList(productData)

            }

            override fun onFailure(call: Call, e: IOException) {
                println("Failed to execute request")
            }
        })

    }

    fun makeList(productData: List<ProductDataItem>?) {

        for (item in productData!!) {
            ITEMS.add(item)

        }

    }

    data class ProductDataItem(val id_produkte : Int, val name_produkte: String, val favorit: String)

}

