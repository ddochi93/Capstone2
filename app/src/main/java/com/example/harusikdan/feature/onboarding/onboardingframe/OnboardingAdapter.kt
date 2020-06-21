package com.example.harusikdan.feature.onboarding.onboardingframe

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import timber.log.Timber

class OnboardingAdapter(fm: FragmentManager, private val activity: OnboardingActivity) : FragmentStatePagerAdapter(
    fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> activity.fragments[0]
            1 -> activity.fragments[1]
            2 -> activity.fragments[2]
            3 -> activity.fragments[3]
            4 -> activity.fragments[4]
            5 -> activity.fragments[5]
            else -> activity.fragments[1]
        }
    }

    override fun getCount() = 6

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        super.destroyItem(container, position, `object`)
        //Log.e("FragmentPagerAdapter", "destroyItem position : $position")
        Timber.e("destroy item Position : $position")
    }
}
