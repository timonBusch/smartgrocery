package com.example.smartgrocery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast

import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.smartgrocery.grocery_lists.ListsFragment
import com.example.smartgrocery.grocery_lists.ProductItemsFragment
import com.example.smartgrocery.repositories.GroceryListItems
import com.example.smartgrocery.repositories.LoginRepository.body
import com.example.smartgrocery.repositories.ProductRepository
import com.google.android.material.navigation.NavigationView
import okhttp3.*
import java.io.IOException


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    ListsFragment.OnListFragmentInteractionListener, ProductItemsFragment.OnProductListFragmentInteractionListener {

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Drawer
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.draw_layout)
        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)


        val  toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Set start fragment
        if(savedInstanceState == null) {

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ListsFragment())
                .commit()

            navigationView.setCheckedItem(R.id.nav_lists)
        }

    }



    // Is triggered when the user presses the back key
    override fun onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }

    }

    // Drawer Menue items action
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.nav_favourites -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, FavouritesFragment())
                    .commit()
            }
            R.id.nav_lists -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, ListsFragment())
                    .commit()
            }
            R.id.nav_contacts -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, ContactsFragment())
                    .commit()
            }
            R.id.nav_settings -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, SettingsFragment())
                    .commit()
            }
            R.id.nav_tags -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, TagsFragment())
                    .commit()
            }

        }

        drawerLayout.closeDrawer(GravityCompat.START)
        return true

    }

    override fun onListFragmentInteraction(item: GroceryListItems.GroceryListDataItem?) {
        ProductRepository.ITEMS.clear()
        ProductRepository.fetchProductListData(body, item!!.id_einkaufsliste)

        if (item == GroceryListItems.ITEMS[GroceryListItems.ITEMS.size - 1]) {
            val grocery = GroceryListItems.GroceryListDataItem(
                item.id_einkaufsliste+1, "NeueListe")
            GroceryListItems.ITEMS.add(grocery)
            insertToDatabase(grocery)
        }

        android.os.Handler().postDelayed(
            {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, ProductItemsFragment())
                    .commit()

            }, 1000
        )

    }

    /**
     * Insert a GroceryListDataItem into a database
     */
    fun insertToDatabase(item: GroceryListItems.GroceryListDataItem) {
        val url = "http://192.168.178.20:8080/smart-grocery-0.0.1-SNAPSHOT/einkaufslisten/$body/add/${item.name_einkaufsliste} ${item.id_einkaufsliste}"

        val request = Request.Builder().url(url).build()

        val  client = OkHttpClient()

        // Make request on REST API
        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                val serverResponse = response.body()?.string()

                if (serverResponse.equals("saved")){
                    println("List was saved in Database")
                }

            }

            override fun onFailure(call: Call, e: IOException) {
                println("Failed to execute request")
            }
        })
    }



    override fun onProductListFragmentInteraction(item: ProductRepository.ProductDataItem?) {
        Toast.makeText(baseContext, "Toast", Toast.LENGTH_LONG).show()
    }

}
