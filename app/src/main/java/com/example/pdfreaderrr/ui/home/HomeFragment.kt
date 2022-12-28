package com.example.pdfreaderrr.ui.home

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pdfreaderrr.PdfModel
import com.example.pdfreaderrr.R
import com.example.pdfreaderrr.adaptors.VideosAdaptor
import com.example.pdfreaderrr.dialogs.PermissionDialog
import com.example.pdfreaderrr.dialogs.SortingDialog
import com.example.pdfreaderrr.interfaces.ButtonClick
import com.example.pdfreaderrr.interfaces.OptionMenuClickListener
import com.example.pdfreaderrr.interfaces.PdfClickedListener
import com.example.pdfreaderrr.interfaces.SortingListeners
import com.example.pdfreaderrr.ui.PdfShowingScreen
import com.example.pdfreaderrr.utills.Constants
import com.example.pdfreaderrr.utills.PreferencesUtility
import com.example.pdfreaderrr.utills.toast
import com.example.pdfreaderrr.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class HomeFragment : Fragment(), SortingListeners, ButtonClick, OptionMenuClickListener {
    var videosAdaptor: VideosAdaptor? = null
    lateinit var layoutManager: LinearLayoutManager
    private val model: MainViewModel by sharedViewModel()
    private var array: ArrayList<PdfModel> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (checkPermission()) {
            setRecyclarView()
        } else {

            val fm: FragmentManager = requireActivity().supportFragmentManager
            val editNameDialogFragment: PermissionDialog =
                PermissionDialog(this)
            editNameDialogFragment.show(fm, "permission")
        }
    }


    fun setRecyclarView() {
        listview.setOnClickListener {
            if (PreferencesUtility.getInstance(context).isAlbumsInGrid) {
                PreferencesUtility.getInstance(context).setAlbumsInGrid(false)
                setLayoutManager()
            } else {
                PreferencesUtility.getInstance(context).setAlbumsInGrid(true)
                setLayoutManager()
            }
        }

        setLayoutManager()
        sortby.setOnClickListener {
            val fm: FragmentManager = requireActivity().supportFragmentManager
            val editNameDialogFragment: SortingDialog =
                SortingDialog(this)
            editNameDialogFragment.show(fm, "SortingDialog")
        }


    }


    private fun setLayoutManager() {
        model.files?.observe(requireActivity()) {
            Log.e("ghhgh", "true")
            if (PreferencesUtility.getInstance(activity).isAlbumsInGrid()) {
                val gridManager = GridLayoutManager(activity, 2)
                videos.setLayoutManager(
                    gridManager
                )
                array.clear()
                array.addAll(it)
                videosAdaptor =
                    VideosAdaptor(this, requireContext(), array, object : PdfClickedListener {
                        override fun onPdfCLicked(uri: Uri) {
                            val intent = Intent(requireContext(), PdfShowingScreen::class.java)
                            intent.putExtra("Character", uri.toString())
                            startActivity(intent)
                        }
                    })

            } else {
                layoutManager =
                    LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
                val dividerItemDecoration = DividerItemDecoration(
                    videos.getContext(),
                    layoutManager.getOrientation()
                )
                videos.addItemDecoration(dividerItemDecoration)
                videos.setLayoutManager(
                    layoutManager
                )
                array.clear()
                array.addAll(it)
                videosAdaptor =
                    VideosAdaptor(this, requireContext(), array, object : PdfClickedListener {
                        override fun onPdfCLicked(uri: Uri) {
                            val intent = Intent(requireContext(), PdfShowingScreen::class.java)
                            intent.putExtra("Character", uri.toString())
                            startActivity(intent)
                        }
                    })
            }
            /*  videos.setAdapter(videosAdaptor)
              videosAdaptor?.notifyDataSetChanged()*/
            videos.adapter = videosAdaptor
            //Log.e("jdkdjf", "${it.size}")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        var root = inflater.inflate(R.layout.fragment_home, container, false)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onDateModified() {
    }

    override fun onSize() {
    }


    private fun checkPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Environment.isExternalStorageManager()
        } else {
            val result =
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            val result1 =
                ContextCompat.checkSelfPermission(
                    requireContext(),
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
                intent.data = Uri.parse(
                    String.format(
                        "package:%s",
                        requireContext().applicationContext.packageName
                    )
                )
                startActivityForResult(intent, 2296)
            } catch (e: Exception) {
                val intent = Intent()
                intent.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
                startActivityForResult(intent, 2296)
            }
        } else {
            //below android 11
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                Constants.PERMISSION_REQUEST_CODE
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2296) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {
                    setRecyclarView()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Allow permission for storage access!",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            Constants.PERMISSION_REQUEST_CODE -> if (grantResults.size > 0) {
                val READ_EXTERNAL_STORAGE = grantResults[0] == PackageManager.PERMISSION_GRANTED
                val WRITE_EXTERNAL_STORAGE = grantResults[1] == PackageManager.PERMISSION_GRANTED
                if (READ_EXTERNAL_STORAGE && WRITE_EXTERNAL_STORAGE) {
                    // loadAllFilesToDatabase()
                    setRecyclarView()

                } else {
                    Toast.makeText(
                        requireContext(),
                        "Allow permission for storage access!",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        }
    }


    override fun clicked() {
        requestPermission()
    }


    override fun delete(uri: PdfModel) {
    }

    override fun rename(uri: PdfModel) {
    }

    override fun info(uri: PdfModel) {
    }

}