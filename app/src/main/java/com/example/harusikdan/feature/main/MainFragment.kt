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
import com.example.harusikdan.data.entity.Person
import com.example.harusikdan.databinding.FragmentMainBinding
import com.example.harusikdan.feature.foodcapture.FoodCaptureActivity
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
    private val realm = Realm.getDefaultInstance()

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
            loadFromDB(foodDate.trim())
            setNutritionalInfo()
        }

        mainBinding.totalCalorieTv.text = "총 섭취량 0 / ${Person.targetCalorie.toInt()}"

    }


    override fun onResume() {
        super.onResume()
        val today = getCurrentDateTime()
        val todayString = today.toString("yyyy-MM-dd")
        requireContext().toastShort(todayString.toString().trim())
        Food.date = todayString.trim()
        loadFromDB(todayString)
        setNutritionalInfo()
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

    private fun loadFromDB(selectedDay: String) {
        val realmResult: RealmResults<FoodVO> = realm.where<FoodVO>().equalTo("date",selectedDay).findAll()
        val breakfast = realmResult.where().equalTo("mealTime","breakfast").findFirst()
        val lunch = realmResult.where().equalTo("mealTime","lunch").findFirst()
        val dinner = realmResult.where().equalTo("mealTime","dinner").findFirst()

        mainBinding.breakfastMenuName.text = breakfast?.foodName
        mainBinding.lunchMenuName.text = lunch?.foodName
        mainBinding.dinnerMenuName.text = dinner?.foodName


        if(breakfast != null) {
            mainBinding.breakfastFoodCalorie.text = "아침 ${breakfast.calorie}Kcal"
            mainBinding.breakfastNut.visibility = View.VISIBLE
            mainBinding.breakfastNut.text = "나트륨 : ${breakfast.salt} / 2000mg"
            Person.calorie += breakfast.calorie
            Person.carbohydrate += breakfast.carbohydrate
            Person.protein += breakfast.protein
            Person.fat += breakfast.fat
        } else {
            mainBinding.breakfastFoodCalorie.text = "아침 0Kcal"
            mainBinding.breakfastNut.visibility = View.INVISIBLE
        }

        if(lunch != null) {
            mainBinding.lunchFoodCalorie.text = "점심 ${lunch.calorie}Kcal"
            mainBinding.lunchNut.visibility = View.VISIBLE
            mainBinding.lunchNut.text = "나트륨 : ${lunch.salt} / 2000mg"
            Person.calorie += lunch.calorie
            Person.carbohydrate += lunch.carbohydrate
            Person.protein += lunch.protein
            Person.fat += lunch.fat
        } else {
            mainBinding.lunchFoodCalorie.text = "점심 0Kcal"
            mainBinding.lunchNut.visibility = View.INVISIBLE
        }

        if(dinner != null) {
            mainBinding.dinnerFoodCalorie.text = "저녁 ${dinner.calorie}Kcal"
            mainBinding.dinnerNut.visibility = View.VISIBLE
            mainBinding.dinnerNut.text = "나트륨 : ${dinner.salt} / 2000mg"
            Person.calorie += dinner.calorie
            Person.carbohydrate += dinner.carbohydrate
            Person.protein += dinner.protein
            Person.fat += dinner.fat
        }else {
            mainBinding.dinnerFoodCalorie.text = "저녁 0Kcal"
            mainBinding.dinnerNut.visibility = View.INVISIBLE
        }

    }

    private fun setNutritionalInfo() {
        mainBinding.totalCalorieTv.text = "총 섭취량 ${Person.calorie.toInt()} / ${Person.targetCalorie.toInt()}"
        mainBinding.totalCarbohydate.text = "${Person.carbohydrate.toInt()}/${(Person.targetCalorie/4 * 0.5).toInt()}g"
        mainBinding.totalProtein.text = "${Person.protein.toInt()}/${Person.weight}g"
        mainBinding.totalFat.text = "${Person.fat}/51.3g"


        //마지막에 0으로 세팅
        Person.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()

    }

}
