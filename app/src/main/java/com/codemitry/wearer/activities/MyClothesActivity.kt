package com.codemitry.wearer.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.codemitry.wearer.R

class MyClothesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_clothes)
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, MyClothesActivity::class.java)
            ContextCompat.startActivity(context, intent, null)
        }
    }
}