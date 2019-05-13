package com.alexvanyo.sportsfeed

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : DaggerAppCompatActivity(), Toolbar.OnMenuItemClickListener {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        // Setup navigation
        navController = findNavController(R.id.container)
        val appBarConfiguration = AppBarConfiguration(navController.graph)

        // Setup the toolbar
        toolbar.setOnMenuItemClickListener(this)
        toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean = true.also { menuInflater.inflate(R.menu.toolbar, menu) }

    override fun onMenuItemClick(item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_settings -> true.also { navController.navigate(MainXmlDirections.actionGlobalMainSettingsFragment()) }
        else -> false
    }
}
