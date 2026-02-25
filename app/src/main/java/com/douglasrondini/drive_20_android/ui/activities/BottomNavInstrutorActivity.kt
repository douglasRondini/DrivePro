package com.douglasrondini.drive_20_android.ui.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.douglasrondini.drive_20_android.R
import com.douglasrondini.drive_20_android.databinding.ActivityBottomNavInstrutorBinding

class BottomNavInstrutorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBottomNavInstrutorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityBottomNavInstrutorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment_HomeInstrutor)
        binding.bottomNavigation.setupWithNavController(navController)
    }
}