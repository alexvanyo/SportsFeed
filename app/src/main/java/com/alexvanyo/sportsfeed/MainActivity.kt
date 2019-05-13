package com.alexvanyo.sportsfeed

import android.os.Bundle
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

        navController = findNavController(R.id.container)
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        toolbar.inflateMenu(R.menu.toolbar)
        toolbar.setOnMenuItemClickListener(this)
        toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    override fun onMenuItemClick(item: MenuItem): Boolean = when(item.itemId) {
        R.id.action_settings -> {
            navController.navigate(MainXmlDirections.actionGlobalMainSettingsFragment())
            true
        }
        else -> false
    }
}
