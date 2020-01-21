package com.example.smartgrocery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.smartgrocery.grocery_list_recyclerview.ItemFragment
import com.example.smartgrocery.repositories.DummyContent
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    ItemFragment.OnListFragmentInteractionListener {

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


        val  toggle: ActionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Set start fragment
        if(savedInstanceState == null) {

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, FavouritesFragment())
                .commit()

            navigationView.setCheckedItem(R.id.nav_favourites)
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
                    .replace(R.id.fragment_container, ItemFragment())
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

    override fun onListFragmentInteraction(item: DummyContent.DummyItem?) {
        val text = "Hello toast!"
        val duration = Toast.LENGTH_SHORT

        val toast = Toast.makeText(applicationContext, text, duration)
        toast.show()

    }


}
