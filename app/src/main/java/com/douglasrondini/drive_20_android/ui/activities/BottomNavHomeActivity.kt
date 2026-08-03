package com.douglasrondini.drive_20_android.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.douglasrondini.drive_20_android.R
import com.douglasrondini.drive_20_android.databinding.ActivityBottomNavHomeBinding

class BottomNavHomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBottomNavHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBottomNavHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_HomeAluno) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNavigation.setupWithNavController(navController)
        
        // Evita recarregar a mesma tela se clicar no item já selecionado
        binding.bottomNavigation.setOnItemReselectedListener { /* do nothing */ }
    }
}