package com.example.smartgrocery.repositories

import okhttp3.*
import java.io.IOException

object SignUpRepository {

    /**
     * Send user data through REST API to the server
     *
     */
    fun sendUserData(userName: String, password: String) {           // Parameter User name und password
        println("Attempting to user data")

        // Rest API URL
        // Make String in dependence of user name and password
        // TODO: Replace URL with new created user information
        val url = "https://jsonplaceholder.typicode.com/todos/1"

        val request = Request.Builder().url(url).build()

        val  client = OkHttpClient()

        // Make request on REST API
        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string()

                // TODO: Check response if user was created

            }

            override fun onFailure(call: Call, e: IOException) {
                println("Failed to execute request")
            }
        })

    }


}