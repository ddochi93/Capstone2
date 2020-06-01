package com.example.harusikdan.feature.onboarding.onboardingpage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.core.content.ContextCompat
import com.example.harusikdan.R
import com.example.harusikdan.data.entity.Person
import com.example.harusikdan.databinding.FragmentOnboardingPreferredFoodBinding
import com.example.harusikdan.databinding.FragmentOnboardingSicknessBinding


class OnboardingPreferredFood : Fragment() {
    private lateinit var onboardingPreferredFoodBinding: FragmentOnboardingPreferredFoodBinding
    private var preferredFoodSet: HashSet<String> = HashSet()

    companion object {
        @JvmStatic
        fun newInstance() = OnboardingPreferredFood()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setUpDataBinding(inflater, container)
        setToggleButtonListener()
        return onboardingPreferredFoodBinding.root
    }

    private fun setUpDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        onboardingPreferredFoodBinding = FragmentOnboardingPreferredFoodBinding.inflate(inflater, container, false)
    }

    private fun setToggleButtonListener() {
        onboardingPreferredFoodBinding.korean.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                addPreferredFood(buttonView, buttonView.text.toString().trim())
            } else {
                removePreferredFood(buttonView, buttonView.text.toString().trim())
            }
        }

        onboardingPreferredFoodBinding.chinese.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                addPreferredFood(buttonView, buttonView.text.toString().trim())
            } else {
                removePreferredFood(buttonView, buttonView.text.toString().trim())
            }
        }

        onboardingPreferredFoodBinding.western.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                addPreferredFood(buttonView, buttonView.text.toString().trim())
            } else {
                removePreferredFood(buttonView, buttonView.text.toString().trim())
            }
        }

        onboardingPreferredFoodBinding.japanese.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                addPreferredFood(buttonView, buttonView.text.toString().trim())
            } else {
                removePreferredFood(buttonView, buttonView.text.toString().trim())
            }
        }

        onboardingPreferredFoodBinding.bunsik.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                addPreferredFood(buttonView, buttonView.text.toString().trim())
            } else {
                removePreferredFood(buttonView, buttonView.text.toString().trim())
            }
        }

        onboardingPreferredFoodBinding.fastfood.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                addPreferredFood(buttonView, buttonView.text.toString().trim())
            } else {
                removePreferredFood(buttonView, buttonView.text.toString().trim())
            }
        }

    }

    private fun addPreferredFood(buttonView: CompoundButton, foodName: String) {
        buttonView.background = ContextCompat.getDrawable(requireContext(), R.drawable.button_pressed_background)
        buttonView.setTextColor(ContextCompat.getColor(requireContext(), R.color.orgDefault))
        preferredFoodSet.add(foodName)

        Person.preferredList.clear()
        val preferredFoodList = ArrayList(preferredFoodSet)
        preferredFoodList.map { foodName ->
            Person.preferredList.add(foodName)
        }
    }

    private fun removePreferredFood(buttonView: CompoundButton, foodName: String) {
        buttonView.background = ContextCompat.getDrawable(requireContext(), R.drawable.button_default_background)
        buttonView.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey4))
        preferredFoodSet.remove(foodName)

        Person.preferredList.clear()
        val preferredFoodList = ArrayList(preferredFoodSet)
        preferredFoodList.map { foodName ->
            Person.preferredList.add(foodName)
        }
    }

}
