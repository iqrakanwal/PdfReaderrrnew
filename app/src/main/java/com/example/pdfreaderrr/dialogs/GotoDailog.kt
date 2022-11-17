package com.example.pdfreaderrr.dialogs
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.pdfreaderrr.R
import kotlinx.android.synthetic.main.goto_page.*
import kotlinx.android.synthetic.main.permission_dialog.*

class GotoDailog(var callbackMethod: (Int) -> Unit) : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog!!.window?.setBackgroundDrawableResource(R.drawable.round_corners)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        var viewRateUsDialog = inflater.inflate(R.layout.goto_page, container, false)
        return viewRateUsDialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // this.pagenum.text
        this.go.setOnClickListener {
            if (!this.pagenum.text.equals("")) {
                try {

                    callbackMethod(Integer.parseInt(pagenum.getText().toString()))
                } catch (ex: Exception) {

                }

            } else {
                // Toast.makeText(this, "blank", Toast.LENGTH_SHORT).

                dismiss()
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