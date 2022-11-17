package com.example.pdfreaderrr.adaptors

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.pdfreaderrr.R
import com.example.pdfreaderrr.ui.fragments.OnboardingFragment


class OnboardingViewPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val context: Context
) :
    FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> OnboardingFragment.newInstance(
                context.resources.getString(R.string.title_onboarding_1),
                R.drawable.ic_undraw_file_searching_re_3evy
            )
            1 -> OnboardingFragment.newInstance(
                context.resources.getString(R.string.title_onboarding_2),
                R.drawable.ic_undraw_connecting_teams_re_hno7
            )
            else -> OnboardingFragment.newInstance(
                context.resources.getString(R.string.title_onboarding_3),
                R.drawable.ic_undraw_file_searching_re_3evy
            )
        }
    }

    override fun getItemCount(): Int {
        return 3
    }
}