package com.example.harusikdan.feature.onboarding.onboardingpage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.harusikdan.R
import com.example.harusikdan.data.entity.Person
import com.example.harusikdan.databinding.FragmentOnboardingActiveBinding
import com.example.harusikdan.databinding.FragmentOnboardingGenderBinding


class OnboardingActive : Fragment() {
    private lateinit var onboardingActiveBinding: FragmentOnboardingActiveBinding

    companion object {
        @JvmStatic
        fun newInstance() = OnboardingActive()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setUpDataBinding(inflater, container)
        setRadioButtonListener()
        return onboardingActiveBinding.root
    }

    private fun setUpDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        onboardingActiveBinding = FragmentOnboardingActiveBinding.inflate(inflater, container, false)
    }

    private fun setRadioButtonListener() {
        //매우 활동적
        onboardingActiveBinding.veryActive.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                buttonView.background = ContextCompat.getDrawable(requireContext(), R.drawable.button_pressed_background)
                buttonView.setTextColor(ContextCompat.getColor(requireContext(), R.color.orgDefault))
                Person.activity = 40
            } else {
                buttonView.background = ContextCompat.getDrawable(requireContext(), R.drawable.button_default_background)
                buttonView.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey4))
            }
        }

        onboardingActiveBinding.active.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                buttonView.background = ContextCompat.getDrawable(requireContext(), R.drawable.button_pressed_background)
                buttonView.setTextColor(ContextCompat.getColor(requireContext(), R.color.orgDefault))
                Person.activity = 33
            } else {
                buttonView.background = ContextCompat.getDrawable(requireContext(), R.drawable.button_default_background)
                buttonView.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey4))
            }
        }

        onboardingActiveBinding.inactive.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                buttonView.background = ContextCompat.getDrawable(requireContext(), R.drawable.button_pressed_background)
                buttonView.setTextColor(ContextCompat.getColor(requireContext(), R.color.orgDefault))
                Person.activity = 25
            } else {
                buttonView.background = ContextCompat.getDrawable(requireContext(), R.drawable.button_default_background)
                buttonView.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey4))
            }
        }

        onboardingActiveBinding.veryInactive.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                buttonView.background = ContextCompat.getDrawable(requireContext(), R.drawable.button_pressed_background)
                buttonView.setTextColor(ContextCompat.getColor(requireContext(), R.color.orgDefault))
                Person.activity = 20
            } else {
                buttonView.background = ContextCompat.getDrawable(requireContext(), R.drawable.button_default_background)
                buttonView.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey4))
            }
        }
    }

}