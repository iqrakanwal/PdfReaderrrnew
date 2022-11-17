package com.example.pdfreaderrr.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.example.pdfreaderrr.MainActivity
import com.example.pdfreaderrr.R
import kotlinx.android.synthetic.main.activity_get_ready.*

class GetReadyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_ready)
        getstarted.setOnClickListener {

            nextActivity()
        }

        Handler().postDelayed(
            {
                getstarted.visibility = View.VISIBLE
                spin_kit.visibility = View.GONE
            }, 5000)
        /* if (mInterstitialAd != null) {

             } else {
                 (activity as StartInfoActivity).nextActivity()
             }*/
    }

    fun nextActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}