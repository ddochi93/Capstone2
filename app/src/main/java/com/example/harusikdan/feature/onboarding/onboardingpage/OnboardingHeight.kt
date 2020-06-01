package com.example.harusikdan.feature.onboarding.onboardingpage

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.harusikdan.R
import com.example.harusikdan.data.entity.Person
import com.example.harusikdan.databinding.FragmentOnboardingHeightBinding
import com.example.harusikdan.databinding.FragmentOnboardingWeightBinding

class OnboardingHeight : Fragment() {
    private lateinit var onboardingHeightBinding: FragmentOnboardingHeightBinding

    companion object {
        @JvmStatic
        fun newInstance() = OnboardingHeight()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setUpDataBinding(inflater, container)
        initView()
        return onboardingHeightBinding.root
    }

    private fun setUpDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        onboardingHeightBinding = FragmentOnboardingHeightBinding.inflate(inflater, container, false)
        onboardingHeightBinding.fragment = this
    }

    private fun initView() {
        //val person: Person = Person
        onboardingHeightBinding.nPicker.minValue = 100
        onboardingHeightBinding.nPicker.maxValue = 200
        onboardingHeightBinding.nPicker.value = 170
        onboardingHeightBinding.nPicker.setOnValueChangedListener { picker, oldVal, newVal ->
            onboardingHeightBinding.textSend.text = picker.value.toString()
            val height = onboardingHeightBinding.textSend.text.toString()
            Person.height = height.toDouble()
        }
    }
}
