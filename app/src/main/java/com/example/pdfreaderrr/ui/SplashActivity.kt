package com.example.pdfreaderrr.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.pdfreaderrr.MainActivity
import com.example.pdfreaderrr.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            if (getSharedPreferences("first_time", MODE_PRIVATE).getBoolean("first", false)) {
                startActivity(Intent(this, OnBoardingScreen::class.java))

            } else {
                startActivity(Intent(this, GetReadyActivity::class.java))
            }
            finish()
        }, 5000)


    }
}