package com.example.smartgrocery.repositories

import okhttp3.*
import java.io.IOException

object SignUpRepository {

    var body: String? = null

    /**
     * Send user data through REST API to the server
     *
     */
    fun sendUserData(userName: String, password: String, email: String) {           // Parameter User name und password
        println("Attempting to send user data")

        // Rest API URL
        // Make String in dependence of user name and password
        // TODO: Replace URL with new created user information
        //val url = "https://jsonplaceholder.typicode.com/todos/1"
        val url = "http://192.168.178.20:8080/smart-grocery-0.0.1-SNAPSHOT/username/add/$userName/$password/$email"

        val request = Request.Builder().url(url).build()

        val  client = OkHttpClient()

        // Make request on REST API
        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                body = response.body()?.string()

                // TODO: Check response if user was created

            }

            override fun onFailure(call: Call, e: IOException) {
                println("Failed to execute request")
            }
        })

    }


}