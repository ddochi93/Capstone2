package com.example.harusikdan.feature.takemenu

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.harusikdan.databinding.FragmentCaptureBinding
import com.example.harusikdan.feature.foodcapture.FoodCaptureActivity

class CaptureFragment : Fragment() {
    private lateinit var captureBinding: FragmentCaptureBinding

    companion object {
        fun newInstance() = CaptureFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setUpDataBinding(inflater, container)
        return captureBinding.root
    }

    private fun setUpDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        captureBinding = FragmentCaptureBinding.inflate(inflater, container, false)
        captureBinding.fragment = this
    }

    fun foodbuttonClicked() {
        startActivity(Intent(context, FoodCaptureActivity::class.java))
    }

    fun menubuttonClicked() {
        startActivity(Intent(context, MenuCaptureActivity::class.java))
    }
}