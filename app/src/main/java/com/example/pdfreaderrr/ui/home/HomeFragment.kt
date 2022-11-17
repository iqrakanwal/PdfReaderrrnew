package com.example.pdfreaderrr.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pdfreaderrr.PdfModel
import com.example.pdfreaderrr.R
import com.example.pdfreaderrr.adaptors.VideosAdaptor
import com.example.pdfreaderrr.dialogs.SortingDialog
import com.example.pdfreaderrr.interfaces.PdfClickedListener
import com.example.pdfreaderrr.interfaces.SortingListeners
import com.example.pdfreaderrr.ui.PdfShowingScreen
import com.example.pdfreaderrr.utills.PreferencesUtility
import com.example.pdfreaderrr.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class HomeFragment : Fragment(), SortingListeners {
    var videosAdaptor: VideosAdaptor? = null
    lateinit var  layoutManager: LinearLayoutManager
    private val model: MainViewModel by sharedViewModel()
    private var array:ArrayList<PdfModel> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

Log.e("ghhgh","true")


            if (PreferencesUtility.getInstance(activity).isAlbumsInGrid()) {
                val gridManager = GridLayoutManager(activity, 2)
                videos.setLayoutManager(
                    gridManager
                )
                array.addAll(it)
                videosAdaptor = VideosAdaptor(requireContext(), array, object:PdfClickedListener{
                    override fun onPdfCLicked(uri: Uri) {
                        val intent = Intent(requireContext(), PdfShowingScreen::class.java)
                        intent.putExtra("Character", uri.toString())
                        startActivity(intent)
                    }

                })


            } else {
                layoutManager= LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)

                val dividerItemDecoration = DividerItemDecoration(
                    videos.getContext(),
                    layoutManager.getOrientation()
                )
                videos.addItemDecoration(dividerItemDecoration)
                videos.setLayoutManager(
                    layoutManager
                )

                array.addAll(it)
                videosAdaptor = VideosAdaptor(requireContext(), array, object:PdfClickedListener{
                    override fun onPdfCLicked(uri: Uri) {
                        val intent = Intent(requireContext(), PdfShowingScreen::class.java)
                        intent.putExtra("Character", uri.toString())
                        startActivity(intent)
                    }

                })


            }
           // videos.setAdapter(videosAdaptor)
          //  videosAdaptor?.notifyDataSetChanged()

            videos.adapter=videosAdaptor
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

}