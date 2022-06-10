package com.android.example.tanmen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.android.example.tanmen.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavView = binding.bottomNavigation
        val navController = findNavController(R.id.nav_host_fragment)
        setupWithNavController(bottomNavView, navController)

        val fab = binding.fab
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Fabを押しました！", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }
}