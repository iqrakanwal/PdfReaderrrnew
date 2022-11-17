package com.example.pdfreaderrr.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.example.pdfreaderrr.BaseClass.Companion.applicationContext
import com.example.pdfreaderrr.R
import kotlinx.android.synthetic.main.fragment_onboarding.view.*


class OnboardingFragment : Fragment() {
    private var title: String? = null
    private var imageResource = 0
    private lateinit var tvTitle: AppCompatTextView
    private lateinit var tvDescription: AppCompatTextView
    private lateinit var image: AppCompatImageView
    private var arrayList: ArrayList<String> = arrayListOf()
    private lateinit var header: AppCompatImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            title = requireArguments().getString(ARG_PARAM1)
            imageResource = requireArguments().getInt(ARG_PARAM3)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view:View=  inflater.inflate(R.layout.fragment_onboarding, container, false)


        val animation: Animation
        animation = AnimationUtils.loadAnimation(
            applicationContext(),
            R.anim.from_left_in
        )

        tvTitle = view.text_onboarding_title
        tvTitle.startAnimation(animation);
        image = view.image_onboarding
        header = view.header
        tvTitle.text = title
        header.setImageResource(imageResource)
        // image.setAnimation(imageResource)
        return view
    }

    companion object {
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"
        private const val ARG_PARAM3 = "param3"
        fun newInstance(
            title: String?,
            imageResource: Int
        ): OnboardingFragment {
            val fragment = OnboardingFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, title)
            args.putInt(ARG_PARAM3, imageResource)
            fragment.arguments = args
            return fragment
        }
    }
}