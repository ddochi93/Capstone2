package com.example.harusikdan.feature.onboarding.onboardingframe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.harusikdan.R
import com.example.harusikdan.data.entity.Person
import com.example.harusikdan.databinding.ActivityOnboardingBinding
import com.example.harusikdan.feature.onboarding.onboardingpage.*
import com.example.harusikdan.feature.tab.TabActivity
import com.example.harusikdan.utils.loadDrawable
import kotlinx.android.synthetic.main.activity_onboarding.*
import timber.log.Timber

class OnboardingActivity : AppCompatActivity() {
    private lateinit var onboardingBinding: ActivityOnboardingBinding
    private val pagerAdapter by lazy { OnboardingAdapter(supportFragmentManager, this) }
    private var isLastPage = false
    val fragments: List<Fragment> = listOf(
        OnboardingWeight.newInstance(),
        OnboardingHeight.newInstance(),
        OnboardingAge.newInstance(),
        OnboardingGender.newInstance(),
        OnboardingActive.newInstance(),
        OnboardingSickness.newInstance(),
        OnboardingPreferredFood.newInstance(),
        OnboardingNonPreferredFood.newInstance()
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpDataBinding()
        initViewPager()
    }

    private fun setUpDataBinding() {
        onboardingBinding = DataBindingUtil.setContentView(this, R.layout.activity_onboarding)
        onboardingBinding.activity = this
    }

    private fun initViewPager() {
        onboardingBinding.viewPager.adapter = pagerAdapter
        onboardingBinding.viewPager.offscreenPageLimit = 7 //조절필요

        onboardingBinding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                circle_indicator.selectDot(position)
                if(position == 7) {
                    onboardingBinding.btnNext.loadDrawable(resources.getDrawable(R.drawable.btn_start_cta, null))
                    isLastPage = true
                } else if(isLastPage) {
                    isLastPage = false
                    onboardingBinding.btnNext.loadDrawable(resources.getDrawable(R.drawable.btn_next_normal, null))
                }
            }
        } )

        onboardingBinding.circleIndicator.createDotPanel(8, R.drawable.indicator_dot_off, R.drawable.indicator_dot_on, 0)
    }

    fun onNextClickListener() {
        val position: Int = onboardingBinding.viewPager.currentItem
        if (position == 7) {
            finishOnboarding()
        }
        onboardingBinding.viewPager.setCurrentItem(position + 1, true)
    }

    override fun onBackPressed() {
        var position: Int = onboardingBinding.viewPager.currentItem
        onboardingBinding.viewPager.setCurrentItem(position - 1, true)
    }

    private fun finishOnboarding() {

        startActivity(Intent(this, TabActivity::class.java))
        finish()
    }
}