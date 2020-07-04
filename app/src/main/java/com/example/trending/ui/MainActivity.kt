package com.example.trending.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.trending.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var bar: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(Layout.activity_main)
        setUpBottomBar()
    }

    private fun setUpBottomBar() {
        bar = findViewById(Id.bottomBar)
        val nav = supportFragmentManager.findFragmentById(Id.fragment) as NavHostFragment
        NavigationUI.setupWithNavController(bar, nav.findNavController())
    }

}