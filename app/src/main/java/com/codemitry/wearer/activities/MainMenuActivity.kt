package com.codemitry.wearer.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.commit
import com.codemitry.wearer.R
import com.codemitry.wearer.databinding.ActivityMainMenuBinding

class MainMenuActivity : AppCompatActivity() {

    private val defaultFragment = ClothesTypesMenuFragment::class
    private lateinit var binding: ActivityMainMenuBinding
    private var activeFragment = defaultFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(R.id.container, activeFragment.java, null, activeFragment.simpleName)
            }
        }
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, MainMenuActivity::class.java)
            ContextCompat.startActivity(context, intent, null)
        }
    }
}