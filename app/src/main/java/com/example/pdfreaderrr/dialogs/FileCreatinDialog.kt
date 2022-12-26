package com.example.pdfreaderrr.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.example.pdfreaderrr.R
import com.example.pdfreaderrr.interfaces.FileCreationInterface
import kotlinx.android.synthetic.main.file_creation_dialog.*

class FileCreatinDialog(var fileCreationInterface: FileCreationInterface): DialogFragment() {



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog!!.window?.setBackgroundDrawableResource(R.drawable.round_corners)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        var viewRateUsDialog = inflater.inflate(R.layout.file_creation_dialog, container, false)
        return viewRateUsDialog
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.filename_et.text

        this.dismiss.setOnClickListener {
            fileCreationInterface.close()
            dismiss()
        }
        this.createfile.setOnClickListener {
            if(!this.filename_et.text.isEmpty()){
                fileCreationInterface.create(this.filename_et.text.toString())
            }
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