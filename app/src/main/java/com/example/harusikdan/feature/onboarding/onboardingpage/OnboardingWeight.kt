package com.example.harusikdan.feature.onboarding.onboardingpage

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.harusikdan.R
import com.example.harusikdan.data.entity.Person
import com.example.harusikdan.databinding.FragmentOnboardingWeightBinding
import kotlinx.android.synthetic.main.fragment_onboarding_weight.*
import kotlin.Double


class OnboardingWeight: Fragment() {
    private lateinit var onboardingWeightBinding: FragmentOnboardingWeightBinding

    companion object {
        @JvmStatic
        fun newInstance() = OnboardingWeight()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setUpDataBinding(inflater, container)
        initView()
        return onboardingWeightBinding.root
    }

    private fun setUpDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        onboardingWeightBinding = FragmentOnboardingWeightBinding.inflate(inflater, container, false)
        onboardingWeightBinding.fragment = this
    }

    private fun initView() {
        //val person: Person = Person
        onboardingWeightBinding.nPicker.minValue = 20
        onboardingWeightBinding.nPicker.maxValue = 100
        onboardingWeightBinding.nPicker.value = 60
        onboardingWeightBinding.nPicker.setOnValueChangedListener { picker, oldVal, newVal ->
            onboardingWeightBinding.textSend.text = picker.value.toString()
            val weight = onboardingWeightBinding.textSend.text.toString()
            Person.weight = weight.toDouble()
        }
    }

}