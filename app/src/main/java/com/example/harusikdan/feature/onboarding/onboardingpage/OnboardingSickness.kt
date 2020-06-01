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
import com.example.harusikdan.databinding.FragmentOnboardingSicknessBinding
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet


class OnboardingSickness : Fragment() {
    private lateinit var onboardingSicknessBinding: FragmentOnboardingSicknessBinding
    private var sicknessSet: HashSet<Int> = HashSet()

    companion object {
        @JvmStatic
        fun newInstance() = OnboardingSickness()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setUpDataBinding(inflater, container)
        setToggleButtonListener()
        return onboardingSicknessBinding.root
    }

    private fun setUpDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        onboardingSicknessBinding = FragmentOnboardingSicknessBinding.inflate(inflater, container, false)
    }

    private fun setToggleButtonListener() {
        onboardingSicknessBinding.toggleButton1.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                addSickness(buttonView, 1)
            } else {
                removeSickness(buttonView, 1)
            }
        }

        onboardingSicknessBinding.toggleButton2.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                addSickness(buttonView, 2)
            } else {
                removeSickness(buttonView, 2)
            }
        }

        onboardingSicknessBinding.toggleButton3.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                addSickness(buttonView, 3)
            } else {
                removeSickness(buttonView, 3)
            }
        }

        onboardingSicknessBinding.toggleButton4.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                addSickness(buttonView, 4)
            } else {
                removeSickness(buttonView, 4)
            }
        }

        onboardingSicknessBinding.toggleButton5.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                addSickness(buttonView, 5)
            } else {
                removeSickness(buttonView, 5)
            }
        }

        onboardingSicknessBinding.toggleButton6.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                addSickness(buttonView, 6)
            } else {
                removeSickness(buttonView, 6)
            }
        }

    }

    private fun addSickness(buttonView: CompoundButton, sicknessNo: Int) {
        buttonView.background = ContextCompat.getDrawable(requireContext(), R.drawable.button_pressed_background)
        buttonView.setTextColor(ContextCompat.getColor(requireContext(), R.color.orgDefault))
        sicknessSet.add(sicknessNo)

        Person.diseaseList.clear()
        val sicknessList = ArrayList(sicknessSet)
        sicknessList.sort()
        sicknessList.map { sicknessNo ->
            Person.diseaseList.add(sicknessNo)
        }
    }

    private fun removeSickness(buttonView: CompoundButton, sicknessNo: Int) {
        buttonView.background = ContextCompat.getDrawable(requireContext(), R.drawable.button_default_background)
        buttonView.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey4))
        sicknessSet.remove(sicknessNo)

        Person.diseaseList.clear()
        val sicknessList = ArrayList(sicknessSet)
        sicknessList.sort()
        sicknessList.map { sicknessNo ->
            Person.diseaseList.add(sicknessNo)
        }
    }

}