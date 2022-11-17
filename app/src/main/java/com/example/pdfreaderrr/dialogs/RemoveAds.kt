package com.example.pdfreaderrr.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.example.pdfreaderrr.R
import kotlinx.android.synthetic.main.remove_ads.*

class RemoveAds(
    var callbackMethod: () -> Unit,
) : DialogFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog!!.window?.setBackgroundDrawableResource(R.drawable.round_corners)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        var viewRemoveadsDialog = inflater.inflate(R.layout.remove_ads, container, false)
        return viewRemoveadsDialog

    }




    fun RemoveAds(){


    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button.setOnClickListener {
            callbackMethod()
            dismiss()

        }
    }


    override fun onResume() {
        super.onResume()

        val width = (resources.displayMetrics.widthPixels * 0.90).toInt()
        val height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialog!!.window!!.setLayout(width, height)

    }

}