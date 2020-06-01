package com.example.harusikdan.feature.onboarding.onboardingpage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.harusikdan.data.entity.Person
import com.example.harusikdan.databinding.FragmentOnboardingAgeBinding


class OnboardingAge : Fragment() {
    private lateinit var onboardingAgeBinding: FragmentOnboardingAgeBinding

    companion object {
        @JvmStatic
        fun newInstance() = OnboardingAge()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setUpDataBinding(inflater, container)
        initView()
        return onboardingAgeBinding.root
    }

    private fun setUpDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        onboardingAgeBinding = FragmentOnboardingAgeBinding.inflate(inflater, container, false)
        onboardingAgeBinding.fragment = this
    }

    private fun initView() {
        //val person: Person = Person
        onboardingAgeBinding.nPicker.minValue = 1
        onboardingAgeBinding.nPicker.maxValue = 100
        onboardingAgeBinding.nPicker.value = 25
        onboardingAgeBinding.nPicker.setOnValueChangedListener { picker, _, _ ->
            onboardingAgeBinding.textSend.text = picker.value.toString()
            val age = onboardingAgeBinding.textSend.text.toString()
            Person.age = age.toInt()
        }
    }
}