package com.codemitry.wearer.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.codemitry.wearer.R
import com.codemitry.wearer.databinding.ActivityMainMenuBinding

class MainMenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainMenuBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        NavigationUI.setupWithNavController(
            binding.bottomMenu,
            (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
            )
    }


    companion object {
        fun start(context: Context) {
            val intent = Intent(context, MainMenuActivity::class.java)
            ContextCompat.startActivity(context, intent, null)
        }
    }
}