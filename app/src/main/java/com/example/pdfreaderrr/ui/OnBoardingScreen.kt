package com.example.pdfreaderrr.ui

import android.content.ContentValues
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.appcompat.widget.Toolbar
import androidx.viewpager2.widget.ViewPager2
import com.example.pdfreaderrr.MainActivity
import com.example.pdfreaderrr.R
import com.example.pdfreaderrr.adaptors.OnboardingViewPagerAdapter
import com.example.pdfreaderrr.utills.Animatoo
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_on_boarding_screen.*

class OnBoardingScreen : AppCompatActivity() {
    private var toolbar: Toolbar? = null
    private lateinit var mViewPager: ViewPager2
    private lateinit var textSkip: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= 21) {
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = this.resources.getColor(R.color.purple_200)
        }
        setContentView(R.layout.activity_on_boarding_screen)
        toolbar = findViewById(R.id.toolbar2)
        toolbar?.title = ""
/*        val workbook = Workbook()
        workbook.getWorksheets().get(0).getCells().get("A1").putValue("Hello World!");
        workbook.save("Excel.xlsx");*/
       /* setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)*/
        mViewPager = viewPager
        mViewPager.adapter = OnboardingViewPagerAdapter(this, this)
        TabLayoutMediator(pageIndicator, mViewPager) { _, _ -> }.attach()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            mViewPager.setOnScrollChangeListener(object:ViewPager2.OnPageChangeCallback(){
//
//
//            })
        }
        textSkip = findViewById(R.id.text_skip)
        textSkip.setOnClickListener {
            finish()
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            Animatoo.animateFade(this)
        }

        val btnNextStep: LinearLayoutCompat = findViewById(R.id.btn_next_step)
        viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }
        })
   /*     val resolver =contentResolver
        val values = ContentValues()
// save to a folder
        values.put(MediaStore.MediaColumns.DISPLAY_NAME, "outputFilename")
        values.put(MediaStore.MediaColumns.MIME_TYPE, "application/txt")
        values.put(MediaStore.MediaColumns.TITLE, "alkjlkj")
        values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS + "/" + "outputDirectory")
        val uri = resolver.insert(MediaStore.Files.getContentUri("external"), values)
// You can use this outputStream to write whatever file you want:
        val outputStream = resolver.openOutputStream(uri!!)*/
        btnNextStep.setOnClickListener {
            if (getItem() > mViewPager.childCount) {
                finish()
                val intent =
                    Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                Animatoo.animateFade(this)

                // Animatoo.animateSlideLeft(this)
            } else {
                mViewPager.setCurrentItem(getItem() + 1, true)
            }
        }


    }

    private fun getItem(): Int {
        return mViewPager.currentItem
    }


 /*   override fun onSupportNavigateUp(): Boolean {
        //onBackPressed()
        val intent =
            Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
        return true
    }*/

}
