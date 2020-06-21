package com.example.harusikdan.feature.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.eroom.domain.utils.toastShort
import com.example.harusikdan.data.entity.Food
import com.example.harusikdan.data.entity.FoodVO
import com.example.harusikdan.databinding.FragmentMainBinding
import com.example.harusikdan.feature.foodcapture.FoodCaptureActivity
import io.ktor.http.auth.HttpAuthHeader.Parameters.Realm
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList


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
            val foodDate = dateTime.toString().substring(0,10)
            context?.toastShort(foodDate)
            Food.date = foodDate.trim()
        }

    }


    override fun onResume() {
        super.onResume()
        val today = getCurrentDateTime()
        val todayString = today.toString("yyyy-MM-dd")
        requireContext().toastShort(todayString.toString().trim())
        Food.date = todayString.trim()
    }


    fun breakfastImageClicked() {
        Food.mealTime = "breakfast"
        startActivity(Intent(context, FoodCaptureActivity::class.java))
    }

    fun lunchImageClicked() {
        Food.mealTime = "lunch"
        startActivity(Intent(context, FoodCaptureActivity::class.java))
    }

    fun dinnerImageClicked() {
        Food.mealTime = "dinner"
        startActivity(Intent(context, FoodCaptureActivity::class.java))
    }

    private fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }

    fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }



    override fun onDestroy() {
        super.onDestroy()


    }

}
