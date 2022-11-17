package com.example.pdfreaderrr.ui

import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.pdfreaderrr.R


class BrowsePictureActivity : AppCompatActivity() {
    private val SELECT_PICTURE = 1
    private  val PHOTO_PICKER_REQUEST_CODE = 36

    private var selectedImagePath: String? = null
    private lateinit var but: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browse_picture)
        but = findViewById(R.id.button)
        but.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE)
           // startImageSelection()

          //  val intent = Intent()
          //  intent.type = "image/*"
            //intent.action = Intent.ACTION_GET_CONTENT
            //startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE )

        }
    }


    private fun startImageSelection() {

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == SELECT_PICTURE) {
            if (resultCode == RESULT_OK) {
                //data.getParcelableArrayExtra(name);
                //If Single image selected then it will fetch from Gallery
                if (data?.data != null) {
                    val mImageUri = data?.data
                } else {
                    if (data?.clipData != null) {
                        val mClipData = data?.clipData
                        val mArrayUri = ArrayList<Uri>()
                        for (i in 0 until mClipData!!.itemCount) {
                            val item = mClipData.getItemAt(i)
                            val uri = item.uri

                            mArrayUri.add(uri)
                            Log.v("LOG_TAG", "Selected Images " + uri)

                        }
                        Log.v("LOG_TAG", "Selected Images" + mArrayUri.size)
                    }
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

  /*  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                val selectedImageUri = data?.data
                selectedImagePath = getPath(selectedImageUri)
            }
        }
    }*/





    /**
     * helper to retrieve the path of an image URI
     */
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
}