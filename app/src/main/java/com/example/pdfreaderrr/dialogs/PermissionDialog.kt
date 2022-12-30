package com.example.pdfreaderrr.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.example.pdfreaderrr.R
import com.example.pdfreaderrr.interfaces.ButtonClick
import kotlinx.android.synthetic.main.permission_dialog.*

class PermissionDialog(var buttonClick: ButtonClick) : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        allow.setOnClickListener {
            buttonClick.clicked()
            dismiss()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog!!.window?.setBackgroundDrawableResource(R.drawable.round_corners)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        var viewRateUsDialog = inflater.inflate(R.layout.permission_dialog, container, false)
        return viewRateUsDialog
    }

    override fun onResume() {
        super.onResume()
        val width = (resources.displayMetrics.widthPixels * 0.90).toInt()
        val height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialog!!.window!!.setLayout(width, height)
    }

}