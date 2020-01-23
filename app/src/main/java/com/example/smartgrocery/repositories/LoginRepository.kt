package com.example.smartgrocery.repositories

import okhttp3.*
import java.io.IOException

object LoginRepository {

    var body: String? = null
    /**
     * Fetch User Data from REST API and create object from it
     *
     */
    fun fetchUserData(userName: String, password: String) {           // Parameter User name und password
        println("Attempting to Fetch JSON")

        // Rest API URL
        // Make String in dependence of user name and password
        //val url = "https://jsonplaceholder.typicode.com/todos/1"
        val url = "http://192.168.178.20:8080/smart-grocery-0.0.1-SNAPSHOT/username/$userName/$password"
        // TODO: Replace URL

        val request = Request.Builder().url(url).build()

        val  client = OkHttpClient()

        // Make request on REST API
        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                body = response.body()?.string()


            }

            override fun onFailure(call: Call, e: IOException) {
                println("Failed to execute request")
            }
        })


    }



}