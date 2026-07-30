package com.douglasrondini.drive_20_android

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.douglasrondini.drive_20_android.ui.activities.AccountActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Simples redirecionamento para a AccountActivity (Splash/Login)
        startActivity(Intent(this, AccountActivity::class.java))
        finish()
    }
}