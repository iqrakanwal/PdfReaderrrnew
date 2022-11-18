package com.example.pdfreaderrr

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.database.getLongOrNull
import androidx.core.database.getStringOrNull
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.pdfreaderrr.dialogs.*
import com.example.pdfreaderrr.interfaces.ButtonClick
import com.example.pdfreaderrr.interfaces.ExitDialogCallbacks
import com.example.pdfreaderrr.ui.SettingConActivity
import com.example.pdfreaderrr.utills.FileTypes
import com.example.pdfreaderrr.utills.toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.android.material.navigation.NavigationView.*
import com.permissionx.guolindev.PermissionX
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File


class MainActivity : AppCompatActivity(), ButtonClick,
    OnNavigationItemSelectedListener {
    private val SELECT_PICTURE = 1
    lateinit var drawer_layout: DrawerLayout
    val PERMISSION_REQUEST_CODE = 2296

    private var selectedImagePath: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility =View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            window.statusBarColor = Color.WHITE
        }
        setContentView(R.layout.drawerlayout)

     //   startActivity(Intent(this@MainActivity, SettingConActivity::class.java))

        drawer_layout = findViewById(R.id.drawer_layout)

        val navView: BottomNavigationView = nav_view
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        setNavigationViewListener()
        navView.setupWithNavController(navController)


/*

        if (checkPermission()) {
            val navView: BottomNavigationView = nav_view
            val navController = findNavController(R.id.nav_host_fragment_activity_main)
            setNavigationViewListener()
            navView.setupWithNavController(navController)
        } else {
            val fm: FragmentManager = supportFragmentManager
            val editNameDialogFragment: PermissionDialog =
                PermissionDialog(this)
            editNameDialogFragment.show(fm, "permission")
        }

*/




    /*    val fm: FragmentManager = supportFragmentManager
        val editNameDialogFragment: PermissionDialog = PermissionDialog(this)
        editNameDialogFragment.show(fm, "about")*/
        // setupActionBarWithNavController(navController, appbarlayotu)
        // Passing each menu ID as a set of Ids because each

        // menu should be considered as top level destinations.
        /*       val appBarConfiguration = AppBarConfiguration(
                   setOf(
                       R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
                   )
               )
               setupActionBarWithNavController(navController, appBarConfiguration)*/


        fkfk.setOnClickListener {


        //    val intent = Intent()
        //    intent.type = "image/*"
          //  intent.action = Intent.ACTION_GET_CONTENT
           // startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE)
        }

        loadAllFilesToDatabase()

    }


    // Method for opening a pdf file
    private fun viewPdf(file: String, directory: String) {
        val pdfFile = File(
            Environment.getExternalStorageDirectory().toString() + "/" + directory + "/" + file
        )
        val path = Uri.fromFile(pdfFile)

        // Setting the intent for pdf reader
        val pdfIntent = Intent(Intent.ACTION_VIEW)
        pdfIntent.setDataAndType(path, "application/pdf")
        pdfIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        try {
            startActivity(pdfIntent)
        } catch (e: ActivityNotFoundException) {
        }
    }
    private fun checkPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Environment.isExternalStorageManager()
        } else {
            val result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            val result1 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED
        }
    }
    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            try {
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                intent.addCategory("android.intent.category.DEFAULT")
                intent.data = Uri.parse(String.format("package:%s", applicationContext.packageName))
                startActivityForResult(intent, 2296)
            } catch (e: java.lang.Exception) {
                val intent = Intent()
                intent.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
                startActivityForResult(intent, 2296)
            }
        } else {
            //below android 11
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ),
                PERMISSION_REQUEST_CODE
            )
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> if (grantResults.size > 0) {
                val READ_EXTERNAL_STORAGE = grantResults[0] == PackageManager.PERMISSION_GRANTED
                val WRITE_EXTERNAL_STORAGE = grantResults[1] == PackageManager.PERMISSION_GRANTED
                if (READ_EXTERNAL_STORAGE && WRITE_EXTERNAL_STORAGE) {
                    val navView: BottomNavigationView = nav_view
                    val navController = findNavController(R.id.nav_host_fragment_activity_main)
                    setNavigationViewListener()
                    navView.setupWithNavController(navController)
                } else {

                }
            }
        }


    }

    override fun clicked() {
        this.toast("dfg")
        requestPermission()
  /*      PermissionX.init(this)
            .permissions(
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.CAMERA,
                Manifest.permission.CALL_PHONE
            ).onForwardToSettings { scope, deniedList ->
                scope.showForwardToSettingsDialog(
                    deniedList,
                    "You need to allow necessary permissions in Settings manually",
                    "OK",
                    "Cancel"
                )
            }
            .request { allGranted, grantedList, deniedList ->
                if (allGranted) {


                } else {
                    Toast.makeText(
                        this,
                        "These permissions are denied: $deniedList",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }*/

    }

    private fun setNavigationViewListener() {
        val navigationView = findViewById<View>(R.id.nav_view_drawer) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)
        navigationView.setItemIconTintList(null);
        navigationicon.setOnClickListener {
            drawer_layout.openDrawer(GravityCompat.START)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                val selectedImageUri = data?.data
                Log.e("file", "${selectedImageUri}")
            }
        }else   if (requestCode == 2296) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {
                    val navView: BottomNavigationView = nav_view
                    val navController = findNavController(R.id.nav_host_fragment_activity_main)
                    setNavigationViewListener()
                    navView.setupWithNavController(navController)

                } else {
                    Toast.makeText(this, "Allow permission for storage access!", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }


    fun getPath(uri: Uri?): String? {
        // just some safety built in
        if (uri == null) {
            // TODO perform some logging or show user feedback
            return null
        }
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor? = managedQuery(uri, projection, null, null, null)
        if (cursor != null) {
            val column_index: Int = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            val path: String = cursor.getString(column_index)
            cursor.close()
            return path
        }
        // this is our fallback here
        return uri.getPath()
    }

    /*override fun onNavigationItemSelected(item: MenuItem): Boolean {
        TODO("Not yet implemented")
    }
*/
    /*   private fun getAllMediaFilesCursor(): Cursor? {
           val projections =
               arrayOf(
                   MediaStore.Files.FileColumns._ID,
                   MediaStore.Files.FileColumns.DATA,
                   MediaStore.Files.FileColumns.DISPLAY_NAME,
                   MediaStore.Files.FileColumns.DATE_MODIFIED,
                   MediaStore.Files.FileColumns.MIME_TYPE,
                   MediaStore.Files.FileColumns.SIZE
               )

           val collection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
               MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL)
           } else {
               MediaStore.Files.getContentUri("external")
           }

           return contentResolver.query(
               collection,
               projections,
               null,
               null,
               null
           )
       }*/

    fun loadAllFilesToDatabase() {
        val cursor = getAllMediaFilesCursor()
        Log.e("ghjj", "${cursor?.count}")
        if (cursor?.moveToFirst() == true) {
            Log.e("d;lfd", "sfkjkfj")
            val idCol = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID)
            val pathCol = cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA)
            val nameCol = cursor.getColumnIndex(MediaStore.Files.FileColumns.DISPLAY_NAME)
            val dateCol = cursor.getColumnIndex(MediaStore.Files.FileColumns.DATE_MODIFIED)
            val mimeType = cursor.getColumnIndex(MediaStore.Files.FileColumns.MIME_TYPE)
            val sizeCol = cursor.getColumnIndex(MediaStore.Files.FileColumns.SIZE)

            do {
                val id = cursor.getLong(idCol)
                val path = cursor.getStringOrNull(pathCol)
                val name = cursor.getStringOrNull(nameCol)
                Log.e("name", "dggf${name}")
                val dateTime = cursor.getLongOrNull(dateCol)
                val type = cursor.getStringOrNull(mimeType)
                val size = cursor.getLongOrNull(sizeCol)
                /*    val contentUri = ContentUris.appendId(
                        MediaStore.Files.getContentUri("external").buildUpon(),
                        id
                    ).build()*/
                //val contentUri =  MediaStore.Files.getContentUri("external")
                val contentUri = MediaStore.Files.getContentUri("external")


                val media =
                    "Uri:$contentUri,\nPath:$path,\nFileName:$name,\nFileSize:$size,\nDate:$dateTime,\ntype:$type"

                Log.e("fgjfd", "${media}")

            } while (cursor.moveToNext())
        }
        cursor?.close()
    }

    /* private fun getAllMediaFilesCursor(): Cursor? {
         val uri = MediaStore.Files.getContentUri("external")
         val projection: Array<String>? = null
         val selection = (MediaStore.Files.FileColumns.MEDIA_TYPE + "=" + MediaStore.Files.FileColumns.MEDIA_TYPE_NONE)
         val selectionArgs: Array<String>? = null // there is no ? in selection so null here
         val sortOrder: String? = null // unordered

        *//* val selectionArgs =
            FileTypes.values().map { it.mimeTypes }.flatten().filterNotNull().toTypedArray()*//*
       *//* val args = selectionArgs.joinToString {
            "?"
        }*//*

   *//*     val collection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL)
        } else {
            MediaStore.Files.getContentUri("external")
        }*//*

        return contentResolver.query(uri, projection, selection, selectionArgs, sortOrder)
    }*/
    private fun getAllMediaFilesCursor(): Cursor? {

        val projections =
            arrayOf(
                MediaStore.Files.FileColumns._ID,
                MediaStore.Files.FileColumns.DATA, //TODO: Use URI instead of this.. see official docs for this field
                MediaStore.Files.FileColumns.DISPLAY_NAME,
                MediaStore.Files.FileColumns.DATE_MODIFIED,
                MediaStore.Files.FileColumns.MIME_TYPE,
                MediaStore.Files.FileColumns.SIZE
            )

        val sortBy = "${MediaStore.Files.FileColumns.DATE_MODIFIED} DESC"

        val selectionArgs =
            FileTypes.values().map { it.mimeTypes }.flatten().filterNotNull().toTypedArray()

        val args = selectionArgs.joinToString {
            "?"
        }

        val selection =
            MediaStore.Files.FileColumns.MIME_TYPE + " IN (" + args + ")"

        val collection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL)
        } else {
            MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL)
        }

        return contentResolver.query(
            collection,
            projections,
            selection,
            selectionArgs,
            sortBy
        )
    }


    override fun onBackPressed() {
        //  val exitDeltedialog = ExitDeltedialog(this@MainActivity)


        // exitDeltedialog.show()


        ExitDeltedialog(
            object : ExitDialogCallbacks {
                override fun exit() {
                    finishAffinity()
                }

                override fun later() {


                }


            }).show(supportFragmentManager!!, "jdfsdjfn")



    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu1, menu)
        return true
    }



    private fun removeAds() {


    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.nav_about_us -> {
                drawer_layout.closeDrawer(GravityCompat.START)

                val fm: FragmentManager = supportFragmentManager
                val editNameDialogFragment: InformationDailog =
                    InformationDailog()
                editNameDialogFragment.show(fm, "InformationDailog")
                true
            }
            R.id.settings -> {
              startActivity(Intent(this@MainActivity, SettingConActivity::class.java))
                true
            }
        /*    R.id.theme -> {

                true
            }*/
            R.id.nav_share_app -> {
                true
            }
            R.id.nav_rate_us -> {
                drawer_layout.closeDrawer(GravityCompat.START)
                val fm: FragmentManager = supportFragmentManager
                val editNameDialogFragment: RankDialog =
                    RankDialog()
                editNameDialogFragment.show(fm, "RankDialog")




                true
            }
            R.id.nav_privacy -> {
                true
            }
            R.id.nav_remove_ads -> {
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.crown) {



            Toast.makeText(this, "sdfd", Toast.LENGTH_SHORT).show()


            val fm: FragmentManager = supportFragmentManager
            val editNameDialogFragment: RemoveAds =
                RemoveAds() {
                    // inAppPurchase.productPurchase()
                    //  Toast.makeText(this, "rsd", Toast.LENGTH_SHORT).show()
                }
            editNameDialogFragment.show(fm, "about")


        }


        return super.onOptionsItemSelected(item)
    }
}

