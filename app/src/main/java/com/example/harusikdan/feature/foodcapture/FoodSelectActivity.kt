package com.example.harusikdan.feature.foodcapture

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.eroom.domain.utils.toastShort
import com.example.harusikdan.R
import com.example.harusikdan.data.entity.Food
import com.example.harusikdan.data.entity.FoodVO
import com.example.harusikdan.databinding.ActivityFoodSelectBinding
import com.example.harusikdan.globalconst.Consts
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import org.koin.android.ext.android.get


class FoodSelectActivity : AppCompatActivity(), FoodSelectContract.View {
    private lateinit var foodSelectBinding: ActivityFoodSelectBinding
    private lateinit var presenter: FoodSelectPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPresenter()
        setUpDataBinding()
        initView()
        setRadioButtonListener()
    }

    private fun initPresenter() {
        presenter = FoodSelectPresenter(this, get())
    }

    private fun setUpDataBinding() {
        foodSelectBinding = DataBindingUtil.setContentView(this, R.layout.activity_food_select)
        foodSelectBinding.activity = this
    }


    private fun initView() {
        val foodNameList = intent.getStringArrayListExtra(Consts.SELECT_FOOD_LIST)
        foodSelectBinding.first.text = foodNameList[0]
        foodSelectBinding.second.text = foodNameList[1]
        foodSelectBinding.third.text = foodNameList[2]
        foodSelectBinding.fourth.text = foodNameList[3]
    }

    private fun setRadioButtonListener() {
        //일번음식
        foodSelectBinding.first.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                buttonView.background = ContextCompat.getDrawable(this, R.drawable.button_pressed_background)
                buttonView.setTextColor(ContextCompat.getColor(this, R.color.orgDefault))
                Food.foodName = buttonView.text.toString().trim()
                presenter.getFoodInfoByFoodName(Food.foodName.toString())
            } else {
                buttonView.background = ContextCompat.getDrawable(this, R.drawable.button_default_background)
                buttonView.setTextColor(ContextCompat.getColor(this, R.color.grey4))
            }

        }

        //이번음식
        foodSelectBinding.second.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                buttonView.background = ContextCompat.getDrawable(this, R.drawable.button_pressed_background)
                buttonView.setTextColor(ContextCompat.getColor(this, R.color.orgDefault))
                Food.foodName = buttonView.text.toString().trim()
                presenter.getFoodInfoByFoodName(Food.foodName.toString())
            } else {
                buttonView.background = ContextCompat.getDrawable(this, R.drawable.button_default_background)
                buttonView.setTextColor(ContextCompat.getColor(this, R.color.grey4))
            }
        }

        //삼번음식
        foodSelectBinding.third.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                buttonView.background = ContextCompat.getDrawable(this, R.drawable.button_pressed_background)
                buttonView.setTextColor(ContextCompat.getColor(this, R.color.orgDefault))
                Food.foodName = buttonView.text.toString().trim()
                presenter.getFoodInfoByFoodName(Food.foodName.toString())
            } else {
                buttonView.background = ContextCompat.getDrawable(this, R.drawable.button_default_background)
                buttonView.setTextColor(ContextCompat.getColor(this, R.color.grey4))
            }
        }

        //사번음식
        foodSelectBinding.fourth.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                buttonView.background = ContextCompat.getDrawable(this, R.drawable.button_pressed_background)
                buttonView.setTextColor(ContextCompat.getColor(this, R.color.orgDefault))
                Food.foodName = buttonView.text.toString().trim()
                presenter.getFoodInfoByFoodName(Food.foodName.toString())
            } else {
                buttonView.background = ContextCompat.getDrawable(this, R.drawable.button_default_background)
                buttonView.setTextColor(ContextCompat.getColor(this, R.color.grey4))
            }
        }
    }

    override fun finishView() {
        finishAffinity()
    }

}