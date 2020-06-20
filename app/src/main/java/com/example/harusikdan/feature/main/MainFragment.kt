package com.example.harusikdan.feature.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.eroom.domain.utils.toastShort
import com.example.harusikdan.data.entity.Food
import com.example.harusikdan.databinding.FragmentMainBinding
import com.example.harusikdan.feature.foodcapture.FoodCaptureActivity


class MainFragment : Fragment(), MainContract.View {
    private lateinit var mainBinding: FragmentMainBinding
    private lateinit var presenter: MainPresenter

    //private lateinit var mFoodInfoAdapter: FoodInfoAdapter
    private var mFoodList: ArrayList<Food> = ArrayList()

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        presenter = MainPresenter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setUpDataBinding(inflater, container)
        initView()
        return mainBinding.root
    }

    private fun setUpDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        mainBinding = FragmentMainBinding.inflate(inflater, container, false)
        mainBinding.fragment = this
    }

    private fun initView() {
        mainBinding.weekCalendar.setOnDateClickListener { dateTime ->
            context?.toastShort("You select $dateTime")
        }

    }


    override fun onResume() {
        super.onResume()
    }


    fun imageClicked() {
        startActivity(Intent(context, FoodCaptureActivity::class.java))
    }

}
