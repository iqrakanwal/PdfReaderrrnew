package com.example.pdfreaderrr.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.print.PrintDocumentAdapter
import android.print.PrintManager
import android.util.Log
import android.view.*
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import com.example.pdfreaderrr.BuildConfig
import com.example.pdfreaderrr.R
import com.example.pdfreaderrr.dialogs.GotoDailog
import com.example.pdfreaderrr.interfaces.ButtonClick
import com.example.pdfreaderrr.interfaces.PdfClickedListener
import com.example.pdfreaderrr.viewmodels.MainViewModel
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_pdf_showing_screen.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.util.*

class PdfShowingScreen : AppCompatActivity() , OnPageChangeListener, OnLoadCompleteListener,
    OnPageErrorListener, ButtonClick, PdfClickedListener {
    private var initialLayoutComplete = false
    lateinit var ads_container_layout: LinearLayout
    lateinit var ads_place_holder: LinearLayout
    lateinit var loading_layout: FrameLayout
    private val mainViewModel: MainViewModel by viewModel()
    private var uri: Uri? = null
    private var horzontalflag = 0
    private var defaultPagenum = 0
    private var facebookads = false
    var file: File? = null
    private var nighflag = 0
    private var ontouch = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf_showing_screen)
        if (Build.VERSION.SDK_INT >= 21) {
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.decorView.systemUiVisibility =View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = this.resources.getColor(R.color.white)
        }
        setContentView(R.layout.activity_pdf_showing_screen)
        setSupportActionBar(toobar);
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
        val sports = intent.getStringExtra("Character")
        file = File(sports!!)
        uri = Uri.fromFile(file)
        toobar.title = file!!.getName()
        lifecycleScope.launch {
            mainViewModel.getvalue()
        }








        pdfview.setOnClickListener {
            if (ontouch == 0) {
                toobar.visibility = View.GONE
                //   menu_yellow.visibility = View.GONE
                ontouch = 1
            } else if (ontouch == 1) {
                toobar.visibility = View.VISIBLE
                //  menu_yellow.visibility = View.VISIBLE
                ontouch = 0
            }
        }

        lifecycleScope.launch {
            loadPdf()
        }




    }

    fun shareFile(file: File?) {
        val uri = FileProvider.getUriForFile(
            Objects.requireNonNull(getApplicationContext()),
            BuildConfig.APPLICATION_ID + ".provider", file!!
        );
        val uris = ArrayList<Uri>()
        uris.add(uri)
        shareFile(uris)
    }

    /**
     * Emails the desired PDF using application of choice by user
     * @param uris - list of uris to be shared
     */
    private fun shareFile(uris: ArrayList<Uri>) {
        val intent = Intent()
        intent.action = Intent.ACTION_SEND_MULTIPLE
        intent.putExtra(
            Intent.EXTRA_TEXT,
            this!!.getString(R.string.i_have_attached_pdfs_to_this_message)
        )
        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.type = this!!.getString(R.string.pdf_type)
        this!!.startActivity(
            Intent.createChooser(
                intent,
                this!!.resources.getString(R.string.share_chooser)
            )
        )
    }


    suspend fun loadPdf() {
        pdfview.fromUri(uri)
            .defaultPage(defaultPagenum)
            .onPageChange(this)
            .enableAnnotationRendering(true)
            .onLoad(this)
            //  .scrollHandle(DefaultScrollHandle(this))
            .spacing(10).nightMode(mainViewModel.getNightMode.first())
            .onPageError(this)
            .swipeHorizontal(mainViewModel.getVerticalView.first())
            .load();


    }


    override fun onPageChanged(page: Int, pageCount: Int) {
        //   pageNumber = page;
        //    setTitle(String.format("%s %s / %s", pdfFileName,  pageCount));

        currentpage.text = "${page.plus(1)} / "
        totalpage.text = pageCount.toString()

    }

    override fun loadComplete(nbPages: Int) {

    }

    override fun onPageError(page: Int, t: Throwable?) {

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.pdf_remaining_option, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        return when (id) {

            R.id.share -> {
                /*        val fm: FragmentManager = supportFragmentManager
                        val editNameDialogFragment: RemainingOptions = RemainingOptions()
                        editNameDialogFragment.show(fm, "RemainingOptions")*/

                val bottomSheetDialog = BottomSheetDialog(this)
                bottomSheetDialog.setContentView(R.layout.bottom_sheet_options)

                val print = bottomSheetDialog.findViewById<LinearLayoutCompat>(R.id.print)

                val gotopage = bottomSheetDialog.findViewById<LinearLayoutCompat>(R.id.gotopage)
                val allignemtn =
                    bottomSheetDialog.findViewById<LinearLayoutCompat>(R.id.greyscale)
                val share = bottomSheetDialog.findViewById<LinearLayoutCompat>(R.id.share)
                val switchgrey = bottomSheetDialog.findViewById<SwitchCompat>(R.id.grey)
                print?.setOnClickListener {
                    printFile(File(uri.toString()))
                    bottomSheetDialog.dismiss()


                }
                gotopage?.setOnClickListener {
                    val fm: FragmentManager = supportFragmentManager
                    val editNameDialogFragment: GotoDailog =
                        GotoDailog {
                            defaultPagenum = it - 1
                            lifecycleScope.launch {
                                loadPdf()
                            }
                            defaultPagenum = 0
                        }
                    editNameDialogFragment.show(fm, "about")
                    bottomSheetDialog.dismiss()


                }
                share?.setOnClickListener {
                    shareFile(
                        file
                    )


                }
                lifecycleScope.launch {
                    if (mainViewModel.getNightMode.first()) {
                        switchgrey?.setChecked(true)

                    } else {
                        switchgrey?.setChecked(false)
                    }
                }





                switchgrey?.setOnCheckedChangeListener { buttonView, isChecked ->
                    if (isChecked) {
                        mainViewModel.saveToNightMode(true)
                        lifecycleScope.launch {
                            delay(200)
                            loadPdf()
                        }
                    } else {
                        mainViewModel.saveToNightMode(false)
                        lifecycleScope.launch {
                            delay(200)
                            loadPdf()
                        }
                    }
                }
                allignemtn?.setOnClickListener {

                    if (horzontalflag == 0) {
                        mainViewModel.saveView(true)
                        lifecycleScope.launch {
                            delay(1000)
                            loadPdf()
                        }
                        horzontalflag = 1
                        //   fab12.labelText = getString(R.string.verticalview)


                    } else if (horzontalflag == 1) {
                        mainViewModel.saveView(false)
                        lifecycleScope.launch {
                            delay(1000)
                            loadPdf()
                        }
                        horzontalflag = 0
                        /// fab12.labelText = getString(R.string.hori)


                    }

                }


                bottomSheetDialog.show()
                true
            }
            R.id.delete ->{

                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    fun uriFromFile(context: Context, file: File): Uri {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return FileProvider.getUriForFile(
                context,
                BuildConfig.APPLICATION_ID + ".provider",
                file
            )
        } else {
            return Uri.fromFile(file)
        }
    }

/*
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId){
            case R.id.item1:
            // what you want to do with first button
            break;
            case .....
            break;
        }
        return true;
    }*/


    /*   @Override
       public boolean onOptionsItemSelected(MenuItem menuItem) {
           if (menuItem.getItemId() = = android.R.id.home) {
               finish();
           }
           return super.onOptionsItemSelected(menuItem);
       }
   */


/*

    private fun loadBanner() {
        adView.adUnitId = resources.getString(R.string.Banner)
        adView.adSize = adSize
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }
*/


    /*   override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
           inflater.inflate(R.menu.ui_menu, menu)

           // Set the item state
           lifecycleScope.launch {
               val isChecked = mainViewModel.getUIMode.first()
               val item = menu.findItem(R.id.action_night_mode)
               item.isChecked = isChecked
               setUIMode(item, isChecked)
           }
       }

   */


    private fun setUIMode(item: MenuItem, isChecked: Boolean) {
        if (isChecked) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            mainViewModel.saveToDataStore(true)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            mainViewModel.saveToDataStore(false)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
        Toast.makeText(this, "dsf", Toast.LENGTH_SHORT).show()


    }


  /*  private fun adsConfig() {
        let { i ->
            fbBannerAds = FbBannerAds(i)
            admobBannerAds = AdmobBannerAds(i)
            when (prioritySmallBanner) {
                1 -> {
                    Log.d(AD_TAG, "Call FB Banner")
                    fbBannerAds.loadBannerAds(ads_container_layout,
                        ads_place_holder,
                        loading_layout,
                        getString(R.string.facebook_banner),
                        smallBannerActive,
                        false,
                        isInternetConnected(i),
                        object : FbBannerCallBack {
                            override fun onError(adError: String) {}

                            override fun onAdLoaded() {}

                            override fun onAdClicked() {}

                            override fun onLoggingImpression() {}

                        })
                }
                2 -> {
                    Log.d(AD_TAG, "Call Admob Banner")
                    admobBannerAds.loadBannerAds(ads_container_layout,
                        ads_place_holder,
                        loading_layout,
                        getString(R.string.Banner),
                        smallBannerActive,
                        false,
                        isInternetConnected(i),
                        object : BannerCallBack {
                            override fun onAdFailedToLoad(adError: String) {
                            }

                            override fun onAdLoaded() {

                                Log.e("load", "load")
                                ads_container_layout.visibility = View.VISIBLE

                            }

                            override fun onAdImpression() {
                            }

                        })

                }
                else -> {
                    ads_container_layout.visibility = View.GONE
                }
            }
        }

    }
*/
    fun printFile(file: File) {



/*
        val mPrintDocumentAdapter: PrintDocumentAdapter = PrintDocumentAdapterHelper(file)
        val printManager = getSystemService(PRINT_SERVICE) as PrintManager
        val jobName: String = getString(R.string.app_name).toString() + " Document"
        if (printManager != null) {
            printManager.print(jobName, mPrintDocumentAdapter, null)

        }*/

    }

    override fun clicked() {




    }

    override fun onPdfCLicked(uri: Uri) {
    }



}