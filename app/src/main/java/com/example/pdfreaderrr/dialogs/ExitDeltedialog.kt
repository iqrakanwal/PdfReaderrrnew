package com.example.pdfreaderrr.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import androidx.fragment.app.DialogFragment
import com.example.pdfreaderrr.R
import com.example.pdfreaderrr.interfaces.ExitDialogCallbacks
import kotlinx.android.synthetic.main.custom_exit_dialog.*

class ExitDeltedialog(var exitDialogCallbacks: ExitDialogCallbacks) : DialogFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val animation: Animation =
//            AlphaAnimation(
//                1.0.toFloat(),
//                0.0.toFloat()
//            ) //to change visibility from visible to invisible
//        animation.duration = 1000 //1 second duration for each animation cycle
//        animation.interpolator = LinearInterpolator()
//        animation.repeatCount = Animation.INFINITE //repeating indefinitely
//        animation.repeatMode = Animation.REVERSE //animation will start from end point once ended.
//        arrow.startAnimation(animation) //to start animation
//        arrowpart.startAnimation(animation)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        exit.setOnClickListener {
            exitDialogCallbacks.exit()
            dismiss()
        }

        later.setOnClickListener {
            exitDialogCallbacks.later()
            dismiss()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.custom_exit_dialog, container, false)
        getDialog()?.getWindow()?.setBackgroundDrawableResource(android.R.color.transparent);
        //  setBackgroundDrawableResource(android.R.color.transparent)
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        );
        return view
    }
}