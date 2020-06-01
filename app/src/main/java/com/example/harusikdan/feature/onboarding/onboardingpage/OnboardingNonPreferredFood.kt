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
import com.example.harusikdan.databinding.FragmentOnboardingNonPreferredFoodBinding
import com.example.harusikdan.databinding.FragmentOnboardingPreferredFoodBinding


class OnboardingNonPreferredFood : Fragment() {
    private lateinit var onboardingNonPreferredFoodBinding: FragmentOnboardingNonPreferredFoodBinding
    private var nonPreferredFoodSet: HashSet<String> = HashSet()

    companion object {
        @JvmStatic
        fun newInstance() = OnboardingNonPreferredFood()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setUpDataBinding(inflater, container)
        setToggleButtonListener()
        return onboardingNonPreferredFoodBinding.root
    }

    private fun setUpDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        onboardingNonPreferredFoodBinding = FragmentOnboardingNonPreferredFoodBinding.inflate(inflater, container, false)
    }

    private fun setToggleButtonListener() {
        onboardingNonPreferredFoodBinding.korean.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                addNonPreferredFood(buttonView, buttonView.text.toString().trim())
            } else {
                removeNonPreferredFood(buttonView, buttonView.text.toString().trim())
            }
        }

        onboardingNonPreferredFoodBinding.chinese.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                addNonPreferredFood(buttonView, buttonView.text.toString().trim())
            } else {
                removeNonPreferredFood(buttonView, buttonView.text.toString().trim())
            }
        }

        onboardingNonPreferredFoodBinding.western.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                addNonPreferredFood(buttonView, buttonView.text.toString().trim())
            } else {
                removeNonPreferredFood(buttonView, buttonView.text.toString().trim())
            }
        }

        onboardingNonPreferredFoodBinding.japanese.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                addNonPreferredFood(buttonView, buttonView.text.toString().trim())
            } else {
                removeNonPreferredFood(buttonView, buttonView.text.toString().trim())
            }
        }

        onboardingNonPreferredFoodBinding.bunsik.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                addNonPreferredFood(buttonView, buttonView.text.toString().trim())
            } else {
                removeNonPreferredFood(buttonView, buttonView.text.toString().trim())
            }
        }

        onboardingNonPreferredFoodBinding.fastfood.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                addNonPreferredFood(buttonView, buttonView.text.toString().trim())
            } else {
                removeNonPreferredFood(buttonView, buttonView.text.toString().trim())
            }
        }

    }

    private fun addNonPreferredFood(buttonView: CompoundButton, foodName: String) {
        buttonView.background = ContextCompat.getDrawable(requireContext(), R.drawable.button_pressed_background)
        buttonView.setTextColor(ContextCompat.getColor(requireContext(), R.color.orgDefault))
        nonPreferredFoodSet.add(foodName)

        Person.nonPreferredList.clear()
        val nonPreferredFoodList = ArrayList(nonPreferredFoodSet)
        nonPreferredFoodList.map { foodName ->
            Person.nonPreferredList.add(foodName)
        }
    }

    private fun removeNonPreferredFood(buttonView: CompoundButton, foodName: String) {
        buttonView.background = ContextCompat.getDrawable(requireContext(), R.drawable.button_default_background)
        buttonView.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey4))
        nonPreferredFoodSet.remove(foodName)

        Person.nonPreferredList.clear()
        val nonPreferredFoodList = ArrayList(nonPreferredFoodSet)
        nonPreferredFoodList.map { foodName ->
            Person.nonPreferredList.add(foodName)
        }
    }

}