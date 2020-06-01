package com.example.harusikdan.feature.onboarding.onboardingpage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.harusikdan.R
import com.example.harusikdan.data.entity.Person
import com.example.harusikdan.databinding.FragmentOnboardingGenderBinding


class OnboardingGender : Fragment() {
    private lateinit var onboardingGenderBinding: FragmentOnboardingGenderBinding

    companion object {
        @JvmStatic
        fun newInstance() = OnboardingGender()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setUpDataBinding(inflater, container)
        initView()
        setRadioButtonListener()
        return onboardingGenderBinding.root
    }

    private fun setUpDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        onboardingGenderBinding = FragmentOnboardingGenderBinding.inflate(inflater, container, false)
    }

    private fun initView() {

    }

    private fun setRadioButtonListener() {
        onboardingGenderBinding.male.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                buttonView.background = ContextCompat.getDrawable(requireContext(), R.drawable.male_clicked)
                Person.gender = 0
            } else {
                buttonView.background = ContextCompat.getDrawable(requireContext(), R.drawable.male)
            }
        }

        onboardingGenderBinding.female.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                buttonView.background = ContextCompat.getDrawable(requireContext(), R.drawable.female_clicked)
                Person.gender = 1
            } else {
                buttonView.background = ContextCompat.getDrawable(requireContext(), R.drawable.female)
            }
        }
    }
}