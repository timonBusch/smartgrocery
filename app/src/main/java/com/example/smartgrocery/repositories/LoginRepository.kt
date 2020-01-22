package com.example.smartgrocery.repositories

import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException

class LoginRepository {


    /**
     * Fetch User Data from REST API and create object from it
     *
     */
    fun fetchUserData(userName: String, password: String): User?{           // Parameter User name und password
        println("Attempting to Fetch JSON")

        // Rest API URL
        // Make String in dependence of user name and password
        val url = "https://jsonplaceholder.typicode.com/todos/1"

        val request = Request.Builder().url(url).build()

        val  client = OkHttpClient()

        var userObject: User? = null
        // Make request on REST API
        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string()

                val gson = GsonBuilder().create()

                // Creates objects out of the body string

                userObject = gson.fromJson(body, User::class.java)

            }

            override fun onFailure(call: Call, e: IOException) {
                println("Failed to execute request")
            }
        })

        return userObject
    }

    data class User(val id_benutzer : Int, val name_benutzer: String, val passwort: String)


}